package ua.gaponov.posterminal.devices.fiscal.vchasno;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.InfoStatus;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Warning;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class VchasnoResponce<T> {

    @SerializedName(value = "ver")
    private int versionApi;
    private String source;
    private String device;
    private String tag;
    private String dt;
    private int type;
    private int res;
    @SerializedName(value = "res_action")
    private int resAction;
    @SerializedName(value = "errortxt")
    private String errorText;
    @SerializedName(value = "aq_errortxt")
    private String aqErrorText;
    private Warning[] warnings;
    private int task;
    private T info;
    @SerializedName(value = "pf_text")
    private String printFormText;
    @SerializedName(value = "pf_image")
    private String printFormImage;
    @SerializedName(value = "pf_pdf")
    private String printFormPdf;
}
