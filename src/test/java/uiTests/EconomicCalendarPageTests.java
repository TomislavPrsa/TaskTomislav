package uiTests;

import constants.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.EconomicCalendarPage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Execution(ExecutionMode.CONCURRENT)
public class EconomicCalendarPageTests extends TestSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(EconomicCalendarPageTests.class);
    private EconomicCalendarPage economicCalendarPage;

    @BeforeEach
    void setUpEconomicCalendarPage() {
        driver.get(Constants.ECONOMIC_CALENDAR_URL);
        economicCalendarPage = new EconomicCalendarPage(driver, wait);
    }

    @ParameterizedTest
    @MethodSource("getDays")
    void economicCalendarTest(List<String> days) {
        economicCalendarPage.verifyEconomicCalendarPageURL();
        if (economicCalendarPage.isCookieModalDisplayed()) {
            economicCalendarPage.clickOnAcceptAllButton();
        }
        economicCalendarPage.switchToIFrame("iFrameResizer0")
                .isCalendarIconDisplayed();
        for (String day : days) {
            economicCalendarPage
                    .clickOnSlideElementUntilTextualDate(day)
                    .verifyTimeSpan(day)
                    .verifyDateByOffset(day);
        }
    }

    @Test
    void economicCalendarTest() {
        economicCalendarPage.verifyEconomicCalendarPageURL();
        if (economicCalendarPage.isCookieModalDisplayed()) {
            economicCalendarPage.clickOnAcceptAllButton();
        }
        economicCalendarPage.clickOnDisclaimerLinkButton()
                .verifyDisclaimerUrl();

    }

    private static Stream<Arguments> getDays() {
        return Stream.of(
                Arguments.of(Arrays.asList("Today", "Tomorrow", "Next Week", "Next Month"))
        );

    }

}
