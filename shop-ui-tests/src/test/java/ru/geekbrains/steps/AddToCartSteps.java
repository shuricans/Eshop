package ru.geekbrains.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.geekbrains.DriverInitializer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddToCartSteps {

    private WebDriver webDriver = null;

    @Given("^I open web browser for frontend testing$")
    public void iOpenWebBrowser() {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to product info page by id = \"([^\"]*)\"$")
    public void iNavigateToProductInfoPageById(String prodId) throws Throwable {
        Thread.sleep(2000);
        String url = DriverInitializer.getProperty("frontend.product.url");
        webDriver.get(url + "/" + prodId);
    }

    @Then("^product name should be \"([^\"]*)\"$")
    public void productNameShouldBe(String prodName) throws Throwable {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.cssSelector(
                "body > app-root > div > app-product-info-page " +
                "> div > div.col-md-6.col-xs-12.ng-star-inserted > h2"));
        assertThat(webElement.getText()).isEqualTo(prodName);
    }

    @And("^I click on add to cart button$")
    public void iClickOnAddToCartButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.cssSelector(
                "body > app-root > div > app-product-info-page " +
                "> div > div.col-md-6.col-xs-12.ng-star-inserted " +
                "> form > div:nth-child(1) > button"));
        webElement.click();
    }

    @And("^I navigate to cart page$")
    public void iNavigateToCartPage() throws InterruptedException {
        Thread.sleep(2000);
        webDriver.get(DriverInitializer.getProperty("frontend.cart.url"));
    }

    @Then("^in cart product name should be \"([^\"]*)\"$")
    public void inCartProductNameShouldBe(String prodName) throws Throwable {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.xpath(
                "/html/body/app-root/div/app-cart-page/div/div[1]/table/tbody/tr/td[1]/a"));
        assertThat(webElement.getText()).isEqualTo(prodName);
    }

    @After
    public void quitBrowser() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
