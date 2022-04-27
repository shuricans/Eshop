package ru.geekbrains.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.geekbrains.DriverInitializer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginSteps {
    private WebDriver webDriver = null;

    @Given("^I open web browser$")
    public void iOpenWebBrowser() {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to login\\.html page$")
    public void iNavigateToLoginHtmlPage() throws Throwable {
        Thread.sleep(2000);
        webDriver.get(DriverInitializer.getProperty("login.url"));
    }

    @When("^I provide login as \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void iProvideLoginAsAndPasswordAs(String login, String password) throws Throwable {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.id("login"));
        webElement.sendKeys(login);
        webElement = webDriver.findElement(By.id("password"));
        webElement.sendKeys(password);
    }

    @When("^I click on login button$")
    public void iClickOnLoginButton() throws Throwable {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.id("btn-login"));
        webElement.click();
    }

    @When("^I click on menu button if it displayed$")
    public void iClickOnMenuButton() throws Throwable {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.id("btn-navbar-toggler"));
        if (webElement.isDisplayed()) {
            webElement.click();
        }
    }

    @Then("^name should be \"([^\"]*)\"$")
    public void nameShouldBe(String name) throws Throwable {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.id("dd_user"));
        assertThat(webElement.getText()).isEqualTo(name);
    }

    @Given("^any user logged in$")
    public void userLoggedIn() throws InterruptedException {
        Thread.sleep(2000);
        webDriver.findElement(By.id("dd_user"));
    }

    @When("^click logout button$")
    public void clickLogoutButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement webElement = webDriver.findElement(By.id("btn-logout"));
        webElement.click();
    }

    @Then("^user logged out$")
    public void userLoggedOut() throws InterruptedException {
        Thread.sleep(2000);
        webDriver.findElement(By.id("login"));
        webDriver.findElement(By.id("password"));
    }

    @After
    public void quitBrowser() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
