package com.pcalouche.awtf_core.util.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum for wait tags
 *
 * @author Philip Calouche
 */
public enum WaitTag {
    WAIT_60_SEC("@wait60sec", 60),
    WAIT_90_SEC("@wait90sec", 90),
    WAIT_120_SEC("@wait120sec", 120),
    WAIT_150_SEC("@wait150sec", 150),
    WAIT_180_SEC("@wait180sec", 180);

    private String tagName;
    private int secondsToWait;

    WaitTag(String tagName, int secondsToWait) {
        this.tagName = tagName;
        this.secondsToWait = secondsToWait;
    }

    public static WaitTag getByTagName(String tagName) {
        for (WaitTag waitTag : WaitTag.values()) {
            if (waitTag.getTagName().equals(tagName)) {
                return waitTag;
            }
        }
        // Return null if we get here
        return null;
    }

    public static String getValidTagNames() {
        List<String> validTagNames = new ArrayList<>();
        for (WaitTag waitTag : WaitTag.values()) {
            validTagNames.add(waitTag.getTagName());
        }
        return StringUtils.join(validTagNames, ", ");
    }

    /**
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @return the secondsToWait
     */
    public int getSecondsToWait() {
        return secondsToWait;
    }
}