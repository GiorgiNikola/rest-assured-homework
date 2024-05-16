package ge.tbc.tbcitacademy.Steps.SpringBootSoap;

import com.example.springboot.soap.interfaces.GetEmployeeByIdRequest;
import com.example.springboot.soap.interfaces.ObjectFactory;
import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import javax.xml.namespace.NamespaceContext;

import java.util.HashSet;
import java.util.Iterator;

import static ge.tbc.tbcitacademy.Util.Marshall.marshallSoapRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.Matchers.*;

public class GetEmployeeSteps {
    Response response;
    String body;
    private final NamespaceContext ns = new NamespaceContext() {
        @Override
        public String getNamespaceURI(String prefix) {
            if ("ns2".equals(prefix)) {
                return Constants.ns2NameSpace;
            } else if ("SOAP-ENV".equals(prefix)) {
                return Constants.soapEnvNameSpace;
            }
            return null;
        }

        @Override
        public String getPrefix(String namespaceURI) {
            if (Constants.ns2NameSpace.equals(namespaceURI)) {
                return "ns2";
            } else if (Constants.soapEnvNameSpace.equals(namespaceURI)) {
                return "SOAP-ENV";
            }
            return null;
        }

        @Override
        public Iterator<String> getPrefixes(String namespaceURI) {
            HashSet<String> prefixes = new HashSet<>();
            String prefix = getPrefix(namespaceURI);
            if (prefix != null) {
                prefixes.add(prefix);
            }
            return prefixes.iterator();
        }
    };
    @Step("Create get request body")
    public GetEmployeeSteps createGetRequestBody(int id){
        ObjectFactory objectFactory = new ObjectFactory();
        GetEmployeeByIdRequest employeeByIdRequest = objectFactory.createGetEmployeeByIdRequest();
        employeeByIdRequest.setEmployeeId(id);
        body = marshallSoapRequest(employeeByIdRequest);
        return this;
    }

    @Step("Send get employee request with body and get response")
    public GetEmployeeSteps sendGetEmployeeRequest(){
        response = given()
                .config(RestAssured.config().xmlConfig(xmlConfig()
                        .declareNamespace("ns2", Constants.ns2NameSpace)
                        .declareNamespace("SOAP-ENV", Constants.soapEnvNameSpace)))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", Constants.getEmployeeByIdAction)
                .body(body)
                .post(Constants.baseUri);
        return this;
    }

    @Step("Validate various employee details")
    public GetEmployeeSteps validateEmployeeDetails(String name,String department,
                                                    String phone, String address, String email) {
        response
                .then()
                .body(hasXPath("//ns2:employeeInfo/ns2:name", ns, equalTo(name)))
                .body(hasXPath("//ns2:employeeInfo/ns2:department", ns, equalTo(department)))
                .body(hasXPath("//ns2:employeeInfo/ns2:phone", ns, equalTo(phone)))
                .body(hasXPath("//ns2:employeeInfo/ns2:address", ns, equalTo(address)))
                .body(hasXPath("//ns2:employeeInfo/ns2:email", ns, equalTo(email)));
        return this;
    }

    @Step("Validate error message in response and status code")
    public GetEmployeeSteps validateErrorMessage(){
        response
                .then()
                .statusCode(500)
                .body(hasXPath("//SOAP-ENV:Fault/faultstring", ns, equalTo(Constants.errorMessage)));
        return this;
    }
}
