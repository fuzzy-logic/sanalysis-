package com.sky.techfutures.sentanal.utils;

/**
 * Created by gawain on 16/05/2014.
 */
public class StringUtils {

    static String stripChars = "_<>\\{}[]=-_+)(*&^%$£@!";
    public static String stripForAnalysis(String text) {

        char substitute = ' ';
        for (int i = 0; i < stripChars.length(); i++){
            char c = stripChars.charAt(i);
            text = text.replace(String.valueOf(c), "");
        }

        return text;
    }
}
