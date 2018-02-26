package org.xi.quick.codegenerator.utils;

public class SystemUtil {

    static {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            SYSTEM_SLASH = "\\";
            REGEX_SYSTEM_SLASH = "\\\\";
            NEW_LINE = "\r\n";
        } else {
            SYSTEM_SLASH = "/";
            REGEX_SYSTEM_SLASH = "/";
            NEW_LINE = "\n";
        }
    }

    public static String SYSTEM_SLASH;
    public static String REGEX_SYSTEM_SLASH;
    public static String NEW_LINE;
}
