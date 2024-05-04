package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.PetstoreSteps;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PetstoreTest {
    PetstoreSteps petstoreSteps;
    @BeforeClass
    public void pageSetup(){
        petstoreSteps = new PetstoreSteps();
    }

    @Test
    public void validateFormTest(){
        Response orderResponse = petstoreSteps.postOrder();
        petstoreSteps
                .validateOrderResponseInfo(orderResponse);
    }

    @Test
    public void updatePetTest(){
        Response updateResponse = petstoreSteps.updatePet(Constants.petId1);
        petstoreSteps
                .validateUpdatedPetResponse(updateResponse);
        Response anotherResponse = petstoreSteps.updatePet(Constants.petId2);
        petstoreSteps
                .validateStatusCode(anotherResponse, Constants.errorCode);
    }

    @Test
    public void loginTest(){
        Response response = petstoreSteps
                .loginWithUser();
        String significantNumbers = petstoreSteps
                .validateStatusCode(response, Constants.successfulOperationCode)
                .validateSignificantNumbers(response);
        petstoreSteps
                .printNumbers(significantNumbers);
    }
}
