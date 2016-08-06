package com.pcalouche.awtf_core.util.appConfig;

public class ElementWithTooltip extends AppElement {
    private final AppElement tooltipElement;

    public ElementWithTooltip(String description, String locator, AppElementLocatorType locatorType,
                              String tooltipLocator, AppElementLocatorType tooltipLocatorType) {
        super(description, locator, locatorType);
        this.tooltipElement = new AppElement(null, tooltipLocator, tooltipLocatorType);
    }

    public AppElement getTooltipElement() {
        return tooltipElement;
    }
}
