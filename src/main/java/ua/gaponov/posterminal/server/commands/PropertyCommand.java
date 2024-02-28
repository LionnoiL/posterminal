package ua.gaponov.posterminal.server.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyCommand {

    @JsonProperty("shop_name")
    String shopName;
    @JsonProperty("shop_address")
    String shopAddress;
    @JsonProperty("cash_register_name")
    String cashRegisterName;
    @JsonProperty("fiscal_name")
    String fiscalName;
    @JsonProperty("fiscal_token")
    String fiscalToken;
    @JsonProperty("fiscal_ip")
    String fiscalIp;
    @JsonProperty("fiscal_auto_plus_sum")
    double fiscalAutoPlusSum;
    @JsonProperty("prostopay_token")
    String prostopayToken;
}
