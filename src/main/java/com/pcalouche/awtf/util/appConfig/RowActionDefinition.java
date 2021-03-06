package com.pcalouche.awtf.util.appConfig;

import com.pcalouche.awtf.util.enums.RowAction;

public class RowActionDefinition extends AppElement {
    private final RowAction rowAction;

    public RowActionDefinition(String description, String locator, AppElementLocatorType locatorType, RowAction rowAction) {
        super(description, locator, locatorType);
        if (!locatorType.equals(AppElementLocatorType.xpath)) {
            throw new IllegalArgumentException("Framework currently supports RowActionLocator of type xpath");
        }
        this.rowAction = rowAction;
    }

    public RowAction getRowAction() {
        return rowAction;
    }
}
