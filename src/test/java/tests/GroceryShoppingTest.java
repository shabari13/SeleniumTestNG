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


public class GroceryShoppingTest extends BaseTest {
    
    private static final Logger logger = LoggerUtil.getLogger(GroceryShoppingTest.class);
    
    @Test
    public void testGroceryShoppingFlow() {
        LoggerUtil.logTestStart(logger, "testGroceryShoppingFlow");
        
        try {
            // Step 1: Login
            LoggerUtil.logStep(logger, "Starting login process");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 1: Starting login process");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.performLogin(
                ConfigReader.getTestEmail(), 
                ConfigReader.getTestPassword()
            );
            
            logger.info("Login performed with credentials");
            Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in");
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ User successfully logged in");
            LoggerUtil.logInfo(logger, "✓ User successfully logged in");
            
            // Step 2: Navigate to Groceries section
            LoggerUtil.logStep(logger, "Navigating to Groceries section");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 2: Navigating to Groceries section");
            
            GroceryPage groceryPage = new GroceryPage(driver);
            groceryPage.clickGroceriesTab();
            
            Assert.assertTrue(
                groceryPage.isGroceryPageLoaded(),
                "Grocery page should be loaded"
            );
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Navigated to Groceries section");
            LoggerUtil.logInfo(logger, "✓ Successfully navigated to Groceries");
            groceryPage.clickWholeFoodsShopNow();
            
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Navigated to Whole Foods storefront");
            LoggerUtil.logInfo(logger, "✓ Navigated to Whole Foods storefront");

            // Step 4: Add Organic Blueberries to cart
            LoggerUtil.logStep(logger, "Adding Organic Blueberries to cart");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 4: Clicking Add to Cart for Organic Blueberries");
            groceryPage.clickAddToCart();
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Clicked Add to Cart for Organic Blueberries");
            LoggerUtil.logInfo(logger, "✓ Clicked Add to Cart for Organic Blueberries");

            // Step 5: Go to Cart
            LoggerUtil.logStep(logger, "Navigating to cart");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 5: Clicking Go to Cart");
            groceryPage.clickGoToCart();
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Navigated to cart page");
            LoggerUtil.logInfo(logger, "✓ Navigated to cart page");

            // Step 6: Proceed to Checkout
            LoggerUtil.logStep(logger, "Proceeding to checkout");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 6: Clicking Proceed to Checkout");
            groceryPage.clickProceedToCheckout();
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Clicked Proceed to Checkout");
            LoggerUtil.logInfo(logger, "✓ Clicked Proceed to Checkout");

         // Step 7: Click Continue after Proceed to Checkout
            LoggerUtil.logStep(logger, "Clicking Continue after checkout");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 7: Clicking Continue button after checkout");
            groceryPage.clickContinueAfterCheckout();
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Clicked Continue after checkout");
            LoggerUtil.logInfo(logger, "✓ Clicked Continue after checkout");
            
         // Step 8: Click substitution Continue submit button
            LoggerUtil.logStep(logger, "Clicking substitution Continue button");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 8: Clicking substitution Continue submit button");
            groceryPage.clickSubstitutionContinue();
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Clicked substitution Continue button");
            LoggerUtil.logInfo(logger, "✓ Clicked substitution Continue button");
            
         // Step 9: Click Add a credit or debit card button
            LoggerUtil.logStep(logger, "Clicking Add a credit or debit card button");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 9: Clicking Add a credit or debit card button");
            groceryPage.clickAddCreditCardButton();
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Clicked Add a credit or debit card button");
            LoggerUtil.logInfo(logger, "✓ Clicked Add a credit or debit card button");
            
         // Step 10: Fill card details inside secure iframe
            LoggerUtil.logStep(logger, "Filling credit card details in secure iframe");
            ExtentReportManager.getExtentTest().log(Status.INFO, "Step 10: Switching to card iframe and filling details");
            groceryPage.fillAndSubmitCardDetails(
            		ConfigReader.getProperty("cardNumber"),
		            ConfigReader.getProperty("cardHolder"),
		            "05",  
		            "2027"
            );
            ExtentReportManager.getExtentTest().log(Status.PASS, "✓ Card details filled and submitted");
            LoggerUtil.logInfo(logger, "✓ Card details filled and submitted");
            
			/*
			 * // Step 9: Verify error page is displayed LoggerUtil.logStep(logger,
			 * "Verifying error page is displayed");
			 * ExtentReportManager.getExtentTest().log(Status.INFO,
			 * "Step 7: Verifying Amazon 500 error page"); boolean errorPageDisplayed =
			 * groceryPage.isErrorPageDisplayed(); Assert.assertTrue( errorPageDisplayed,
			 * "Expected Amazon 500 error page or 'Sorry we couldn't find that page' message to be displayed"
			 * ); ExtentReportManager.getExtentTest().log(Status.PASS,
			 * "✓ Error page verified — 'Sorry! We couldn't find that page' displayed as expected"
			 * ); LoggerUtil.logInfo(logger, "✓ Error page verified successfully");
			 */

            LoggerUtil.logTestEnd(logger, "testGroceryShoppingFlow", "PASSED");
            
        } catch (Exception e) {
            LoggerUtil.logError(logger, "Test failed with exception", e);
            ExtentReportManager.getExtentTest().log(Status.FAIL, "❌ Test failed: " + e.getMessage());
            LoggerUtil.logTestEnd(logger, "testGroceryShoppingFlow", "FAILED");
            throw e;
        }
    }
}