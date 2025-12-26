package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {
    
    @FindBy(name = "proceedToRetailCheckout")
    private WebElement checkoutButton;
    
    @FindBy(xpath = "//a[contains(@class, 'pmts-add-cc-default-trigger-link')]")
    private WebElement addCardLink;
    
    @FindBy(css = "iframe[name^='ApxSecureIframe']")
    private WebElement paymentIframe;
    
    @FindBy(name = "addCreditCardNumber")
    private WebElement cardNumberInput;
    
    @FindBy(name = "ppw-accountHolderName")
    private WebElement cardHolderNameInput;
    
    @FindBy(name = "ppw-expirationDate_month")
    private WebElement monthDropdown;
    
    @FindBy(name = "ppw-expirationDate_year")
    private WebElement yearDropdown;
    
    @FindBy(name = "addCreditCardVerificationNumber")
    private WebElement cvvInput;
    
    @FindBy(name = "ppw-widgetEvent:AddCreditCardEvent")
    private WebElement submitCardButton;
    
    @FindBy(css = "h4.a-alert-heading")
    private WebElement errorHeading;
    
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }
    
    public void proceedToCheckout() {
        click(checkoutButton);
        System.out.println("Clicked 'Proceed to Checkout' button");
        waitForSeconds(2);
    }
    
    public void clickAddCardLink() {
        click(addCardLink);
        System.out.println("Clicked 'Add Card' link");
    }
    
    public void switchToPaymentFrame() {
        switchToFrame(paymentIframe);
        System.out.println("Switched to payment iframe");
    }
    
    public void enterCardDetails(String cardNumber, String cardHolder, 
                                String month, String year, String cvv) {
  
        sendKeys(cardNumberInput, cardNumber);
        System.out.println("Entered card number");
        waitForSeconds(2);
        
    
        sendKeys(cardHolderNameInput, cardHolder);
        System.out.println("Entered card holder name: " + cardHolder);
        waitForSeconds(2);
        
     
        selectDropdownByValue(monthDropdown, month);
        System.out.println("Selected expiry month: " + month);
        
        
        selectDropdownByValue(yearDropdown, year);
        System.out.println("Selected expiry year: " + year);
        waitForSeconds(3);
        
        click(cvvInput);
        clear(cvvInput);
        
        sendKeys(cvvInput, cvv);
        System.out.println("Entered CVV");
        waitForSeconds(3);
    }
    
    public void submitCard() {
        click(submitCardButton);
        System.out.println("Clicked Submit button");
    }
    
    public String getErrorHeading() {
        return getText(errorHeading);
    }
    
    public boolean isErrorDisplayed() {
        return isDisplayed(errorHeading);
    }
}

