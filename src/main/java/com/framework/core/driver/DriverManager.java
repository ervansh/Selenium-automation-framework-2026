package com.framework.core.driver;

import org.openqa.selenium.WebDriver;

/** Owns one WebDriver per executing test thread. */
public final class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private DriverManager() { }

    public static void setDriver(WebDriver driver) {
        if (driver == null) throw new IllegalArgumentException("WebDriver cannot be null.");
        if (DRIVER.get() != null) throw new IllegalStateException("A WebDriver is already bound to this test thread.");
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("No WebDriver is bound to the current test thread.");
        return driver;
    }

    public static WebDriver getDriverOrNull() { return DRIVER.get(); }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        try { if (driver != null) driver.quit(); }
        finally { DRIVER.remove(); }
    }
}