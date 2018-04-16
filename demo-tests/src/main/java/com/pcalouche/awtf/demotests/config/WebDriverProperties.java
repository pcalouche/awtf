package com.pcalouche.awtf.demotests.config;

import com.pcalouche.awtf.core.webdriver.WebDriverType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ConfigurationProperties(prefix = "webdriver")
public class WebDriverProperties {
    /**
     * The web driver type to use
     */
    private WebDriverType type = WebDriverType.CHROME_LOCAL;
    /**
     * The remote Selenium grid hub URL
     */
    private String remoteGridHubUrl = "http://c3cisegrid1.premierinc.com:4444/wd/hub";
    /**
     * The local file download path
     */
    private Path localFileDownloadPath = Paths.get(System.getProperty("user.home"), "awtf", "fileDownloads");
    /**
     * The remote file download path
     */
    private Path remoteFileDownloadPath = Paths.get("//C3CISEGRID1/selenium-downloads");

    public WebDriverType getType() {
        return type;
    }

    public void setType(WebDriverType type) {
        this.type = type;
    }

    public String getRemoteGridHubUrl() {
        return remoteGridHubUrl;
    }

    public void setRemoteGridHubUrl(String remoteGridHubUrl) {
        this.remoteGridHubUrl = remoteGridHubUrl;
    }

    public Path getLocalFileDownloadPath() {
        return localFileDownloadPath;
    }

    public void setLocalFileDownloadPath(Path localFileDownloadPath) {
        this.localFileDownloadPath = localFileDownloadPath;
    }

    public Path getRemoteFileDownloadPath() {
        return remoteFileDownloadPath;
    }

    public void setRemoteFileDownloadPath(Path remoteFileDownloadPath) {
        this.remoteFileDownloadPath = remoteFileDownloadPath;
    }

    @Override
    public String toString() {
        return "\nWeb Driver Properties:\n" +
                String.format("\tWeb Driver Type-> %s\n", getType()) +
                String.format("\tRemote Selenium Grid URL-> %s\n", getRemoteGridHubUrl()) +
                String.format("\tLocal File Download Path-> %s\n", getLocalFileDownloadPath()) +
                String.format("\tRemote File Download Path-> %s\n", getRemoteFileDownloadPath());
    }
}
