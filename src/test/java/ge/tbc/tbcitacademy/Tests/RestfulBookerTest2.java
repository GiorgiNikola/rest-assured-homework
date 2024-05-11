package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.DataProvider.CustomDataProvider;
import ge.tbc.tbcitacademy.Steps.RestulBookerSteps.*;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Restful Booker API Test")
public class RestfulBookerTest2 {
    UpdateSteps updateSteps;
    RequestSpecification requestSpec;
    @BeforeClass
    public void pageSetup(){
        RestAssured.filters(new AllureRestAssured());
        updateSteps = new UpdateSteps();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(Constants.restfulBookerUri)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Updating Bookings")
    @Story("Update Booking with various inputs")
    @Description("Update an existing booking with different combinations of inputs")
    @Test(dataProvider = "bookingUpdateProvider", dataProviderClass = CustomDataProvider.class,
    description = "Update booking test with different input combinations")
    public void updatingTest(String firstname, String lastname, int totalPrice,
                             boolean depositPaid, String checkin,
                             String checkout, String additionalNeeds,
                             int salesPrice, String passportNo){
        updateSteps
                .generateUpdateRequest(firstname, lastname, totalPrice, depositPaid,
                        checkin, checkout, additionalNeeds, salesPrice, passportNo)
                .updateRequest(requestSpec)
                .deserializeResponse()
                .validateResponse();
    }
}
