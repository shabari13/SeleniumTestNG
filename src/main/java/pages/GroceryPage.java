	package pages;
	import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
	
	public class GroceryPage {
	    
	    private WebDriver driver;
	    private WebDriverWait wait;
	    
	    // Locators
	    private By groceriesTabLink = By.id("nav-link-groceries");
	    private By grocerySearchBox = By.id("twotabsearchtextbox");
	    private By searchButton = By.id("nav-search-submit-button");
	    private By searchResults = By.cssSelector("div[data-component-type='s-search-result']");
	    private By firstProduct = By.cssSelector("div[data-component-type='s-search-result']:first-child h2 a");
	    private By groceryPageIndicator = By.cssSelector("div#main-content, div.a-section");
	    private By groceryHeader = By.cssSelector("h2.a-spacing-medium");
	    private By wholeFoodsShopNowButton = By.id("a-autoid-2-announce");
	    
	    private By allDealAddButtons = By.cssSelector(
	    	    "button[id^='qs-widget-atc-button-atfc-p13n-alm-cards-alm-carousel-desktop']"
	    	);
	    private By goToCartLink = By.cssSelector("a[href*='/cart/localmarket']");
	    private By proceedToCheckoutButton = By.cssSelector("input[name='proceedToALMCheckout-VUZHIFdob2xlIEZvb2Rz']");
	    private By errorPageImage = By.cssSelector("img[src*='error'][alt*='Sorry']");
	    private By continueButton = By.cssSelector("a[name='proceedToCheckout'][href*='/alm/substitution']");
	 // Locator for the substitution Continue/Submit button
	    private By substitutionContinueButton = By.cssSelector(
	        "input[type='submit'][aria-labelledby='subsContinueButton-announce']"
	    );
	    private By addCreditCardButton = By.cssSelector(
	    	    "input[type='submit'][aria-labelledby^='pp-']"
	    	);
	    
	 // Locators for the card iframe and its fields
	    private By cardIframe = By.cssSelector("iframe[name^='ApxSecureIframe']");
	    private By cardNumberField = By.cssSelector("input[name='addCreditCardNumber']");
	    private By nameOnCardField = By.cssSelector("input[name='ppw-accountHolderName']");

	    
	 // Month/Year are custom span dropdowns, NOT <select> elements
	    private By expirationMonthDropdown = By.cssSelector(
	    	    "span[data-action='a-dropdown-button']:first-of-type"
	    	);
	    	private By expirationYearDropdown = By.cssSelector(
	    	    "span[data-action='a-dropdown-button']:last-of-type"
	    	);
	    	private By monthOptions = By.cssSelector("ul.a-dropdown-content li a");
	    	private By yearOptions  = By.cssSelector("ul.a-dropdown-content li a");
	    private By addYourCardButton = By.cssSelector("input[type='submit'][aria-label*='Add your card'], span#add-card-btn-text");


	
	    // Constructor
	    public GroceryPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    }
	    
	    /**
	     * Click on the Groceries tab in the navigation menu
	     */
	    public void clickGroceriesTab() {
	        try {
	            WebElement groceriesLink = wait.until(ExpectedConditions.elementToBeClickable(groceriesTabLink));
	            groceriesLink.click();
	            System.out.println("Clicked on Groceries tab");
	            
	            // Wait for page to load
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to click Groceries tab: " + e.getMessage());
	            throw new RuntimeException("Could not navigate to Groceries section", e);
	        }
	    }
	    
	    /**
	     * Check if grocery page is loaded
	     */
	    /**
	     * Check if grocery page is loaded
	     */
	    public boolean isGroceryPageLoaded() {
	        try {
	            // Wait for the grocery page header to be present
	            wait.until(ExpectedConditions.presenceOfElementLocated(groceryHeader));
	            
	            // Verify the header text
	            WebElement headerElement = driver.findElement(groceryHeader);
	            String headerText = headerElement.getText();
	            
	            // Check URL and header text
	            String currentUrl = driver.getCurrentUrl();
	            boolean urlMatches = currentUrl.contains("grocery") || currentUrl.contains("fmc");
	            boolean headerMatches = headerText.equalsIgnoreCase("Shop groceries");
	            
	            return urlMatches && headerMatches;
	        } catch (Exception e) {
	            System.err.println("Grocery page not loaded: " + e.getMessage());
	            return false;
	        }
	    }
	    
	   
	    /**
	     * Click on Shop Now button for Whole Foods Market
	     */
	    public void clickWholeFoodsShopNow() {
	        try {
	            WebElement shopNowButton = wait.until(ExpectedConditions.elementToBeClickable(wholeFoodsShopNowButton));
	            shopNowButton.click();
	            System.out.println("Clicked on Whole Foods Shop Now button");
	            
	            // Optional: Wait for the Whole Foods storefront page to load
	            wait.until(ExpectedConditions.urlContains("storefront"));
	        } catch (Exception e) {
	            System.err.println("Failed to click Shop Now button: " + e.getMessage());
	            throw new RuntimeException("Could not click Shop Now button", e);
	        }
	    }
	    
	    /**
	     * Click the Add to Cart button for Organic Blueberries
	     */
	    public void clickAddToCart() {
	        try {
	        	List<WebElement> addButtons = wait.until(
	                    ExpectedConditions.presenceOfAllElementsLocatedBy(allDealAddButtons)
	                );
	        	if (addButtons.isEmpty()) {
	                throw new RuntimeException("No deal + buttons found on page");
	            }
	        	WebElement firstButton = addButtons.get(0);
	        	String ariaLabel = firstButton.getAttribute("aria-label");
	            System.out.println("Clicking first deal button: " + ariaLabel);
	            wait.until(ExpectedConditions.elementToBeClickable(firstButton)).click();
	            System.out.println("✓ Clicked first + button: " + ariaLabel);
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to click Add to Cart button: " + e.getMessage());
	            throw new RuntimeException("Could not click Add to Cart button", e);
	        }
	    }
	    
	    /**
	     * Click the Proceed to Checkout button
	     */
	    public void clickProceedToCheckout() {
	        try {
	            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
	            checkoutButton.click();
	            System.out.println("Clicked Proceed to Checkout button");
	            Thread.sleep(3000);
	        } catch (Exception e) {
	            System.err.println("Failed to click Proceed to Checkout button: " + e.getMessage());
	            throw new RuntimeException("Could not click Proceed to Checkout button", e);
	        }
	    }
	    
	    /**
	     * Click the Continue button after Proceed to Checkout (substitution/glow page)
	     */
	    public void clickContinueAfterCheckout() {
	        try {
	            WebElement continueBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(continueButton)
	            );
	            System.out.println("Clicking Continue button after checkout...");
	            continueBtn.click();
	            System.out.println("✓ Clicked Continue button");
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to click Continue button: " + e.getMessage());
	            throw new RuntimeException("Could not click Continue button after checkout", e);
	        }
	    }
	    
	    /**
	     * Click the substitution Continue submit button
	     */
	    public void clickSubstitutionContinue() {
	        try {
	            WebElement subsBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(substitutionContinueButton)
	            );
	            System.out.println("Clicking substitution Continue submit button...");
	            subsBtn.click();
	            System.out.println("✓ Clicked substitution Continue button");
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to click substitution Continue button: " + e.getMessage());
	            throw new RuntimeException("Could not click substitution Continue button", e);
	        }
	    }
	    
	    public void clickAddCreditCardButton() {
	        try {
	            List<WebElement> buttons = wait.until(
	                ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.cssSelector("input[type='submit'][aria-labelledby^='pp-']")
	                )
	            );

	            WebElement targetButton = null;
	            for (WebElement btn : buttons) {
	                String labelId = btn.getAttribute("aria-labelledby");
	                try {
	                    WebElement labelSpan = driver.findElement(By.id(labelId));
	                    if (labelSpan.getText().contains("Add a credit or debit card")) {
	                        targetButton = btn;
	                        break;
	                    }
	                } catch (Exception ignored) {}
	            }

	            if (targetButton == null) {
	                throw new RuntimeException("Could not find 'Add a credit or debit card' button by label text");
	            }

	            wait.until(ExpectedConditions.elementToBeClickable(targetButton)).click();
	            System.out.println("✓ Clicked Add a credit or debit card button");
	            Thread.sleep(2000);

	        } catch (Exception e) {
	            System.err.println("Failed to click Add credit card button: " + e.getMessage());
	            throw new RuntimeException("Could not click Add a credit or debit card button", e);
	        }
	    }
	    
	    /**
	     * Switch into the ApxSecureIframe credit card frame
	     */
	    public void switchToCardIframe() {
	        try {
	            WebElement iframe = wait.until(
	                ExpectedConditions.presenceOfElementLocated(cardIframe)
	            );
	            driver.switchTo().frame(iframe);
	            System.out.println("✓ Switched into ApxSecureIframe card frame");
	        } catch (Exception e) {
	            System.err.println("Failed to switch to card iframe: " + e.getMessage());
	            throw new RuntimeException("Could not switch to card iframe", e);
	        }
	    }
	    
	    /**
	     * Switch back out of the card iframe to the main document
	     */
	    public void switchToDefaultContent() {
	        try {
	            driver.switchTo().defaultContent();
	            System.out.println("✓ Switched back to default content");
	        } catch (Exception e) {
	            System.err.println("Failed to switch to default content: " + e.getMessage());
	            throw new RuntimeException("Could not switch to default content", e);
	        }
	    }

	    public void fillAndSubmitCardDetails(String cardNumber, String nameOnCard, String month, String year) {
	        try {
	            switchToCardIframe();

	            // Card number
	            WebElement cardField = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(cardNumberField)
	            );
	            cardField.clear();
	            cardField.sendKeys(cardNumber);
	            System.out.println("✓ Entered card number");

	            // Name on card
	            WebElement nameField = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(nameOnCardField)
	            );
	            nameField.clear();
	            nameField.sendKeys(nameOnCard);
	            System.out.println("✓ Entered name on card");


	            	// Find ALL dropdown triggers and use index explicitly
	            	List<WebElement> dropdownTriggers = wait.until(
	            	    ExpectedConditions.presenceOfAllElementsLocatedBy(
	            	        By.cssSelector("span[data-action='a-dropdown-button']")
	            	    )
	            	);
	            	System.out.println("Found " + dropdownTriggers.size() + " dropdown triggers");

	            	WebElement monthTrigger = dropdownTriggers.get(0);
	            	((JavascriptExecutor) driver).executeScript("arguments[0].click();", monthTrigger);
	            	System.out.println("✓ Clicked month dropdown trigger");

	            	wait.until(ExpectedConditions.visibilityOfElementLocated(
	            	    By.cssSelector("div.a-popover-inner")
	            	));

	            	List<WebElement> monthLinks = driver.findElements(
	            	    By.cssSelector("div.a-popover-inner a.a-dropdown-link")
	            	);
	            	String paddedMonth = String.format("%02d", Integer.parseInt(month));
	            	boolean monthSelected = false;
	            	for (WebElement link : monthLinks) {
	            	    if (link.getText().trim().equals(paddedMonth)) {
	            	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
	            	        System.out.println("✓ Selected month: " + paddedMonth);
	            	        monthSelected = true;
	            	        break;
	            	    }
	            	}
	            	if (!monthSelected) {
	            	    throw new RuntimeException("Month option not found: " + paddedMonth);
	            	}

	            	// Wait for dropdown links to become invisible (popover stays in DOM but hides)
	            	wait.until(ExpectedConditions.invisibilityOfElementLocated(
	            	    By.cssSelector("div.a-popover-inner a.a-dropdown-link")
	            	));
	            	System.out.println("✓ Month dropdown closed");
	            	Thread.sleep(500);

	            	WebElement yearTrigger = dropdownTriggers.get(1);
	            	((JavascriptExecutor) driver).executeScript("arguments[0].click();", yearTrigger);
	            	System.out.println("✓ Clicked year dropdown trigger");

	            	// Use data-value to find the exact year option - more reliable than text matching
	            	Thread.sleep(1000);
	            	WebElement yearOption = wait.until(ExpectedConditions.elementToBeClickable(
	            	    By.cssSelector("a.a-dropdown-link[data-value*='\"" + year + "\"']")
	            	));
	            	((JavascriptExecutor) driver).executeScript("arguments[0].click();", yearOption);
	            	System.out.println("✓ Selected year: " + year);
	            	Thread.sleep(500);

	            

	            // Submit
	            wait.until(ExpectedConditions.elementToBeClickable(
	            	    By.cssSelector("input[type='submit'][name='ppw-widgetEvent:AddCreditCardEvent']")
	            	)).click();
	            	System.out.println("✓ Clicked Add your card button");
	            Thread.sleep(2000);

	        } catch (Exception e) {
	            System.err.println("Failed to fill card details: " + e.getMessage());
	            throw new RuntimeException("Could not fill card details", e);
	        } finally {
	            switchToDefaultContent();
	        }
	    }
	    
	    /**
	     * Verify the Amazon 500 error page is displayed.
	     * Checks either the URL contains '/500' or the error image is present on the page.
	     * @return true if the error page is detected, false otherwise
	     */
	    public boolean isErrorPageDisplayed() {
	        try {
	            String currentUrl = driver.getCurrentUrl();
	            System.out.println("Current URL after checkout attempt: " + currentUrl);

	            // Check URL for /500
	            if (currentUrl.contains("/500")) {
	                System.out.println("Detected error page via URL: " + currentUrl);
	                return true;
	            }

	            // Check for the error image on the page
	            List<WebElement> errorImages = driver.findElements(errorPageImage);
	            if (!errorImages.isEmpty()) {
	                System.out.println("Detected error page via error image element");
	                return true;
	            }

	            // Check page source for the sorry message as a fallback
	            String pageSource = driver.getPageSource();
	            if (pageSource.contains("Sorry! We couldn") || pageSource.contains("couldn't find that page")) {
	                System.out.println("Detected error page via page source content");
	                return true;
	            }

	            System.out.println("Error page was NOT detected");
	            return false;
	        } catch (Exception e) {
	            System.err.println("Error while checking for error page: " + e.getMessage());
	            return false;
	        }
	    }
	    
	    /**
	     * Click the Go to Cart link
	     */
	    public void clickGoToCart() {
	        try {
	            WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(goToCartLink));
	            cartLink.click();
	            System.out.println("Clicked Go to Cart link");
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to click Go to Cart link: " + e.getMessage());
	            throw new RuntimeException("Could not click Go to Cart link", e);
	        }
	    }


	    
	    /**
	     * Search for a grocery product
	     */
	    public void searchForGroceryProduct(String productName) {
	        try {
	            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(grocerySearchBox));
	            searchBox.clear();
	            searchBox.sendKeys(productName);
	            System.out.println("Entered grocery product: " + productName);
	            
	            WebElement searchBtn = driver.findElement(searchButton);
	            searchBtn.click();
	            System.out.println("Clicked search button");
	            
	            // Wait for search results
	            wait.until(ExpectedConditions.presenceOfElementLocated(searchResults));
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to search for grocery product: " + e.getMessage());
	            throw new RuntimeException("Search failed", e);
	        }
	    }
	    
	    /**
	     * Check if products are displayed in search results
	     */
	    public boolean isProductDisplayedInResults() {
	        try {
	            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResults));
	            int productCount = driver.findElements(searchResults).size();
	            System.out.println("Found " + productCount + " products in search results");
	            return productCount > 0;
	        } catch (Exception e) {
	            System.err.println("No products found in search results: " + e.getMessage());
	            return false;
	        }
	    }
	    
	    /**
	     * Select the first product from search results
	     */
	    public void selectFirstProduct() {
	        try {
	            WebElement firstProductLink = wait.until(ExpectedConditions.elementToBeClickable(firstProduct));
	            String productTitle = firstProductLink.getText();
	            System.out.println("Selecting product: " + productTitle);
	            firstProductLink.click();
	            
	            // Wait for product page to load
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to select product: " + e.getMessage());
	            throw new RuntimeException("Product selection failed", e);
	        }
	    }
	    
	    /**
	     * Alternative method: Select product by index
	     */
	    public void selectProductByIndex(int index) {
	        try {
	            By productByIndex = By.cssSelector("div[data-component-type='s-search-result']:nth-child(" + index + ") h2 a");
	            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(productByIndex));
	            String productTitle = productLink.getText();
	            System.out.println("Selecting product at index " + index + ": " + productTitle);
	            productLink.click();
	            
	            Thread.sleep(2000);
	        } catch (Exception e) {
	            System.err.println("Failed to select product at index " + index + ": " + e.getMessage());
	            throw new RuntimeException("Product selection failed", e);
	        }
	    }
	}
