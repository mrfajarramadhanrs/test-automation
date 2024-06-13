package steps;

import com.ApiController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


public class CheckOutTransactionSteps {
    private JSONObject transactionDetails;
    private JSONArray userDetails;
    private JSONArray wordsDetails;

    Response response;
    ApiController api = new ApiController();

    @Given("User get data from {string}")
    public void getAllData(String sourceFrom) {
        if (sourceFrom.equals("checkout transaction")) {
            response = api.apiGetCheckoutTransactions();
            transactionDetails = new JSONObject(response.asString());
        } else if (sourceFrom.equals("user history")) {
            response = api.apiGetUserHistory();
            userDetails = new JSONArray(response.asString());
        }else if(sourceFrom.equals("palindrome")){
            response = api.apiGetWords();
            wordsDetails = new JSONArray(response.asString());
        }
    }

    @Then("Verify response from {string}")
    public void verifyCheckoutTransaction(String responseFrom) {
        if (responseFrom.equals("checkout transaction")) {

            assertNotNull("transaction_data should not be null", transactionDetails.getJSONObject("transaction_data"));
            assertNotNull("seller_data should not be null", transactionDetails.getJSONArray("seller_data"));
            assertNotNull("customer_data should not be null", transactionDetails.getJSONObject("customer_data"));
            assertNotNull("shipping_address_data should not be null", transactionDetails.getJSONObject("shipping_address_data"));
        } else if (responseFrom.equals("user history")) {
            assertNotNull("user_history should not be null", userDetails);
        } else if (responseFrom.equals("palindrome")) {
            assertNotNull("user_history should not be null", wordsDetails);
        }


    }

    @When("Verify price from checkout transaction")
    public void verifyPriceAndTotalItems() {
        int price = 0;
        int qty = 0;
        transactionDetails = new JSONObject(response.asString());
        int amount = transactionDetails.getJSONObject("transaction_data").getInt("amount");
        JSONArray items = transactionDetails.getJSONObject("transaction_data").getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject itemDetails = items.getJSONObject(i);
            price = itemDetails.getInt("price");
            qty = itemDetails.getInt("quantity");

        }
        int totalPrice = price * qty;
        assertNotEquals(amount, totalPrice);
    }

    @When("Verify data customer and shipping is not equal")
    public void verifyCustomerAndShipping() {
        transactionDetails = new JSONObject(response.asString());
        Object customer = transactionDetails.getJSONObject("customer_data");
        Object shipping = transactionDetails.getJSONObject("shipping_address_data");

        assertNotEquals(customer, shipping);

    }

    @When("Verify last login with more than 1 years")
    public void getUserHistory() {
        ApiController api = new ApiController();
        long lastLoginTime;
        JSONObject lastLoginDetails;
        long oneYearAgo = Instant.now().minus(365, ChronoUnit.DAYS).toEpochMilli();

        for (int i = 0; i < userDetails.length(); i++) {
            lastLoginDetails = userDetails.getJSONObject(i);
            lastLoginTime = lastLoginDetails.getLong("last_login");
            if (lastLoginTime < oneYearAgo) {
                lastLoginDetails.put("status", "ACTIVE");
                JsonPath jsonPath = new JsonPath(lastLoginDetails.toString());
                String jsonArrayString = "[" + jsonPath.prettify() + "]";

                JSONArray getIndex = new JSONArray(api.apiUpdateUser(jsonArrayString).asString());
                String currentStatus = getIndex.getJSONObject(0).get("status").toString();
                Assert.assertEquals("ACTIVE", currentStatus);
            } else {
                lastLoginDetails.put("status", "INACTIVE");
                JsonPath jsonPath = new JsonPath(lastLoginDetails.toString());
                String jsonArrayString = "[" + jsonPath.prettify() + "]";

                JSONArray getIndex = new JSONArray(api.apiUpdateUser(jsonArrayString).asString());
                String currentStatus = getIndex.getJSONObject(0).get("status").toString();
                Assert.assertEquals("INACTIVE", currentStatus);
            }
        }
    }

    @Then("Verify if the words are palindrome")
    public boolean getWordsDetails() {
        JSONObject words;
        String checkerWords;
        for (int i = 0; i < wordsDetails.length(); i++) {
            words = wordsDetails.getJSONObject(i);
            checkerWords = words.getString("word");
            for (int n = 0; i < checkerWords.length() / 2; i++) {
                if (checkerWords.charAt(n) != checkerWords.charAt(checkerWords.length() - 1 - i)) {
                    System.out.println("This is Palindrome");
                }
            }
            System.out.println("This is not Palindrome");;

        }
        return false;
    }
}
