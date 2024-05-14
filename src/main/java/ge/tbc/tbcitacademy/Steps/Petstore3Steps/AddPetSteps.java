package ge.tbc.tbcitacademy.Steps.Petstore3Steps;

import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.model.Category;
import pet.store.v3.model.Pet;
import pet.store.v3.model.Tag;

import static org.hamcrest.Matchers.equalTo;

public class AddPetSteps {
    Pet pet;
    Response response;
    @Step("Generate pet request body")
    public AddPetSteps generatePetRequest(){
        pet = new Pet()
                .id(Constants.Id)
                .name(Constants.petName)
                .category(new Category()
                        .id(Constants.Id)
                        .name(Constants.petName))
                .addPhotoUrlsItem(Constants.photoURL)
                .addTagsItem(new Tag()
                        .id(Constants.Id)
                        .name(Constants.petName))
                .status(Pet.StatusEnum.AVAILABLE);
        return this;
    }

    @Step("Post new pet and get response")
    public AddPetSteps postNewPet(ApiClient api){
        response = api
                .pet()
                .addPet()
                .body(pet)
                .execute(res -> res);
        return this;
    }

    @Step("Validate with rest-assured functional")
    public AddPetSteps validatePet(){
        response
                .then()
                .statusCode(200)
                .body("id", equalTo(pet.getId().intValue()),
                        "name", equalTo(pet.getName()),
                        "photoUrls", equalTo(pet.getPhotoUrls()),
                        "status", equalTo(pet.getStatus().getValue()));
        return this;
    }
}
