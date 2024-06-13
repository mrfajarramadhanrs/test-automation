package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class LoremIpsumSteps {
        private String baseUrl = "https://www.lipsum.com/";
        WebDriver driver;



    @Given("open the lipsum url")
    public void navigate_to_lipsum_url() {
        System.setProperty("webdriver.gecko.driver", "/Users/catalyst/Downloads/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
        driver = new FirefoxDriver(options);
        driver.get(baseUrl);
    }

    @When("scroll to element text box for changing the value of text")
    public void scroll_to_element() {
        WebElement textbox = driver.findElement(By.xpath("//input[@name='amount']"));
        WebElement rdBtnWords = driver.findElement(By.xpath("//input[@value='words']"));
        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));

        assertTrue(textbox.isDisplayed());
        textbox.clear();
        textbox.sendKeys("500");
        rdBtnWords.click();
        submitBtn.click();
    }

    @Then("Verify all words that repeated more than 2 times")
    public void verify_words() {
        WebElement innerParagraph = driver.findElement(By.xpath("//div[@id='Inner']"));
        String paragraph = innerParagraph.getText();
        String[] words = paragraph.split("\\W+");


        Map<String, Integer> countWords = new HashMap<>();
        for (String count : words){
            count = count.toLowerCase();
            countWords.put(count, countWords.getOrDefault(count, 0) + 1);
        }
        //System.out.println(words);
        for(Map.Entry<String, Integer> entry : countWords.entrySet()){
            if (entry.getValue() > 2){
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }

        driver.quit();
    }
}
