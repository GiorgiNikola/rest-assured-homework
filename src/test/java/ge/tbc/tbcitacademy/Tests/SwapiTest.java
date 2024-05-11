package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.SwapiSteps.PlanetsSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Star Wars API Test")
public class SwapiTest {
    PlanetsSteps planetsSteps;
    RequestSpecification requestSpec;

    @BeforeClass
    public void beforeClass(){
        RestAssured.filters(new AllureRestAssured());
        planetsSteps = new PlanetsSteps();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(Constants.swapApiBaseUri)
                .setAccept(ContentType.JSON)
                .build();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Planets")
    @Story("Validate planet fields")
    @Description("Validate specific fields of planets")
    @Test(description = "Getting planets and doing things with them")
    public void planetsTest(){
        planetsSteps
                .getResponse(requestSpec)
                .deserializeResponse()
                .identifyThreeMostRecentPlanets()
                .fiveValidation()
                .getPlanetWithBiggestRotationPeriod()
                .deserializeResident()
                .residentValidation();
    }
}
