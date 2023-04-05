package uiTests;

import constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.DisclaimerPageObject;

@Execution(ExecutionMode.CONCURRENT)
public class DisclaimerPageTests extends TestSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisclaimerPageTests.class);

    private DisclaimerPageObject disclaimerPage;

    @BeforeEach
    public void setUpEconomicCalendarPage() {
        driver.get(Constants.DISCLAIMER_URL);
        disclaimerPage = new DisclaimerPageObject(driver, wait);
    }

    @Test
    public void disclaimerTest() {
        disclaimerPage.verifyDisclaimerUrl();
        if (disclaimerPage.isCookieModalDisplayed())
            disclaimerPage.clickOnAcceptAllButton();
        disclaimerPage.clickOnRiskWarningLinkAndVeryNewTab();
    }
}
