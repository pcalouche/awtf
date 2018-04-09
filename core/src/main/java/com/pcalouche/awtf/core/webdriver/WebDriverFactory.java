package com.pcalouche.awtf.core.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final Path WEB_DRIVERS_PATH = Paths.get(System.getProperty("user.dir")).resolve("src/test/resources/webdrivers");
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static WebDriver getWebDriver(WebDriverType webDriverType, String gridHubUrl, String fileDownloadPath) throws MalformedURLException {
        switch (webDriverType) {
            case CHROME_LOCAL:
                Path basePath = WebDriverFactory.WEB_DRIVERS_PATH.resolve("chrome/2.36");
                if (isWindows()) {
                    logger.info("Using Windows Chrome local driver");
                    System.setProperty("webdriver.chrome.driver", basePath.resolve("windows").resolve("chromedriver.exe").toString());
                } else if (isMac()) {
                    logger.info("Using Mac Chrome local driver");
                    System.setProperty("webdriver.chrome.driver", basePath.resolve("mac").resolve("chromedriver").toString());
                } else if (isLinux()) {
                    logger.info("Using Linux Chrome local driver");
                    System.setProperty("webdriver.chrome.driver", basePath.resolve("linux").resolve("chromedriver").toString());
                } else {
                    throw new RuntimeException("Unable to determine local operating system for web driver.");
                }
                return new ChromeDriver(DesiredCapabilitiesFactory.getChromeOptions(fileDownloadPath));
            case CHROME_REMOTE:
                logger.info("Using Chrome remote driver");
                return new RemoteWebDriver(new URL(gridHubUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(webDriverType, fileDownloadPath));
            default:
                throw new IllegalArgumentException(webDriverType + " is not a supported web driver.  Valid web drivers are: " + WebDriverType.asStringList());
        }

    }

    // Would move these into a utility class if OS detection turns out to be needed in other places.
    private static boolean isWindows() {
        return OS_NAME.contains("windows");
    }

    private static boolean isMac() {
        return OS_NAME.contains("mac");
    }

    private static boolean isLinux() {
        return OS_NAME.contains("linux");
    }
}