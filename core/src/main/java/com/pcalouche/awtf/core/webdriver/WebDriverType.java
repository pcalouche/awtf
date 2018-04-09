package com.pcalouche.awtf.core.webdriver;

import java.util.Arrays;

public enum WebDriverType {
    CHROME_LOCAL(false),
    CHROME_REMOTE(true);

    private boolean remote;

    WebDriverType(boolean remote) {
        this.remote = remote;
    }

    public boolean isRemote() {
        return remote;
    }

    public static String asStringList() {
        return Arrays.asList(WebDriverType.values()).toString();
    }
}
