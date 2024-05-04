package ge.tbc.tbcitacademy.DataProvider;

import org.testng.annotations.DataProvider;

public class CustomDataProvider {
    @DataProvider
    public Object[][] infoProvider(){
        return new Object[][]{
                {0, "books[0].isbn"},
                {1, "books[1].isbn"},
                {2, "books[2].isbn"},
                {3, "books[3].isbn"},
        };
    }
}
