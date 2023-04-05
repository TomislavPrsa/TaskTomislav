package browserFactory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BrowserFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserFactory.class);

    private BrowserFactory() {
    }

    public static WebDriver getBrowser(String browser) {
        WebDriver driver = null;
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                driver = new ChromeDriver(options);
                LOGGER.info("Chrome browser opened successfully.");
            }
            case "firefox" -> {
                driver = new FirefoxDriver();
                LOGGER.info("Firefox browser opened successfully.");
            }
            case "edge" -> {
                driver = new EdgeDriver();
                LOGGER.info("Edge browser opened successfully.");
            }
            default -> LOGGER.error("Invalid browser type provided.");
        }
        return driver;
    }

}