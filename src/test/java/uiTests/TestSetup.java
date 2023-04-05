package uiTests;

import browserFactory.BrowserFactory;
import driverSingleton.WebDriverManager;
import driverSingleton.WebDriverWaits;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;


public abstract class TestSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestSetup.class);
    protected ConfigReader configReader;
    protected WebDriverWait wait;
    protected WebDriver driver;

    @BeforeEach
    public void beforeTest() {
        configReader = new ConfigReader();
        WebDriverManager.getInstance().setDriver(BrowserFactory.getBrowser(configReader.getBrowser()));
        driver = WebDriverManager.getInstance().getDriver();
        wait = WebDriverWaits.getWait(driver);
        driver.get(configReader.getUrl());
        driver.manage().window().maximize();
        String screenSize = configReader.getScreenSize();
        switch (screenSize) {
            case "1024x768":
                driver.manage().window().setSize(new Dimension(1024, 768));
                break;
            case "800x600":
                driver.manage().window().setSize(new Dimension(800, 600));
                break;
            default:
                driver.manage().window().maximize();
                break;
        }

    }
    @AfterEach
    public void afterTest() {
        WebDriverWaits.removeWait();
        WebDriverManager.getInstance().closeDriver();
    }
}

