package com.pcalouche.awtf_core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class FileDownloadUtils {
    public static final Path DOWNLOAD_PATH = Paths.get(System.getProperty("user.home")).resolve("Downloads").resolve("awtf_files");
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadUtils.class);

    public static void deleteDownloadedFiles(Path path) throws IOException {
        Files.walk(path).forEach(currentPath -> {
            try {
                if (Files.isDirectory(currentPath)) {
                    deleteDownloadedFiles(currentPath);
                } else {
                    Files.delete(currentPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void waitForFile(String downloadFilename) throws IOException, InterruptedException {
        logger.info("filename to look for is " + downloadFilename);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        DOWNLOAD_PATH.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        Runnable downloadTimeoutRunnable = () -> {
            try {
                logger.info("Sleeping for 2 seconds");
                TimeUnit.SECONDS.sleep(2);
                logger.info("Waking up after 2 seconds and closing the watch service");
                watchService.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread downloadTimeoutThread = new Thread(downloadTimeoutRunnable);
        downloadTimeoutThread.start();

        boolean valid;
        boolean fileFound = false;
        try {
            do {
                WatchKey watchKey = watchService.take();
                for (WatchEvent event : watchKey.pollEvents()) {
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                        String filename = event.context().toString();
                        logger.info("File Created:" + filename);
                        if (filename.equals(downloadFilename)) {
                            logger.info("file found!");
                            fileFound = true;
                            break;
                        }
                    }
                }
                if (fileFound) {
                    logger.info("file found so cancelling watch key");
                    watchKey.cancel();
                    downloadTimeoutThread.join();
                }
                valid = watchKey.reset();
                logger.info("valid->" + valid);

            } while (valid);
        } catch (ClosedWatchServiceException e) {
            logger.info("Watching service closed!!!");
        }
    }
}
