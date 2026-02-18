package com.example.listeners;

import com.example.tests.BaseTest;
import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.extension.*;

public class TestWatcherExtension implements TestWatcher {

     @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        attachScreenshot(context, "Screenshot - FAILED");
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        attachScreenshot(context, "Screenshot - PASSED");
    }

    private void attachScreenshot(ExtensionContext context, String name) {
        Object testInstance = context.getRequiredTestInstance();

        if (testInstance instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) testInstance;

            Page page = baseTest.getPage();

       if (page != null && !page.isClosed()) {
                try {
                    byte[] screenshot = page.screenshot(
                        new Page.ScreenshotOptions().setFullPage(true)
                    );
                    Allure.addAttachment(
                        name,
                        "image/png",                        // ✅ MIME type
                        new ByteArrayInputStream(screenshot),
                        "png"                               // ✅ extension
                    );
                } catch (Exception e) {
                    System.err.println("Screenshot failed: " + e.getMessage());
                }
            }

            // ✅ Close browser AFTER screenshot
            baseTest.closeBrowser();
        }
    }
}