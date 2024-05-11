package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import com.github.javafaker.Faker;
import ge.tbc.tbcitacademy.Data.Status;
import ge.tbc.tbcitacademy.Models.Requests.Petstore.Category;
import ge.tbc.tbcitacademy.Models.Requests.Petstore.Pet;
import ge.tbc.tbcitacademy.Models.Requests.Petstore.TagsItem;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddPetSteps {
    Faker faker = new Faker();
    public Pet createPetRequestBody(){
        Pet pet = new Pet();
        pet.setId(8000);

        Category category = new Category();
        category.setId(8000);
        category.setName(faker.animal().name());
        pet.setCategory(category);

        pet.setName(faker.animal().name());
        pet.setPhotoUrls(List.of("photos"));

        TagsItem tagsItem = new TagsItem();
        tagsItem.setId(8000);
        tagsItem.setName(faker.animal().name());
        pet.setTags(List.of(tagsItem));
        pet.setStatus(Status.AVAILABLE);
        return pet;
    }

    public Response sendRequest(RequestSpecification requestSpec, Pet pet){
        return RestAssured
                .given(requestSpec)
                .body(pet)
                .when()
                .post("/pet");
    }

    public AddPetSteps validatePet(Response response, Pet pet){
        Pet responsePet = response.then().extract().as(Pet.class);
        assertThat(responsePet.getId(), equalTo(pet.getId()));
        assertThat(responsePet.getName(), equalTo(pet.getName()));
        assertThat(responsePet.getStatus(), equalTo(pet.getStatus()));
        return this;
    }
}
