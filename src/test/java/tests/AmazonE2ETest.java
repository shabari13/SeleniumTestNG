package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.Status;

import pages.*;
import utils.ExtentReportManager;
import utils.LoggerUtil;


public class AmazonE2ETest extends BaseTest {
    
	private static final Logger logger = LoggerUtil.getLogger(AmazonE2ETest.class);
    @Test
    public void testAmazonEndToEndFlow() {
    	LoggerUtil.logTestStart(logger, "testAmazonEndToEndFlow");
    	
    	try {
    		    LoggerUtil.logStep(logger, "Starting login process");
		        LoginPage loginPage = new LoginPage(driver);
		        loginPage.performLogin(
		            ConfigReader.getTestEmail(), 
		            ConfigReader.getTestPassword()
		        );
		        
		        logger.info("Login performed with credentials");
		        Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in");
		        LoggerUtil.logInfo(logger, "✓ User successfully logged in");
		        
		     
		        SearchPage searchPage = new SearchPage(driver);
		        String searchProduct = ConfigReader.getProperty("searchProduct");
		        logger.info("Search product from config: " + searchProduct);
		        searchPage.searchForProduct(searchProduct);
		        
		        Assert.assertTrue(
		            searchPage.isProductDisplayedInResults("adidas-Womens-Court-Sneaker-White"),
		            "Product should be displayed in search results"
		        );
		        
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
		        
		       System.out.println("test 123");
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
		            "3",  
		            "2027", 
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
		      
		        LoggerUtil.logTestEnd(logger, "testAmazonEndToEndFlow", "PASSED");
		    } catch (Exception e) {
		    	 LoggerUtil.logError(logger, "Test failed with exception", e);
	            LoggerUtil.logTestEnd(logger, "testAmazonEndToEndFlow", "FAILED");
	            throw e;
	        }
    }
}
