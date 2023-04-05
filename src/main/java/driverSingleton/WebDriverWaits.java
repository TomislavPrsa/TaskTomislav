package driverSingleton;

import constants.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebDriverWaits {
    private static final Duration WAIT_TIME=Duration.ofSeconds(2);
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverWaits.class);

    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public static WebDriverWait getWait(WebDriver driver) {
        LOGGER.info("Getting WebDriverWait instance.");
        System.out.println(driver.hashCode());
        WebDriverWait webDriverWait = wait.get();

        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(driver, WAIT_TIME);
            setWait(webDriverWait);
        }

        return webDriverWait;
    }

    public static void setWait(WebDriverWait webDriverWait) {
        wait.set(webDriverWait);
        LOGGER.info("WebDriverWait instance set.");
    }

    public static void removeWait() {
        wait.remove();
        LOGGER.info("WebDriverWait instance removed.");
    }

}