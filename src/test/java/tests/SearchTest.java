package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SearchPage;

public class SearchTest extends BaseTest {
    
    @Test
    public void testProductSearch() {
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        loginPage.performLogin(
            ConfigReader.getTestEmail(), 
            ConfigReader.getTestPassword()
        );
        
        // Search for product
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchForProduct(ConfigReader.getProperty("searchProduct"));
        
        Assert.assertTrue(
            searchPage.isProductDisplayedInResults("adidas-Womens-Court-Sneaker"),
            "Product should be found in search results"
        );
        System.out.println("✓ Search test passed");
    }
}
