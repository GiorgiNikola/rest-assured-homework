package ge.tbc.tbcitacademy.Steps.BookstoreSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

public class BookstoreValidationSteps {

    public BookstoreValidationSteps validatePages(Response response){
        response
                .then()
                .body("books.pages", Matchers.everyItem(Matchers.lessThan(1000)));
        return this;
    }

    public BookstoreValidationSteps validateAuthors(Response response){
        response
                .then()
                .body("books[0].author", Matchers.is(Constants.author1),
                        "books[1].author", Matchers.is(Constants.author2));
        return this;
    }
}
