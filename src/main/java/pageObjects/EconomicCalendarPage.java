package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import constants.Constants;
import org.junit.jupiter.api.Assumptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.TestAbortedException;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.DateUtility.areDatesIdenticalWithAdjustedCurrentDate;
import static utils.WebDriverWaitsUtil.waitForElementToBeClickable;
import static utils.WebDriverWaitsUtil.waitForElementToBeDisplayed;

public class EconomicCalendarPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(EconomicCalendarPage.class);
    private WebDriver driver;
    private WebDriverWait waits;


    @FindBy(css = ".tc-economic-calendar-item-header-left-title.tc-normal-text")
    WebElement date;
    @FindBy(css = ".tc-finalval-tmz div")
    WebElement timeSpan;

    @FindBy(css = ".modal-content")
    WebElement cookieModal;

    @FindBy(xpath = "//button[text()=\"ACCEPT ALL\"]")
    WebElement cookieModalAcceptAllButton;

    @FindBy(xpath = "//a[text()=\"here\"]")
    WebElement disclaimerLink;

    @FindBy(xpath = "//mat-icon[contains(@class, \"mat-badge\")]")
    WebElement calendarIcon;

    public EconomicCalendarPage(WebDriver driver, WebDriverWait waits) {
        this.waits = waits;
        this.driver = driver;
        PageFactory.initElements(driver, this);
        LOGGER.info("EconomicCalendarPage initialized.");
    }

    public EconomicCalendarPage verifyEconomicCalendarPageURL() {
        assertEquals(Constants.ECONOMIC_CALENDAR_URL, driver.getCurrentUrl());
        LOGGER.info("Verified Economic Calendar page URL: " + Constants.ECONOMIC_CALENDAR_URL);
        return this;
    }

    public EconomicCalendarPage clickOnSlideElementUntilTextualDate(String textualDate) {
        final int maxAttempts = 10;
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                javascriptExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", timeSpan);
                waitForElementToBeDisplayed(waits, timeSpan);
            } catch (StaleElementReferenceException e) {
                waitForElementToBeDisplayed(waits, timeSpan);
            }
            if (timeSpan.getText().equalsIgnoreCase(textualDate)) {
                break;
            }
            LOGGER.info("Sliding calendar to find textual date: " + textualDate);
            javascriptExecutor.executeScript("const slider = document.querySelector('#RecogniaContent > table > tbody > tr > td > economic-calendar > div > tc-economic-calendar-landing > div > tc-economic-calendar-view-container > div > div > div.tc-economic-calendar-view-container-filter-n-list.tc-normal-background > div.tc-advanced-filter-panel.tc-border-style.tc-border-no-left.tc-border-no-top > tc-time-filter-container > div > div > span > mat-slider');\n" +
                    "const event = new KeyboardEvent('keydown', {\n" +
                    "  key: 'ArrowRight',\n" +
                    "  code: 'ArrowRight',\n" +
                    "  keyCode: 39,\n" +
                    "  which: 39\n" +
                    "});\n" +
                    "slider.dispatchEvent(event);");
        }
        return this;
    }

    public EconomicCalendarPage verifyDateByOffset(String offset) {
        waitForElementToBeDisplayed(waits, date);
        assertTrue(areDatesIdenticalWithAdjustedCurrentDate(date.getText(), offset));
        LOGGER.info("Verified date by offset: " + offset);
        return this;
    }

    public EconomicCalendarPage verifyTimeSpan(String expectedTimeSpan) {
        waitForElementToBeDisplayed(waits, timeSpan);
        assertEquals(expectedTimeSpan, timeSpan.getText().trim());
        LOGGER.info("Verified time span: " + expectedTimeSpan);
        return this;
    }

    public boolean isCookieModalDisplayed() {
        try {
            waitForElementToBeDisplayed(waits, cookieModal);
            Assumptions.assumeTrue(cookieModal.isDisplayed());
            LOGGER.info("Cookie modal is displayed.");
            return true;
        } catch (TestAbortedException e) {
            LOGGER.info("Cookie modal is not displayed.");
            return false;
        }

    }

    public EconomicCalendarPage clickOnAcceptAllButton() {
        LOGGER.info("Clicking on the Accept All button in the cookie modal.");
        waitForElementToBeDisplayed(waits, cookieModalAcceptAllButton);
        waitForElementToBeClickable(waits, cookieModalAcceptAllButton);
        cookieModalAcceptAllButton.click();
        return this;
    }

    public EconomicCalendarPage clickOnDisclaimerLinkButton() {
        waitForElementToBeDisplayed(waits, disclaimerLink);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        LOGGER.info("Clicking on accept all button");
        js.executeScript("arguments[0].click()", disclaimerLink);
        return this;
    }

    private void clickOnCalendarIcon() {
        waitForElementToBeDisplayed(waits, calendarIcon);
        waitForElementToBeClickable(waits, calendarIcon);
        calendarIcon.click();
        LOGGER.info("Clicking on calendar icon");
    }

    public EconomicCalendarPage isCalendarIconDisplayed(){
        try {
            waitForElementToBeDisplayed(waits,calendarIcon);
            clickOnCalendarIcon();
        }catch (TimeoutException e){
            LOGGER.info("Clicking on calendar icon isn't displayed");
        }
        return this;
    }

    public DisclaimerPageObject verifyDisclaimerUrl() {
        LOGGER.info("Verifying disclaimer URL");
        assertEquals(driver.getCurrentUrl(), Constants.DISCLAIMER_URL);
        return new DisclaimerPageObject(driver, waits);
    }

    public EconomicCalendarPage switchToIFrame(String iFrameName) {
        try {
            driver.switchTo().frame(iFrameName);
        } catch (NoSuchFrameException | StaleElementReferenceException e) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.tagName("iframe"))));
            driver.switchTo().frame(iFrameName);
        }
        return this;
    }
}
