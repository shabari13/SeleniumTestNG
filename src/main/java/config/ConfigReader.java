package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;
    
    static {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties");
        }
    }
    
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
    
    public static String getBrowser() {
        return prop.getProperty("browser", "chrome");
    }
    
    public static String getBaseUrl() {
        return prop.getProperty("baseUrl");
    }
    
    public static int getImplicitWait() {
        return Integer.parseInt(prop.getProperty("implicitWait", "10"));
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(prop.getProperty("explicitWait", "15"));
    }
    
    public static String getTestEmail() {
        return prop.getProperty("testEmail");
    }
    
    public static String getTestPassword() {
        return prop.getProperty("testPassword");
    }
}

