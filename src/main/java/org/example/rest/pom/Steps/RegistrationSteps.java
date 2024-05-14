package org.example.rest.pom.Steps;

import com.example.local.invoker.ApiClient;
import com.example.local.model.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.rest.pom.Data.Constants;

import static com.example.local.invoker.ResponseSpecBuilders.shouldBeCode;
import static com.example.local.invoker.ResponseSpecBuilders.validatedWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;


public class RegistrationSteps {
    RegisterRequest registerRequest;
    AuthenticationResponse registerResponse;
    AuthenticationResponse authResponse;
    Response getResourceResponse;
    RefreshTokenResponse refTokenResponse;

    @Step("Generate registration request body")
    public RegistrationSteps generateRequestBody(String email, String password){
        registerRequest = new RegisterRequest()
                .firstname(Constants.username)
                .lastname(Constants.lastname)
                .email(email)
                .password(password)
                .role(RegisterRequest.RoleEnum.ADMIN);
        return this;
    }

    @Step("Register with generated user")
    public RegistrationSteps registerUser(ApiClient api){
        registerResponse = api
                .authentication()
                .register()
                .body(registerRequest)
                .executeAs(response -> {
                    validatedWith(shouldBeCode(200));
                    return response;
                });
        return this;
    }

    @Step("Authenticate with registered user")
    public RegistrationSteps authenticate(ApiClient api){
        authResponse = api
                .authentication()
                .authenticate()
                .body(new AuthenticationRequest()
                        .email(registerRequest.email())
                        .password(registerRequest.password()))
                .executeAs(response -> response);
        return this;
    }

    @Step("Validate that authenticated user has privileges")
    public RegistrationSteps validateAuthentication(){
        assertThat(authResponse.getRoles(), hasItems(
                Constants.updatePrivilege,
                Constants.deletePrivilege,
                Constants.readPrivilege,
                Constants.writePrivilege,
                Constants.adminPrivilege));
        return this;
    }

    @Step("Pass authorization with given token and get resource")
    public RegistrationSteps authorization(ApiClient api){
        getResourceResponse = api
                .authorization()
                .sayHelloWithRoleAdminAndReadAuthority()
                .reqSpec(requestSpecBuilder -> requestSpecBuilder
                        .addHeader(Constants.authorization,
                                Constants.bearer + registerResponse.accessToken()))
                .execute(response -> response);
        return this;
    }

    @Step("Validate get resource response body")
    public RegistrationSteps validateGetResourceResponse(){
        getResourceResponse
                .then()
                .statusCode(200)
                .body(equalTo(Constants.resourceMessage));
        return this;
    }

    @Step("Refresh token")
    public RegistrationSteps refreshToken(ApiClient api){
        refTokenResponse = api
                .authentication()
                .refreshToken()
                .body(new RefreshTokenRequest()
                        .refreshToken(authResponse.refreshToken()))
                .executeAs(response -> {
                    validatedWith(shouldBeCode(200));
                    return response;
                });
        return this;
    }

    @Step("Validate old token is still valid")
    public RegistrationSteps validateOldToken(ApiClient api){
        Response oldTokenResponse = api
                .authorization()
                .sayHelloWithRoleAdminAndReadAuthority()
                .reqSpec(requestSpecBuilder -> requestSpecBuilder
                        .addHeader(Constants.authorization,
                                Constants.bearer + registerResponse.accessToken()))
                .execute(response -> response);
        oldTokenResponse.then().statusCode(200);
        return this;
    }
}
