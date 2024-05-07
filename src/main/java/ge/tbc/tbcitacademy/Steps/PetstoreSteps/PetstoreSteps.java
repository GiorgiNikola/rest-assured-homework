package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.notNullValue;

public class PetstoreSteps {
    public PetstoreSteps() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public Response postOrder(){
        return RestAssured
                .given()
                .contentType(Constants.responseFormat)
                .body("{ \"id\": 35, \"petId\": 0, \"quantity\": 0, \"shipDate\": \"2023-05-04T12:00:00.000+0000\", " +
                        "\"status\": \"placed\", \"complete\": true }")
                .accept(Constants.responseFormat)
                .when()
                .post("/store/order");
    }

    public Response updatePet(int petId){
        return RestAssured
                .given()
                .formParams("petId", petId,
                        "name", "manuchar",
                        "status", "sold")
                .accept(Constants.responseFormat)
                .when()
                .post("/pet/{petId}", petId);
    }

    public PetstoreSteps validateUpdatedPetResponse(Response response){
        response
                .then()
                .body("code", notNullValue(),
                        "type", notNullValue(),
                        "message", notNullValue());
        return this;
    }

    public PetstoreSteps validateOrderResponseInfo(Response response){
        response
                .then()
                .body("id", Matchers.is(35),
                        "petId" , Matchers.is(0),
                        "shipDate", Matchers.is("2023-05-04T12:00:00.000+0000"));
        return this;
    }

    public PetstoreSteps validateStatusCode(Response response, int statusCode){
        response
                .then()
                .statusCode(statusCode);
        return this;
    }

    public Response loginWithUser(){
        return RestAssured
                .given()
                .queryParam("username", Constants.username)
                .queryParam("password", Constants.password)
                .accept(Constants.responseFormat)
                .when()
                .get("/user/login");
    }

    public String validateSignificantNumbers(Response response){
        response
                .then()
                .body("message", Matchers.matchesPattern(".*\\d{10}.*"));
        String number = response.jsonPath().get("message");
        return number.replaceAll("[^0-9]", "").substring(0, 10);
    }

    public PetstoreSteps printNumbers(String significantNumbers){
        System.out.println(Constants.printingText + significantNumbers);
        return this;
    }
}
