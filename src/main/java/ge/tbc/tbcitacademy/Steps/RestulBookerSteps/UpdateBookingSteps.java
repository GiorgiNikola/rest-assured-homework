package ge.tbc.tbcitacademy.Steps.RestulBookerSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Requests.RestfulBooker.AuthRequest;
import ge.tbc.tbcitacademy.Models.Requests.RestfulBooker.BookingDates;
import ge.tbc.tbcitacademy.Models.Requests.RestfulBooker.PartialUpdateRequest;
import ge.tbc.tbcitacademy.Models.Requests.RestfulBooker.UpdateBookingRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateBookingSteps {

    public UpdateBookingRequest generateRequest(){
        UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
        updateBookingRequest.setFirstname(Constants.firstname);
        updateBookingRequest.setLastname(Constants.lastname);
        updateBookingRequest.setTotalprice(2000);
        updateBookingRequest.setDepositpaid(false);
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin(Constants.checkIn);
        bookingDates.setCheckout(Constants.checkOut);
        updateBookingRequest.setBookingdates(bookingDates);
        updateBookingRequest.setAdditionalneeds(Constants.specialNeed);
        return updateBookingRequest;
    }

    public String tokenGeneration(RequestSpecification requestSpec){
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(Constants.restfulUsername);
        authRequest.setPassword(Constants.restfulPassword);
        Response tokenResponse = RestAssured.given()
                .spec(requestSpec)
                .body(authRequest)
                .post("/auth");
        return tokenResponse.jsonPath().getString("token");
    }

    public Response updateBooking(RequestSpecification requestSpec, UpdateBookingRequest updateBookingRequest, String token){
        return RestAssured
                .given(requestSpec)
                .body(updateBookingRequest)
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .put("/booking/1");
    }

    public Response partialUpdateBooking(RequestSpecification requestSpec, String token, int id){
        PartialUpdateRequest partialUpdateRequest = new PartialUpdateRequest();
        partialUpdateRequest.setFirstname(Constants.firstname);
        partialUpdateRequest.setLastname(Constants.lastname);
        return RestAssured
                .given(requestSpec)
                .body(partialUpdateRequest)
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .patch("/booking/" + id);
    }

    public UpdateBookingSteps validatePartialUpdate(Response partialUpdate, Response get){
        UpdateBookingRequest partialUpdateResponse = partialUpdate.then().extract().as(UpdateBookingRequest.class);
        UpdateBookingRequest getResponse = get.then().extract().as(UpdateBookingRequest.class);
        assertThat(partialUpdateResponse.getFirstname(), equalTo(getResponse.getFirstname()));
        assertThat(partialUpdateResponse.getLastname(), equalTo(getResponse.getLastname()));
        return this;
    }

    public int returnStatusCode(Response response){
        return response.then().log().ifStatusCodeIsEqualTo(201).extract().statusCode();
    }

}
