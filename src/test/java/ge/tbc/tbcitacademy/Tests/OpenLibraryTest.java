package ge.tbc.tbcitacademy.Tests;

import ge.tbc.tbcitacademy.Steps.BookstoreSteps;
import ge.tbc.tbcitacademy.Steps.OpenLibrarySteps;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OpenLibraryTest {
    OpenLibrarySteps openLibrarySteps;

    @BeforeClass
    public void pageSetup(){
        openLibrarySteps = new OpenLibrarySteps();
    }

    @Test
    public void booksTest(){
        Response response = openLibrarySteps.getBooks();
        openLibrarySteps
                .validateResponseContainsBooks(response)
                .validateFirstBookAuthorAndTitle(response)
                .validatePlaceArray(response);
    }
}
