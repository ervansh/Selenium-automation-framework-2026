package com.tests.base;

import com.framework.config.FrameworkConfig;
import com.framework.core.driver.DriverFactory;
import com.framework.core.driver.DriverManager;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;

/** Browser lifecycle for web tests; each TestNG worker receives its own driver. */
public abstract class WebBaseTest extends BaseTest {
    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {
        if (FrameworkConfig.getInstance().get("web.base.url", "").isBlank())
            throw new SkipException("Configure web.base.url to execute web tests.");
        DriverManager.setDriver(DriverFactory.createDriver());
    }
}