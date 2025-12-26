package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    
    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;
    
    @FindBy(id = "nav-cart-count")
    private WebElement cartCount;
    
    public ProductPage(WebDriver driver) {
        super(driver);
    }
    
    public void addToCart() {
        click(addToCartButton);
        System.out.println("Clicked 'Add to Cart' button");
        waitForSeconds(2);
    }
    
    public String getCartCount() {
        return getText(cartCount);
    }
    
    public boolean isCartCountUpdated(String expectedCount) {
        String actualCount = getCartCount();
        return actualCount.equals(expectedCount);
    }
}
