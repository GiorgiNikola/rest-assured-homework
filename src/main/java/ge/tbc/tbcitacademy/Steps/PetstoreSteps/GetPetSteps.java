package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Data.Status;
import ge.tbc.tbcitacademy.Models.Requests.Petstore.Pet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetPetSteps {

    public Response getPet(RequestSpecification requestSpec, int petId){
        return RestAssured
                .given(requestSpec)
                .when()
                .get("/pet/{petId}", petId);
    }

    public GetPetSteps validatePet(Response response){
        Pet pet = response.then().extract().as(Pet.class);
        assertThat(pet.getName(), equalTo(Constants.username));
        assertThat(pet.getStatus(), equalTo(Status.SOLD));
        return this;
    }
}
