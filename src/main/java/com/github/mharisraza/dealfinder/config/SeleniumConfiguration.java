package com.github.mharisraza.dealfinder.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumConfiguration {

    /*
     * setup webDriver with headless mode.
     * used PageLoadStrategy.EAGER to load page eagerly.
     */
    public static WebDriver setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
//        chromeOptions.addArguments("--headless=new");
        return new ChromeDriver(chromeOptions);
    }
}
