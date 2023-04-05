package apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.ReasearchAndEducationPage;

import java.util.List;


import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwapiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwapiTest.class);

    private static final String BASE_URI = "https://swapi.dev/api";

    private static Response filmResponse;
    private static Response characterResponse;
    private static Response starshipResponse;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @Order(1)
    @DisplayName("Find a Film with title 'A New Hope'")
    public void testFindFilmWithNewHope() {
        LOGGER.info("Running testFindFilmWithNewHope()...");
        filmResponse = given()
                .queryParam("search", "A New Hope")
                .when()
                .get("/films");

        filmResponse.then().statusCode(200);

        String filmTitle = filmResponse.jsonPath().getString("results[0].title");
        Assertions.assertEquals("A New Hope", filmTitle);
        LOGGER.info("Finished testFindFilmWithNewHope().");
    }

    @Test
    @Order(2)
    @DisplayName("Find character 'Biggs Darklighter' in the Film")
    public void testFindBiggsDarklighterInCharacters() {
        LOGGER.info("Running testFindBiggsDarklighterInCharacters()...");
        Assertions.assertNotNull(filmResponse);

        String characterUrl = "";
        List<String> characterUrls = filmResponse.jsonPath().getList("results[0].characters");
        for (String url : characterUrls) {
            Response characterResponse = given().when().get(url);
            characterResponse.then().statusCode(200);

            String characterName = characterResponse.jsonPath().getString("name");
            if (characterName.equals("Biggs Darklighter")) {
                characterUrl = url;
                break;
            }
        }

        characterResponse = given().when().get(characterUrl);
        characterResponse.then().statusCode(200);

        String characterName = characterResponse.jsonPath().getString("name");
        Assertions.assertEquals("Biggs Darklighter", characterName);
        LOGGER.info("Finished testFindBiggsDarklighterInCharacters().");
    }

    @Test
    @Order(3)
    @DisplayName("Find Biggs Darklighter's Starship Model")
    public void testFindBiggsStarship() {
        LOGGER.info("Running testFindBiggsStarship()...");
        Assertions.assertNotNull(characterResponse);

        List<String> starshipUrls = characterResponse.jsonPath().getList("starships");
        String starshipUrl = starshipUrls.get(0);
        starshipResponse = given().when().get(starshipUrl);
        starshipResponse.then().statusCode(200);

        String starshipModel = starshipResponse.jsonPath().getString("model");
        LOGGER.info("Biggs Darklighter's Starship Model: {}", starshipModel);
        LOGGER.info("Finished testFindBiggsStarship().");
    }

    @Test
    @Order(4)
    @DisplayName("Check if Luke Skywalker is a Starfighter Pilot")
    public void testFindLukeSkywalkerAsStarfighterPilot() {
        LOGGER.info("Running testFindLukeSkywalkerAsStarfighterPilot()...");
        Assertions.assertTrue(starshipResponse != null);

        String starshipClass = starshipResponse.jsonPath().getString("starship_class");
        LOGGER.info("Starship class: {}", starshipClass);
        Assertions.assertEquals(starshipClass, "Starfighter");

        List<String> pilotUrls = starshipResponse.jsonPath().getList("pilots");
        LOGGER.info("Pilot URLs: {}", pilotUrls);
        boolean isLukeSkywalkerPilot = false;
        for (String url : pilotUrls) {
            Response response = given().when().get(url);
            response.then().statusCode(200);

            String pilotName = response.jsonPath().getString("name");
            LOGGER.info("Pilot name: {}", pilotName);
            if (pilotName.equals("Luke Skywalker")) {
                isLukeSkywalkerPilot = true;
                break;
            }
        }
        LOGGER.info("Is Luke Skywalker a starfighter pilot? {}", isLukeSkywalkerPilot);
        Assertions.assertTrue(isLukeSkywalkerPilot);
    }
}
