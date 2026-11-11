package com.tests.base;

import com.framework.core.driver.DriverManager;
import org.testng.annotations.AfterMethod;

/** Common cleanup. Browser lifecycle belongs to WebBaseTest only. */
public abstract class BaseTest {
    @AfterMethod(alwaysRun = true)
    public void cleanUp() { DriverManager.quitDriver(); }
}