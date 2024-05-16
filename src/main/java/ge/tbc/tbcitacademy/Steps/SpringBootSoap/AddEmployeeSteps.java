package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.AddEmployeeRequest;
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

import static ge.tbc.tbcitacademy.Util.Marshall.marshallSoapRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.Matchers.hasXPath;

public class AddEmployeeSteps {
    Response response;
    String body;
    @Step("Generate add request body")
    public AddEmployeeSteps createAddRequestBody() {
        ObjectFactory objectFactory = new ObjectFactory();
        AddEmployeeRequest employeeRequest = objectFactory.createAddEmployeeRequest();
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
        employeeInfo.setEmployeeId(Constants.employeeId);
        employeeInfo.setName(Constants.name);
        employeeInfo.setAddress(Constants.address);
        employeeInfo.setEmail(Constants.email);
        employeeInfo.setDepartment(Constants.department);
        try {
            employeeInfo.setBirthDate(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(LocalDate.of(Constants.birthYear, Constants.birthMonth, Constants.birthDay).toString()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        employeeInfo.setPhone(Constants.phone);
        employeeInfo.setSalary(BigDecimal.valueOf(Constants.salary));
        employeeRequest.setEmployeeInfo(employeeInfo);
        body = marshallSoapRequest(employeeRequest);
        return this;
    }

    @Step("Send request with body and get response")
    public AddEmployeeSteps sendAddEmployeeRequest(){
        response = given()
                .config(RestAssured.config().xmlConfig(xmlConfig()
                        .declareNamespace("ns2", Constants.ns2NameSpace)
                        .declareNamespace("SOAP-ENV", Constants.soapEnvNameSpace)))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", Constants.addEmployeeAction)
                .body(body)
                .post(Constants.baseUri);
        return this;
    }

    @Step("Validate message in response")
    public AddEmployeeSteps validateAddEmployeeResponse() {
        response
                .then()
                .statusCode(200)
                .body(hasXPath("//*[local-name()='message' and namespace-uri()='" + Constants.ns2NameSpace +"']" +
                        "[text()='" + Constants.contentAddedSuccessfully + "']"));
        return this;
    }
}
