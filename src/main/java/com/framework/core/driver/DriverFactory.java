package com.framework.core.driver;

import com.framework.config.FrameworkConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Locale;

/** Creates local or Selenium Grid drivers. Selenium Manager resolves local browser drivers. */
public final class DriverFactory {
    private DriverFactory() { }

    public static WebDriver createDriver() {
        FrameworkConfig config = FrameworkConfig.getInstance();
        String browser = config.get("browser", "chrome").toLowerCase(Locale.ROOT);
        boolean headless = config.getBoolean("browser.headless", false);
        String gridUrl = config.get("grid.url", "");
        return gridUrl.isBlank() ? createLocal(browser, headless) : createRemote(browser, headless, gridUrl);
    }

    private static WebDriver createLocal(String browser, boolean headless) {
        return switch (browser) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            case "edge" -> new EdgeDriver(edgeOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static WebDriver createRemote(String browser, boolean headless, String gridUrl) {
        try {
            var url = URI.create(gridUrl).toURL();
            return switch (browser) {
                case "chrome" -> new RemoteWebDriver(url, chromeOptions(headless));
                case "firefox" -> new RemoteWebDriver(url, firefoxOptions(headless));
                case "edge" -> new RemoteWebDriver(url, edgeOptions(headless));
                default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
            };
        } catch (MalformedURLException exception) {
            throw new IllegalArgumentException("Invalid Selenium Grid URL: " + gridUrl, exception);
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("-headless");
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        return options;
    }
}