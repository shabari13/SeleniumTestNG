package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;
    
    public synchronized static ExtentReports createInstance() {
        if (extent != null) {
            return extent;
        }
        
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "TestReport_" + timestamp + ".html";
        reportPath = System.getProperty("user.dir") + "/test-output/ExtentReports/" + reportName;
        
        File reportDir = new File(System.getProperty("user.dir") + "/test-output/ExtentReports/");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Amazon E2E Test Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        
        return extent;
    }
    
    public static ExtentReports getExtent() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }
    
    public static synchronized void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }
    
    public static synchronized ExtentTest getExtentTest() {
        return extentTest.get();
    }
    
    public static String getReportPath() {
        return reportPath;
    }
    
    public static synchronized void removeExtentTest() {
        extentTest.remove();
    }
}