package ge.tbc.tbcitacademy.Steps.FakerApiSteps;

import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreditCardValidationSteps {
    File schema = new File("src/main/resources/credit_cards_schema.json");
    Response response;

    @Step("Get response from faker api")
    public CreditCardValidationSteps getResponse(RequestSpecification requestSpec){
        response = given(requestSpec)
                .when()
                .get("/credit_cards?_quantity=2");
        return this;
    }

    @Step("Validate response schema")
    public CreditCardValidationSteps validateSchema(){
        response
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schema));
        return this;
    }

}
