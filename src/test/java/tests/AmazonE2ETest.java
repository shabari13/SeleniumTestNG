package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class AmazonE2ETest extends BaseTest {
    
    @Test
    public void testAmazonEndToEndFlow() {
      
        LoginPage loginPage = new LoginPage(driver);
        System.out.println("In E2E");
        loginPage.performLogin(
            ConfigReader.getTestEmail(), 
            ConfigReader.getTestPassword()
        );
        
        Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in");
        System.out.println("✓ Successfully logged in");
        
     
        SearchPage searchPage = new SearchPage(driver);
        String searchProduct = ConfigReader.getProperty("searchProduct");
        searchPage.searchForProduct(searchProduct);
        
        Assert.assertTrue(
            searchPage.isProductDisplayedInResults("adidas-Womens-Court-Sneaker-White"),
            "Product should be displayed in search results"
        );
        System.out.println("✓ Product found in search results");
        
        
        String productAsin = ConfigReader.getProperty("productAsin");
        searchPage.clickProductByAsin(productAsin);
        
        
        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();
        
        String count = productPage.getCartCount();
    	
       
       if (count.equals("1")) {
            System.out.println("Item successfully added to cart!");
       } else {
            System.out.println("Cart count is: " + count);
       }
        
		/*
		 * Assert.assertTrue( productPage.isCartCountUpdated("1"),
		 * "Cart count should be updated to 1" );
		 * System.out.println("✓ Item successfully added to cart");
		 */
        
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.proceedToCheckout();
        
       
        checkoutPage.clickAddCardLink();
        checkoutPage.switchToPaymentFrame();
        
        checkoutPage.enterCardDetails(
            ConfigReader.getProperty("cardNumber"),
            ConfigReader.getProperty("cardHolder"),
            "3",  // Month
            "2027",  // Year
            ConfigReader.getProperty("cvv")
        );
        
        checkoutPage.submitCard();
        
        
        Assert.assertTrue(
            checkoutPage.isErrorDisplayed(),
            "Error message should be displayed for invalid card"
        );
        
        Assert.assertEquals(
            checkoutPage.getErrorHeading().trim(),
            "There was a problem.",
            "Error heading should match expected text"
        );
        
        System.out.println("✓ Test completed successfully - Invalid card error verified");
    }
}
