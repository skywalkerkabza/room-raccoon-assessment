package com.roomraccoon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {
    private By confirmationMessage = By.cssSelector("[data-test='checkout-complete-container']");
    private By backToProductsButton = By.cssSelector("[data-test='back-to-products']");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmationMessage).getText();
    }

    public void clickBackToProducts() {
        driver.findElement(backToProductsButton).click();
    }
}
