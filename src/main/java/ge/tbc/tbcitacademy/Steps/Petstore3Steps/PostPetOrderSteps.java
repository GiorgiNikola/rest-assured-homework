package ge.tbc.tbcitacademy.Steps.Petstore3Steps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Requests.Petstore3.PostPet;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostPetOrderSteps {
    Response postPetResponse;
    PostPet postPet;
    PostPet responsePet;

    @Step("Generate request pojo pet object")
    public PostPetOrderSteps generatePet(){
        postPet = new PostPet()
                .setId(Constants.Id)
                .setPetId(Constants.postPetID)
                .setQuantity(Constants.petQuantity)
                .setShipDate(Constants.petShipDate)
                .setStatus(Constants.petStatus)
                .setComplete(false);
        return this;
    }

    @Step("Send post pet request")
    public PostPetOrderSteps postPet(RequestSpecification requestSpec){
        postPetResponse = given(requestSpec)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(postPet)
                .when()
                .post("/store/order");
        return this;
    }

    @Step("Deserialize pet into pojo object")
    public PostPetOrderSteps deserializePet(){
        responsePet = postPetResponse
                .then()
                .extract()
                .as(PostPet.class);
        return this;
    }

    @Step("Added validations using deserialized pojo pet")
    public PostPetOrderSteps validatePet(){
        assertThat(responsePet.getId(), equalTo(postPet.getId()));
        assertThat(responsePet.getPetId(), equalTo(postPet.getPetId()));
        assertThat(responsePet.getQuantity(), greaterThan(Constants.quantity));
        assertThat(responsePet.getStatus(), is(Constants.postPetStatus));
        return this;
    }
}
