package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import static org.testng.Assert.assertEquals;

public class FindPetsSteps {

    public Response findPets(RequestSpecification requestSpec){
        return RestAssured
                .given(requestSpec)
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus");
    }

    public FindPetsSteps validateArrayContainsPet(Response response){
        response
                .then()
                .body("id", Matchers.hasItem(8000));
        return this;
    }

    public JSONObject returnPetObject(Response response){
        LinkedHashMap<String, Object> petMap = response
                .jsonPath()
                .getJsonObject("find { it.id == 8000 }");
        return new JSONObject(petMap);
    }

    public FindPetsSteps validateExtractedPet(JSONObject petObject, JSONObject requestBody){
        assertEquals(petObject.getLong("id"), requestBody.getLong("id"));
        assertEquals(petObject.getString("name"), requestBody.getString("name"));
        assertEquals(petObject.getString("status"), requestBody.getString("status"));
        return this;
    }
}
