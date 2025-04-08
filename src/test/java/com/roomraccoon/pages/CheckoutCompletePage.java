// src/test/java/com/roomraccoon/pages/CheckoutCompletePage.java
package com.roomraccoon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {
    //private By thankYouMessage = By.xpath("//h2[@data-test='complete-header']");
    private By confirmationMessage = By.xpath("//div[@data-test='complete-text']");
    private By backToProductsButton = By.id("back-to-products");

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

