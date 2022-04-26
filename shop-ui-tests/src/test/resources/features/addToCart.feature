Feature: AddToCart

  Scenario Outline: Successful add product to cart
    Given I open web browser for frontend testing
    When I navigate to product info page by id = "<prodId>"
    Then product name should be "<prodName>"
    And I click on add to cart button
    And I navigate to cart page
    Then in cart product name should be "<prodName>"

    Examples:
      | prodId | prodName |
      | 1 | AMD Ryzen Threadripper PRO 3995WX ohne Kühler |



