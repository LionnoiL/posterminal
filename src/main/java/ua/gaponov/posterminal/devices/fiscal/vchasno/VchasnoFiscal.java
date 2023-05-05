package ua.gaponov.posterminal.devices.fiscal.vchasno;

import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.devices.fiscal.DeviceFiscalPrinter;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Fiscal;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.InfoOpenShift;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.InfoStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public void beginReceipt() {
        if (!shiftIsOpen()){
            openShift();
        }

    }

    @Override
    public void endReceipt() {

    }

    @Override
    public void printLine(String sProductName, double dPrice, double dUnits, int taxInfo) {

    }

    @Override
    public void printMessage(String sMessage) {

    }

    @Override
    public void printTotal(String sPayment, double dPaid) {

    }

    @Override
    public void printZReport() {

    }

    @Override
    public void printXReport() {
        if (shiftIsOpen()){

        }
    }

    public boolean openShift(){
        VchasnoRequest request = VchasnoRequest.of(deviceName, token, Fiscal.openShift());
        try {
            String sResponce = sendRequest(request);
            Type collectionType = new TypeToken<VchasnoResponce<InfoOpenShift>>(){}.getType();
            VchasnoResponce<InfoOpenShift> vchasnoResponce = GSON.fromJson(sResponce, collectionType);
            if (vchasnoResponce.getRes()==0){
                return true;
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
            Type collectionType = new TypeToken<VchasnoResponce<InfoStatus>>(){}.getType();
            VchasnoResponce<InfoStatus> vchasnoResponce = GSON.fromJson(sResponce, collectionType);
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
