package ua.gaponov.posterminal.server.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyCommand {

    private int terminalId;
    private String shopName;
    private String shopAddress;
    private String cashRegisterName;
    private String fiscalName;
    private String fiscalToken;
    private String fiscalIp;
    private double fiscalAutoPlusSum;
    private String prostopayToken;
    private int exchangeIntervalMin;
    private boolean exchangeEnable;
    private int defaultMerchantId;
}
