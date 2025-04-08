// src/test/java/com/roomraccoon/stepdefinitions/SauceDemoSteps.java
package com.roomraccoon.stepdefinitions;

import com.roomraccoon.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;

public class SauceDemoSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutInformationPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) throws Exception {
        this.scenario = scenario;
        String hubUrl = System.getenv("SELENIUM_HUB_URL");
        if (hubUrl == null || hubUrl.isEmpty()) {
            hubUrl = "http://host.docker.internal:4444/wd/hub";
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL(hubUrl), capabilities);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutInfoPage = new CheckoutInformationPage(driver);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                // Take screenshot
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName() + "_failure");
                
                // Additional debug info
                String pageSource = driver.getPageSource();
                scenario.attach(pageSource, "text/html", "Page Source on Failure");
                
                // Log current URL
                scenario.log("Current URL: " + driver.getCurrentUrl());
            } catch (Exception e) {
                scenario.log("Failed to capture screenshot: " + e.getMessage());
            }
        }
        
        if (driver != null) {
            driver.quit();
        }
    }

    // Helper method to take screenshots for successful steps if needed
    private void takeScreenshot(String stepName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            scenario.attach(screenshot, "image/png", stepName + "_" + timestamp);
        } catch (Exception e) {
            scenario.log("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        loginPage.navigateTo(url);
        takeScreenshot("After_Navigation");
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.login(username, password);
        takeScreenshot("After_Login");
    }

    @When("I add {string} to the cart")
    public void i_add_to_the_cart(String item) {
        if (item.equalsIgnoreCase("Sauce Labs Backpack")) {
            inventoryPage.addItemToCart();
            takeScreenshot("After_Add_To_Cart");
        }
    }

    @When("I proceed to checkout with first name {string}, last name {string}, and postal code {string}")
    public void i_proceed_to_checkout(String firstName, String lastName, String postalCode) {
        inventoryPage.clickOnCart();
        takeScreenshot("Cart_Page");
        
        cartPage.clickCheckout();
        takeScreenshot("Checkout_Info_Page");
        
        checkoutInfoPage.fillCheckoutInformation(firstName, lastName, postalCode);
        takeScreenshot("After_Filling_Info");
        
        checkoutOverviewPage.clickFinish();
        takeScreenshot("Checkout_Complete_Page");
    }

    @Then("I should see a confirmation message {string}")
    public void i_should_see_a_confirmation_message(String expectedMessage) {
        try {
            String actualMessage = checkoutCompletePage.getConfirmationMessage();
            Assert.assertTrue(actualMessage.contains(expectedMessage), 
                "Confirmation message did not contain expected text. Expected: " + expectedMessage + 
                ", Actual: " + actualMessage);
            takeScreenshot("Confirmation_Message");
        } catch (AssertionError e) {
            takeScreenshot("Confirmation_Message_Failure");
            throw e;
        }
        
        checkoutCompletePage.clickBackToProducts();
        takeScreenshot("After_Return_To_Products");
    }

    @And("I pause for one minute")
    public void i_pause_for_one_minute() throws InterruptedException {
    // Pause for 60,000 milliseconds (i.e., 1 minute)
    Thread.sleep(60000);
}
}