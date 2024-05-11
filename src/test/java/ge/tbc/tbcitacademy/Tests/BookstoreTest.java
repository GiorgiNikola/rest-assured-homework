package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.DataProvider.CustomDataProvider;
import ge.tbc.tbcitacademy.Steps.BookstoreSteps.BookstoreSteps;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class BookstoreTest {
    BookstoreSteps bookstoreSteps;

    @BeforeClass
    public void pageSetup(){
        bookstoreSteps = new BookstoreSteps();
    }

    @Test
    public void extractFirstAndSecondBookInfoTest(){
        // This retrieves the list of books (first step)
        Response booksResponse = bookstoreSteps.getAllBook();
        List<Map<String, Object>> books = booksResponse.jsonPath().getList(Constants.booksTxt);
        Map<String, Object> firstBook = books.get(0);
        String firstBookIsbn = bookstoreSteps.extractData(firstBook, Constants.isbnTxt);
        String firstBookAuthor = bookstoreSteps.extractData(firstBook, Constants.authorTxt);

        Map<String, Object> secondBook = books.get(1);
        String secondBookIsbn = bookstoreSteps.extractData(secondBook, Constants.isbnTxt);
        String secondBookAuthor = bookstoreSteps.extractData(secondBook, Constants.authorTxt);
    }

    // Third Step for each book
    @Test
    public void validateAllBookTest(){
        Response booksResponse = bookstoreSteps.getAllBook();
        List<Map<String, Object>> books = booksResponse.jsonPath().getList(Constants.booksTxt);

        for (int i = 0; i < books.size(); i++){
            Map<String, Object> book = books.get(i);

            String isbn = bookstoreSteps.extractData(book, Constants.isbnTxt);
            String expectedAuthor = bookstoreSteps.extractData(book, Constants.authorTxt);

            Response response = bookstoreSteps.getBookInfo(isbn);
            bookstoreSteps
                    .validateStatusCode(response, Constants.successfulOperationCode)
                    .validateAuthors(response, expectedAuthor)
                    .validateResponseContainsInfo(response);
        }
    }

    // Fourth step with data provider (same as third step but with data provider)
    @Test(dataProvider = "infoProvider", dataProviderClass = CustomDataProvider.class)
    public void iterateBooksTest(int index, String isbn){
        Response booksResponse = bookstoreSteps.getAllBook();
        List<Map<String, Object>> books = booksResponse.jsonPath().getList(Constants.booksTxt);
        String actualIsbn = booksResponse.jsonPath().getString(isbn);

        if (index >= 0 && index < books.size()){
            Map<String, Object> book = books.get(index);
            String expectedAuthor = bookstoreSteps.extractData(book, Constants.authorTxt);

            Response response = bookstoreSteps.getBookInfo(actualIsbn);
            bookstoreSteps
                    .validateStatusCode(response, Constants.successfulOperationCode)
                    .validateAuthors(response, expectedAuthor)
                    .validateResponseContainsInfo(response);
        }
    }

    @Test
    public void deleteBookTest(){
        Response deletedResponse = bookstoreSteps
                .deleteResponse();
        bookstoreSteps
                .validateStatusCode(deletedResponse, Constants.unauthorizedOperationCode)
                .validateMessage(deletedResponse, Constants.unauthorizedMessage);
    }
}
