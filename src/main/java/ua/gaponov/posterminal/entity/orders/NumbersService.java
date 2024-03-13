package ua.gaponov.posterminal.entity.orders;

import ua.gaponov.posterminal.utils.PropertiesUtils;

import static ua.gaponov.posterminal.utils.Constants.ORDER_NUMBER_NAME;

public class NumbersService {

    public static long loadNumber(String key) {
        String number = PropertiesUtils.getApplicationTempValue(key);
        if (number == null || number.isBlank()) {
            switch (key) {
                case ORDER_NUMBER_NAME:
                    return OrderService.getCount() + 1;
                default:
                    return 1;
            }
        }
        return Long.valueOf(number) + 1;
    }

    public static void saveNumber(String key, Long value) {
        PropertiesUtils.saveApplicationTempValue(key, String.valueOf(value));
    }
}
