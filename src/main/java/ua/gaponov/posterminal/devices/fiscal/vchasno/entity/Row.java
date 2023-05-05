package ua.gaponov.posterminal.devices.fiscal.vchasno.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.TaxGroup;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Row {

    private String code;
    @SerializedName(value = "code1")
    private String barCode;
    @SerializedName(value = "code2")
    private String uktzedCode;
    @SerializedName(value = "code3")
    private String dkppCode;
    @SerializedName(value = "code_a")
    private String exciseCode;
    @SerializedName(value = "code_aa")
    private String[] exciseCodes;
    private String name;
    private double cnt;
    private double price;
    private double disc;
    private double cost;
    @SerializedName(value = "taxgrp")
    private TaxGroup taxGroup;
    private String comment;
}
