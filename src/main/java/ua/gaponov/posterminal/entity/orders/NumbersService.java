package ua.gaponov.posterminal.entity.orders;

import ua.gaponov.posterminal.utils.PropertiesUtils;

public class NumbersService {

    public static long loadNumber(String key) {
        String number = PropertiesUtils.getApplicationTempValue(key);
        if (number == null || number.isBlank()) {
            return 1;
        }
        return Long.valueOf(number) + 1;
    }

    public static void saveNumber(String key, Long value) {
        PropertiesUtils.saveApplicationTempValue(key, String.valueOf(value));
    }
}
