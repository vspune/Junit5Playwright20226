package com.example.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.Locator;

public class HomePage {
    private final Page page;
    private final Locator search;   

    public HomePage(Page page) {
        this.page = page;
        this.search = page.locator("//section[@id='sidebar']//b[1]"); 
    }

    public void isLoaded() {
       page.waitForLoadState(LoadState.NETWORKIDLE);
       page.locator(".card-body").first().waitFor(new Locator.WaitForOptions().setTimeout(5000));
       boolean loaded = page.url().contains("dashboard");
       System.out.println("✅ Home page check: " + loaded + " | URL: " + page.url());
       //return loaded;
    }

    public String getTitle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.title();
    }

    public void searchisdisplayed() {
        search.isVisible();
    }
     


}
