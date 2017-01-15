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

    public static WebDriver getWebDriver(BrowserType browserType, boolean runRemote, String seleniumGridUrl) throws MalformedURLException {
        switch (browserType) {
            case chrome:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                } else {
                    System.setProperty("webdriver.chrome.driver", WEB_DRIVERS_PATH.resolve("chrome/2.27/chromedriver.exe").toString());
                    return new ChromeDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                }
            case firefox:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                } else {
                    System.setProperty("webdriver.gecko.driver", WEB_DRIVERS_PATH.resolve("gecko/0.13.0/geckodriver.exe").toString());
                    return new FirefoxDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                }
            case phantomjs:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                } else {
                    return new PhantomJSDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                }
            case edge:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                } else {
                    System.setProperty("webdriver.edge.driver", WEB_DRIVERS_PATH.resolve("edge/14393/MicrosoftWebDriver.exe").toString());
                    return new EdgeDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                }
            case internetExplorer:
                if (runRemote) {
                    return new RemoteWebDriver(new URL(seleniumGridUrl), DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                } else {
                    System.setProperty("webdriver.ie.driver", WEB_DRIVERS_PATH.resolve("internetExplorer/3.0.0/IEDriverServer32.exe").toString());
                    return new InternetExplorerDriver(DesiredCapabilitiesFactory.getDesiredCapabilities(browserType));
                }
        }
        return null;
    }
}