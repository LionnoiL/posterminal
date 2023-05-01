package ua.gaponov.posterminal.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String[] splitStringByCharCount(String text, int charCount){
        String newStr  = text.replaceAll("(.{"+charCount+"})", "$1|");
        return newStr.split("\\|");
    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
