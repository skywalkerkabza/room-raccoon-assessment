Feature: Sauce Demo Purchase Flow

  Scenario: Add item to cart and complete checkout
    Given I navigate to "https://www.saucedemo.com/"
    When I login with username "standard_user" and password "secret_sauce"
    And I add "Sauce Labs Backpack" to the cart
    And I proceed to checkout with first name "KABELO", last name "Doe", and postal code "12345"
    Then I should see a confirmation message "Thank you for your order! Your order has been dispatched, and will arrive just as fast as the pony can get there!"
