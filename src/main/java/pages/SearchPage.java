package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {
    
    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;
    
    public SearchPage(WebDriver driver) {
        super(driver);
    }
    
    public void searchForProduct(String productName) {
        click(searchBox);
        System.out.println("Clicked on search box");
        
        sendKeys(searchBox, productName);
        System.out.println("Entered search text: " + productName);
        
        searchBox.sendKeys(Keys.ENTER);
        System.out.println("Pressed Enter to search");
        waitForSeconds(3);
    }
    
    public boolean isProductDisplayedInResults(String partialHref) {
        try {
            WebElement productLink = driver.findElement(
                By.xpath("//a[contains(@href, '" + partialHref + "')]")
            );
            return productLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickProductByAsin(String asin) {
        WebElement productLink = driver.findElement(
            By.xpath("//a[contains(@href, '" + asin + "')]")
        );
        productLink.click();
        System.out.println("Clicked on product with ASIN: " + asin);
        waitForSeconds(3);
    }
}

