package ge.tbc.tbcitacademy.Steps.CountryInfoService;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Util.Marshall;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import jakarta.xml.bind.JAXBException;
import org.hamcrest.Matchers;
import org.oorsprong.websamples.ListOfContinentsByName;
import org.oorsprong.websamples.ObjectFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContinentSteps {
    String body;
    Response response;
    List<String> sNames;
    List<String> sCodes;
    @Step("Serialize soap request body")
    public ContinentSteps serializeRequest() {
        ObjectFactory objectFactory = new ObjectFactory();
        ListOfContinentsByName requestObject = objectFactory.createListOfContinentsByName();
        body = Marshall.marshallSoapRequest(requestObject);
        return this;
    }

    @Step("Send request to service and get response")
    public ContinentSteps sendRequest(){
        response = given()
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", Constants.listOfContinentsByNameAction)
                .body(body)
                .post(Constants.countryInfoServiceUri);
        return this;
    }

    @Step("Initialise sName and sCode lists")
    public ContinentSteps initialiseLists(){
        sNames = response.xmlPath().getList( Constants.basePath + ".sName");
        sCodes = response.xmlPath().getList( Constants.basePath + ".sCode");
        return this;
    }

    @Step("Validate count of sNames")
    public ContinentSteps validateCount(){
        assertThat(sNames.size(), Matchers.is(6));
        return this;
    }

    @Step("Validate sName values")
    public ContinentSteps validateNames(){
        assertThat(sNames, Matchers.hasItems(Constants.sNames));
        return this;
    }

    @Step("Validate sName node result with value of sCode equals to AN")
    public ContinentSteps validateAntarctica(){
        String sName = response.xmlPath().get(Constants.basePath + ".find { it.sCode == 'AN' }.sName");
        assertThat(sName, equalTo(Constants.antarctica));
        return this;
    }

    @Step("Validate the last `tContinent` node's `sName` value")
    public ContinentSteps validateLastNode(){
        assertThat(sNames.get(sNames.size() - 1), equalTo(Constants.americas));
        return this;
    }

    @Step("Verify that each `sName` is unique")
    public ContinentSteps validateUniqueness(){
        for (int i = 0; i < sNames.size(); i++){
            for (int j = 0; j < sNames.size(); j++){
                if (i != j){
                    assertThat(sNames.get(i), Matchers.not(equalToIgnoringCase(sNames.get(j))));
                }
            }
        }
        return this;
    }

    @Step("Validate the presence and correctness of both `sCode` and `sName`")
    public ContinentSteps validateSCodeSName(){
        assertThat(sCodes, hasItems(Constants.sCodes));
        assertThat(sNames, hasItems(Constants.sNames));
        return this;
    }

    @Step("Validate that `sCode` values follow a specific pattern (two uppercase letters)")
    public ContinentSteps validateSCodePattern(){
        for (String sCode : sCodes) {
            assertThat(sCode, matchesPattern("^[A-Z]{2}$"));
        }
        return this;
    }

    @Step("Ensure that the order of `tContinent` nodes corresponds to a specific sequence")
    public ContinentSteps validateSNameOrder(){
        List<String> sortedNames = new java.util.ArrayList<>(List.copyOf(sNames));
        sortedNames.sort(String::compareTo);
        assertThat(sNames, equalTo(sortedNames));
        return this;
    }

    @Step("Validate that no `sName` node contains numeric characters")
    public ContinentSteps validateNotNumeric(){
        for (String sName : sNames) {
            assertThat(sName, not(matchesPattern(".*\\d.*")));
        }
        return this;
    }

    @Step("find `sCode` that starting with `O` and ensure that is `Ocenania`")
    public ContinentSteps validateOceania(){
        String sName = response.xmlPath().get(Constants.basePath + ".find { it.sCode.text().startsWith('O') }.sName");
        assertThat(sName, equalTo(Constants.ocenania));
        return this;
    }

    @Step("findAll `sName` that stars with `A` and ends with `ca`")
    public ContinentSteps findAllSName(){
        List<String> sNames = response.xmlPath().getList(Constants.basePath + ".findAll { it.sName.text().startsWith('A') && it.sName.text().endsWith('ca') }.sName");
        assertThat(sNames, hasItems(Constants.filteredSNames));
        return this;
    }

    @Step("No Numeric Characters in `sName`")
    public ContinentSteps noNumericCharacters(){
        List<String> sNames = response.xmlPath().getList(Constants.basePath + ".findAll { !(it.sName.text() =~ /\\d/) }.sName");
        assertThat(sNames.size(), equalTo(6));
        return this;
    }
}
