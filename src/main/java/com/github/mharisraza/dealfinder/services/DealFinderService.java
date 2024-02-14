package com.github.mharisraza.dealfinder.services;

import com.github.mharisraza.dealfinder.config.SeleniumConfiguration;
import com.github.mharisraza.dealfinder.models.Product;
import com.github.mharisraza.dealfinder.models.requestmodels.DealFinderForm;
import com.github.mharisraza.dealfinder.utils.Constant;
import com.github.mharisraza.dealfinder.utils.WebDriverHelper;
import org.apache.juli.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DealFinderService {

    Logger LOGGER = LoggerFactory.getLogger(DealFinderService.class);

    private final ThreadLocal<WebDriverHelper> webDriverHelperThreadLocal = new ThreadLocal<>();

    public List<Product> getBestDeals(DealFinderForm dealFinderForm) {
       return startScraperAndGetDeals(dealFinderForm);
    }

    public List<Product> startScraperAndGetDeals(DealFinderForm dealFinderForm) {

       List<String> selectedPlatforms = dealFinderForm.getSelectedPlatforms();
       AtomicReference<List<Product>> productsRef = new AtomicReference<>(new ArrayList<>());
       CountDownLatch latch = new CountDownLatch(selectedPlatforms.size());


        ExecutorService executorService = Executors.newFixedThreadPool(selectedPlatforms.size());
       selectedPlatforms.forEach((platform) -> {
           executorService.submit(() -> {

               WebDriverHelper helper = new WebDriverHelper();
               webDriverHelperThreadLocal.set(helper);

              List<Product> scrapedDeals = scrapDeals(platform, dealFinderForm.getProductTitle(), dealFinderForm.getPriceRange(), dealFinderForm.getNumberOfTotalBestDealsToGet());
              productsRef.updateAndGet((existingProducts) -> {
                  existingProducts.addAll(scrapedDeals);
                  return existingProducts;
              });

              helper.shutDownScraper();
              webDriverHelperThreadLocal.remove();
              latch.countDown();
           });
       });

        // Wait for all tasks to complete
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.error("Thread Interrupted: {0}", e.getStackTrace());
        }

        executorService.shutdown();
        return productsRef.get();
    }


    private List<Product> scrapDeals(String platform, String productTitle, Double priceRange, Integer numberOfTotalBestDealsToGet) {
        List<Product> bestDeals = new ArrayList<>();

        switch (platform) {
            case "amazon" -> bestDeals.addAll(getBestDealsFromAmazon(productTitle, priceRange, numberOfTotalBestDealsToGet));
            case "flipkart" -> bestDeals.addAll(getBestDealsFromFlipkart(productTitle, priceRange));
        }
        return bestDeals;
    }

    private List<Product> getBestDealsFromAmazon(String title, Double priceRange, int numberOfTotalBestDealsToGet) {

        // go to the platform.
        WebDriverHelper driverHelper = getCurrentInstanceOfWebDriverHelper();
        driverHelper.goTo(Constant.AMAZON_PLATFORM_INDIA);

        //TODO: verify if there's no captcha then proceed else suspend.

        // input title and search
        driverHelper.getElementIfExist(By.id("twotabsearchtextbox")).sendKeys(title);
        driverHelper.getElementIfExist(By.id("nav-search-submit-button")).click();
        return new ArrayList<>();
    }

    private List<Product> getBestDealsFromFlipkart(String title, Double priceRange) {
        return new ArrayList<>();
    }

    private WebDriverHelper getCurrentInstanceOfWebDriverHelper() {
        return webDriverHelperThreadLocal.get();
    }


}
