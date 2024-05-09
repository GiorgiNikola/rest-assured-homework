package ge.tbc.tbcitacademy.Steps.RestulBookerSteps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetBookingSteps {

    public Response getBooking(RequestSpecification requestSpec, String token, int id){
        return RestAssured
                .given(requestSpec)
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .get("/booking/" + id);
    }

    public GetBookingSteps validateStatusCode(Response response, int id){
        response.then().statusCode(id);
        return this;
    }
}
