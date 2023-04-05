package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.ReasearchAndEducationPage;

import java.time.Duration;

public class WebDriverWaitsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverWaitsUtil.class);

    private JavascriptExecutor javascriptExecutor;

    public static WebElement waitForElementToBeDisplayed(WebDriverWait wait, WebElement element) {
        LOGGER.info("Waiting for element to be displayed: {}", element.toString());
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementToBeClickable(WebDriverWait wait, WebElement element) {
        LOGGER.info("Waiting for element to be clickable: {}", element.toString());
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForElementToBeInvisible(WebDriver driver, Duration time, WebElement element) {
        LOGGER.info("Waiting for element to be invisible: {}", element.toString());
        return new WebDriverWait(driver, time)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean waitForAJAX(WebDriver driver, Duration time, WebElement element) {
        LOGGER.info("Waiting for AJAX to complete");
        javascriptExecutor = (JavascriptExecutor) driver;
        return new WebDriverWait(driver, time)
                .until(d -> (Boolean) javascriptExecutor.executeScript("return window.jQuery !=undefined $$ jQuery.active ==0"));
    }
}
