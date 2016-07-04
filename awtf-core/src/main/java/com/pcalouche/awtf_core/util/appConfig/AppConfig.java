package com.pcalouche.awtf_core.util.appConfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pcalouche.awtf_core.util.enums.RowAction;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppConfig {
    private AppElement loadingIndicatorLocator;
    private AppElement modalLocator;
    private AppElement tooltipLocator;
    private List<AppElement> appWebElements = new ArrayList<>();
    private List<RowActionDefinition> rowActionDefinitions = new ArrayList<>();
    private List<String> errorMessageClasses = new ArrayList<>();
    private String messageBundleLocation;
    private ResourceBundle messageBundle;

    public AppConfig() {
    }

    public AppElement getLoadingIndicatorLocator() {
        return loadingIndicatorLocator;
    }

    public void setLoadingIndicatorLocator(AppElement loadingIndicatorLocator) {
        this.loadingIndicatorLocator = loadingIndicatorLocator;
    }

    public AppElement getModalLocator() {
        return modalLocator;
    }

    public void setModalLocator(AppElement modalLocator) {
        this.modalLocator = modalLocator;
    }

    public AppElement getTooltipLocator() {
        return tooltipLocator;
    }

    public void setTooltipLocator(AppElement tooltipLocator) {
        this.tooltipLocator = tooltipLocator;
    }

    public List<AppElement> getAppWebElements() {
        return appWebElements;
    }

    public void setAppWebElements(List<AppElement> appWebElements) {
        this.appWebElements = appWebElements;
    }

    public List<RowActionDefinition> getRowActionDefinitions() {
        return rowActionDefinitions;
    }

    public void setRowActionDefinitions(List<RowActionDefinition> rowActionDefinitions) {
        this.rowActionDefinitions = rowActionDefinitions;
    }

    public List<String> getErrorMessageClasses() {
        return errorMessageClasses;
    }

    public void setErrorMessageClasses(List<String> errorMessageClasses) {
        this.errorMessageClasses = errorMessageClasses;
    }

    public String getMessageBundleLocation() {
        return messageBundleLocation;
    }

    public void setMessageBundleLocation(String messageBundleLocation) {
        this.messageBundleLocation = messageBundleLocation;
    }

    @JsonIgnore
    public ResourceBundle getMessageBundle() {
        if (messageBundle == null && messageBundleLocation != null) {
            messageBundle = ResourceBundle.getBundle(messageBundleLocation);
        }
        return messageBundle;
    }

    public AppElement findAppWebElement(String description) {
        return findAppWebElement(description, null);
    }

    public AppElement findAppWebElement(String description, Class<?> clazz) {
        AppElement foundBaseWebElement = null;
        for (AppElement appWebElement : appWebElements) {
//            if ((clazz == null || appWebElement.getClass().equals(clazz)) && appWebElement.getDescription().equals(description)) {
            if (appWebElement.getDescription().equals(description)) {
                return appWebElement;
            }
        }
        return foundBaseWebElement;
    }

    public RowActionDefinition findRowActionLocator(RowAction rowAction) {
        RowActionDefinition foundRowActionLocator = null;
        for (RowActionDefinition rowActionLocator : rowActionDefinitions) {
            if (rowActionLocator.getRowAction() == rowAction) {
                foundRowActionLocator = rowActionLocator;
                break;
            }
        }
        return foundRowActionLocator;
    }

    @JsonIgnore
    public String getValidKnownDescriptions(Class<?> clazz) {
        List<String> descriptions = new ArrayList<>();
        for (AppElement appWebElement : appWebElements) {
            if (clazz == null || appWebElement.getClass().equals(clazz)) {
                descriptions.add(appWebElement.getDescription());
            }
        }
        return StringUtils.join(descriptions, ", ");
    }

    public boolean webElementHasErrorClass(WebElement webElement) {
        String webElementClasses = webElement.getAttribute("class");
        for (String errorMessageClass : this.getErrorMessageClasses()) {
            if (webElementClasses.contains(errorMessageClass)) {
                return true;
            }
        }
        return false;
    }
}
