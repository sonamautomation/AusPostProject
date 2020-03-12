package testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.Base;
import dataprovider.MyDataProvider;
import helpers.MyException;
import listener.CustomListener;

public class BOEClosureTest extends Base {

	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
	public void T001_openBOEClosure(Map<String,String> testData, Method method) {
		try {
			setTestCase(getParentTestCase().createNode(method.getName()));
			dashboard = loginPage.User_Login(testData.get("type"),testData.get("username"),testData.get("password"));
			boeClosure = dashboard.navigateToBoeClosure();
		} catch (MyException e) {
			 Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

//	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
//	 public void T002_checkAutofillToDate(Map<String,String> testData, Method method) {
//	 try {
//		 setTestCase(getParentTestCase().createNode(method.getName()));
//	 // Enter from date and click on search button, auto populate to date
//	 boeClosure.chooseFromDate(testData.get("from date"));
//	 boeClosure.clickSearch();
//	 assertNotNull(boeClosure.getToDateValue());
//	 } catch (Exception e) {
//	 e.printStackTrace();
//	 }
//	 }
//	
//	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
//	 public void T003_searchByBOEnumber(Map<String,String> testData, Method method) {
//	 try {
//		 setTestCase(getParentTestCase().createNode(method.getName()));
//	 // Enter from date,BOE number and click on search button
//	 boeClosure.chooseFromDate(testData.get("from date"));
//	 boeClosure.enterBOENumber(testData.get("boe number"));
//	 boeClosure.clickSearch();
//	 Assert.assertNotNull(boeClosure.getToDateValue());
//	 } catch (Exception e) {
//	 Assert.fail(e.getMessage());
//	 e.printStackTrace();
//	 } finally {
//	 boeClosure.clearFields();
//	 }
//	 }
//	
//	@Test
//	 public void T004_checkAdCodeAutoDisable(Method method) {
//	 // enable Other bank AD code and check disable of AD code lookup
//	 try {
//		 setTestCase(getParentTestCase().createNode(method.getName()));
//	 boeClosure.enableOtherBankAD();
//	 Assert.assertTrue(boeClosure.adCodedisabled());
//	 } catch (Exception e) {
//	 Assert.fail(e.getMessage());
//	
//	 }
//	 }
//
//	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
//	public void T005_uploadBOEFile(Map<String,String> testData, Method method) {
//		// enable Other bank AD code and check disable of AD code lookup
//		try {
//			setTestCase(getParentTestCase().createNode(method.getName()));
//			boeClosure.uploadBOEclosureFile(testData.get("filepath"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			boeClosure.clearFields();
//		}
//
//	}
//
//	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
//	public void T006_DownloadCSVFile(Map<String,String> testData, Method method) {
//		// Search and download the CSV file of the BOE list
//		try {
//			setTestCase(getParentTestCase().createNode(method.getName()));
//			boeClosure.chooseFromDate(testData.get("from date"));
//			boeClosure.clickSearch();
//			boeClosure.downloadCSV();
//		} catch (Exception e) {
//			Assert.fail(e.getMessage());
//		}
//
//	}
//
//	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
//	public void T007_ViewBOE(Map<String,String> testData, Method method) {
//		try {
//			setTestCase(getParentTestCase().createNode(method.getName()));
//			boeClosure.chooseFromDate(testData.get("from date"));
//			boeClosure.clickSearch();
//			Thread.sleep(2000);
//			boeClosure.chooseFirstBOE();
//			Assert.assertEquals(true, boeClosure.checkBOEInvoicedetails());
//			// take screenshot also here and add to report along with assert
//		} catch (Exception e) {
//			Assert.fail(e.getMessage());
//			e.printStackTrace();
//		} finally {
//			boeClosure.goback();
//		}
//	}
//
//	// BOE Closure for partially paid record
//	@Test(dataProvider = "BOEClosureTestData",dataProviderClass=MyDataProvider.class)
//	public void T008_RaiseBOEClosure(Map<String,String> testData, Method method) {
//		try {
//			setTestCase(getParentTestCase().createNode(method.getName()));
//			boeClosure.chooseFromDate(testData.get("from date"));
//			boeClosure.enterBOENumber(testData.get("boe number")); // change BOE number with partially payment record
//			boeClosure.clickSearch();
//			boeClosure.chooseFirstBOE();
//			String response=boeClosure.raiseBOEClosureByADbank(testData.get("ADRefNo"),testData.get("date"),testData.get("amount"),	testData.get("indicator"),testData.get("approvedBy"));
//			System.out.println(response);
//			Assert.assertEquals(response,"New Closure details saved successfully");
//		} catch (Exception e) {
//			Assert.fail(e.getMessage());
//			boeClosure.goback();
//		}
//	}
	
	
	
}
