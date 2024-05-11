package ge.tbc.tbcitacademy.DataProvider;

import ge.tbc.tbcitacademy.Data.Constants;
import org.checkerframework.checker.units.qual.C;
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

    @DataProvider
    public Object[][] bookingUpdateProvider(){
        return new Object[][]{
                {
                    Constants.firstname,
                    Constants.lastname,
                    Constants.totalPrice,
                    Constants.depositPaid,
                    Constants.checkIn,
                    Constants.checkOut,
                    Constants.specialNeed,
                    Constants.salesPrice,
                    Constants.passportNo
                },
                {
                    Constants.firstname1,
                    Constants.lastname1,
                    Constants.totalPrice1,
                    Constants.depositPaid1,
                    Constants.checkIn1,
                    Constants.checkOut1,
                    Constants.specialNeed1,
                    Constants.salesPrice1,
                    Constants.passportNo
                }
        };
    }
}
