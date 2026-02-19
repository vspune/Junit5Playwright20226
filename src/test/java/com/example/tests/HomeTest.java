package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.LoginPage;
import com.example.utlis.*;

import io.qameta.allure.Epic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Tag;

public class HomeTest extends BaseTest {
    @Test
    @Tag("HomePage")
    @Epic("Home Page")
    
    public void TitleHomeSite() {
    LoginPage login = new LoginPage(page);      // PRECONDITION → reach dashboard

        login.setEmail(configreader.get("valid.email"));
        login.setPassword(configreader.get("valid.password"));
        login.clickLogin();
        HomePage home = new HomePage(page);
        home.searchisdisplayed();
        String title = home.getTitle();
        System.out.println("Home page title: " + title);
        assertNotNull(title);
        assertEquals("Let's Shop", title, "Home page title should be 'Let's Shop'");

    }
}
