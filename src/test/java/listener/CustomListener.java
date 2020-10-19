package listener;

import java.io.File;
import java.io.IOException;

import helpers.MountebankStubApi;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.Base;
import helpers.MyException;

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

		//Create stub if flags set to true
		if(runApi && useMountebankStub){
			try{

				MountebankStubApi.createImposter(stubConfigPath+ mountebankStubAPIJSONFilename,mountebankStubServer);
			} catch (IOException e1){
				Log.error("Failed to create Mountebank stub api endpoint" +"`\n" + e1.getMessage());
			}

			uri = mountebankStubServerDomain + ":" + mountebankStubAPIPort + "/";
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		try {
			stopGrid(typeOfBrowser);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Delete mountebank api stub
		if(useMountebankStub){
			MountebankStubApi.deleteImposter(mountebankStubAPIPort,mountebankStubServer);
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
			if(runApi) {
				getTestCase().log(Status.FAIL, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.RED).getMarkup());
			}else {
				getTestCase().log(Status.FAIL, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.RED).getMarkup(), MediaEntityBuilder.createScreenCaptureFromPath(new File(snap.get().saveAs(testResult.getName() + ".png")).getAbsolutePath()).build());

			}} catch (IOException | MyException e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
			}
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		Log.info(getTestMethodName(testResult) + " : Test Case Skipped");
		try {
			if(runApi) {
				getTestCase().log(Status.SKIP, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.LIME).getMarkup());
			}else {
			getTestCase().log(Status.SKIP, MarkupHelper.createLabel(testResult.getName()+" FAILED ", ExtentColor.LIME).getMarkup(), MediaEntityBuilder.createScreenCaptureFromPath(new File(snap.get().saveAs(testResult.getName() + ".png")).getAbsolutePath()).build());
		} }catch (IOException e) {
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
		if(!runApi) {
			getBrowser().quit();
		}
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
