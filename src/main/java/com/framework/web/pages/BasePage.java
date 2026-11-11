package com.framework.web.pages;

import com.framework.config.FrameworkConfig;
import com.framework.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/** Shared explicit-wait and browser operations for Page Objects. */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(
                FrameworkConfig.getInstance().getInt("wait.timeout.seconds", 15)));
    }

    protected WebElement visible(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    protected WebElement clickable(By locator) { return wait.until(ExpectedConditions.elementToBeClickable(locator)); }
    protected void click(By locator) { clickable(locator).click(); }
    protected void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value);
    }
    protected String textOf(By locator) { return visible(locator).getText(); }
    protected void navigateTo(String url) { driver.navigate().to(url); }
}