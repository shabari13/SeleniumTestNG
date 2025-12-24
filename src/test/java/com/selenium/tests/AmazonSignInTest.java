package com.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AmazonSignInTest {
    
    WebDriver driver;
    WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Uncomment for headless mode (for Jenkins)
        // options.addArguments("--headless");
        String tempProfile = System.getProperty("java.io.tmpdir") + "/selenium_" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + tempProfile);
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        // Disable automation flags
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        // Set a realistic user agent
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        
        // Additional options
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        
        // Additional settings to appear more human
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        // Preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        
        driver = new ChromeDriver(options);
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @Test
    public void testAmazonSignInFlow() {
        try {
        	
            // Step 1: Open Amazon
            driver.get("https://www.amazon.com");
            System.out.println("Opened Amazon.com");
            driver.manage().deleteAllCookies();
            
            // Reload the page
            driver.navigate().refresh();
            
            // Step 2: Hover over "Hello, sign in Account & Lists"
            WebElement accountAndLists = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.id("nav-link-accountList")
                )
            );
            
            Actions actions = new Actions(driver);
            actions.moveToElement(accountAndLists).perform();
            System.out.println("Hovered over 'Account & Lists'");
            
            // Wait for dropdown to appear
            Thread.sleep(1500);
            
            // Step 3: Click on "Sign in" button in the dropdown
            WebElement signInButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='nav-flyout-accountList']//span[text()='Sign in']")
                )
            );
            signInButton.click();
            System.out.println("Clicked on 'Sign in' button");
            
            // Step 4: Wait for sign-in page to load
            Thread.sleep(2000);
            
            // Verify we're on sign-in page
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            Assert.assertTrue(currentUrl.contains("signin") || currentUrl.contains("ap/signin"), 
                "Not on sign-in page");
            
            // Step 5: Find the email/phone input field using the ID you found
            WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ap_email_login"))
            );
            Assert.assertTrue(emailInput.isDisplayed(), "Email input field not visible");
            System.out.println("Email input field is visible");
            
            // Step 6: Enter email/phone number
            String testEmail = "shabarials@gmail.com"; // Replace with your test email
            emailInput.clear(); // Clear any existing text
            emailInput.sendKeys(testEmail);
            System.out.println("Entered email: " + testEmail);
            
            // Step 7: Click Continue button
            WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("continue"))
            );
            continueButton.click();
            System.out.println("Clicked Continue button");
            
            // Step 8: Wait and verify password page loads
            Thread.sleep(2000);
            
            // Verify password field appears
            WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ap_password"))
            );
            Assert.assertTrue(passwordField.isDisplayed(), "Password field not visible");
            System.out.println("✓ Successfully navigated to password page");
            
            String testPassword = "labone2six"; 
            passwordField.clear(); // Clear any existing text
            passwordField.sendKeys(testPassword);
            System.out.println("Entered password: " + "***");
            
            WebElement signInButton2 = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.id("signInSubmit")
                    )
                );
            signInButton2.click();
           // Thread.sleep(10000);
            
            Thread.sleep(3000);
            // Step 2: Hover over "Hello, sign in Account & Lists"
            WebElement accountListView = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.id("nav-link-accountList-nav-line-1")
                )
            );
            
            System.out.println("Successfully logged in");
            
            WebElement searchBox = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox"))
                );
                searchBox.click();
                System.out.println("Clicked on search box");
                
                // Step 9: Enter search text
                String searchText = "adidas Women's VL Court 3.0 Sneaker";
                searchBox.clear();
                searchBox.sendKeys(searchText);
                System.out.println("Entered search text: " + searchText);
                
                // Step 10: Press Enter
                searchBox.sendKeys(Keys.ENTER);
                System.out.println("Pressed Enter to search");
                
                // Wait for search results to load
                Thread.sleep(3000);
                WebElement productLink = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//a[contains(@href, 'adidas-Womens-Court-Sneaker-White')]")
                            )
                        );
                Assert.assertTrue(productLink.isDisplayed(), 
                    "Product 'Women's VL Court 3.0 Sneaker' not found in search results");
                System.out.println("✓ Verified product heading: " + productLink.getText());
                
                // Step 12: Click on the product to go to product details page
                //productHeading.click();
                //System.out.println("Clicked on product");
                
                // Wait for product page to load
                Thread.sleep(3000);
                
                String asin = "B0C2JZ4K9V";

                WebElement productLink1 = driver.findElement(
                    By.xpath("//a[contains(@href, '" + asin + "')]")
                );
                productLink1.click();
                
                // Step 14: Verify item added to cart (optional)
                Thread.sleep(2000);
                WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
                addToCartButton.click();
                
                WebElement cartCount = driver.findElement(By.id("nav-cart-count"));

	             // Get the text (count)
	            String count = cartCount.getText();
	
	             // Verify it's 1
	            if (count.equals("1")) {
	                 System.out.println("Item successfully added to cart!");
	            } else {
	                 System.out.println("Cart count is: " + count);
	            }
	            
	            WebElement checkoutButton = driver.findElement(By.name("proceedToRetailCheckout"));
	            checkoutButton.click();
	            WebElement addCardLink = driver.findElement(
	            	    By.xpath("//a[contains(@class, 'pmts-add-cc-default-trigger-link')]")
	            	);
	            	addCardLink.click();
					/*
					 * wait.until(ExpectedConditions.visibilityOfElementLocated(
					 * By.xpath("//h4[contains(text(), 'Add a credit or debit card')]") ));
					 */

					/*
					 * WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
					 * By.cssSelector("div.a-popover[role='dialog']") ));
					 */
	            	
	            	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
	            	        By.cssSelector("iframe[name^='ApxSecureIframe']")
	            	));
							                    
	                    System.out.println("Popup is visible");
	                    WebElement cardNumberInput = wait.until(
	                            ExpectedConditions.presenceOfElementLocated(
	                                    By.name("addCreditCardNumber")
	                            )
	                    );
	                    
						/*
						 * // 1. Enter Card Number WebElement cardNumberInput = wait.until(
						 * ExpectedConditions.elementToBeClickable(By.name("addCreditCardNumber")) );
						 */
	                    cardNumberInput.clear();
	                    cardNumberInput.sendKeys("123");
	                    System.out.println("Entered card number: " + "123");
	                    
	                    Thread.sleep(2000);
	                    
	                    WebElement cardHpldernput = wait.until(
	                            ExpectedConditions.presenceOfElementLocated(
	                                    By.name("ppw-accountHolderName")
	                            )
	                    );
	                    
	                    cardHpldernput.clear();
	                    cardHpldernput.sendKeys("Shabari Shetty");
	                    
	                    Thread.sleep(2000);
	                    
	                    WebElement monthSelectElement = wait.until(
	                            ExpectedConditions.elementToBeClickable(
	                                    By.name("ppw-expirationDate_month")
	                            )
	                    );

	                    Select monthSelect = new Select(monthSelectElement);
	                    monthSelect.selectByValue("3");
	                    
	                    WebElement yearSelectElement = wait.until(
	                            ExpectedConditions.elementToBeClickable(
	                                    By.name("ppw-expirationDate_year")
	                            )
	                    );

	                    Select yearSelect = new Select(yearSelectElement);
	                    yearSelect.selectByValue("2027");
	                    
	                    
	                    Thread.sleep(2000);
	                    
	                    WebElement cvvInput = wait.until(
	                            ExpectedConditions.elementToBeClickable(
	                                    By.name("addCreditCardVerificationNumber")
	                            )
	                    );
	                    cvvInput.click(); 
	                    cvvInput.clear();
	                    cvvInput.sendKeys("234");
	                    Thread.sleep(2000);
	                    
	                    WebElement submitButton = wait.until(
	                            ExpectedConditions.elementToBeClickable(
	                                    By.name("ppw-widgetEvent:AddCreditCardEvent")
	                            )
	                    );

	                    submitButton.click();
	                    WebElement errorHeading = wait.until(
	                            ExpectedConditions.visibilityOfElementLocated(
	                                    By.cssSelector("h4.a-alert-heading")
	                            )
	                    );

	                    Assert.assertEquals(
	                            errorHeading.getText().trim(),
	                            "There was a problem.",
	                            "Expected error heading was not displayed"
	                    );
	            	
						/*
						 * WebElement cardNumberInput = wait.until(
						 * ExpectedConditions.visibilityOfElementLocated( By.name("addCreditCardNumber")
						 * ) );
						 */

            		// Clear and enter the card number
            		cardNumberInput.clear();
            		cardNumberInput.sendKeys("123");
	                
            } catch (InterruptedException e) {
                e.printStackTrace();
                Assert.fail("Test interrupted: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Test failed: " + e.getMessage());
            }
        }
        
        @AfterMethod
        public void tearDown() {
            if (driver != null) {
                try {
                    Thread.sleep(3000); // Wait to see the result
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                driver.quit();
            }
        }
    
}