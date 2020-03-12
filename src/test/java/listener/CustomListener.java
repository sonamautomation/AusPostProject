package listener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.openqa.selenium.WebDriver;
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
		// TODO Auto-generated method stub
		setDate();
		setLog4j("TRRACS");
		Log.info(suite.getName() + " : Test Suite Begins");
		try {
			gatherConfigProperties();
			initializeTestReport();
		} catch (MyException e) {
			Log.error("Failed To Gather Config Properties" + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		// getBrowser().quit();
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Passed");
		// getBrowser().quit();
			getTestCase().log(Status.PASS, MarkupHelper.createLabel(testResult.getName()+" PASSED ", ExtentColor.GREEN).getMarkup());
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		// TODO Auto-generated method stub
		Log.info(getTestMethodName(testResult) + " : Test Case Failed");
		try {
			getTestCase().log(Status.FAIL, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.RED).getMarkup(), MediaEntityBuilder.createScreenCaptureFromPath(new File(snap.get().saveAs(testResult.getName() + ".png")).getAbsolutePath()).build());
		} catch (IOException | MyException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();
		}
		// getBrowser().quit();
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		// TODO Auto-generated method stub
		Log.info(getTestMethodName(testResult) + " : Test Case Skipped");
		try {
			getTestCase().log(Status.SKIP, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.LIME).getMarkup(), MediaEntityBuilder.createScreenCaptureFromPath(new File(snap.get().saveAs(testResult.getName() + ".png")).getAbsolutePath()).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// getBrowser().quit();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		Log.info(context.getName() + " : Test Begins");
		try {
			try {
				trigger(typeOfBrowser, nameOfBrowser, driverFilesDirectory);
				getBrowser().get("http://172.16.104.66:8080/trracs/faces/pages/login.xhtml");
				 setParentTestCase(extentReport.createTest(context.getCurrentXmlTest().getName()));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loginPage = new LoginPage(getBrowser());
				System.out.println(loginPage);
			} catch (MyException e) {
				Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
				e.getMessage();
			}
		} catch (MalformedURLException e) {
			Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
			e.getMessage();
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		Log.info(context.getName() + " : Test Ends");
		getBrowser().quit();
		extentReport.flush();
	}
	
	
	@AfterMethod
	public void after_each_test_case(ITestResult result) {
		Log.info(result.getMethod().getMethodName() + " : Test Case Ends");
		// getBrowser().quit();
	}

	private static String getTestMethodName(ITestResult testResult) {
		return testResult.getMethod().getConstructorOrMethod().getName();
	}

}
