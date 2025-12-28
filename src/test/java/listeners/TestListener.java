package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n========================================");
        System.out.println("STARTING TEST: " + result.getMethod().getMethodName());
        System.out.println("========================================");
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("\n✓ TEST PASSED: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.err.println("\n❌ TEST FAILED: " + result.getMethod().getMethodName());
        System.err.println("========================================");
        System.err.println("FAILURE REASON:");
        System.err.println("========================================");
        
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            throwable.printStackTrace();
        }
        
        System.err.println("========================================\n");
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("\n⊘ TEST SKIPPED: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test failed but within success percentage: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n========================================");
        System.out.println("STARTING TEST SUITE: " + context.getName());
        System.out.println("========================================\n");
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n========================================");
        System.out.println("FINISHED TEST SUITE: " + context.getName());
        System.out.println("Tests Passed: " + context.getPassedTests().size());
        System.out.println("Tests Failed: " + context.getFailedTests().size());
        System.out.println("Tests Skipped: " + context.getSkippedTests().size());
        System.out.println("========================================\n");
    }
}
