package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.GetEmployeeByIdRequest;
import com.example.springboot.soap.interfaces.GetEmployeeByIdResponse;
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

public class GetEmployeeSteps {
    Response response;
    String body;
    GetEmployeeByIdResponse getEmployee;
    @Step("Create get request body")
    public GetEmployeeSteps createGetRequestBody(int id){
        ObjectFactory objectFactory = new ObjectFactory();
        GetEmployeeByIdRequest employeeByIdRequest = objectFactory.createGetEmployeeByIdRequest()
                        .withEmployeeId(id);
        body = marshallSoapRequest(employeeByIdRequest);
        return this;
    }

    @Step("Send get employee request with body and get response")
    public GetEmployeeSteps sendGetEmployeeRequest(){
        response = send(Constants.baseUri, Constants.getEmployeeByIdAction, body);
        return this;
    }

    @Step("Deserialize response into object")
    public GetEmployeeSteps deserializeResponse(){
        getEmployee = unmarshallResponse(response.asString(), GetEmployeeByIdResponse.class);
        return this;
    }

    @Step("Validate various employee details")
    public GetEmployeeSteps validateEmployeeDetails(String name,String department,
                                                    String phone, String address, String email) {
        assertThat(getEmployee.getEmployeeInfo().getName(), equalTo(name));
        assertThat(getEmployee.getEmployeeInfo().getDepartment(), equalTo(department));
        assertThat(getEmployee.getEmployeeInfo().getPhone(), equalTo(phone));
        assertThat(getEmployee.getEmployeeInfo().getAddress(), equalTo(address));
        assertThat(getEmployee.getEmployeeInfo().getEmail(), equalTo(email));
        return this;
    }

    @Step("Validate error message in response and status code")
    public GetEmployeeSteps validateErrorMessage(){
        response
                .then()
                .statusCode(500)
                // There was not class which would deserialize response with error message
                .body("Envelope.Body.Fault.faultstring", equalTo(Constants.errorMessage));
        return this;
    }
}
