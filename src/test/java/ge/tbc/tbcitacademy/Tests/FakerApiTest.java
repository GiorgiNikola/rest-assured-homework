package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.FakerApiSteps.CreditCardValidationSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Faker API Test")
public class FakerApiTest {
    CreditCardValidationSteps creditCardValidationSteps;
    RequestSpecification requestSpec;
    @BeforeClass
    public void pageSetup(){
        RestAssured.filters(new AllureRestAssured());
        creditCardValidationSteps = new CreditCardValidationSteps();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(Constants.fakerApiUri)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Credit Card Validation")
    @Story("Validate Credit Card Response Schema")
    @Description("Validate the response schema for credit card data obtained from the Faker API")
    @Test(description = "Test case to validate the credit card response schema")
    public void creditCardSchemaTest(){
        creditCardValidationSteps
                .getResponse(requestSpec)
                .validateSchema();
    }
}
