package ua.gaponov.posterminal.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String[] splitStringByCharCount(String text, int charCount){
        String newStr  = text.replaceAll("(.{"+charCount+"})", "$1|");
        return newStr.split("\\|");
    }

}
