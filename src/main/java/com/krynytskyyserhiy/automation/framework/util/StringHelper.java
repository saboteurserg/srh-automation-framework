package com.krynytskyyserhiy.automation.framework.util;

/**
 * Created by Orest Bashmat on 13.04.2017.
 */
public class StringHelper {

    /***
     * Appends StringBuilder instance with string and adds line end separator
     *
     * @param string
     *            stringBuilder instance append to
     * @param line
     *            String line to be appended
     * @return string builder object appended with string and line separator;
     */
    public static StringBuilder appendln(StringBuilder stringBuilder, String line) {
        return stringBuilder.append(line).append(System.getProperty("line.separator"));
    }

    public static StringBuilder appendln(StringBuilder string, String first_part, String second_part) {
        return appendln(string.append(first_part), second_part);
    }
}