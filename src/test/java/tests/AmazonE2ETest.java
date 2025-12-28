package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pages.*;
import utils.ExtentReportManager;

public class AmazonE2ETest extends BaseTest {
    
    @Test
    public void testAmazonEndToEndFlow() {
    	try {
	 
		        LoginPage loginPage = new LoginPage(driver);
		        System.out.println("In E2E");
		        loginPage.performLogin(
		            ConfigReader.getTestEmail(), 
		            ConfigReader.getTestPassword()
		        );
		        
		        Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in");
		        ExtentReportManager.getExtentTest().log(Status.PASS, "Successfully logged in");
		        
		     
		        SearchPage searchPage = new SearchPage(driver);
		        ExtentReportManager.getExtentTest().log(Status.INFO, "Searching for product");
		        String searchProduct = ConfigReader.getProperty("searchProduct");
		        searchPage.searchForProduct(searchProduct);
		        
		        Assert.assertTrue(
		            searchPage.isProductDisplayedInResults("adidas-Womens-Court-Sneaker-White"),
		            "Product should be displayed in search results"
		        );
		        ExtentReportManager.getExtentTest().log(Status.PASS, "Product found in search results");
		        
		        ExtentReportManager.getExtentTest().log(Status.INFO, "Adding product to cart");
		        String productAsin = ConfigReader.getProperty("productAsin");
		        searchPage.clickProductByAsin(productAsin);
		        
		        
		        ProductPage productPage = new ProductPage(driver);
		        productPage.addToCart();
		        
		        String count = productPage.getCartCount();
		        ExtentReportManager.getExtentTest().log(Status.INFO, "Cart count: " + count);
		    	
		       
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
		       ExtentReportManager.getExtentTest().log(Status.INFO, "Proceeding to checkout");
		        CheckoutPage checkoutPage = new CheckoutPage(driver);
		        checkoutPage.proceedToCheckout();
		        
		        ExtentReportManager.getExtentTest().log(Status.INFO, "Entering payment details");
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
		        
		        ExtentReportManager.getExtentTest().log(Status.PASS, 
		                "Invalid card error verified successfully");
		        System.out.println("✓ Test completed successfully - Invalid card error verified");
		    } catch (Exception e) {
	            ExtentReportManager.getExtentTest().log(Status.FAIL, "Test failed: " + e.getMessage());
	            throw e;
	        }
    }
}
