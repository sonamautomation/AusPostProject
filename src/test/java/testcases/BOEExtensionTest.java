package testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import dataprovider.MyDataProvider;
import helpers.ExcelUtility;
import helpers.MyException;
import listener.CustomListener;

public class BOEExtensionTest extends Base {

		
	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T001_openBOEExtension(Map<String,String> testData,Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			dashboard = loginPage.User_Login(testData.get("type"), testData.get("username"), testData.get("password"));
			boeExtension = dashboard.navigateToBoeExtension();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T002_checkAutofillToDate(Map<String,String> testData, Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			// Enter from date and click on search button, auto populate to date
			boeExtension.chooseFromDate(testData.get("from date"));
			boeExtension.clickSearch();
			assertNotNull(boeExtension.getToDateValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T003_searchByBOEnumber(Map<String,String> testData, Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			// Enter from date,BOE number and click on search button
			boeExtension.chooseFromDate(testData.get("from date"));
			boeExtension.enterBOENumber(testData.get("boe number"));
			boeExtension.clickSearch();
			Assert.assertNotNull(boeExtension.getToDateValue());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		} finally {
			boeExtension.clearFields();
		}
	}

	@Test
	public void T004_checkAdCodeAutoDisable(Method method) {
		// enable Other bank AD code and check disable of AD code lookup
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			boeExtension.enableOtherBankAD();
			Assert.assertTrue(boeExtension.adCodedisabled());
		} catch (Exception e) {
			Assert.fail(e.getMessage());

		}
	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T005_uploadBOEFile(Map<String,String> testData, Method method) {
		// enable Other bank AD code and check disable of AD code lookup
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			boeExtension.uploadBOEExtnFile(testData.get("filepath"));
		} catch (Exception e) {
			Assert.fail();
			e.printStackTrace();
		} finally {
			boeExtension.clearFields();
		}

	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T006_DownloadCSVFile(Map<String,String> testData, Method method) {
		// Search and download the CSV file of the BOE list
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			boeExtension.chooseFromDate(testData.get("from date"));
			boeExtension.clickSearch();
			boeExtension.downloadCSV();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T007_ViewBOE(Map<String,String> testData, Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			boeExtension.chooseFromDate(testData.get("from date"));
			boeExtension.clickSearch();
			Thread.sleep(2000);
			boeExtension.chooseFirstBOE();
			Assert.assertEquals(true, boeExtension.checkBOEInvoicedetails());
			// take screenshot also here and add to report along with assert
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		} finally {
			boeExtension.goback();
		}
	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T008_RaiseBOEExtension(Map<String,String> testData, Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			boeExtension.chooseFromDate(testData.get("from date"));
			boeExtension.enterBOENumber(testData.get("boe number")); // change BOE number
			boeExtension.clickSearch();
			Thread.sleep(2000);
			String value = boeExtension.raiseExtensionByADbank(testData.get("extension date"));
			Thread.sleep(2000);
			System.out.println(value);
			// take screenshot also here and add to report along with assert
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "BOEExtensionTestData",dataProviderClass=MyDataProvider.class)
	public void T009_cancelBOEExtension(Map<String,String> testData, Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			boeExtension.chooseFromDate(testData.get("from date"));
			boeExtension.enterBOENumber(testData.get("boe number")); // change BOE number
			boeExtension.clickSearch();
			Thread.sleep(2000);
			boeExtension.raiseExtensionByADbank(testData.get("extension date"));
			String value = boeExtension.cancelNflagExtension();
			Thread.sleep(2000);
			System.out.println(value);
			// take screenshot also here and add to report along with assert
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
