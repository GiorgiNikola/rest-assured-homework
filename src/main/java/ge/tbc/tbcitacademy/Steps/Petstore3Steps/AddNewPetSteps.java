package ge.tbc.tbcitacademy.Steps.Petstore3Steps;

import com.github.javafaker.Faker;
import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Data.Status;
import ge.tbc.tbcitacademy.Models.Requests.Petstore3.Category;
import ge.tbc.tbcitacademy.Models.Requests.Petstore3.Pet;
import ge.tbc.tbcitacademy.Models.Requests.Petstore3.TagsItem;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddNewPetSteps {
    Pet requestPet;
    Pet responsePet;
    Response response;
    Faker faker = new Faker();
    @Step("Create new pet request body in xml format")
    public AddNewPetSteps createNewPetRequest(){
        requestPet = new Pet()
                .setId(Constants.Id)
                .setName(faker.animal().name())
                .setCategory(new Category()
                        .setId(Constants.Id)
                        .setName(faker.animal().name()))
                .setPhotoUrls(List.of(Constants.photoUrl))
                .setTags(List.of(new TagsItem()
                        .setId(Constants.Id)
                        .setName(faker.animal().name())))
                .setStatus(Status.AVAILABLE);
        return this;
    }

    @Step("Post new pet and get response")
    public AddNewPetSteps postNewPet(RequestSpecification requestSpec){
        response = given(requestSpec)
                .contentType(ContentType.XML)
                // .accept(ContentType.XML) did not work, so I typed it manually
                .accept(Constants.xmlFormat)
                .body(requestPet, ObjectMapperType.JAKARTA_EE)
                .when()
                .post("/pet");
        return this;
    }

    @Step("Deserialize response into pojo object")
    public AddNewPetSteps deserializeResponse(){
        responsePet = response
                .then()
                .extract()
                .as(Pet.class);
        return this;
    }

    @Step("Validate response using request")
    public AddNewPetSteps validatePet(){
        assertThat(responsePet.getId(), equalTo(requestPet.getId()));
        assertThat(responsePet.getName(), equalTo(requestPet.getName()));
        assertThat(responsePet.getPhotoUrls(), equalTo(requestPet.getPhotoUrls()));
        assertThat(responsePet.getStatus(), equalTo(requestPet.getStatus()));
        assertThat(responsePet.getCategory().getId(), equalTo(requestPet.getCategory().getId()));
        assertThat(responsePet.getCategory().getName(), equalTo(requestPet.getCategory().getName()));
        assertThat(responsePet.getTags().size(), equalTo(requestPet.getTags().size()));
        return this;
    }
}
