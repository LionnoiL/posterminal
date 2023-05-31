package ua.gaponov.posterminal.devices.fiscal;

import lombok.*;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class XReportDto {

    private double safe;
    private double moneyIn;
    private double moneyOut;
    private double saleCash;
    private double saleCard;
    private double returnCash;
    private double returnCard;
    private int countSaleReceipts;
    private int countReturnReceipts;
}
