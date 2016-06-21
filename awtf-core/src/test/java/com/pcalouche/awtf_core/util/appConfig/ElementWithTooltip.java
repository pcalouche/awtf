package com.pcalouche.awtf_core.util.appConfig;

public class ElementWithTooltip extends AppElement {
    private AppElement tooltipElement;

    public ElementWithTooltip() {
        super();
    }

    public ElementWithTooltip(String description, String locator, AppElementLocatorType locatorType,
                              String tooltipLocator, AppElementLocatorType tooltipLocatorType) {
        super(description, locator, locatorType);
        this.tooltipElement = new AppElement(null, tooltipLocator, tooltipLocatorType);
    }

    /**
     * @return the tooltipElement
     */
    public AppElement getTooltipElement() {
        return tooltipElement;
    }

    /**
     * @param tooltipElement the tooltipElement to set
     */
    public void setTooltipElement(AppElement tooltipElement) {
        this.tooltipElement = tooltipElement;
    }
}
