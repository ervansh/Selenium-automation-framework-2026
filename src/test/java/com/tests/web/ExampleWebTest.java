package com.tests.web;

import com.framework.config.FrameworkConfig;
import com.framework.core.driver.DriverManager;
import com.tests.base.WebBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExampleWebTest extends WebBaseTest {
    @Test
    public void configuredApplicationShouldLoad() {
        String baseUrl = FrameworkConfig.getInstance().get("web.base.url", "");
        DriverManager.getDriver().get(baseUrl);
        Assert.assertFalse(DriverManager.getDriver().getTitle().isBlank(),
                "Expected the application to return a non-empty page title.");
    }
}