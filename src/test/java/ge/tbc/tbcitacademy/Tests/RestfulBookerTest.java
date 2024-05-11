package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Models.Requests.RestfulBooker.UpdateBookingRequest;
import ge.tbc.tbcitacademy.Steps.RestulBookerSteps.CreateBookingSteps;
import ge.tbc.tbcitacademy.Steps.RestulBookerSteps.DeleteBookingSteps;
import ge.tbc.tbcitacademy.Steps.RestulBookerSteps.GetBookingSteps;
import ge.tbc.tbcitacademy.Steps.RestulBookerSteps.UpdateBookingSteps;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBookerTest {
    UpdateBookingSteps updateBookingSteps;
    CreateBookingSteps createBookingSteps;
    GetBookingSteps getBookingSteps;
    DeleteBookingSteps deleteBookingSteps;
    RequestSpecification requestSpec;
    private String token;

    @BeforeClass
    public void pageSetup(){
        updateBookingSteps = new UpdateBookingSteps();
        createBookingSteps = new CreateBookingSteps();
        getBookingSteps = new GetBookingSteps();
        deleteBookingSteps = new DeleteBookingSteps();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .build();
        token = updateBookingSteps
                .tokenGeneration(requestSpec);
    }

    @Test
    public void updateBookingTest(){
        UpdateBookingRequest updateBookingRequest = updateBookingSteps
                .generateRequest();
        Response updateResponse = updateBookingSteps
                .updateBooking(requestSpec, updateBookingRequest, token);
        int statusCode = updateBookingSteps.returnStatusCode(updateResponse);
    }

    @Test
    public void BookingOperationsTest(){
        // Create booking
        UpdateBookingRequest updateBookingRequest = updateBookingSteps
                .generateRequest();
        Response createResponse = createBookingSteps
                .createBooking(requestSpec, updateBookingRequest, token);
        int createdBookingId = createBookingSteps
                .returnId(createResponse);
        Response getResponse = getBookingSteps
                .getBooking(requestSpec, token, createdBookingId);
        getBookingSteps
                .validateStatusCode(getResponse, 200);
        createBookingSteps
                .validateCreatedBooking(createResponse, getResponse);

        // Partial Update
        Response partialUpdateResponse = updateBookingSteps
                .partialUpdateBooking(requestSpec, token, createdBookingId);
        getBookingSteps
                .validateStatusCode(partialUpdateResponse, 200);
        Response getResponseNew = getBookingSteps
                .getBooking(requestSpec, token, createdBookingId);
        getBookingSteps
                .validateStatusCode(getResponse, 200);
        updateBookingSteps
                .validatePartialUpdate(partialUpdateResponse, getResponseNew);

        // Delete Booking
        Response deleteResponse = deleteBookingSteps
                .deleteBooking(requestSpec, token, createdBookingId);
        getBookingSteps
                .validateStatusCode(deleteResponse, 201);
    }
}
