package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    
    @FindBy(id = "nav-link-accountList")
    private WebElement accountListLink;
    
    @FindBy(xpath = "//div[@id='nav-flyout-accountList']//span[text()='Sign in']")
    private WebElement signInDropdownButton;
    
    @FindBy(id = "ap_email_login")
    private WebElement emailInput;
    
    @FindBy(id = "continue")
    private WebElement continueButton;
    
    @FindBy(id = "ap_password")
    private WebElement passwordInput;
    
    @FindBy(id = "signInSubmit")
    private WebElement signInSubmitButton;
    
    @FindBy(id = "nav-link-accountList-nav-line-1")
    private WebElement accountGreeting;
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void hoverOverAccountAndLists() {
        hoverOverElement(accountListLink);
        waitForSeconds(2);
        System.out.println("Hovered over 'Account & Lists'");
    }
    
    public void clickSignInButton() {
        click(signInDropdownButton);
        System.out.println("Clicked on 'Sign in' button");
    }
    
    public void enterEmail(String email) {
        sendKeys(emailInput, email);
        System.out.println("Entered email: " + email);
    }
    
    public void clickContinue() {
        click(continueButton);
        System.out.println("Clicked Continue button");
        waitForSeconds(2);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
        System.out.println("Entered password");
    }
    
    public void clickSignIn() {
        click(signInSubmitButton);
        System.out.println("Clicked Sign In button");
        waitForSeconds(3);
    }
    
    public void performLogin(String email, String password) {
    	handleContinueShoppingIfPresent();
        hoverOverAccountAndLists();
        clickSignInButton();
        waitForSeconds(2);
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickSignIn();
    }
    
    public boolean isLoggedIn() {
        return isDisplayed(accountGreeting);
    }
    
    public boolean isEmailInputDisplayed() {
        return isDisplayed(emailInput);
    }
    
    public boolean isPasswordInputDisplayed() {
        return isDisplayed(passwordInput);
    }
}

