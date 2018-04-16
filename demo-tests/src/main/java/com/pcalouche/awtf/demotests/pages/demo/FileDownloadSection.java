package com.pcalouche.awtf.demotests.pages.demo;

import com.pcalouche.awtf.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "cucumber-glue")
public class FileDownloadSection extends PageObject {
    @FindBy(linkText = "Download Excel File")
    private WebElement downloadExcelLink;
    @FindBy(linkText = "Download PDF File")
    private WebElement downloadPdfFile;

    public void clickDownloadLink(String downloadLink) {
        switch (downloadLink) {
            case "Download Excel File":
                webElementClick(downloadExcelLink);
                break;
            case "Download PDF File":
                webElementClick(downloadPdfFile);
                break;
        }
    }
}
