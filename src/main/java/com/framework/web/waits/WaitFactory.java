package com.framework.web.waits;

import com.framework.config.FrameworkConfig;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/** Centralizes explicit-wait policy. Implicit waits should remain disabled. */
public final class WaitFactory {
    private WaitFactory() { }
    public static WebDriverWait create(WebDriver driver) {
        FrameworkConfig config = FrameworkConfig.getInstance();
        int timeoutSeconds = config.getInt("wait.timeout.seconds", 15);
        int pollingMillis = config.getInt("wait.polling.millis", 250);
        if (timeoutSeconds <= 0 || pollingMillis <= 0) {
            throw new IllegalArgumentException("Wait timeout and polling interval must be positive.");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds),
                Duration.ofMillis(pollingMillis));
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }
}
