package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoundUtils {

    public static double round(double dValue) {
        double fractionMultiplier = Math.pow(10.0, 2);
        return Math.rint(dValue * fractionMultiplier) / fractionMultiplier;
    }

    public static double roundHalfUp(double dValue) {
        BigDecimal result2 = BigDecimal.valueOf(dValue).setScale(1, BigDecimal.ROUND_HALF_UP);
        return result2.doubleValue();
    }

    public static int compare(double d1, double d2) {
        return Double.compare(round(d1), round(d2));
    }

    public static double getValue(Double value) {
        return value == null ? 0.0 : value.doubleValue();
    }
}
