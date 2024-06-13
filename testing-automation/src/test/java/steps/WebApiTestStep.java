package steps;

import com.ApiController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class WebApiTestStep {
    static String url;

    ApiController api = new ApiController();
    JSONObject transactionDetails;
    JSONArray userDetails;
    Response response;
    WebDriver driver;

    @Given("User hit API from transaction data -> seller data")
    public void userHitAPIFromTransactionDataSellerData() {
        response = api.apiGetCheckoutTransactions();
        transactionDetails = new JSONObject(response.asString());
        userDetails = new JSONArray(transactionDetails.get("seller_data").toString());
        url = new JSONObject(userDetails.get(0).toString())
                .get("url").toString();
    }

    @And("User open url data from the api")
    public void userOpenUrlDataFromTheApi() {
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/gecko/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
        driver = new FirefoxDriver(options);
        driver.get(url);
        driver.manage().window().maximize();
    }

    @When("User go to about us")
    public void userGoToAboutUs() {
        WebElement buttonAboutUs = driver.findElement(By.xpath("//a[@class='nav-link' and text()='About Us']"));

        assertTrue(buttonAboutUs.isDisplayed());
        buttonAboutUs.click();

    }

    @Then("User verify people on about us contains with {string}")
    public void userVerifyPeopleOnAboutUsContainsWith(String word) {
        WebElement isOnAboutUsPage = driver.findElement(By.xpath("//h1[text()='About Us']"));

        Assert.assertTrue(isOnAboutUsPage.isDisplayed());
            List<WebElement> people = driver.findElements(By.xpath("//div[@class='col-md-3 team-item']")); // Adjust the XPath as needed

            // Iterate through each person element
            for (WebElement person : people) {
                // Find name and title elements within each person element
                String name = person.findElement(By.xpath(".//h4")).getText();
                String title = person.findElement(By.xpath(".//p")).getText();
                if (title.toLowerCase().contains(word)) {
                    System.out.println("Engineer found: " + name);
                }
            }
            // Close the browser
            driver.quit();

    }

}
