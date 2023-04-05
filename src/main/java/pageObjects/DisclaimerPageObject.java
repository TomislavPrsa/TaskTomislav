package pageObjects;


import constants.Constants;

import org.junit.jupiter.api.Assumptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.TestAbortedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.WebDriverWaitsUtil.waitForElementToBeClickable;
import static utils.WebDriverWaitsUtil.waitForElementToBeDisplayed;

public class DisclaimerPageObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisclaimerPageObject.class);
    private WebDriver driver;
    private WebDriverWait waits;

    @FindBy(xpath = "//h5[text()=\"Risk Warning\"]/following-sibling::p/a[text()=\"here\"]")
    WebElement riskWarningLink;
    @FindBy(css = ".modal-content")
    WebElement cookieModal;

    @FindBy(xpath = "//button[text()=\"ACCEPT ALL\"]")
    WebElement cookieModalAcceptAllButton;


    public DisclaimerPageObject(WebDriver driver, WebDriverWait waits) {
        this.waits = waits;
        this.driver = driver;
        PageFactory.initElements(driver, this);
        LOGGER.info("Initializing DisclaimerPageObject class");
    }

    public DisclaimerPageObject verifyDisclaimerUrl() {
        LOGGER.info("Verifying the current URL is {}", Constants.DISCLAIMER_URL);
        assertEquals(driver.getCurrentUrl(), Constants.DISCLAIMER_URL);
        return this;
    }

    public DisclaimerPageObject clickOnRiskWarningLinkAndVeryNewTab() {
        LOGGER.info("Clicking on Risk Warning link and verifying the new tab");
        String currentWindowHandle = driver.getWindowHandle();
        waitForElementToBeDisplayed(waits, riskWarningLink);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", riskWarningLink);
        waits.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(currentWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        assertNotEquals(currentWindowHandle, driver.getWindowHandle());
        assertEquals(driver.getCurrentUrl(), Constants.RISK_DISCLOSURE_URL);
        LOGGER.info("Switched to the new tab with URL {}", driver.getCurrentUrl());
        return this;
    }

    public boolean isCookieModalDisplayed() {
        try {
            LOGGER.info("Checking if the cookie modal is displayed");
            waitForElementToBeDisplayed(waits, cookieModal);
            Assumptions.assumeTrue(cookieModal.isDisplayed());
            return true;
        } catch (TestAbortedException e) {
            LOGGER.warn("Cookie modal was not displayed");
            return false;
        }

    }

    public DisclaimerPageObject clickOnAcceptAllButton() {
        LOGGER.info("Clicking on Accept All button in the cookie modal");
        waitForElementToBeDisplayed(waits, cookieModalAcceptAllButton);
        waitForElementToBeClickable(waits, cookieModalAcceptAllButton);
        cookieModalAcceptAllButton.click();
        return this;
    }

}