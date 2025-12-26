package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    
    @Test(priority = 1)
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.hoverOverAccountAndLists();
        loginPage.clickSignInButton();
        
        Assert.assertTrue(loginPage.isEmailInputDisplayed(), 
            "Email input should be visible");
        
        loginPage.enterEmail(ConfigReader.getTestEmail());
        loginPage.clickContinue();
        
        Assert.assertTrue(loginPage.isPasswordInputDisplayed(), 
            "Password input should be visible");
        
        loginPage.enterPassword(ConfigReader.getTestPassword());
        loginPage.clickSignIn();
        
        Assert.assertTrue(loginPage.isLoggedIn(), 
            "User should be logged in successfully");
        System.out.println("✓ Login test passed");
    }
}
