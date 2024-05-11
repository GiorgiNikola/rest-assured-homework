package ge.tbc.tbcitacademy.Steps.RestulBookerSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Requests.RestfulBookerLombok.BookingDatesLombok;
import ge.tbc.tbcitacademy.Models.Requests.RestfulBookerLombok.UpdateRequestLombok;
import ge.tbc.tbcitacademy.Models.Responses.RestfulBookerLombok.UpdateResponseLombok;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateSteps {
    Response updateResponse;
    UpdateRequestLombok updateRequestLombok;
    UpdateResponseLombok updateResponseLombok;
    @Step("Generate booking update pojo request object")
    public UpdateSteps generateUpdateRequest(String firstname, String lastname, int totalPrice,
                                             boolean depositPaid, String checkin,
                                             String checkout, String additionalNeeds,
                                             int salesPrice, String passportNo){
        updateRequestLombok = new UpdateRequestLombok()
                .setFirstName(firstname)
                .setLastName(lastname)
                .setTotalPrice(totalPrice)
                .setDepositPaid(depositPaid)
                .setBookingDates(new BookingDatesLombok()
                        .setCheckIn(checkin)
                        .setCheckOut(checkout))
                .setAdditionalNeeds(additionalNeeds)
                .setSalesprice(salesPrice)
                .setPassportNo(passportNo);
        return this;
    }

    @Step("Put update request with our generated pojo object")
    public UpdateSteps updateRequest(RequestSpecification requestSpec){
        updateResponse = given(requestSpec)
                .body(updateRequestLombok)
                .auth()
                .preemptive()
                .basic(Constants.restfulUsername, Constants.restfulPassword)
                .header(Constants.authorization, Constants.authToken)
                .when()
                .put("/booking/1");
        return this;
    }

    @Step("Deserialize response into pojo object")
    public UpdateSteps deserializeResponse(){
        updateResponseLombok = updateResponse
                .then()
                .statusCode(Constants.successfulOperationCode)
                .extract()
                .as(UpdateResponseLombok.class);
        return this;
    }

    @Step("Validate response fields")
    public UpdateSteps validateResponse(){
        assertThat(updateResponseLombok.getFirstName(), equalTo(updateRequestLombok.getFirstName()));
        assertThat(updateResponseLombok.getLastName(), equalTo(updateRequestLombok.getLastName()));
        assertThat(updateResponseLombok.getTotalPrice(), equalTo(updateRequestLombok.getTotalPrice()));
        assertThat(updateResponseLombok.isDepositPaid(), equalTo(updateRequestLombok.isDepositPaid()));
        assertThat(updateResponseLombok.getBookingDates().getCheckIn(),
                equalTo(updateRequestLombok.getBookingDates().getCheckIn()));
        assertThat(updateResponseLombok.getBookingDates().getCheckOut(),
                equalTo(updateRequestLombok.getBookingDates().getCheckOut()));
        assertThat(updateResponseLombok.getAdditionalNeeds(),
                equalTo(updateRequestLombok.getAdditionalNeeds()));
        return this;
    }
}
