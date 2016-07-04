package com.pcalouche.awtf_core.util.appConfig;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ElementWithTooltip extends AppElement {
    private String tooltipLocator;
    private AppElementLocatorType tooltipLocatorType;
    private AppElement tooltipElement;


    public ElementWithTooltip() {
        super();
    }

    public ElementWithTooltip(String description, String locator, AppElementLocatorType locatorType,
                              String tooltipLocator, AppElementLocatorType tooltipLocatorType) {
        super(description, locator, locatorType);
        this.tooltipLocator = tooltipLocator;
        this.tooltipLocatorType = tooltipLocatorType;
    }

    public String getTooltipLocator() {
        return tooltipLocator;
    }

    public void setTooltipLocator(String tooltipLocator) {
        this.tooltipLocator = tooltipLocator;
    }

    public AppElementLocatorType getTooltipLocatorType() {
        return tooltipLocatorType;
    }

    public void setTooltipLocatorType(AppElementLocatorType tooltipLocatorType) {
        this.tooltipLocatorType = tooltipLocatorType;
    }

    @JsonIgnore
    public AppElement getTooltipElement() {
        if (this.tooltipElement == null) {
            this.tooltipElement = new AppElement(null, tooltipLocator, tooltipLocatorType);
        }
        return tooltipElement;
    }
}
