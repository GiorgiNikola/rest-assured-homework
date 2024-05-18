package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.DeleteEmployeeRequest;
import com.example.springboot.soap.interfaces.DeleteEmployeeResponse;
import com.example.springboot.soap.interfaces.ObjectFactory;
import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static ge.tbc.tbcitacademy.Steps.CommonSteps.Marshall.marshallSoapRequest;
import static ge.tbc.tbcitacademy.Steps.CommonSteps.SoapServiceSender.send;
import static ge.tbc.tbcitacademy.Steps.CommonSteps.Unmarshall.unmarshallResponse;
import static io.restassured.RestAssured.given;
import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteEmployeeSteps {
    String body;
    Response response;
    DeleteEmployeeResponse deleteEmployeeResponse;
    @Step("Create delete request body")
    public DeleteEmployeeSteps createDeleteRequestBody(long id){
        ObjectFactory objectFactory = new ObjectFactory();
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest()
                .withEmployeeId(id);
        body = marshallSoapRequest(deleteEmployeeRequest);
        return this;
    }

    @Step("Send delete Request body and get response")
    public DeleteEmployeeSteps sendDeleteEmployeeRequest(){
        response = send(Constants.baseUri,Constants.deleteEmployeeAction, body);
        return this;
    }

    @Step("Deserialize response into object")
    public DeleteEmployeeSteps deserializeResponse(){
        deleteEmployeeResponse = unmarshallResponse(response.asString(), DeleteEmployeeResponse.class);
        return this;
    }

    @Step("Validate message in response")
    public DeleteEmployeeSteps validateDelete(){
        assertThat(deleteEmployeeResponse.getServiceStatus().getMessage(), equalTo(Constants.contentDeletedSuccessfully));
        return this;
    }
}
