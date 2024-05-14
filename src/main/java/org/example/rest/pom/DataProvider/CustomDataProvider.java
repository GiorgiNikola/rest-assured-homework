package org.example.rest.pom.DataProvider;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

public class CustomDataProvider {
    Faker faker = new Faker();
    @DataProvider
    public Object[][] userCredentialProvider(){
        return new Object[][]{
                // Valid Credentials
                {faker.internet().emailAddress(), "P@ssw0rd"},
                // Invalid Credentials
                {faker.internet().emailAddress(), faker.lorem().characters(9)},  // More than 8 characters
                {faker.internet().emailAddress(), faker.lorem().characters(8, true, false)}, // Only lowercase letters
                {faker.internet().emailAddress(), faker.lorem().characters(8, false, true)}, // Only uppercase letters
                {faker.internet().emailAddress(), faker.lorem().characters(8, false, false)} // Only special characters
        };
    }
}
