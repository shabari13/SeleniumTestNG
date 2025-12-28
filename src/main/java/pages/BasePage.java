package pages;

import config.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Handles the intermittent "Continue shopping" page that Amazon shows
     * Returns true if the button was found and clicked, false otherwise
     */
    public boolean handleContinueShoppingIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement continueShoppingButton = shortWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'a-button-text') and contains(text(), 'Continue shopping')]")
                )
            );
            continueShoppingButton.click();
            System.out.println("Clicked 'Continue shopping' button");
            Thread.sleep(1000); // Small wait for page to load
            return true;
        } catch (Exception e) {
            System.out.println("'Continue shopping' page not displayed, proceeding normally");
            return false;
        }
    }
    
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    
    protected void clear(WebElement element) {
    	element.clear();
    }
    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }
    
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
    
    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void hoverOverElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        actions.moveToElement(element).perform();
    }
    

    protected void selectDropdownByValue(WebElement element, String value) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            System.err.println("❌ Error selecting dropdown normally, trying JavaScript approach");
            selectDropdownByValueUsingJS(element, value);
        }
    }
    
    protected void selectDropdownByValueUsingJS(WebElement element, String value) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                org.openqa.selenium.By.xpath("//select[@name='" + element.getAttribute("name") + "']")
            ));
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1]; " +
                           "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", 
                           element, value);
            
            System.out.println("Selected dropdown value using JavaScript: " + value);
        } catch (Exception e) {
            System.err.println("❌ Error selecting dropdown with JavaScript");
            e.printStackTrace();
            throw e;
        }
    }
    
    protected void selectDropdownForFirefox(WebElement element, String value) {
        try {
            // Wait for element to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(
                org.openqa.selenium.By.xpath("//select[@name='" + element.getAttribute("name") + "']")
            ));
            
            // Scroll element into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            
            // Small wait for scrolling to complete
            Thread.sleep(500);
            
            // Set value using JavaScript
            js.executeScript(
                "var select = arguments[0];" +
                "select.value = arguments[1];" +
                "var event = new Event('change', { bubbles: true });" +
                "select.dispatchEvent(event);",
                element, value
            );
            
            System.out.println("Selected dropdown value for Firefox: " + value);
            
        } catch (Exception e) {
            System.err.println("❌ Error in Firefox dropdown selection");
            e.printStackTrace();
            throw new RuntimeException("Failed to select dropdown value: " + value, e);
        }
    }
    
    protected void switchToFrame(WebElement frameElement) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }
    
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
