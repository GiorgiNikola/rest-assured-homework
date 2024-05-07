package ge.tbc.tbcitacademy.Steps.RestulBookerSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class UpdateBookingSteps {

    public JSONObject generateJSONObject(){
        return new JSONObject()
                .put("firstname", Constants.firstname)
                .put("lastname", Constants.lastname)
                .put("totalprice", 2000)
                .put("depositpaid", false)
                .put("bookingdates", new JSONObject()
                        .put("checkin", Constants.checkIn)
                        .put("checkout", Constants.checkOut))
                .put("additionalneeds", Constants.specialNeed);
    }

    public String tokenGeneration(RequestSpecification requestSpec){
        JSONObject jsonObject = new JSONObject()
                .put("username", Constants.restfulUsername)
                .put("password", Constants.restfulPassword);
        Response tokenResponse = RestAssured.given()
                .spec(requestSpec)
                .body(jsonObject.toString())
                .post("/auth");
        return tokenResponse.jsonPath().getString("token");
    }

    public Response updateBooking(RequestSpecification requestSpec, JSONObject jsonObject, String token){
        return RestAssured
                .given(requestSpec)
                .body(jsonObject.toString())
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .put("/booking/1");
    }

    public int returnStatusCode(Response response){
        return response.then().log().ifStatusCodeIsEqualTo(201).extract().statusCode();
    }

}
