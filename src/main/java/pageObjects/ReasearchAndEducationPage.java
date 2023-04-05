package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.WebDriverWaitsUtil.waitForElementToBeClickable;
import static utils.WebDriverWaitsUtil.waitForElementToBeDisplayed;

public class ReasearchAndEducationPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReasearchAndEducationPage.class);
    private WebDriver driver;
    private WebDriverWait waits;

    @FindBy(xpath = "//span[contains(text(),\"RESEARCH & EDUCATION\")]")
    WebElement researchAndEducationTitle;

    @FindBy(css = ".main_nav_research.selected")
    WebElement researchAndEducationLinkSelected;

    @FindBy(xpath = "//a[@href=\"https://www.xm.com/research/economicCalendar\" and contains(text(),\"Economic Calendar\")]")
    WebElement economicCalendarLink;

    @FindBy(xpath = "//ul[@class=\"navbar-nav__list\"]//a[@href=\"https://www.xm.com/research/economicCalendar\"]")
    WebElement economicCalendarLinkToggled;

    public ReasearchAndEducationPage(WebDriver driver, WebDriverWait waits) {
        this.waits = waits;
        this.driver = driver;
        PageFactory.initElements(driver, this);
        LOGGER.info("ReasearchAndEducationPage initialized.");
    }

    public ReasearchAndEducationPage verifyResearchAndEducationTitle() {
        LOGGER.info("Verifying the Research and Education title...");
        waitForElementToBeDisplayed(waits, researchAndEducationTitle);
        assertTrue(researchAndEducationTitle.isDisplayed());
        LOGGER.info("The Research and Education title verification is complete.");
        return this;
    }

    public ReasearchAndEducationPage verifyResearchAndEducationButtonIsSelected() {
        LOGGER.info("Verifying that the Research and Education button is selected...");
        waitForElementToBeDisplayed(waits, researchAndEducationLinkSelected);
        assertTrue(researchAndEducationLinkSelected.isDisplayed());
        LOGGER.info("The Research and Education button selection verification is complete.");
        return this;
    }

    public EconomicCalendarPage clickOnEconomicCalendarLink() {
        LOGGER.info("Clicking on the Economic Calendar link...");
        waitForElementToBeDisplayed(waits, economicCalendarLink);
        waitForElementToBeClickable(waits, economicCalendarLink);
        economicCalendarLink.click();
        LOGGER.info("The Economic Calendar link click is complete.");
        return new EconomicCalendarPage(driver, waits);
    }
    public EconomicCalendarPage clickOnEconomicCalendarLinkToggled() {
        LOGGER.info("Clicking on the Economic Calendar link toggled...");
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", economicCalendarLinkToggled);
        waitForElementToBeDisplayed(waits, economicCalendarLinkToggled);
        waitForElementToBeClickable(waits, economicCalendarLinkToggled);
        economicCalendarLinkToggled.click();
        LOGGER.info("The Economic Calendar link toggled click is complete.");
        return new EconomicCalendarPage(driver, waits);
    }
}
