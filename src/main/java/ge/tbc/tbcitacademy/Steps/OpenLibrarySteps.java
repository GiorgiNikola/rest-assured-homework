package ge.tbc.tbcitacademy.Steps;

import ge.tbc.tbcitacademy.Data.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

public class OpenLibrarySteps {
    public OpenLibrarySteps() {
        RestAssured.baseURI = "https://openlibrary.org";
    }

    public Response getBooks(){
        return RestAssured
                .given()
                .queryParam("q", Constants.harryPotter)
                .accept(Constants.responseFormat)
                .when()
                .get("/search.json");
    }

    public OpenLibrarySteps validateResponseContainsBooks(Response response){
        response
                .then()
                .body("docs", Matchers.notNullValue());
        return this;
    }

    public OpenLibrarySteps validateFirstBookAuthorAndTitle(Response response){
        response
                .then()
                .body("docs[0].author_name[0]", Matchers.is(Constants.harryPotterAuthor),
                        "docs[0].title", Matchers.is(Constants.harryPotterFirstBook));
        return this;
    }

    public OpenLibrarySteps validatePlaceArray(Response response){
        response
                .then()
                .body("docs[0].place", Matchers.hasItems(Constants.harryPotterPlace1,
                        Constants.harryPotterPlace2,
                        Constants.harryPotterPlace3));
        return this;
    }
}
