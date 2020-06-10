package listener;

import java.net.MalformedURLException;

import org.testng.IClass;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import base.Base;
import helpers.MyException;

public class MyListener extends Base implements ITestListener, ISuiteListener{
	
	/* <---------- Once Test Execution Begins ---------> */
	@Override
	public void onTestStart(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Execution Begins");
		try {
			try {
				trigger(typeOfBrowser, nameOfBrowser, driverFilesDirectory);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
			e.getMessage();
		} catch (MyException e) {
			Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
			e.getMessage();
		}
	}
	
	/* <---------- When Test Case Is A Success ---------> */
	@Override
	public void onTestSuccess(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Passed");
		getBrowser().quit();
	}
	
	/* <---------- When Test Case Fails ---------> */
	@Override
	public void onTestFailure(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Failed");
		getBrowser().quit();
	}

	/* <---------- When Test Case Is Skipped ---------> */
	@Override
	public void onTestSkipped(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Skipped");
		getBrowser().quit();
	}

	/* <---------- Before Test Suite Execution ---------> */
	@Override
	public void onStart(ISuite suite) {
		setDate();
		setLog4j("TRRACS");
		Log.info(suite.getName() + " : Test Suite Begins");
		try {
			try {
				gatherConfigProperties();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			Log.error("Failed To Gather Config Properties" + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	/* <---------- After Each Test Case Execution ---------> */
	@AfterMethod
	public void after_each_test_case(ITestResult result) {
		Log.info(result.getMethod().getMethodName() + " : Test Case Ends");
		getBrowser().quit();
	}

	private static String getTestMethodName(ITestResult testResult) {
		return testResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
		Log.info(arg0.getName() + " : Test suite Ends");
		getBrowser().quit();
	}

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
	

}