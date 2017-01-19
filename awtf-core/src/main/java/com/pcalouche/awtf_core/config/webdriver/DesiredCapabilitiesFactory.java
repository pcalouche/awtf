package com.pcalouche.awtf_core.config.webdriver;

import com.pcalouche.awtf_core.util.FileDownloadHelper;
import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DesiredCapabilitiesFactory {
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
            "application/xml"
    };

    public static DesiredCapabilities getDesiredCapabilities(BrowserType browserType, String localOperatingSystem) {
        switch (browserType) {
            case chrome:
                return getChromeDesiredCapabilities();
            case firefox:
                return getFirefoxDesiredCapabilities();
            case phantomjs:
                return getPhantomJsDesiredCapabilities(localOperatingSystem);
            case edge:
                return getEdgeDesiredCapabilities();
            case internetExplorer:
                return getInternetExplorerDesiredCapabilities();
            default:
                return null;
        }
    }

    private static DesiredCapabilities getChromeDesiredCapabilities() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", FileDownloadHelper.DOWNLOAD_PATH.toString());
        HashMap<String, Object> pluginMap = new HashMap<>();
        pluginMap.put("enabled", false);
        pluginMap.put("name", "Chrome PDF Viewer");
        prefs.put("plugins.plugins_list", Arrays.asList(pluginMap));

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return desiredCapabilities;
    }

    private static DesiredCapabilities getFirefoxDesiredCapabilities() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.dir", FileDownloadHelper.DOWNLOAD_PATH.toString());
        firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile", String.join(",", ALLOWED_MIME_TYPES));
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", String.join(",", ALLOWED_MIME_TYPES));
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.useWindow", false);
        firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
        firefoxProfile.setPreference("browser.download.manager.closeWhenDone", false);
        // Disable Firefox's built-in PDF viewer
        firefoxProfile.setPreference("pdfjs.disabled", true);

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        return desiredCapabilities;
    }

    private static DesiredCapabilities getPhantomJsDesiredCapabilities(String localOperatingSystem) {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
        Path basePath = WebDriverFactory.WEB_DRIVERS_PATH.resolve("phantomjs/2.1.1");
        switch (localOperatingSystem) {
            case "windows":
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, basePath.resolve("windows/phantomjs.exe").toString());
                break;
            case "mac":
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, basePath.resolve("mac/phantomjs").toString());
                break;
            case "linux":
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, basePath.resolve("linux/phantomjs").toString());
                break;
        }
        desiredCapabilities.setCapability("acceptSslCerts", true);
        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"});
        return desiredCapabilities;
    }

    private static DesiredCapabilities getEdgeDesiredCapabilities() {
        return DesiredCapabilities.edge();
    }

    private static DesiredCapabilities getInternetExplorerDesiredCapabilities() {
        return DesiredCapabilities.internetExplorer();
    }
}
