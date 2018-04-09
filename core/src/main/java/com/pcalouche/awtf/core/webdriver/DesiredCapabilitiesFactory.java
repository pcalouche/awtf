package com.pcalouche.awtf.core.webdriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

class DesiredCapabilitiesFactory {
    private static final String[] ALLOWED_MIME_TYPES = {
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "text/html",
            "text/plain",
            "text/csv",
            "application/xml",
            "application/zip",
            "application/x-zip",
            "application/octet-stream",
            "application/x-zip-compressed"
    };

    static DesiredCapabilities getDesiredCapabilities(WebDriverType webDriverType, String fileDownloadPath) {
        switch (webDriverType) {
            case CHROME_LOCAL:
                return getChromeDesiredCapabilities(fileDownloadPath);
            case CHROME_REMOTE:
                return getChromeDesiredCapabilities(fileDownloadPath);
            default:
                throw new IllegalArgumentException(webDriverType + " is not a supported web driver.  Valid web drivers are: " + WebDriverType.asStringList());
        }
    }

    static ChromeOptions getChromeOptions(String fileDownloadPath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", fileDownloadPath);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);
        return chromeOptions;
    }

    private static DesiredCapabilities getChromeDesiredCapabilities(String fileDownloadPath) {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions(fileDownloadPath));
        return desiredCapabilities;
    }
}
