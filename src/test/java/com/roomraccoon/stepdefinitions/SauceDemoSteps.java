package com.roomraccoon.stepdefinitions;

import com.roomraccoon.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import org.testng.Assert;

public class SauceDemoSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutInformationPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    // Setup WebDriver using Selenium Grid before each scenario
    @Before
    public void setUp() throws Exception {
        // Choose browser dynamically as needed; here we use Chrome as an example
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        // Initialize pages
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutInfoPage = new CheckoutInformationPage(driver);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        loginPage.navigateTo(url);
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.login(username, password);
    }

    @When("I add {string} to the cart")
    public void i_add_to_the_cart(String item) {
        // Currently supporting only "Sauce Labs Backpack"
        if (item.equalsIgnoreCase("Sauce Labs Backpack")) {
            inventoryPage.addItemToCart();
        }
    }

    @When("I proceed to checkout with first name {string}, last name {string}, and postal code {string}")
    public void i_proceed_to_checkout(String firstName, String lastName, String postalCode) {
        inventoryPage.clickOnCart();
        cartPage.clickCheckout();
        checkoutInfoPage.fillCheckoutInformation(firstName, lastName, postalCode);
        checkoutOverviewPage.clickFinish();
    }

    @Then("I should see a confirmation message {string}")
    public void i_should_see_a_confirmation_message(String expectedMessage) {
        String actualMessage = checkoutCompletePage.getConfirmationMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Confirmation message did not match.");
        checkoutCompletePage.clickBackToProducts();
    }
}
