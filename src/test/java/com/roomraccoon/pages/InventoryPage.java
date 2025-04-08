package com.roomraccoon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {
    private By addToCartBackpack = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private By shoppingCartLink = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart() {
        driver.findElement(addToCartBackpack).click();
    }

    public void clickOnCart() {
        driver.findElement(shoppingCartLink).click();
    }
}
