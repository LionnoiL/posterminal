package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static String[] splitStringByCharCount(String text, int charCount) {
        String newStr = text.replaceAll("(.{" + charCount + "})", "$1|");
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

    public static List<Character> convertStringToCharList(String str) {
        List<Character> chars = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }

    public static String getSpaces(int spaceCount) {
        if (spaceCount <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaceCount; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
