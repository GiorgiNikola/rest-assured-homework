package org.example.rest.tests;

import com.example.local.invoker.ApiClient;
import com.example.local.invoker.JacksonObjectMapper;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import org.example.rest.pom.Data.Constants;
import org.example.rest.pom.DataProvider.CustomDataProvider;
import org.example.rest.pom.Steps.RegistrationSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

@Epic("Local API Test")
public class RegistrationTest {

    private ApiClient api;
    RegistrationSteps registrationSteps;
    @BeforeSuite
    public void createApiClient() {
        api = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()
                                        .defaultObjectMapper(JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter())
                        .addFilter(new AllureRestAssured())
                        .setBaseUri(Constants.localUri)));
    }

    @BeforeClass
    public void pageSetup(){
        registrationSteps = new RegistrationSteps();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("LocalAPI registration")
    @Story("Registration,authentication and getting resource")
    @Description("Register in service, pass authentication then pass authorization with token " +
            "to get resource and validate it then refresh token and validate old token")
    @Test(dataProvider = "userCredentialProvider", dataProviderClass = CustomDataProvider.class,
    description = "Registration, authentication, authorization and token refreshing")
    public void registrationTest(String email, String password){
        registrationSteps
                .generateRequestBody(email, password)
                .registerUser(api)
                .authenticate(api)
                .validateAuthentication()
                .authorization(api)
                .validateGetResourceResponse()
                .refreshToken(api)
                .validateOldToken(api);
    }
}
