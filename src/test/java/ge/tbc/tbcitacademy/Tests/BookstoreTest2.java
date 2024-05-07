package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Steps.BookstoreSteps.BookstoreSteps;
import ge.tbc.tbcitacademy.Steps.BookstoreSteps.BookstoreValidationSteps;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookstoreTest2 {
    BookstoreValidationSteps bookstoreValidationSteps;
    BookstoreSteps bookstoreSteps;

    @BeforeClass
    public void pageSetup(){
        bookstoreValidationSteps = new BookstoreValidationSteps();
        bookstoreSteps = new BookstoreSteps();
    }

    @Test
    public void validationsTest(){
        Response response = bookstoreSteps
                .getAllBook();
        bookstoreValidationSteps
                .validatePages(response)
                .validateAuthors(response);
    }
}
