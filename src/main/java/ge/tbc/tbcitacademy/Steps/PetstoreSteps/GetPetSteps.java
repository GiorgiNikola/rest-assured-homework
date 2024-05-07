package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class GetPetSteps {

    public Response getPet(RequestSpecification requestSpec, int petId){
        return RestAssured
                .given(requestSpec)
                .when()
                .get("/pet/{petId}", petId);
    }

    public GetPetSteps validatePet(Response response){
        response
                .then()
                .body("name", Matchers.is("manuchar"),
                        "status", Matchers.is("sold"));
        return this;
    }
}
