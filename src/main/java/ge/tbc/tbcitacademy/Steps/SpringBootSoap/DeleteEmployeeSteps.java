package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.DeleteEmployeeRequest;
import com.example.springboot.soap.interfaces.ObjectFactory;
import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static ge.tbc.tbcitacademy.Util.Marshall.marshallSoapRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.Matchers.hasXPath;

public class DeleteEmployeeSteps {
    String body;
    Response response;
    @Step("Create delete request body")
    public DeleteEmployeeSteps createDeleteRequestBody(long id){
        ObjectFactory objectFactory = new ObjectFactory();
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest();
        deleteEmployeeRequest.setEmployeeId(id);
        body = marshallSoapRequest(deleteEmployeeRequest);
        return this;
    }

    @Step("Send delete Request body and get response")
    public DeleteEmployeeSteps sendDeleteEmployeeRequest(){
        response = given()
                .config(RestAssured.config().xmlConfig(xmlConfig()
                        .declareNamespace("ns2", Constants.ns2NameSpace)
                        .declareNamespace("SOAP-ENV", Constants.soapEnvNameSpace)))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", Constants.deleteEmployeeAction)
                .body(body)
                .post(Constants.baseUri);
        return this;
    }

    @Step("Validate message in response")
    public DeleteEmployeeSteps validateDelete(){
        response
                .then()
                .statusCode(200)
                .body(hasXPath("//*[local-name()='message' and namespace-uri()='" + Constants.ns2NameSpace +"']" +
                        "[text()='" + Constants.contentDeletedSuccessfully + "']"));
        return this;
    }
}
