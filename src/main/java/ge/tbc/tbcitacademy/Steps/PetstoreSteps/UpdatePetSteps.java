package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import ge.tbc.tbcitacademy.Models.Responses.Petstore.UploadImageResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class UpdatePetSteps {
    public Response updatePetImage(RequestSpecification requestSpec, int petId, String metaData, File file){
        return RestAssured
                .given(requestSpec)
                .contentType(ContentType.MULTIPART)
                .pathParam("petId", petId)
                .multiPart("additionalMetadata", metaData, "text/plain")
                .multiPart("file", file)
                .post("/pet/{petId}/uploadImage");
    }

    public UpdatePetSteps validatePetWithImage(Response response, String metaData, File pictureFile){
        UploadImageResponse uploadImageResponse =
                response.then().statusCode(200).extract().as(UploadImageResponse.class);
        assertThat(uploadImageResponse.getMessage(), containsString(metaData));
        assertThat(uploadImageResponse.getMessage(), containsString(pictureFile.getName()));
        assertThat(uploadImageResponse.getMessage(), containsString(pictureFile.length() + " bytes"));
        return this;
    }
}
