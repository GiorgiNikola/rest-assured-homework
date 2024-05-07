package ge.tbc.tbcitacademy.Steps.PetstoreSteps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import java.io.File;
import java.nio.charset.StandardCharsets;

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
        response
                .then()
                .statusCode(200)
                .body("message", Matchers.containsString(metaData),
                        "message", Matchers.containsString(pictureFile.getName()),
                        "message", Matchers.containsString(pictureFile.length() + " bytes"));
        return this;
    }
}
