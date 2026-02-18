package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPage {
    private final Page page;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator toasterrorMessage;
    private final Locator blankemail;
    private final Locator blankpassword;

    public LoginPage(Page page) {
        this.page = page;
        this.emailInput = page.locator("input#userEmail, input[type='email']");
        this.passwordInput = page.locator("input#userPassword, input[type='password']");
        this.loginButton = page.locator("#login");
        this.toasterrorMessage = page.locator("#toast-container"); 
        this.blankpassword = page.locator("div[class='ng-star-inserted']");    
        this.blankemail = page.locator("div[class='ng-star-inserted']");

    }// Adjust selector based on actual error message element

    public void open(String url) {
        page.navigate(url);
    }

    public boolean isEmailVisible() {
        return emailInput.isVisible();
    }

    public boolean isPasswordInputVisible() {
        return passwordInput.isVisible();
    }

    public boolean isloginbuttonVisible() {
        return loginButton.isVisible();
    }

    public void setEmail(String email) {
        emailInput.fill(email);
    }

    public void setPassword(String password) {
        passwordInput.fill(password);
    }

    public String getBlankEmailError() {
        try {
            return blankemail.first().textContent();
        } catch (Exception e) {
            return null;
        }
    }
    
    public String getBlankPasswordError() {
        try {
            return blankpassword.first().textContent();
        } catch (Exception e) {
            return null;
        }
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isErrorVisible() {
        try {
            Locator toast = page.locator(".toast-message");

        toast.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        String toastText = toast.innerText();
        System.out.println("Toast message: " + toastText);
            return toasterrorMessage.first().isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    // public String errorText() {
    //     try {
    //         return errorMessage.first().textContent();
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }
}
