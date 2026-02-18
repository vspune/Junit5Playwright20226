package com.example.tests;
import com.example.utlis.*;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import com.example.pages.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


@Tag("Smoke")
@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    private LoginPage login;

    @BeforeEach
    void initPages() {
        login = new LoginPage(page);  // ✅ single initialisation
        Allure.parameter("Environment", configreader.getEnvironment());
        Allure.parameter("Base URL", configreader.get("base.url"));
    }

    // -------------------------------------------------------
    // TC-001
    // -------------------------------------------------------
    @Test
    @Story("Invalid Credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies error message appears for invalid credentials")
    void invalidCredentialsShouldShowError() {
        assertTrue(login.isEmailVisible(), "Email input should be visible");

        login.setEmail(configreader.get("invalid.email"));      // ✅ from config
        login.setPassword(configreader.get("invalid.password")); // ✅ from config
        login.clickLogin();

        assertTrue(login.isErrorVisible(),
            "Error should appear for invalid credentials");
    }

    // -------------------------------------------------------
    // TC-002
    // -------------------------------------------------------
    @ParameterizedTest(name = "[{index}] {2}") //Auto-incremented number — 1, 2, 3, 4 , values of Value of the 3rd parameter (zero-based index)
    @CsvSource({
        "'', Test@1234,          EMAIL_REQUIRED",
        "test@example.com, '',   PASSWORD_REQUIRED",
        "'', '',                 BOTH_EMAIL_PASSWORD_REQUIRED", 
        "wrong@email.com, wrongpass, INVALID_CREDENTIALS"
    })
    @Story("Validation Messages")
    @Severity(SeverityLevel.NORMAL)
    void invalidLoginShouldShowError( String email, String password, Loginerror expectedError) {

        assertAll("Login page validation",
            () -> assertEquals(login.isEmailVisible(), "Email is visible"),
            () -> assertTrue(login.isPasswordInputVisible(), "Passowrd is visible"),
            () -> assertTrue(login.isloginbuttonVisible(), "Login button is visible")
        );

        login.setEmail(email);
        login.setPassword(password);
        login.clickLogin();

        switch (expectedError) {
            case EMAIL_REQUIRED:
                assertTrue(login.getBlankEmailError().contains("*Email is required"));
                break;

            case PASSWORD_REQUIRED:
                assertTrue(login.getBlankPasswordError().contains("*Password is required"));
                break;

            case BOTH_EMAIL_PASSWORD_REQUIRED:              
                assertTrue(login.getBlankEmailError()
                    .contains("*Email is required"));
                assertTrue(login.getBlankPasswordError()
                    .contains("*Password is required"));
                break;

            case INVALID_CREDENTIALS:
                assertTrue(login.isErrorVisible());
                break;

            default:
                throw new IllegalArgumentException(       
                    "Unknown error type: " + expectedError);
        }
    }

    // -------------------------------------------------------
    // TC-003
    // -------------------------------------------------------
    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies successful login navigates to home page")
    void validLoginShouldSucceed() {

        login.setEmail(configreader.get("valid.email"));      
        login.setPassword(configreader.get("valid.password"));
        login.clickLogin();

        HomePage home = new HomePage(page);
        home.isLoaded();
    }
}


