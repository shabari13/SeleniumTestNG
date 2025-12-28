package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {

    public static WebDriver createDriver(String browserName) {
        WebDriver driver;
        
        switch (browserName.toLowerCase()) {
            case "firefox":
            	WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(getFirefoxOptions());
                System.out.println("Firefox browser launched");
                break;
                
            case "edge":
            	 WebDriverManager.edgedriver().setup(); 
                driver = new EdgeDriver(getEdgeOptions());
                System.out.println("Edge browser launched");
                break;
                
            case "chrome":
            default:
            	 WebDriverManager.chromedriver().setup(); 
                driver = new ChromeDriver(getChromeOptions());
                System.out.println("Chrome browser launched");
                break;
        }
        
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        
        String tempProfile = System.getProperty("java.io.tmpdir") + "/selenium_chrome_" + System.currentTimeMillis();
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
        
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        
        options.addArguments("-private");
        options.addPreference("dom.webdriver.enabled", false);
        options.addPreference("useAutomationExtension", false);
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autofillForms", false);
        options.addPreference("dom.webnotifications.enabled", false);
        
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        
        options.addArguments("--inprivate");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        
        return options;
    }
}
