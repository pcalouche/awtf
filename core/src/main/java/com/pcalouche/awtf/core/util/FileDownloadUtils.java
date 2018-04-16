package com.pcalouche.awtf.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileDownloadUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadUtils.class);
    private static Path downloadPath;

    public FileDownloadUtils(Path downloadPath) {
        FileDownloadUtils.downloadPath = downloadPath;
    }

    public void deletePath(Path pathToDelete) {
        try {
            if (Files.isDirectory(pathToDelete)) {
                for (Path currentPath : Files.list(pathToDelete).collect(Collectors.toList())) {
                    if (Files.isDirectory(currentPath)) {
                        deletePath(currentPath);
                        Files.deleteIfExists(currentPath);
                    } else {
                        Files.deleteIfExists(currentPath);
                    }
                }
            } else {
                Files.deleteIfExists(pathToDelete);
            }
        } catch (IOException e) {
            logger.error("IOException in deletePath", e);
        }
    }

    public Path getDownloadedFilePath(String downloadedFilename) {
        if (StringUtils.isEmpty(parsePlaceholders(downloadedFilename))) {
            return downloadPath;
        } else {
            return downloadPath.resolve(parsePlaceholders(downloadedFilename));
        }
    }

    public boolean waitForFile(String downloadedFilename, int maxSecondsToWait) throws InterruptedException {
        String parsedFilename = parsePlaceholders(downloadedFilename);
        logger.warn("Be sure you have a step to delete the old file, otherwise a copy with a number will be downloaded instead (file(1), file(2)) and you will be evaluating an out of date file in your test");
        logger.info("filename to look for is " + downloadPath.resolve(parsedFilename).toAbsolutePath());
        Path pathToFind = downloadPath.resolve(parsedFilename);

        boolean fileFound = false;
        for (int i = 0; i < maxSecondsToWait; i++) {
            if (Files.exists(pathToFind)) {
                logger.info(String.format("%s was found after %d seconds", downloadPath.resolve(parsedFilename).toString(), i + 1));
                fileFound = true;
                break;
            }
            TimeUnit.SECONDS.sleep(1);
        }
        if (!fileFound) {
            logger.info(String.format("%s was NOT found after %d seconds", downloadPath.resolve(parsedFilename).toString(), maxSecondsToWait));
        }

        return fileFound;
    }

    private String parsePlaceholders(String filename) {
        String parsedFilename = filename;
        LocalDate now = LocalDate.now();
        if (filename.contains(Placeholders.CURRENT_DAY.getPlaceholderText())) {
            parsedFilename = parsedFilename.replace(Placeholders.CURRENT_DAY.getPlaceholderText(), now.format(DateTimeFormatter.ofPattern("dd")));
        }
        if (filename.contains(Placeholders.CURRENT_MONTH.getPlaceholderText())) {
            parsedFilename = parsedFilename.replace(Placeholders.CURRENT_MONTH.getPlaceholderText(), now.format(DateTimeFormatter.ofPattern("MM")));
        }
        if (filename.contains(Placeholders.CURRENT_YEAR.getPlaceholderText())) {
            parsedFilename = parsedFilename.replace(Placeholders.CURRENT_YEAR.getPlaceholderText(), now.format(DateTimeFormatter.ofPattern("YYYY")));
        }
        return parsedFilename;
    }

    // Can be expanded to support other file place holders for dynamic data
    public enum Placeholders {
        CURRENT_DAY("[CURRENT_DAY]"),
        CURRENT_MONTH("[CURRENT_MONTH]"),
        CURRENT_YEAR("[CURRENT_YEAR]");

        private String placeholderText;

        Placeholders(String placeholderText) {
            this.placeholderText = placeholderText;
        }

        public String getPlaceholderText() {
            return placeholderText;
        }
    }
}
