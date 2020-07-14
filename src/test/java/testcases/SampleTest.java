package testcases;


import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.Base;


public class SampleTest extends Base {

	@Test
	public void testSample1(Method method) throws Exception
	{
		setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("Test"));
		loginPage.nbnWebTest();
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO VALIDATE THE PAGE ");
	}

}
