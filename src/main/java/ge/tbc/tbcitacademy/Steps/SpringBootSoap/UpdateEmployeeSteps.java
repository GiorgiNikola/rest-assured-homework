package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.EmployeeInfo;
import com.example.springboot.soap.interfaces.ObjectFactory;
import com.example.springboot.soap.interfaces.UpdateEmployeeRequest;
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

public class UpdateEmployeeSteps {
    String body;
    Response response;
    @Step("Create update request body")
    public UpdateEmployeeSteps createUpdateRequestBody(){
        ObjectFactory objectFactory = new ObjectFactory();
        UpdateEmployeeRequest updateEmployeeRequest = objectFactory.createUpdateEmployeeRequest();
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
        employeeInfo.setEmployeeId(Constants.employeeId);
        employeeInfo.setName(Constants.updatedName);
        employeeInfo.setAddress(Constants.updatedAddress);
        employeeInfo.setEmail(Constants.updatedEmail);
        employeeInfo.setDepartment(Constants.updatedDepartment);
        try {
            employeeInfo.setBirthDate(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(LocalDate.of(Constants.updatedBirthYear, Constants.birthMonth, Constants.birthDay).toString()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        employeeInfo.setPhone(Constants.updatedPhone);
        employeeInfo.setSalary(BigDecimal.valueOf(Constants.updatedSalary));
        updateEmployeeRequest.setEmployeeInfo(employeeInfo);
        body = marshallSoapRequest(updateEmployeeRequest);
        return this;
    }

    @Step("Send update employee request with body and get response")
    public UpdateEmployeeSteps sendUpdateEmployeeRequest(){
        response = given()
                .config(RestAssured.config().xmlConfig(xmlConfig()
                        .declareNamespace("ns2", Constants.ns2NameSpace)
                        .declareNamespace("SOAP-ENV", Constants.soapEnvNameSpace)))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", Constants.updateEmployeeAction)
                .body(body)
                .post(Constants.baseUri);
        return this;
    }

    @Step("Validate message in response")
    public UpdateEmployeeSteps validateUpdateEmployeeResponse() {
        response
                .then()
                .statusCode(200)
                .body(hasXPath("//*[local-name()='message' and namespace-uri()='" + Constants.ns2NameSpace +"']" +
                        "[text()='" + Constants.contentUpdatedSuccessfully + "']"));
        return this;
    }
}
