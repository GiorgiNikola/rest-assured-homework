package ge.tbc.tbcitacademy.Steps.BookstoreSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Responses.Bookstore.BooksItem;
import ge.tbc.tbcitacademy.Models.Responses.Bookstore.BooksResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookstoreValidationSteps {

    public BookstoreValidationSteps validatePages(BooksResponse booksResponse){
        for (BooksItem book : booksResponse.getBooks()){
            assertThat(book.getPages(), lessThan(1000));
        }
        return this;
    }

    public BookstoreValidationSteps validateFirstAndSecondAuthor(BooksResponse booksResponse){
        assertThat(booksResponse.getBooks().get(0).getAuthor(), equalTo(Constants.author1));
        assertThat(booksResponse.getBooks().get(1).getAuthor(), equalTo(Constants.author2));
        return this;
    }

    public BookstoreValidationSteps validateLastTwoAuthors(BooksResponse booksResponse){
        int booksSize = booksResponse.getBooks().size();
        assertThat(booksResponse.getBooks().get(booksSize - 2).getAuthor(), equalTo(Constants.author3));
        assertThat(booksResponse.getBooks().get(booksSize - 1).getAuthor(), equalTo(Constants.author4));
        return this;
    }
}
