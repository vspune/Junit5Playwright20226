package com.example.tests;

import com.example.listeners.TestWatcherExtension;
import com.example.utlis.*;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import io.qameta.allure.Allure;
import io.qameta.allure.junit5.AllureJunit5;

@ExtendWith(AllureJunit5.class)
@ExtendWith(TestWatcherExtension.class)
public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    void setUp() {

        System.out.println("========================================");
        //System.out.println("🌍 Environment: " + configreader.getEnvironment());
        System.out.println("🔗 Base URL: " + configreader.get("base.url"));
        System.out.println("========================================");

        String browserName = System.getProperty("browser", "chromium");

        Allure.label("browser", browserName);
        Allure.parameter("Browser", browserName);

        playwright = Playwright.create();

        BrowserType browserType;
        switch (browserName.toLowerCase()) {
            case "firefox": browserType = playwright.firefox(); break;
            case "webkit":  browserType = playwright.webkit();  break;
            default:        browserType = playwright.chromium();
        }

        browser = browserType.launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );

        context = browser.newContext();
        page = context.newPage();
        page.navigate(configreader.get("base.url"));

        page.navigate(configreader.get("base.url")); 

   
    }

    // ✅ No @AfterEach here — TestWatcher calls this AFTER screenshot
    public void closeBrowser() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public Page getPage() {
        return page;
    }
}



