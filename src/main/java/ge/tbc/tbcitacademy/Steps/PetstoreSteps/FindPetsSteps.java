package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import ge.tbc.tbcitacademy.Data.Status;
import ge.tbc.tbcitacademy.Models.Requests.Petstore.Pet;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class FindPetsSteps {

    public Response findPets(RequestSpecification requestSpec){
        return RestAssured
                .given(requestSpec)
                .queryParam("status", Status.AVAILABLE.getValue())
                .when()
                .get("/pet/findByStatus");
    }

    public FindPetsSteps validateArrayContainsPet(Response response){
        List<Pet> pets = response.then().extract().body().jsonPath().getList("", Pet.class);
        assertThat(pets, hasItem(hasProperty("id", equalTo(8000L))));
        return this;
    }

    public Pet returnPetObject(Response response){
        return response.jsonPath()
                .getObject("find { it.id == 8000 }", Pet.class);
    }

    public FindPetsSteps validateExtractedPet(Pet petObject, Pet requestBody){
        assertThat(petObject.getId(), equalTo(requestBody.getId()));
        assertThat(petObject.getName(), equalTo(requestBody.getName()));
        assertThat(petObject.getStatus(), equalTo(requestBody.getStatus()));
        return this;
    }
}
