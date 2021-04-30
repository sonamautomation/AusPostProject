package testcases;

import java.io.IOException;
import java.lang.reflect.Method;

import base.Base;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;

import helpers.MyException;
import listener.MyListener;

public class SampleTest_001 extends Base {

	@Test
	public void TC_001(Method method) throws Exception
	{
		setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("Test"));
		loginPage.GoogleTest();
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO COMPLETE THE TEST ");
	}
	
	@Test(enabled = false)
	public void TC_002() throws MyException, IOException, InterruptedException {
		getBrowser().get("http://www.newtours.demoaut.com/");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}

	@Test(enabled = false)
	public void TC_003() throws MyException, IOException, InterruptedException {
		getBrowser().get("https://www.way2automation.com/demo.html");
		Thread.sleep(5000);
		System.out.println("Thread ID : " + Thread.currentThread().getId());
	}

}