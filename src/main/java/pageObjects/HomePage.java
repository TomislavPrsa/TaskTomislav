package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.WebDriverWaitsUtil.waitForElementToBeClickable;
import static utils.WebDriverWaitsUtil.waitForElementToBeDisplayed;


public class HomePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);
    private ConfigReader configReader = new ConfigReader();
    private WebDriver driver;
    private WebDriverWait waits;
    @FindBy(css = ".modal-content")
    WebElement cookieModal;
    @FindBy(xpath = "//button[text()=\"ACCEPT ALL\"]")
    WebElement cookieModalAcceptAllButton;

    @FindBy(xpath = "//li[@class=\"main_nav_research\"]")
    WebElement researchAndEducationLink;

    @FindBy(xpath = "//a[@href=\"#researchMenu\"]")
    WebElement researchAndEducationLinkToggled;

    @FindBy(xpath = "//*[contains(text(),\"(T&Cs Apply)\")]")
    WebElement termsAndConditionsLink;

    @FindBy(css = ".toggleLeftNav")
    WebElement toggleMenuButton;


    public HomePage(WebDriver driver, ConfigReader configReader, WebDriverWait waits) {
        this.driver = driver;
        this.configReader = configReader;
        this.waits = waits;
        PageFactory.initElements(driver, this);
        LOGGER.info("HomePage initialized.");
    }

    public HomePage verifyHomePageURL() {
        LOGGER.info("Verifying home page URL");
        assertEquals(driver.getCurrentUrl(), configReader.getUrl());
        return this;
    }

    public HomePage verifyCookieModal() {
        LOGGER.info("Verifying cookie modal is displayed");
        waitForElementToBeDisplayed(waits, cookieModal);
        assertTrue(cookieModal.isDisplayed());
        return this;
    }

    public HomePage verifyTermsAndConditionsLink() {
        LOGGER.info("Verifying terms and conditions link is displayed");
        waitForElementToBeDisplayed(waits, termsAndConditionsLink);
        assertTrue(termsAndConditionsLink.isDisplayed());
        return this;
    }


    public HomePage clickOnAcceptAllButton() {
        LOGGER.info("Clicking on Accept All button of cookie modal");
        waitForElementToBeDisplayed(waits, cookieModalAcceptAllButton);
        waitForElementToBeClickable(waits, cookieModalAcceptAllButton);
        cookieModalAcceptAllButton.click();
        return this;
    }

    public boolean isToggleMenuButtonDisplayed() {
        try {
            waitForElementToBeDisplayed(waits, toggleMenuButton);
            toggleMenuButton.click();
            return true;
        }catch (TimeoutException e){
            LOGGER.info("Toggle Button isn't displayed");
            return false;
        }
    }

    public ReasearchAndEducationPage clickOnResearchAndEducationLink() {
        LOGGER.info("Clicking on Research and Education link");
        waitForElementToBeDisplayed(waits, researchAndEducationLink);
        waitForElementToBeClickable(waits, researchAndEducationLink);
        researchAndEducationLink.click();
        return new ReasearchAndEducationPage(driver, waits);
    }
    public ReasearchAndEducationPage clickOnResearchAndEducationLinkToggled() {
        LOGGER.info("Clicking on Research and Education link");
        waitForElementToBeDisplayed(waits, researchAndEducationLinkToggled);
        waitForElementToBeClickable(waits, researchAndEducationLinkToggled);
        researchAndEducationLinkToggled.click();
        return new ReasearchAndEducationPage(driver, waits);
    }


}
