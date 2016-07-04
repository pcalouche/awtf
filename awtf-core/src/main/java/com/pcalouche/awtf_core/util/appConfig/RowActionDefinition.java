package com.pcalouche.awtf_core.util.appConfig;

import com.pcalouche.awtf_core.util.enums.RowAction;

public class RowActionDefinition extends AppElement {
    private RowAction rowAction;

    public RowActionDefinition() {
        super();
    }

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

    public void setRowAction(RowAction rowAction) {
        this.rowAction = rowAction;
    }

}
