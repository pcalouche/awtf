package com.pcalouche.awtf.demotests.config;

import com.pcalouche.awtf.core.pages.PageObject;
import com.pcalouche.awtf.core.util.FileDownloadUtils;
import com.pcalouche.awtf.core.webdriver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class WebDriverConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);

    @Bean
    @Scope(value = "cucumber-glue")
    public FileDownloadUtils fileDownloadUtils(WebDriverProperties webDriverProperties) throws IOException {
        Path downloadPath;
        if (webDriverProperties.getType().isRemote()) {
            logger.info("using remote file download path of " + webDriverProperties.getRemoteFileDownloadPath().toAbsolutePath());
            downloadPath = webDriverProperties.getRemoteFileDownloadPath();
        } else {
            logger.info("using local file download path of " + webDriverProperties.getLocalFileDownloadPath().toAbsolutePath());
            downloadPath = webDriverProperties.getLocalFileDownloadPath();
        }

        // Try to create path if it doesn't exist, so file download steps don't fail
        if (Files.notExists(downloadPath) && !webDriverProperties.getType().isRemote()) {
            Files.createDirectories(downloadPath);
        }
        return new FileDownloadUtils(downloadPath);
    }

    @Bean(destroyMethod = "quit")
    @Scope(value = "cucumber-glue")
    public WebDriver webDriver(WebDriverProperties webDriverProperties,
                               FileDownloadUtils fileDownloadUtils) throws IOException {
        return WebDriverFactory.getWebDriver(
                webDriverProperties.getType(),
                webDriverProperties.getRemoteGridHubUrl(),
                fileDownloadUtils.getDownloadedFilePath("").toString());
    }

    @Bean
    @Scope(value = "cucumber-glue")
    public WebDriverWait webDriverWait(WebDriver webDriver) {
        return new WebDriverWait(webDriver, PageObject.DEFAULT_WEB_DRIVER_WAIT);
    }
}
