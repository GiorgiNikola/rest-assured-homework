package ge.tbc.tbcitacademy.Steps.SoapDemo;

import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.tempuri.FindPerson;
import org.tempuri.FindPersonResponse;
import org.tempuri.ObjectFactory;

import java.util.List;
import java.util.stream.IntStream;

import static ge.tbc.tbcitacademy.Steps.CommonSteps.Marshall.marshallSoapRequest;
import static ge.tbc.tbcitacademy.Steps.CommonSteps.SoapServiceSender.send;
import static ge.tbc.tbcitacademy.Steps.CommonSteps.Unmarshall.unmarshallResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class SoapDemoSteps {
    String body;
    Response response;
    FindPersonResponse findPersonResponse;
    @Step("Create request body for find person")
    public SoapDemoSteps createFindPersonRequestBody(){
        ObjectFactory objectFactory = new ObjectFactory();
        FindPerson findPerson = objectFactory.createFindPerson();
        findPerson
                .withId("10");
        body = marshallSoapRequest(findPerson);
        return this;
    }

    @Step("Send request for find person and get response")
    public SoapDemoSteps sendRequestForFindPerson(){
        response = send(Constants.soapDemoUri, Constants.findPersonAction, body);
        return this;
    }

    @Step("Deserialize response into object")
    public SoapDemoSteps deserializeResponse(){
        findPersonResponse = unmarshallResponse(response.asString(), FindPersonResponse.class);
        return this;
    }

    @Step("Validate the presence (not null) of the FindPersonResult element")
    public SoapDemoSteps validateNotNull(){
        assertThat(findPersonResponse.getFindPersonResult(), notNullValue());
        return this;
    }

    @Step("Validate the person's `name`")
    public SoapDemoSteps validatePersonName(){
        assertThat(findPersonResponse.getFindPersonResult().getName(), equalTo(Constants.personName));
        return this;
    }

    @Step("Validate the `SSN` value")
    public SoapDemoSteps validateSSNValue(){
        assertThat(findPersonResponse.getFindPersonResult().getSSN(), equalTo(Constants.personSSN));
        return this;
    }

    @Step("Validate the number of characters in the `SSN`")
    public SoapDemoSteps validateNumberOfCharactersINSNN(){
        assertThat(findPersonResponse.getFindPersonResult().getSSN().length(), equalTo(11));
        return this;
    }

    @Step("Check if the SSN follows the pattern `NNN-NN-NNNN` (with digits only)")
    public SoapDemoSteps validateSSNPattern(){
        assertThat(findPersonResponse.getFindPersonResult().getSSN(), matchesPattern("\\d{3}-\\d{2}-\\d{4}"));
        return this;
    }

    @Step("Validate date of birth of person")
    public SoapDemoSteps validateDOB(){
        assertThat(findPersonResponse.getFindPersonResult().getDOB().toString(), equalTo(Constants.personDOB));
        return this;
    }

    @Step("Validate the presence of the `favorite colors` [Orange, Red]")
    public SoapDemoSteps validateFavouriteColors(){
        assertThat(findPersonResponse.getFindPersonResult().getFavoriteColors().getFavoriteColorsItem(), hasItems(Constants.personFavoriteColors));
        return this;
    }

    @Step("Validate the person's `last name`")
    public SoapDemoSteps validatePersonLastName(){
        String name = findPersonResponse.getFindPersonResult().getName();
        String lastName = name.split(",")[0];
        assertThat(lastName, equalTo(Constants.personLastName));
        return this;
    }

    @Step("Check if the person has `Red` as one of favorite colors and its index is 2")
    public SoapDemoSteps validateColor(){
        List<String> favoriteColors = findPersonResponse
                .getFindPersonResult().getFavoriteColors().getFavoriteColorsItem();
        boolean isRedAtIndex2 = IntStream.range(0, favoriteColors.size())
                .anyMatch(index -> Constants.favoriteColor.equals(favoriteColors.get(index)) && index == 1);
        assertThat(isRedAtIndex2, is(true));
        return this;
    }

    @Step("Validate that the `office` zip code is not the same as the `home` zip code")
    public SoapDemoSteps validateZipsNotEqual(){
        assertThat(findPersonResponse.getFindPersonResult().getHome().getZip(),
                not(equalTo(findPersonResponse.getFindPersonResult().getOffice().getZip())));
        return this;
    }
}
