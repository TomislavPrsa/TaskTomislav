package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.ReasearchAndEducationPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReasearchAndEducationPage.class);

    private Properties properties;

    public ConfigReader() {
        loadProperties();
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(inputStream);
            LOGGER.info("Loaded properties from config.properties file.");
        } catch (IOException e) {
            LOGGER.error("Failed to load config.properties file.", e);
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    public String getUrl() {
        LOGGER.info("Retrieving URL from config.properties file");
        return properties.getProperty("url");

    }

    public String getBrowser() {
        LOGGER.info("Retrieving browser from config.properties file");
        return properties.getProperty("browser");
    }

    public String getScreenSize() {
        LOGGER.info("Retrieving browser from config.properties file");
        return properties.getProperty("screenSize");
    }

}