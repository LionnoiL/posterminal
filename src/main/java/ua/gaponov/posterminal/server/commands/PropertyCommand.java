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

    String shopName;
    String shopAddress;
    String cashRegisterName;
    String fiscalName;
    String fiscalToken;
    String fiscalIp;
    double fiscalAutoPlusSum;
    String prostopayToken;
}
