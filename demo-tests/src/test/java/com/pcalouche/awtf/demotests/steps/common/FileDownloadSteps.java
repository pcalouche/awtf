package com.pcalouche.awtf.demotests.steps.common;

import com.pcalouche.awtf.core.util.FileDownloadUtils;
import com.pcalouche.awtf.demotests.steps.AbstractSteps;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileDownloadSteps extends AbstractSteps {
    private final FileDownloadUtils fileDownloadUtils;

    @Autowired
    public FileDownloadSteps(FileDownloadUtils fileDownloadUtils) {
        this.fileDownloadUtils = fileDownloadUtils;
    }

    @And("^I wait up to \"([^\"]*)\" seconds for a file named \"([^\"]*)\" to download$")
    public void waitForFileDownload(int maxSecondsToWait, String filename) throws InterruptedException {
        assertThat(fileDownloadUtils.waitForFile(filename, maxSecondsToWait))
                .as(String.format("Expected file %s did not download", fileDownloadUtils.getDownloadedFilePath(filename)))
                .isTrue();
    }

    @And("^I delete the file named \"([^\"]*)\" if it exists$")
    public void deleteIfFileExists(String filename) {
        fileDownloadUtils.deletePath(fileDownloadUtils.getDownloadedFilePath(filename));
    }
}