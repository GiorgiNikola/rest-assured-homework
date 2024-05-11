package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.Petstore3Steps.AddNewPetSteps;
import ge.tbc.tbcitacademy.Steps.Petstore3Steps.PostPetOrderSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Epic("Petstore API Test")
public class Petstore3Test {
    RequestSpecification requestSpec;
    PostPetOrderSteps postPetOrderSteps;
    AddNewPetSteps addNewPetSteps;

    @BeforeClass
    public void pageSetup(){
        RestAssured.filters(new AllureRestAssured());
        postPetOrderSteps = new PostPetOrderSteps();
        addNewPetSteps = new AddNewPetSteps();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(Constants.petstore3Uri)
                .build();
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Petstore post placement")
    @Story("Posting pet order on petstore")
    @Description("Generate pet order body, send request then get response deserialize it and validate")
    @Test(description = "Generate pet order and send request")
    public void postPetOrderTest(){
        postPetOrderSteps
                .generatePet()
                .postPet(requestSpec)
                .deserializePet()
                .validatePet();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Add new pet to petstore")
    @Story("Adding new pet to petstore using xml")
    @Description("Generate new pet request body in xml format, " +
            "send request then get response deserialize it xml format and validate")
    @Test(description = "Adding new pet to petstore, using xml format to serialize and deserialize")
    public void addNewPetTest(){
        addNewPetSteps
                .createNewPetRequest()
                .postNewPet(requestSpec)
                .deserializeResponse()
                .validatePet();
    }
}
