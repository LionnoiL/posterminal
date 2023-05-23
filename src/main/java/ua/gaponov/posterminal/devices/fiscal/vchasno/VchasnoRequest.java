package ua.gaponov.posterminal.devices.fiscal.vchasno;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.fiscal.vchasno.entity.Fiscal;
import ua.gaponov.posterminal.utils.DateUtils;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class VchasnoRequest {

    private static final String DATE_FORMAT_PATTERN = "yyyyMMddHHmmssSSS";

    @SerializedName(value = "ver")
    private int versionApi;
    private String source;
    private String device;
    private String tag;
    private String dt;
    private String token;
    @SerializedName(value = "need_pf_img")
    private int needReturnImagePrintForm;
    @SerializedName(value = "need_pf_pdf")
    private int needReturnPdfPrintForm;
    @SerializedName(value = "need_pf_txt")
    private int needReturnTxtPrintForm;
    private int type;
    private Fiscal fiscal;

    public static VchasnoRequest of(String deviceName, String token, Fiscal fiscal){
        VchasnoRequest request = new VchasnoRequest();
        request.setToken(token);
        request.setDevice(deviceName);
        request.setVersionApi(6);
        request.setSource("POS " + AppProperties.getShopName() + " " + AppProperties.getCashRegisterName());
        request.setType(1);
        request.setTag("");
        request.setDt(DateUtils.getDateTimeNow(DATE_FORMAT_PATTERN));
        request.setFiscal(fiscal);

        return request;
    }
}
