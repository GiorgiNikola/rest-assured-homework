package ge.tbc.tbcitacademy.Steps.Petstore3Steps;

import ge.tbc.tbcitacademy.Data.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseOptions;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.model.Order;

import java.time.OffsetDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static pet.store.v3.invoker.ResponseSpecBuilders.shouldBeCode;
import static pet.store.v3.invoker.ResponseSpecBuilders.validatedWith;

public class StoreOrderSteps {
    Order requestOrder;
    Order responseOrder;
    Response response;
    @Step("Generate order request body")
    public StoreOrderSteps generateOrderBody(){
        requestOrder = new Order()
                .id(Constants.Id)
                .petId(Constants.Id)
                .quantity(Constants.quantity)
                .shipDate(OffsetDateTime.parse(Constants.shipDate))
                .status(Order.StatusEnum.DELIVERED)
                .complete(Constants.complete);
        return this;
    }
    @Step("Post store order, get response and deserialized pojo object, validate status code")
    public StoreOrderSteps postStoreOrder(ApiClient api){
        responseOrder = api
                .store()
                .placeOrder()
                .body(requestOrder)
                .executeAs(response -> {
                    this.response = response;
                    validatedWith(shouldBeCode(200)).andThen(ResponseBody::print).apply(response);
                    return response;
                });
        return this;
    }

    @Step("Validate response pojo object")
    public StoreOrderSteps validateResponse(){
        assertThat(responseOrder.getId(), equalTo(requestOrder.getId()));
        assertThat(responseOrder.getPetId(), equalTo(requestOrder.getPetId()));
        assertThat(responseOrder.getQuantity(), equalTo(requestOrder.getQuantity()));
        assertThat(responseOrder.getShipDate(), equalTo(requestOrder.getShipDate()));
        assertThat(responseOrder.getStatus(), equalTo(requestOrder.getStatus()));
        assertThat(responseOrder.getComplete(), equalTo(requestOrder.getComplete()));
        return this;
    }

}
