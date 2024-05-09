package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Models.Responses.Formula1.DriversItem;
import ge.tbc.tbcitacademy.Steps.Formula1Steps.ExtractDriverSteps;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class F1Test {
    ExtractDriverSteps extractDriverSteps;

    @BeforeClass
    public void getResponse(){
        extractDriverSteps = new ExtractDriverSteps();
    }

    @Test
    public void driverTest(){
        Response response = extractDriverSteps
                .getResponse();
        DriversItem extractedDriver = extractDriverSteps
                .extractDriver(response);
        extractDriverSteps
                .validateDriver(extractedDriver);
    }
}
