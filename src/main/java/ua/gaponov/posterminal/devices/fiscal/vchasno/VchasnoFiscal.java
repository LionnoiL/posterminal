package ua.gaponov.posterminal.devices.fiscal.vchasno;

import com.google.gson.reflect.TypeToken;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.fiscal.DeviceFiscalPrinter;
import ua.gaponov.posterminal.devices.fiscal.PrintFiscalOrder;
import ua.gaponov.posterminal.devices.fiscal.PrintFiscalXReport;
import ua.gaponov.posterminal.devices.fiscal.XReportDto;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Fiscal;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Pay;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Receipt;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Row;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info.*;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.PayType;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.TaxGroup;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderDetail;
import ua.gaponov.posterminal.entity.orders.OrderService;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.organization.OrganizationService;
import ua.gaponov.posterminal.entity.products.Product;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

import static ua.gaponov.posterminal.utils.JsonUtils.GSON;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor
public class VchasnoFiscal implements DeviceFiscalPrinter {

    private static final Logger LOG = LoggerFactory.getLogger(VchasnoFiscal.class);
    private static final String VCHASNO_DEVICE_HOST = "http://" + AppProperties.getFiscalIp() + ":3939/dm/execute";
    private String deviceName;
    private String token;

    public VchasnoFiscal(String deviceName, String token) {
        this.deviceName = deviceName;
        this.token = token;
    }

    @Override
    public boolean printOrder(Order order) {
        if (Objects.isNull(token) || token.isEmpty()) {
            return printFiscalOrderByOrganization(order);
        } else {
            return printAllRowsInOneFiscal(order);
        }
    }

