package base;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    
    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
    	//String browser = ConfigReader.getBrowser();
    	driver = BrowserFactory.createDriver(browser);
        
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(ConfigReader.getImplicitWait())
        );
        
        driver.get(ConfigReader.getBaseUrl());
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        
        System.out.println("Browser setup completed. Navigated to: " + ConfigReader.getBaseUrl());
    }
    
    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        
        String tempProfile = System.getProperty("java.io.tmpdir") + "/selenium_" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + tempProfile);
        

        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        

        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        

        Map<String, Object> prefs = new HashMap<>();
       // prefs.put("credentials_enable_service", false);
        //prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        // Uncomment for headless mode
        // options.addArguments("--headless");
        
        return options;
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("Browser closed successfully");
        }
    }
    
    public WebDriver getDriver() {
        return driver;
    }
}

