package ge.tbc.tbcitacademy.Steps.RestulBookerSteps;

import ge.tbc.tbcitacademy.Models.Requests.RestfulBooker.UpdateBookingRequest;
import ge.tbc.tbcitacademy.Models.Responses.RestfulBooker.BookingResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateBookingSteps {

    public Response createBooking(RequestSpecification requestSpec, UpdateBookingRequest updateBookingRequest, String token){
        return RestAssured
                .given(requestSpec)
                .body(updateBookingRequest)
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .post("/booking");
    }

    public int returnId(Response response){
        return response.then().statusCode(200).extract().as(BookingResponse.class).getBookingid();
    }

    public CreateBookingSteps validateCreatedBooking(Response createResponse, Response getResponse){
        BookingResponse create = createResponse.then().extract().as(BookingResponse.class);
        UpdateBookingRequest get = getResponse.then().extract().as(UpdateBookingRequest.class);
        assertThat(create.getBooking().getFirstname(), equalTo(get.getFirstname()));
        assertThat(create.getBooking().getLastname(), equalTo(get.getLastname()));
        return this;
    }
}
