package com.github.mharisraza.dealfinder.utils;

import com.github.mharisraza.dealfinder.config.SeleniumConfiguration;
import com.github.mharisraza.dealfinder.exceptions.ExpectedPageNotLoadedException;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;


/*
 * This class is responsible for holding common methods for scraping products.
 */
public class WebDriverHelper {

    private WebDriver driver;

    public WebDriverHelper() {
        driver = SeleniumConfiguration.setup();
    }


    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public WebElement getElementIfExist(By locator) {
        if (isElementPresent(locator)) return driver.findElement(locator);
        return null;
    }

    public List<WebElement> getElementsIfExists(By locator) {
        if (isElementPresent(locator)) return driver.findElements(locator);
        return null;
    }

    public static WebElement getRelatedElementIfExist(WebElement element, By relatedLocator) {
        try {
            return element.findElement(relatedLocator);
        } catch (NoSuchElementException ignored) {
            return null;
        }
    }

    public void waitUntilExpectedPageLoaded(String expectedUrl, By elementLocator) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.MAX_WAIT_TIME));
        try {
            wait.until(ExpectedConditions.urlContains(expectedUrl));
            if (elementLocator != null) {
                wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            }
        } catch (Exception e) {
            throw new ExpectedPageNotLoadedException(String.format("Expected page '%s' not loaded in %d seconds", expectedUrl, Constant.MAX_WAIT_TIME));
        }
    }

    public void goTo(String url) {
        if (url.isBlank()) throw new RuntimeException("Can't go to the page, provided URL is empty or null");
        driver.get(url);
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    public void shutDownScraper() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}