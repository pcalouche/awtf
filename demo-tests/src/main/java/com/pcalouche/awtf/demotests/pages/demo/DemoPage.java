package com.pcalouche.awtf.demotests.pages.demo;

import com.pcalouche.awtf.core.pages.PageObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "cucumber-glue")
public class DemoPage extends PageObject {
    private final FileDownloadSection fileDownloadSection;

    public DemoPage(FileDownloadSection fileDownloadSection) {
        this.fileDownloadSection = fileDownloadSection;
    }
}
