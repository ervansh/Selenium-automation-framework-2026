package com.framework.web.actions;

import com.framework.web.waits.WaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/** Reusable browser interactions; application-specific workflows belong in Page Objects. */
public final class WebActions {
    private final WebDriver driver;
    public WebActions(WebDriver driver) {
        if (driver == null) throw new IllegalArgumentException("WebDriver cannot be null.");
        this.driver = driver;
    }
    public void click(By locator) {
        WaitFactory.create(driver).until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    public void type(By locator, String value) {
        WebElement element = WaitFactory.create(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }
    public String text(By locator) {
        return WaitFactory.create(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
    public boolean isVisible(By locator) {
        try {
            return WaitFactory.create(driver)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
    }
    public void selectByVisibleText(By locator, String visibleText) {
        WebElement element = WaitFactory.create(driver)
                .until(ExpectedConditions.elementToBeClickable(locator));
        new Select(element).selectByVisibleText(visibleText);
    }
    public void waitUntilInvisible(By locator) {
        WaitFactory.create(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
