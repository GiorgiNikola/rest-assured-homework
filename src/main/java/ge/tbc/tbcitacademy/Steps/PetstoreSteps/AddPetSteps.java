package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddPetSteps {
    Faker faker = new Faker();
    public JSONObject createPetRequestBody(){
        return new JSONObject()
                .put("id", 8000)
                .put("category", new JSONObject()
                        .put("id", 8000)
                        .put("name", faker.animal().name()))
                .put("name", faker.animal().name())
                .put("photoUrls", new JSONArray()
                        .put("string"))
                .put("tags", new JSONArray()
                        .put(new JSONObject()
                                .put("id", 8000)
                                .put("name", faker.animal().name())))
                .put("status", "available");
    }

    public Response sendRequest(RequestSpecification requestSpec, JSONObject jsonObject){
        return RestAssured
                .given(requestSpec)
                .body(jsonObject.toString())
                .when()
                .post("/pet");
    }

    public AddPetSteps validatePet(Response response, JSONObject petJson){
        response
                .then()
                .body("id", Matchers.is(petJson.get("id")),
                        "name", Matchers.is(petJson.get("name")),
                        "status", Matchers.is(petJson.get("status")));
        return this;
    }
}
