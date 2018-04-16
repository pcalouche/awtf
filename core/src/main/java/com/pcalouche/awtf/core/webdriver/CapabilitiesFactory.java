package com.pcalouche.awtf.core.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

class CapabilitiesFactory {

    static Capabilities getCapabilities(WebDriverType webDriverType, String fileDownloadPath) {
        switch (webDriverType) {
            case CHROME_LOCAL:
                return getChromeOptions(fileDownloadPath);
            case CHROME_REMOTE:
                return getChromeOptions(fileDownloadPath);
            default:
                throw new IllegalArgumentException(webDriverType + " is not a supported web driver.  Valid web drivers are: " + WebDriverType.asStringList());
        }
    }

    private static ChromeOptions getChromeOptions(String fileDownloadPath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", fileDownloadPath);
        prefs.put("plugins.always_open_pdf_externally", true);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);
        return chromeOptions;
    }
}
