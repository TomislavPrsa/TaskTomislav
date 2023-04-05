package uiTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.HomePage;
import pageObjects.ReasearchAndEducationPage;

@Execution(ExecutionMode.CONCURRENT)
public class HomePageAndResearchTest extends TestSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageAndResearchTest.class);
    private HomePage homePage;
    private ReasearchAndEducationPage reasearchAndEducationPage;

    @BeforeEach
    public void HomePageTestSetup() {
        homePage = new HomePage(driver, configReader, wait);
        reasearchAndEducationPage = new ReasearchAndEducationPage(driver, wait);
    }

    @Test
    public void HomePageAndResearchTest() {
        homePage.verifyHomePageURL()
                .verifyCookieModal()
                .clickOnAcceptAllButton()
                .verifyTermsAndConditionsLink();
        if (homePage.isToggleMenuButtonDisplayed()) {
            homePage.clickOnResearchAndEducationLinkToggled()
                    .clickOnEconomicCalendarLinkToggled()
                    .verifyEconomicCalendarPageURL();
            ;
        } else {
            homePage.clickOnResearchAndEducationLink()
                    .verifyResearchAndEducationButtonIsSelected()
                    .verifyResearchAndEducationTitle()
                    .clickOnEconomicCalendarLink()
                    .verifyEconomicCalendarPageURL();
        }
    }
}
