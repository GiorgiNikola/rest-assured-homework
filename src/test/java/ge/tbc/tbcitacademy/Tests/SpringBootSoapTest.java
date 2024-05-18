package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.SpringBootSoap.AddEmployeeSteps;
import ge.tbc.tbcitacademy.Steps.SpringBootSoap.DeleteEmployeeSteps;
import ge.tbc.tbcitacademy.Steps.SpringBootSoap.GetEmployeeSteps;
import ge.tbc.tbcitacademy.Steps.SpringBootSoap.UpdateEmployeeSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeConfigurationException;

@Epic("SpringBoot SOAP Web Service")
public class SpringBootSoapTest {

    AddEmployeeSteps addEmployeeSteps;
    GetEmployeeSteps getEmployeeSteps;
    UpdateEmployeeSteps updateEmployeeSteps;
    DeleteEmployeeSteps deleteEmployeeSteps;

    @BeforeClass
    public void pageSetup(){
        RestAssured.filters(new AllureRestAssured());
        addEmployeeSteps = new AddEmployeeSteps();
        getEmployeeSteps = new GetEmployeeSteps();
        updateEmployeeSteps = new UpdateEmployeeSteps();
        deleteEmployeeSteps = new DeleteEmployeeSteps();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Employee Management")
    @Story("CRUD Operations on Employee")
    @Description("This test case performs Add, Get, Update, and Delete operations" +
            " on an employee and validates the result")
    @Test(description = "CRUD Operations on Employee")
    public void employeeTest() throws DatatypeConfigurationException {
        addEmployeeSteps
                .createAddRequestBody()
                .sendAddEmployeeRequest()
                .deserializeResponse()
                .validateAddEmployeeResponse();
        getEmployeeSteps
                .createGetRequestBody(Constants.employeeId)
                .sendGetEmployeeRequest()
                .deserializeResponse()
                .validateEmployeeDetails(Constants.name, Constants.department,
                        Constants.phone, Constants.address, Constants.email);
        updateEmployeeSteps
                .createUpdateRequestBody()
                .sendUpdateEmployeeRequest()
                .deserializeResponse()
                .validateUpdateEmployeeResponse();
        getEmployeeSteps
                .createGetRequestBody(Constants.employeeId)
                .sendGetEmployeeRequest()
                .deserializeResponse()
                .validateEmployeeDetails(Constants.updatedName, Constants.updatedDepartment,
                         Constants.updatedPhone, Constants.updatedAddress, Constants.updatedEmail);
        deleteEmployeeSteps
                .createDeleteRequestBody(Constants.employeeId)
                .sendDeleteEmployeeRequest()
                .deserializeResponse()
                .validateDelete();
        getEmployeeSteps
                .createGetRequestBody(Constants.employeeId)
                .sendGetEmployeeRequest()
                .validateErrorMessage();
    }

}
