package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.EmployeeInfo;
import com.example.springboot.soap.interfaces.ObjectFactory;
import com.example.springboot.soap.interfaces.UpdateEmployeeRequest;
import com.example.springboot.soap.interfaces.UpdateEmployeeResponse;
import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

import static ge.tbc.tbcitacademy.Steps.CommonSteps.Marshall.marshallSoapRequest;
import static ge.tbc.tbcitacademy.Steps.CommonSteps.SoapServiceSender.send;
import static ge.tbc.tbcitacademy.Steps.CommonSteps.Unmarshall.unmarshallResponse;
import static io.restassured.RestAssured.given;
import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateEmployeeSteps {
    String body;
    Response response;
    UpdateEmployeeResponse updateEmployeeResponse;
    @Step("Create update request body")
    public UpdateEmployeeSteps createUpdateRequestBody() throws DatatypeConfigurationException {
        ObjectFactory objectFactory = new ObjectFactory();
        UpdateEmployeeRequest updateEmployeeRequest = objectFactory.createUpdateEmployeeRequest();
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo()
                .withEmployeeId(Constants.employeeId)
                .withName(Constants.updatedName)
                .withAddress(Constants.updatedAddress)
                .withEmail(Constants.updatedEmail)
                .withDepartment(Constants.updatedDepartment)
                .withBirthDate(DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(LocalDate.of(Constants.updatedBirthYear, Constants.birthMonth, Constants.birthDay).toString()))
                .withPhone(Constants.updatedPhone)
                .withSalary(BigDecimal.valueOf(Constants.updatedSalary));
        updateEmployeeRequest.withEmployeeInfo(employeeInfo);
        body = marshallSoapRequest(updateEmployeeRequest);
        return this;
    }

    @Step("Send update employee request with body and get response")
    public UpdateEmployeeSteps sendUpdateEmployeeRequest(){
        response = send(Constants.baseUri, Constants.updateEmployeeAction, body);
        return this;
    }

    @Step("Deserialize response into object")
    public UpdateEmployeeSteps deserializeResponse(){
        updateEmployeeResponse = unmarshallResponse(response.asString(), UpdateEmployeeResponse.class);
        return this;
    }

    @Step("Validate message in response")
    public UpdateEmployeeSteps validateUpdateEmployeeResponse() {
        assertThat(updateEmployeeResponse.getServiceStatus().getMessage(), equalTo(Constants.contentUpdatedSuccessfully));
        return this;
    }
}
