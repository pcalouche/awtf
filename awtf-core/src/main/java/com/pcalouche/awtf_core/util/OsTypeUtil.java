package com.pcalouche.awtf_core.util;

public class OsTypeUtil {
    public static final String UNKNOWN_OS_ERROR_MESSAGE = "Unknown OS type.  Unable to create web driver";
    private static final String osName = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return osName.contains("windows");
    }

    public static boolean isMac(){
        return osName.contains("mac");
    }

    public static boolean isLinux() {
        return  osName.contains("linux");
    }
}
