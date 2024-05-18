package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Steps.SoapDemo.SoapDemoSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("SOAP Demo Testing")
public class SoapDemoTest {
    SoapDemoSteps soapDemoSteps;

    @BeforeClass
    public void pageSetup(){
        RestAssured.filters(new AllureRestAssured());
        soapDemoSteps = new SoapDemoSteps();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Person Information Validation")
    @Story("Validate person details from SOAP response")
    @Description("This test case validates various aspects of the person information received" +
            " from the SOAP response, including name, SSN, DOB, favorite colors, and zip codes.")
    @Test(description = "Validate person information from SOAP response")
    public void personTest(){
        soapDemoSteps
                .createFindPersonRequestBody()
                .sendRequestForFindPerson()
                .deserializeResponse()
                .validateNotNull()
                .validatePersonName()
                .validateSSNValue()
                .validateNumberOfCharactersINSNN()
                .validateSSNPattern()
                .validateDOB()
                .validateFavouriteColors()
                .validatePersonLastName()
                .validateColor()
                .validateZipsNotEqual();
    }
}
