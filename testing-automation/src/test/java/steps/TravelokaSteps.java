package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.LocalDate;

public class TravelokaSteps {
    private String baseUrl = "https://www.traveloka.com/id-id";
    WebDriver driver;

    @Given("open the traveloka url")
    public void navigate_to_traveloka_url() {
        System.setProperty("webdriver.gecko.driver", "/Users/catalyst/Downloads/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
        driver = new FirefoxDriver(options);
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    @When("search Bandung Hotel for next week")
    public void search_hotel_at_bandung() {
        WebElement hotelIcon = driver.findElement(By.xpath("//svg[@data-id='IcProductDuotoneHotelFill']"));
        WebElement cityfield = driver.findElement(By.xpath("//input[@placeholder='Kota, hotel, tempat wisata']"));
        WebElement dateField = driver.findElement(By.xpath("//input[@data-testid='check-in-date-field']"));
        WebElement searchBtn = driver.findElement(By.xpath("//div[@data-testid='search-submit-button']"));
        WebElement closeGoogleBtn = driver.findElement(By.id("close"));
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(7);
        String futureDateString = futureDate.toString();

        hotelIcon.click();
        cityfield.sendKeys("Bandung");
        dateField.sendKeys(futureDateString);
        searchBtn.click();
    }

}
