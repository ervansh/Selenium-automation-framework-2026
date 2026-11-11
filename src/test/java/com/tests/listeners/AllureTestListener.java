package com.tests.listeners;

import com.framework.core.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.ByteArrayInputStream;

/** Attaches a screenshot to failed web tests when a driver is available. */
public class AllureTestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriverOrNull();
        if (driver instanceof TakesScreenshot screenshotDriver) {
            try {
                byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Failure screenshot", "image/png",
                        new ByteArrayInputStream(screenshot), ".png");
            } catch (RuntimeException ignored) {
                // Do not mask the original test failure if screenshot capture is unavailable.
            }
        }
    }
}