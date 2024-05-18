package ge.tbc.tbcitacademy.Data;

public class Constants {
    public static final String antarctica = "Antarctica",
            americas = "The Americas", ocenania = "Ocenania",
            basePath = "Envelope.Body.ListOfContinentsByNameResponse.ListOfContinentsByNameResult.tContinent",
            ns2NameSpace = "http://interfaces.soap.springboot.example.com",
            soapEnvNameSpace = "http://schemas.xmlsoap.org/soap/envelope/",
            getEmployeeByIdAction = "interfaces.soap.springboot.example.com/exampleSoapHttp/getEmployeeByIdRequest",
            baseUri = "http://localhost:8087/ws", errorMessage = "Source must not be null",
            contentDeletedSuccessfully = "Content Deleted Successfully",
            deleteEmployeeAction = "interfaces.soap.springboot.example.com/exampleSoapHttp/deleteEmployeeRequest",
            updatedName = "Dante", updatedAddress = "Vegas", updatedEmail = "d@gmail.com",
            updatedDepartment = "Sector 5", updatedPhone = "22222222",
            contentUpdatedSuccessfully = "Content Updated Successfully",
            updateEmployeeAction = "interfaces.soap.springboot.example.com/exampleSoapHttp/updateEmployeeRequest",
            contentAddedSuccessfully = "Content Added Successfully",
            addEmployeeAction = "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest",
            name = "Giorgi", address = "Manhattan", email = "g@gmail.com",
            department = "Sector 2", phone = "11111111",
            listOfContinentsByNameAction = "http://www.oorsprong.org/websamples.countryinfo/CountryInfoServiceSoapType/ListOfContinentsByNameRequest",
            countryInfoServiceUri = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso",
            personName = "Xavier,Joe I.", personSSN = "640-94-6892", personDOB = "2013-12-12",
            soapDemoUri = "https://www.crcind.com:443/csp/samples/SOAP.Demo.cls",
            findPersonAction = "https://tempuri.org/SOAP.Demo.FindPerson",
            personLastName = "Xavier", favoriteColor = "Red";
    public static String[] sNames = new String[]{"Africa","Antarctica","Asia",
            "Ocenania","Europe","The Americas"}, sCodes = new String[]{"AF", "AN", "AS", "EU", "OC", "AM"},
            filteredSNames = new String[]{"Africa","Antarctica"}, personFavoriteColors = new String[]{"Orange", "Red"};
    public static final int updatedSalary = 10000, employeeId = 1, birthYear = 1995,
            updatedBirthYear = 1999, birthMonth = 4, birthDay = 25, salary = 1000;
}
