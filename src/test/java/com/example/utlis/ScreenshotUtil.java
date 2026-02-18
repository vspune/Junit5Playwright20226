package com.example.utlis;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static void capture(Page page, String testName) {

        String filePath =
                "test-results/screenshots/" + testName + ".png";

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(filePath))
                .setFullPage(true));
    }
}

