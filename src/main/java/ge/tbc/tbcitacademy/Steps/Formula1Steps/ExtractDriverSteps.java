package ge.tbc.tbcitacademy.Steps.Formula1Steps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Responses.Formula1.DriversItem;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class ExtractDriverSteps {

    public Response getResponse(){
        return RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get("http://ergast.com/api/f1/2016/drivers.json");
    }

    public DriversItem extractDriver(Response response){
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        return jsonPath.getObject("MRData.DriverTable.Drivers[0]", DriversItem.class);
    }

    public ExtractDriverSteps validateDriver(DriversItem actualDriver){
        DriversItem expectedDriver = new DriversItem();
        expectedDriver.setDriverId(Constants.driverId);
        expectedDriver.setPermanentNumber(Constants.driverNumber);
        expectedDriver.setCode(Constants.driverCode);
        expectedDriver.setUrl(Constants.driverURL);
        expectedDriver.setGivenName(Constants.driverName);
        expectedDriver.setFamilyName(Constants.driverFamilyName);
        expectedDriver.setDateOfBirth(Constants.driverDateOfBirth);
        expectedDriver.setNationality(Constants.driverNationality);

        assertThat(actualDriver, samePropertyValuesAs(expectedDriver));
        return this;
    }
}
