package com.pcalouche.awtf_core.config.webdriver;

import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebDriverFactory {
    public static final Path WEB_DRIVERS_PATH = Paths.get(System.getProperty("user.dir")).resolve("src/test/resources/webdrivers");

    public static WebDriver getWebDriver(BrowserType browserType, String localOperatingSystem, boolean runRemote, String seleniumGridUrl) throws MalformedURLException {
        switch (browserType) {
            case chrome:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                } else {
                    Path basePath = WebDriverFactory.WEB_DRIVERS_PATH.resolve("chrome/2.27");
                    switch (localOperatingSystem) {
                        case "windows":
                            System.setProperty("webdriver.chrome.driver", WEB_DRIVERS_PATH.resolve("chrome/2.27/windows/chromedriver.exe").toString());
                            break;
                        case "mac":
                            System.setProperty("webdriver.chrome.driver", WEB_DRIVERS_PATH.resolve("chrome/2.27/mac/chromedriver").toString());
                            break;
                        case "linux":
                            System.setProperty("webdriver.chrome.driver", WEB_DRIVERS_PATH.resolve("chrome/2.27/linux/chromedriver").toString());
                            break;
                    }
                    return new ChromeDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                }
            case firefox:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                } else {
                    Path basePath = WebDriverFactory.WEB_DRIVERS_PATH.resolve("gecko/0.13.0");
                    switch (localOperatingSystem) {
                        case "windows":
                            System.setProperty("webdriver.gecko.driver", basePath.resolve("windows/geckodriver.exe").toString());
                            break;
                        case "mac":
                            System.setProperty("webdriver.gecko.driver", basePath.resolve("mac/geckodriver").toString());
                            break;
                        case "linux":
                            System.setProperty("webdriver.gecko.driver", basePath.resolve("linux/geckodriver").toString());
                            break;
                    }
                    return new FirefoxDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                }
            case phantomjs:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                } else {
                    return new PhantomJSDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                }
            case edge:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                } else {
                    System.setProperty("webdriver.edge.driver", WEB_DRIVERS_PATH.resolve("edge/14393/MicrosoftWebDriver.exe").toString());
                    return new EdgeDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                }
            case internetExplorer:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                } else {
                    System.setProperty("webdriver.ie.driver", WEB_DRIVERS_PATH.resolve("internetExplorer/3.0.0/IEDriverServer32.exe").toString());
                    return new InternetExplorerDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType, localOperatingSystem));
                }
        }
        return null;
    }
}