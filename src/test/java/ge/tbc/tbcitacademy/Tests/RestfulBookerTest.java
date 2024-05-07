package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Steps.RestulBookerSteps.UpdateBookingSteps;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBookerTest {
    UpdateBookingSteps updateBookingSteps;
    RequestSpecification requestSpec;

    @BeforeClass
    public void pageSetup(){
        updateBookingSteps = new UpdateBookingSteps();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @Test
    public void updateBookingTest(){
        JSONObject info = updateBookingSteps
                .generateJSONObject();
        String token = updateBookingSteps
                .tokenGeneration(requestSpec);
        Response updateResponse = updateBookingSteps
                .updateBooking(requestSpec, info, token);
        int statusCode = updateBookingSteps.returnStatusCode(updateResponse);
    }

}
