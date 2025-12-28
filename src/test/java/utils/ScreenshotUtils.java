package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String fileName = screenshotName + "_" + timestamp + ".png";
        
        String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/";
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String screenshotPath = screenshotDir + fileName;
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotPath);
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot captured: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getBase64Screenshot(WebDriver driver) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to capture base64 screenshot: " + e.getMessage());
            return null;
        }
    }
}