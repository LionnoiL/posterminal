package ua.gaponov.posterminal.devices.fiscal.vchasno;

import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.devices.fiscal.DeviceFiscalPrinter;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Fiscal;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Pay;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Receipt;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Row;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info.InfoDocument;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info.InfoOpenShift;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info.InfoReport;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info.InfoStatus;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.PayType;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.TaxGroup;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.documents.orders.OrderDetail;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static ua.gaponov.posterminal.utils.JsonUtils.GSON;

/**
 * @author Andriy Gaponov
 */
public class VchasnoFiscal implements DeviceFiscalPrinter {

    private static final Logger LOG = LoggerFactory.getLogger(VchasnoFiscal.class);
    private static final String VCHASNO_DEVICE_HOST = "http://192.168.1.111:3939/dm/execute"; //TODO ip address
    private final String deviceName;
    private final String token;

    public VchasnoFiscal(String deviceName, String token) {
        this.deviceName = deviceName;
        this.token = token;
    }

    @Override
    public boolean printOrder(Order order) {
        if (Objects.isNull(order)){
            return false;
        }

        if (!shiftIsOpen()){
            openShift();
        }

        if (!shiftIsOpen()){
            return false;
        }

        Fiscal fiscal = Fiscal.salesReceipt();
        Receipt receipt = new Receipt();
        receipt.setSum(order.getDocumentSum());

        Row[] rows = new Row[order.getDetails().size()];
        for (OrderDetail detail : order.getDetails()) {
            Row row = new Row();
            row.setName(detail.getProduct().getName());
            row.setCnt(detail.getQty());
            row.setCost(detail.getPrice());
            row.setCode(detail.getProduct().getCode());
            row.setExciseCode(detail.getExcise());
            row.setTaxGroup(TaxGroup.VAT_20);
            rows[detail.getLineNumber()-1] =row;
        }
        receipt.setRows(rows);

        Pay[] pays = new Pay[1];
        Pay pay = new Pay();
        pay.setType(PayType.CASH);
        pay.setSum(order.getDocumentSum());
        pays[0] = pay;

        receipt.setPays(pays);
        fiscal.setReceipt(receipt);
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, fiscal);

        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoDocument>>(){}.getType();
            VchasnoResponce<InfoDocument> vchasnoResponce = GSON.fromJson(sResponce, type);
            //TODO print receipt
            return vchasnoResponce.getRes()==0;
        } catch (Exception e) {
            LOG.error("Get z-report failed", e);
        }
        LOG.info("Z-report success");

        return false;
    }

    @Override
    public boolean printZReport() {
        if (shiftIsOpen()){
            VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.zReport());
            try {
                String sResponce = sendRequest(request);
                Type type = new TypeToken<VchasnoResponce<InfoReport>>(){}.getType();
                VchasnoResponce<InfoReport> vchasnoResponce = GSON.fromJson(sResponce, type);
                //TODO print report
                return vchasnoResponce.getRes()==0;
            } catch (Exception e) {
                LOG.error("Get z-report failed", e);
            }
            LOG.info("Z-report success");
        }
        return false;
    }

    @Override
    public void printXReport() {
        if (shiftIsOpen()){
            VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.xReport());
            try {
                String sResponce = sendRequest(request);
                Type type = new TypeToken<VchasnoResponce<InfoReport>>(){}.getType();
                VchasnoResponce<InfoReport> vchasnoResponce = GSON.fromJson(sResponce, type);
                //TODO print report
            } catch (Exception e) {
                LOG.error("Get x-report failed", e);
            }
        }
    }

    public boolean openShift(){
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.openShift());
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoOpenShift>>(){}.getType();
            VchasnoResponce<InfoOpenShift> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes()==0){
                LOG.info("Shift open");
                return true;
            } else {
                LOG.error("Open shift failed");
            }
        } catch (Exception e) {
            LOG.error("Open shift failed", e);
            return false;
        }
        return false;
    }

    public boolean shiftIsOpen() {
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.status());
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoStatus>>(){}.getType();
            VchasnoResponce<InfoStatus> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes()==0){
                InfoStatus info = vchasnoResponce.getInfo();
                return info.getShiftStatus()==1;
            }
        } catch (Exception e) {
            LOG.error("Get fiscal status failed", e);
            return false;
        }
        return false;
    }

    private String sendRequest(VchasnoRequest vchasnoRequest) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VCHASNO_DEVICE_HOST))
                .header("Content-type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(vchasnoRequest)))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            return "";
        }
    }
}
