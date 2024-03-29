package ua.gaponov.posterminal.devices.terminal.ingenico;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.terminal.Terminal;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.utils.DialogUtils;

/**
 * @author Andriy Gaponov
 */
@Slf4j
public class IngenicoTerminal implements Terminal {

    private ActiveXComponent device = null;

    @Override
    public boolean pay(int merchId, double summa, Order order) {
        String prn;
        String authCode;

        try {
            createDevice();
            open();
            if (!isOpen()) {
                return false;
            }

            Dispatch.call(device, "Purchase", summa * 100, 0, merchId);
            if (isOk(40)) {
                prn = Dispatch.call(device, "RRN").toString();
                authCode = Dispatch.call(device, "AuthCode").toString();

                Dispatch.call(device, "Confirm");
                if (isOk(30)) {
                    order.setPrnCode(prn);
                    order.setAuthCode(authCode);
                    close();
                    deleteDevice();
                    return true;
                } else {
                    log.error("ingenico Confirm timeout");
                    close();
                    deleteDevice();
                    return false;
                }
            } else {
                log.error("ingenico Purchase timeout");
                close();
                deleteDevice();
                return false;
            }

        } catch (Exception e) {
            if (isOpen()) {
                close();
            }
            deleteDevice();
            DialogUtils.error(null, e.getMessage());
            return false;
        }
    }

    private void deleteDevice(){
        if (Objects.nonNull(device)){
            device.getObject().safeRelease();
            device = null;
        }
    }

    private void createDevice() {
        try {
            device = new ActiveXComponent("ECRCommX.BPOS1Lib");
        } catch (Error|Exception ex){
            log.error("Error create ingenico device: {}", ex);
            throw new RuntimeException("Error create ingenico device");
        }
    }

    private boolean open() {
        int com = 1;
        try {
            String terminalPort = AppProperties.getTerminalPort();
            com = Integer.parseInt(terminalPort.replace("COM", ""));
        } catch (Exception ex){
            return false;
        }

        Dispatch.call(device, "CommOpen", com, 11520);
        return isOk(2);
    }

    private void close() {
        if (Objects.nonNull(device)){
            Dispatch.call(device, "CommClose");
        }
    }

    private boolean isOpen() {
        return true;
    }

    private boolean isOk(int timeOut) {
        long start = System.currentTimeMillis();
        long end = start + timeOut * 1000;
        Variant lastResult = null;
        while (System.currentTimeMillis() < end) {
            lastResult = Dispatch.call(device, "LastResult");
            int resultInt = Integer.parseInt(lastResult.toString());
            if (resultInt == 0) {
                return true;
            } else if (resultInt == 1) {
                Variant lastErrorDescription = Dispatch.call(device, "LastErrorDescription");
                String errorString = lastErrorDescription.toString();
                if (errorString != null && errorString.length() > 50) {
                    errorString = "Невідома помилка терміналу";
                }
                DialogUtils.error(null, errorString);
                log.error("Error ingenico: {}", errorString);
                lastErrorDescription.VariantClear();
                lastErrorDescription.safeRelease();
                return false;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        lastResult.VariantClear();
        lastResult.safeRelease();
        return false;
    }

}
