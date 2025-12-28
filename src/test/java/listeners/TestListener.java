package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.BaseTest;

import com.aventstack.extentreports.ExtentReports;

import utils.ExtentReportManager;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {
	private static ExtentReports extent = ExtentReportManager.getExtent();

	@Override
	public void onStart(ITestContext context) {
		System.out.println("\n========================================");
		System.out.println("STARTING TEST SUITE: " + context.getName());
		System.out.println("========================================\n");

		// Initialize extent report
		extent = ExtentReportManager.getExtent();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("\n========================================");
		System.out.println("STARTING TEST: " + result.getMethod().getMethodName());
		System.out.println("========================================");

		try {
			if (extent == null) {
				extent = ExtentReportManager.getExtent();
			}

			ExtentTest test = extent.createTest(result.getMethod().getMethodName());
			ExtentReportManager.setExtentTest(test);

			if (ExtentReportManager.getExtentTest() != null) {
				ExtentReportManager.getExtentTest().log(Status.INFO,
						"Test Started: " + result.getMethod().getMethodName());
			}
		} catch (Exception e) {
			System.err.println("Error in onTestStart: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("\n✓ TEST PASSED: " + result.getMethod().getMethodName());

		try {
			ExtentTest test = ExtentReportManager.getExtentTest();
			if (test != null) {
				test.log(Status.PASS, MarkupHelper.createLabel("Test Passed: " + result.getMethod().getMethodName(),
						ExtentColor.GREEN));
			}
		} catch (Exception e) {
			System.err.println("Error logging test success: " + e.getMessage());
		} finally {
			ExtentReportManager.removeExtentTest();
		}
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

		try {
			ExtentTest test = ExtentReportManager.getExtentTest();
			if (test == null) {
				System.out.println("ExtentTest was null, creating new test instance");
				if (extent == null) {
					extent = ExtentReportManager.getExtent();
				}
				test = extent.createTest(result.getMethod().getMethodName());
				ExtentReportManager.setExtentTest(test);
			}

			test.log(Status.FAIL,
					MarkupHelper.createLabel("Test Failed: " + result.getMethod().getMethodName(), ExtentColor.RED));

			if (throwable != null) {
				test.fail(throwable);
			}

			captureScreenshotOnFailure(result, test);

		} catch (Exception e) {
			System.err.println("Error in onTestFailure: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.err.println("========================================\n");
			ExtentReportManager.removeExtentTest();
		}
	}

	private void captureScreenshotOnFailure(ITestResult result, ExtentTest test) {
		try {
			Object testClass = result.getInstance();
			WebDriver driver = null;

			// Get driver from BaseTest
			if (testClass instanceof BaseTest) {
				driver = ((BaseTest) testClass).getDriver();
			}

			if (driver != null) {
				String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());

				if (screenshotPath != null && test != null) {
					test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
					System.out.println("Screenshot attached to report: " + screenshotPath);
				}
			} else {
				System.out.println("WebDriver is null, cannot capture screenshot");
			}
		} catch (Exception e) {
			System.err.println("Failed to capture screenshot: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("\n⊘ TEST SKIPPED: " + result.getMethod().getMethodName());

		try {
			ExtentTest test = ExtentReportManager.getExtentTest();
			if (test == null) {
				if (extent == null) {
					extent = ExtentReportManager.getExtent();
				}
				test = extent.createTest(result.getMethod().getMethodName());
				ExtentReportManager.setExtentTest(test);
			}

			test.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped: " + result.getMethod().getMethodName(),
					ExtentColor.YELLOW));

			if (result.getThrowable() != null) {
				test.skip(result.getThrowable());
			}
		} catch (Exception e) {
			System.err.println("Error logging test skip: " + e.getMessage());
		} finally {
			ExtentReportManager.removeExtentTest();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test failed but within success percentage: " + result.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("\n========================================");
		System.out.println("FINISHED TEST SUITE: " + context.getName());
		System.out.println("Tests Passed: " + context.getPassedTests().size());
		System.out.println("Tests Failed: " + context.getFailedTests().size());
		System.out.println("Tests Skipped: " + context.getSkippedTests().size());
		System.out.println("========================================\n");

		try {
			if (extent != null) {
				extent.flush();
				System.out.println("Extent Report generated at: " + ExtentReportManager.getReportPath());
			}
		} catch (Exception e) {
			System.err.println("Error flushing extent report: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
