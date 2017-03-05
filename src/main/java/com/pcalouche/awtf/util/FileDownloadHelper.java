package com.pcalouche.awtf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileDownloadHelper {
    public static final Path DOWNLOAD_PATH = Paths.get(System.getProperty("user.home")).resolve("Downloads").resolve("awtf");
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadHelper.class);

    public static void deletePath(Path pathToDelete) throws IOException {
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

    public static Path getDownloadedFilePath(String downloadedFilename) {
        return DOWNLOAD_PATH.resolve(downloadedFilename);
    }

    public static boolean waitForFile(String downloadedFilename, int maxSecondsToWait) throws IOException, InterruptedException {
        logger.info("filename to look for is " + downloadedFilename);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        DOWNLOAD_PATH.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        Runnable downloadTimeoutRunnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(maxSecondsToWait);
                logger.info(String.format("Waking up after %d seconds and closing the watch service", maxSecondsToWait));
                watchService.close();
            } catch (InterruptedException e) {
                // We could have been interrupted because file was found, so just ignore
                logger.warn("downloadTimeoutRunnable was interrupted probably because file was found");
            } catch (IOException e) {
                logger.error("IOException in downloadTimeoutRunnable", e);
            }
        };

        Thread downloadTimeoutThread = new Thread(downloadTimeoutRunnable);
        downloadTimeoutThread.start();

        boolean valid = true;
        boolean fileFound = false;
        try {
            for (; valid; ) {
                WatchKey watchKey = watchService.take();
                for (WatchEvent event : watchKey.pollEvents()) {
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                        String filename = event.context().toString();
                        logger.info(String.format("File Created->%s", filename));
                        if (filename.equals(downloadedFilename)) {
                            logger.info(String.format("Expected file %s found", filename));
                            fileFound = true;
                            break;
                        }
                    }
                }
                if (fileFound) {
                    // Cancels watch key which will make it invalid when reset is called
                    watchKey.cancel();
                    downloadTimeoutThread.interrupt();
                }
                valid = watchKey.reset();
            }
            watchService.close();
        } catch (ClosedWatchServiceException e) {
            logger.warn("ClosedWatchServiceException happened.  Probably occurred because file was not found in time.");
        }
        return fileFound;
    }
}
