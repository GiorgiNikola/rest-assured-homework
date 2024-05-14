package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Steps.Petstore3Steps.AddPetSteps;
import ge.tbc.tbcitacademy.Steps.Petstore3Steps.StoreOrderSteps;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.invoker.JacksonObjectMapper;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

@Epic("Petstore API Test")
public class PetstoreApiTest {
    private ApiClient api;
    StoreOrderSteps storeOrderSteps;
    AddPetSteps addPetSteps;
    @BeforeSuite
    public void createApiClient() {
        api = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .log(LogDetail.ALL)
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()

                                        .defaultObjectMapper(JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter())
                        .addFilter(new AllureRestAssured())
                        .setBaseUri(Constants.petstoreUri)));
    }

    @BeforeClass
    public void pageSetup(){
        storeOrderSteps = new StoreOrderSteps();
        addPetSteps = new AddPetSteps();
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Petstore post placement")
    @Story("Posting pet order on petstore")
    @Description("Generate pet order body, send request then get response deserialize it and validate")
    @Test(description = "Generate pet order and send request")
    public void storeOrderTest() {
        storeOrderSteps
                .generateOrderBody()
                .postStoreOrder(api)
                .validateResponse();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Add new pet to petstore")
    @Story("Adding new pet to petstore")
    @Description("Generate new pet request body send request then get response ")
    @Test(description = "Adding new pet to petstore, then validating response")
    public void addNewPetTest(){
        addPetSteps
                .generatePetRequest()
                .postNewPet(api)
                .validatePet();
    }
}
