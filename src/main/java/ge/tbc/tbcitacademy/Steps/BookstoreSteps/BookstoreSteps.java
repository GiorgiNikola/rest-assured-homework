package ge.tbc.tbcitacademy.Steps.BookstoreSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Responses.Bookstore.BooksResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.Map;

public class BookstoreSteps {
    public BookstoreSteps() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/BookStore/v1";
    }

    public Response getAllBook(){
        return RestAssured
                .given()
                .accept(Constants.responseFormat)
                .when()
                .get("/Books");
    }

    public BooksResponse getBooks(Response response){
        return response
                .then()
                .extract()
                .as(BooksResponse.class);
    }

    public String extractData(Map<String, Object> book, String key){
        return (String) book.get(key);
    }

    public Response getBookInfo(String isbn){
        return RestAssured
                .given()
                .queryParam("ISBN", isbn)
                .accept(Constants.responseFormat)
                .when()
                .get("/Book");
    }

    public BookstoreSteps validateStatusCode(Response response, int statusCode){
        response
                .then()
                .statusCode(statusCode);
        return this;
    }

    public BookstoreSteps validateAuthors(Response response, String author){
        response
                .then()
                .body("author", Matchers.is(author));
        return this;
    }

    public BookstoreSteps validateResponseContainsInfo(Response response){
        response
                .then()
                .body("title", Matchers.notNullValue(),
                        "isbn", Matchers.notNullValue(),
                        "publish_date", Matchers.notNullValue(),
                        "pages", Matchers.notNullValue());
        return this;
    }

    public Response deleteResponse(){
        return RestAssured
                .given()
                .accept(Constants.responseFormat)
                .when()
                .delete("/Book");
    }

    public BookstoreSteps validateMessage(Response response, String message){
        response
                .then()
                .body("message", Matchers.is(message));
        return this;
    }
}
