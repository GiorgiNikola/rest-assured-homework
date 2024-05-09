package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Requests.Petstore.Pet;
import ge.tbc.tbcitacademy.Steps.PetstoreSteps.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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
        Pet pet = addPetSteps
                .createPetRequestBody();
        Response addPetResponse = addPetSteps
                .sendRequest(requestSpecification, pet);
        petstoreSteps
                .validateStatusCode(addPetResponse, 200);
        addPetSteps
                .validatePet(addPetResponse, pet);
        Response findPetsResponse = findPetsSteps
                .findPets(requestSpecification);
        findPetsSteps
                .validateArrayContainsPet(findPetsResponse);
        Pet extractedPetObject = findPetsSteps
                .returnPetObject(findPetsResponse);
        findPetsSteps
                .validateExtractedPet(extractedPetObject, pet);
        petstoreSteps.updatePet(8000);
        Response getPet = getPetSteps
                .getPet(requestSpecification, 8000);
        getPetSteps
                .validatePet(getPet);
        File dogoImage = new File(Constants.imagePath);
        Response updatedPetWithImage = updatePetSteps
                .updatePetImage(requestSpecification, 8000, Constants.metaData, dogoImage);
        updatePetSteps
                .validatePetWithImage(updatedPetWithImage, Constants.metaData, dogoImage);
    }
}
