package ge.tbc.tbcitacademy.Steps.RestulBookerSteps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteBookingSteps {

    public Response deleteBooking(RequestSpecification requestSpec, String token, int id){
        return RestAssured
                .given(requestSpec)
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("/booking/" + id);
    }
}
