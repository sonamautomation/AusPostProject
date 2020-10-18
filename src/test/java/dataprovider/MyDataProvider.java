package dataprovider;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestClass;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import bsh.classpath.BshClassPath.GeneratedClassSource;
import helpers.ExcelUtility;

public class MyDataProvider extends Base {
	
	@DataProvider(name = "BOEExtensionTestData")
	public static Object[][] dataProviderMethod1(Method method) throws Exception {
		Object[][] testDataArray = null;
		ExcelUtility.setExcelFile(config.properties.get("testDataFilesPath")+config.properties.get("testDataFile"), "BOEExtension");
		/*Test method name and value in test data should be same(value highlighted)*/
		testDataArray = ExcelUtility.getTestData(method.getName());
		return testDataArray;
	}
	
	@DataProvider(name = "BOEClosureTestData")
	public static Object[][] dataProviderMethod2(Method method) throws Exception {
		Object[][] testDataArray = null;
		ExcelUtility.setExcelFile(config.properties.get("testDataFilesPath")+config.properties.get("testDataFile"), "BOEClosure");
		/*Test method name and value in test data should be same(value highlighted)*/
		testDataArray = ExcelUtility.getTestData(method.getName());
		return testDataArray;
	}
	
	@DataProvider(name = "OrmMasterTestData")
	public static Object[][] dataProviderMethod3 (Method method) throws Exception {
		Object[][] testDataArray = null;
		ExcelUtility.setExcelFile(config.properties.get("testDataFilesPath") + config.properties.get("testDataFile"),"ORMMaster");
		/* Test method name and value in test data should be same(value highlighted) */
		testDataArray = ExcelUtility.getTestData(method.getName());
		return testDataArray;
	}

	@DataProvider(name = "APITestData")
	public static Object[][] dataProviderMethod4 (Method method) throws Exception {
		Object[][] testDataArray = null;
		ExcelUtility.setExcelFile(config.properties.get("testDataFilesPath") + config.properties.get("testDataFile"),"API");
		/* Test method name and value in test data should be same(value highlighted) */
		testDataArray = ExcelUtility.getTestData(method.getName());
		return testDataArray;
	}

	@DataProvider(name = "JDBCDataProvider")
	public static Object[][] dataProviderMethod5(Method method) throws Exception {
		Object[][] testDataArray = null;
		com.htcindia.trracs.helpers.ExcelUtility.setExcelFile(config.properties.get("testDataFilesPath") + config.properties.get("testDataFile"),"JDBC");
		/* Test method name and value in test data should be same(value highlighted) */
		testDataArray = com.htcindia.trracs.helpers.ExcelUtility.getTestData(method.getName());
		return testDataArray;
	}
}

