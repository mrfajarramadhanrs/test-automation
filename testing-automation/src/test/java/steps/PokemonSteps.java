package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class PokemonSteps {

    private String baseUrl = "https://pokeapi.co/api/v2/pokemon-species";
    private String pikachuUrl;
    private JSONObject pikachuSpeciesDetails;
    private JSONObject evolutionChain;
    private String Uri;

    @Given("Get the Pokemon list")
    public void getPokemonSpeciesList() {
        int offset = 5;
        int limit = 20;
        boolean foundPikachu = false;


        while (!foundPikachu) {
            Response response = given()
                    .queryParam("offset", offset)
                    .queryParam("limit", limit)
                    .when()
                    .get(baseUrl)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

//            System.out.println(response);
            JSONObject jsonObject = new JSONObject(response.asString());
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                if (results.getJSONObject(i).getString("name").equals("pikachu")) {
                    foundPikachu = true;
                    pikachuUrl = results.getJSONObject(i).getString("url");
                    //System.out.println(pikachuUrl);
                    break;
                }
            }
        }
        Uri = pikachuUrl;
    }

    @When("I find Pikachu in the list")
    public void findPikachu() {
        System.out.println(Uri);
        assertTrue("id for Pikachu is 25", Uri.contains("/25/"));
        assertNotNull("Pikachu URL should not be null", Uri);

    }

    @Then("I should get Pikachu species details")
    public void getPikachuSpeciesDetails() {
        Response response = given()
                .when()
                .get(pikachuUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();

        pikachuSpeciesDetails = new JSONObject(response.asString());
        assertNotNull("Pikachu species details should not be null", pikachuSpeciesDetails);
        assertEquals("the color of Pikachu is 'yellow'", "yellow",pikachuSpeciesDetails.getJSONObject("color").getString("name"));
        assertNotNull("'evolution_chain' of Pikachu should not be null", pikachuSpeciesDetails.getJSONObject("evolution_chain").getString("url"));
    }

    @Then("I should get Pikachu evolution chain")
    public void getPikachuEvolutionChain(){
        String evolutionChainUrl = pikachuSpeciesDetails.getJSONObject("evolution_chain").getString("url");

        Response response = given()
                .when()
                .get(evolutionChainUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();

        evolutionChain = new JSONObject(response.asString());

        assertNotNull("Pikachu evolution chain should not be null", evolutionChain);
        assertTrue("evolution chain should have 'chain'", evolutionChain.has("chain"));

    }

    @Then("I should find the next evolution of Pikachu")
    public void findNextEvolutionOfPikachu() {
        JSONObject getChain = evolutionChain.getJSONObject("chain");
        JSONArray evolvesTo = getChain.getJSONArray("evolves_to");
        assertTrue("Evolves_to should not be empty", evolvesTo.length() > 0);

        String nextEvolution = null;
        for (int i = 0; i < evolvesTo.length(); i++) {
            JSONObject evolutionDetails = evolvesTo.getJSONObject(i);
            if (evolutionDetails.getJSONObject("species").getString("name").equals("pikachu")) {
                nextEvolution = evolutionDetails.getJSONArray("evolves_to").getJSONObject(0).getJSONObject("species").getString("name");
                break;
            }
        }

        assertEquals("raichu", nextEvolution);// Pikachu evolves into Raichu
        assertTrue("Chain should contain 'evolves_to' key", getChain.has("evolves_to"));
        assertEquals("id should be '10'",10,evolutionChain.getInt("id"));


    }
}
