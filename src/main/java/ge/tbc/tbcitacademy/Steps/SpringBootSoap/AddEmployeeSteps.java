package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.AddEmployeeRequest;
import com.example.springboot.soap.interfaces.AddEmployeeResponse;
import com.example.springboot.soap.interfaces.EmployeeInfo;
import com.example.springboot.soap.interfaces.ObjectFactory;
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

public class AddEmployeeSteps {
    Response response;
    String body;
    AddEmployeeResponse addEmployeeResponse;
    @Step("Generate add request body")
    public AddEmployeeSteps createAddRequestBody() throws DatatypeConfigurationException {
        ObjectFactory objectFactory = new ObjectFactory();
        AddEmployeeRequest employeeRequest = objectFactory.createAddEmployeeRequest();
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo()
                .withEmployeeId(Constants.employeeId)
                .withName(Constants.name)
                .withAddress(Constants.address)
                .withEmail(Constants.email)
                .withDepartment(Constants.department)
                .withBirthDate(DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(LocalDate.of(Constants.birthYear, Constants.birthMonth, Constants.birthDay).toString()))
                .withPhone(Constants.phone)
                .withSalary(BigDecimal.valueOf(Constants.salary));
        employeeRequest
                .withEmployeeInfo(employeeInfo);
        body = marshallSoapRequest(employeeRequest);
        return this;
    }

    @Step("Send request with body and get response")
    public AddEmployeeSteps sendAddEmployeeRequest(){
        response = send(Constants.baseUri, Constants.addEmployeeAction, body);
        return this;
    }

    @Step("Deserialize response into object")
    public AddEmployeeSteps deserializeResponse(){
        addEmployeeResponse = unmarshallResponse(response.asString(), AddEmployeeResponse.class);
        return this;
    }

    @Step("Validate message in response")
    public AddEmployeeSteps validateAddEmployeeResponse() {
        assertThat(addEmployeeResponse.getServiceStatus().getMessage(), equalTo(Constants.contentAddedSuccessfully));
        return this;
    }
}