    private boolean printFiscalOrderByOrganization(Order order) {
        Map<Organization, Double> totalsByOrganizations = order.getTotalsByOrganizations();
        for (Map.Entry<Organization, Double> organizationDoubleEntry : totalsByOrganizations.entrySet()) {
            if (Objects.nonNull(organizationDoubleEntry.getKey())) {
                Organization organization = OrganizationService.getByGuid(organizationDoubleEntry.getKey().getGuid());
                if (organization.isRroActive()) {
                    Order tempOrder = OrderService.createOrderByOrganization(order, organization);
                    DeviceFiscalPrinter fiscalPrinter = new VchasnoFiscal(organization.getRroName(), organization.getRroToken());

                    if (!fiscalPrinter.printOrder(tempOrder)) {
                        return false;
                    }
                    for (OrderDetail detail : order.getDetails()) {
                        for (OrderDetail tempOrderDetail : tempOrder.getDetails()) {
                            if (Objects.equals(tempOrderDetail.getProduct(), detail.getProduct())) {
                                detail.setOrganization(organization);
                                detail.setFiscalPrint(true);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean printAllRowsInOneFiscal(Order order) {
        if (Objects.isNull(order)) {
            return false;
        }

        if (!shiftIsOpen()) {
            openShift();
        }

        if (!shiftIsOpen()) {
            return false;
        }

        Fiscal fiscal = Fiscal.salesReceipt();
        if (Objects.equals(DocumentTypes.RETURN,order.getDocumentType())){
            fiscal = Fiscal.returnReceipt();
        }
        Receipt receipt = new Receipt();
        receipt.setSum(order.getDocumentSum());

        addOrderRows(order, receipt);
        addPays(order, receipt);

        fiscal.setReceipt(receipt);
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, fiscal);
        request.setNeedReturnImagePrintForm(2);

        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoDocument>>() {
            }.getType();
            VchasnoResponce<InfoDocument> vchasnoResponce = GSON.fromJson(sResponce, type);
            new PrintFiscalOrder(vchasnoResponce.getPrintFormImage());
            return vchasnoResponce.getRes() == 0;
        } catch (Exception e) {
            LOG.error("Print fiscal order failed", e);
        }
        LOG.info("Print fiscal order success");

        return false;
    }

    private void addPays(Order order, Receipt receipt) {
        Pay[] pays = new Pay[1];
        Pay pay = new Pay();
        pay.setType(getPayType(order.getPayType()));
        pay.setSum(order.getDocumentSum());
        pays[0] = pay;
        receipt.setPays(pays);
    }

    private PayType getPayType(PayTypes payTypes) {
        if (PayTypes.CARD.equals(payTypes)) {
            return PayType.CARD;
        }
        return PayType.CASH;
    }

    private void addOrderRows(Order order, Receipt receipt) {
        Row[] rows = new Row[order.getDetails().size()];
        int line = 0;
        for (OrderDetail detail : order.getDetails()) {
            Row row = new Row();
            row.setName(detail.getProduct().getName());
            row.setCnt(detail.getQty());
            row.setPrice(detail.getPrice());
            row.setCost(detail.getSumma());
            row.setCode(detail.getProduct().getCode());
            row.setExciseCode(detail.getExcise());
            row.setTaxGroup(getTaxGroup(detail.getProduct()));
            rows[line] = row;
            line = line + 1;
        }
        receipt.setRows(rows);
    }

    private TaxGroup getTaxGroup(Product product) {
        int taxGroup = product.getTaxGroup();
        switch (taxGroup) {
            case 1:
                return TaxGroup.VAT_20;
            case 4:
                return TaxGroup.NO_VAT_5;
            case 5:
                return TaxGroup.VAT_14;
            default:
                return TaxGroup.NO_VAT;
        }
    }

    @Override
    public boolean printZReport() {
        if (shiftIsOpen()) {
            VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.zReport());
            try {
                String sResponce = sendRequest(request);
                Type type = new TypeToken<VchasnoResponce<InfoReport>>() {
                }.getType();
                VchasnoResponce<InfoReport> vchasnoResponce = GSON.fromJson(sResponce, type);
                //TODO print report
                return vchasnoResponce.getRes() == 0;
            } catch (Exception e) {
                LOG.error("Get z-report failed", e);
            }
            LOG.info("Z-report success");
        }
        return false;
    }

    @Override
    public void printXReport() {
        if (shiftIsOpen()) {
            VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.xReport());
            try {
                String sResponce = sendRequest(request);
                Type type = new TypeToken<VchasnoResponce<InfoReport>>() {
                }.getType();
                VchasnoResponce<InfoReport> vchasnoResponce = GSON.fromJson(sResponce, type);
                new PrintFiscalXReport(convertXReport(vchasnoResponce));
            } catch (Exception e) {
                LOG.error("Get x-report failed", e);
            }
        }
    }

    private XReportDto convertXReport(VchasnoResponce<InfoReport> vchasnoResponce) {
        XReportDto report = new XReportDto();
        InfoReport info = vchasnoResponce.getInfo();

        report.setSafe(info.getSafe());

        ReportTotalPays[] pays = info.getPays();
        for (ReportTotalPays pay : pays) {
            if (pay.getType() == 0) {
                report.setSaleCash(pay.getSumSale());
                report.setReturnCash(pay.getSumReturn());
            }
            if (pay.getType() == 2) {
                report.setSaleCard(pay.getSumSale());
                report.setReturnCard(pay.getSumReturn());
            }
        }

        ReportTotalMoneys[] money = info.getMoney();
        for (ReportTotalMoneys reportTotalMoneys : money) {
            if (reportTotalMoneys.getType() == 0) {
                report.setMoneyIn(reportTotalMoneys.getSumSale());
                report.setMoneyOut(reportTotalMoneys.getSumReturn());
            }
        }

        report.setCountSaleReceipts(info.getReceipt().getCountReceiptSale());
        report.setCountReturnReceipts(info.getReceipt().getCountReceiptReturn());

        return report;
    }

    public boolean openShift() {
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.openShift());
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoOpenShift>>() {
            }.getType();
            VchasnoResponce<InfoOpenShift> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes() == 0) {
                LOG.info("Shift open");
                if (AppProperties.getFiscalAutoPlusMoneySum()>0){
                    moneyPlus(AppProperties.getFiscalAutoPlusMoneySum());
                }
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

    @Override
    public double getSafeMoney() {
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.status());
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoStatus>>() {
            }.getType();
            VchasnoResponce<InfoStatus> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes() == 0) {
                InfoStatus info = vchasnoResponce.getInfo();
                return info.getSafe();
            }
        } catch (Exception e) {
            LOG.error("Get safe failed", e);
            return 0;
        }
        return 0;
    }

    @Override
    public boolean moneyPlus(double money) {
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.moneyPlus(money));
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoStatus>>() {
            }.getType();
            VchasnoResponce<InfoStatus> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes() == 0) {
                return true;
            }
        } catch (Exception e) {
            LOG.error("Plus money failed", e);
            return false;
        }
        return false;
    }

    @Override
    public boolean moneyMinus(double money) {
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.moneyMinus(money));
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoStatus>>() {
            }.getType();
            VchasnoResponce<InfoStatus> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes() == 0) {
                return true;
            }
        } catch (Exception e) {
            LOG.error("Minus money failed", e);
            return false;
        }
        return false;
    }

    public boolean shiftIsOpen() {
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.status());
        try {
            String sResponce = sendRequest(request);
            Type type = new TypeToken<VchasnoResponce<InfoStatus>>() {
            }.getType();
            VchasnoResponce<InfoStatus> vchasnoResponce = GSON.fromJson(sResponce, type);
            if (vchasnoResponce.getRes() == 0) {
                InfoStatus info = vchasnoResponce.getInfo();
                return info.getShiftStatus() == 1;
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

    private String sendRequest(String url, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(body))
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
