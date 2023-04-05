package driverSingleton;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverManager.class);

    private static ThreadLocal<WebDriverManager> instance=new ThreadLocal<>();
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private WebDriverManager() {
    }

    public static WebDriverManager getInstance() {
        if (instance.get() == null) {
            instance.set(new WebDriverManager());
        }
        return instance.get();
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
        LOGGER.info("Driver instance set.");
    }

    public void closeDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
            LOGGER.info("Driver instance closed.");
        }
    }
}