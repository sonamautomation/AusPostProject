package listener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.Base;
import helpers.MyException;
import pages.LoginPage;

public class CustomListener extends Base implements ITestListener, ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		setDate();
		setLog4j("TRRACS");
		Log.info(suite.getName() + " : Test Suite Begins");
		try {
			try {
				gatherConfigProperties();
			    startGrid(typeOfBrowser);
			} catch (Exception e) {
				e.printStackTrace();
			}
			initializeTestReport();
		} catch (MyException e) {
			Log.error("Failed To Gather Config Properties" + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		try {
			stopGrid(typeOfBrowser);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		 
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Passed");
		getTestCase().log(Status.PASS, MarkupHelper.createLabel(testResult.getName()+" PASSED ", ExtentColor.GREEN).getMarkup());
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Failed");
		try {
			getTestCase().log(Status.FAIL, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.RED).getMarkup(), MediaEntityBuilder.createScreenCaptureFromPath(new File(snap.get().saveAs(testResult.getName() + ".png")).getAbsolutePath()).build());
		} catch (IOException | MyException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Skipped");
		try {
			getTestCase().log(Status.SKIP, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.LIME).getMarkup(), MediaEntityBuilder.createScreenCaptureFromPath(new File(snap.get().saveAs(testResult.getName() + ".png")).getAbsolutePath()).build());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void onStart(ITestContext context) {
		Log.info(context.getName() + " : Test Begins");
		setParentTestCase(extentReport.createTest(context.getCurrentXmlTest().getName()));
	}

	@Override
	public void onFinish(ITestContext context) {
		Log.info(context.getName() + " : Test Ends");
		getBrowser().quit();
		extentReport.flush();
	}


	@AfterMethod
	public void after_each_test_case(ITestResult result) {
		Log.info(result.getMethod().getMethodName() + " : Test Case Ends");
	}
	
	private static String getTestMethodName(ITestResult testResult) {
		return testResult.getMethod().getConstructorOrMethod().getName();
	}

	public void startGrid(String browserType) {
		if(browserType.equalsIgnoreCase("REMOTE") && container.equalsIgnoreCase("true")) {
			try {
				Runtime.getRuntime().exec("cmd /c start StartGrid.bat");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void stopGrid(String browserType) throws IOException {
		if(browserType.equalsIgnoreCase("REMOTE") && container.equalsIgnoreCase("true")) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Runtime.getRuntime().exec("cmd /c start StopGrid.bat");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		}
	}

}
