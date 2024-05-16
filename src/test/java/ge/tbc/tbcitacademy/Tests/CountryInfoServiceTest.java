package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Steps.CountryInfoService.ContinentSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import jakarta.xml.bind.JAXBException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Country Info Service Test")
public class CountryInfoServiceTest {

    ContinentSteps continentSteps;

    @BeforeClass
    public void pageSetup(){
        RestAssured.filters(new AllureRestAssured());
        continentSteps = new ContinentSteps();
    }
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Continent Information")
    @Story("Validate Continent Information")
    @Description("This test case validates various aspects of the continent" +
            " information retrieved from the Country Info Service.")
    @Test(description = "Validate Continent Information")
    public void continentTest() throws JAXBException {
        continentSteps
                .serializeRequest()
                .sendRequest()
                .initialiseLists()
                .validateCount()
                .validateNames()
                .validateAntarctica()
                .validateLastNode()
                .validateUniqueness()
                .validateSCodeSName()
                .validateSCodePattern()
                .validateSNameOrder()
                .validateNotNumeric()
                .validateOceania()
                .findAllSName()
                .noNumericCharacters();
    }
}
