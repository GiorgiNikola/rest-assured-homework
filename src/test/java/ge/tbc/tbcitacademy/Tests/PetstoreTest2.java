package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.PetstoreSteps.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class PetstoreTest2 {
    AddPetSteps addPetSteps;
    PetstoreSteps petstoreSteps;
    FindPetsSteps findPetsSteps;
    GetPetSteps getPetSteps;
    UpdatePetSteps updatePetSteps;
    RequestSpecification requestSpecification;

    @BeforeClass
    public void pageSetup(){
        addPetSteps = new AddPetSteps();
        petstoreSteps = new PetstoreSteps();
        findPetsSteps = new FindPetsSteps();
        getPetSteps = new GetPetSteps();
        updatePetSteps = new UpdatePetSteps();
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @Test
    public void addPetTest(){
        JSONObject petJson = addPetSteps
                .createPetRequestBody();
        Response addPetResponse = addPetSteps
                .sendRequest(requestSpecification, petJson);
        petstoreSteps
                .validateStatusCode(addPetResponse, 200);
        addPetSteps
                .validatePet(addPetResponse, petJson);
        Response findPetsResponse = findPetsSteps
                .findPets(requestSpecification);
        findPetsSteps
                .validateArrayContainsPet(findPetsResponse);
        JSONObject extractedPetObject = findPetsSteps
                .returnPetObject(findPetsResponse);
        findPetsSteps
                .validateExtractedPet(extractedPetObject, petJson);
        petstoreSteps.updatePet(8000);
        Response getPet = getPetSteps
                .getPet(requestSpecification, 8000);
        getPetSteps
                .validatePet(getPet);
        File dogoImage = new File("src/main/resources/dogo_image.jpg");
        Response updatedPetWithImage = updatePetSteps
                .updatePetImage(requestSpecification, 8000, Constants.metaData, dogoImage);
        updatePetSteps
                .validatePetWithImage(updatedPetWithImage, Constants.metaData, dogoImage);
    }
}
