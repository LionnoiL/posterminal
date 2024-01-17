package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BarcodeConverter {

    private static String latinKeys = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
    private static String cyrKeys = "ЙЦУКЕНГШЩЗФЫВАПРОЛДЯЧСМИТЬйцукенгшщзфывапролдячсмить";

    public static String convertToLatin(String barcode) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < barcode.length(); i++) {
            char currentChar = barcode.charAt(i);
            int index = cyrKeys.indexOf(currentChar);

            if (index != -1) {
                char latinChar = latinKeys.charAt(index);
                result.append(latinChar);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
