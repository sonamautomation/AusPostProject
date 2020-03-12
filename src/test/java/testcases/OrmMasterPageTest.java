package testcases;

import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.mongodb.diagnostics.logging.Logger;

import base.Base;
import dataprovider.MyDataProvider;
import helpers.MyException;

public class OrmMasterPageTest extends Base {



	// Navigate to ORM Master
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC01_navigateToOrmMaster(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		dashboard=loginPage.User_Login(testData.get("userType"),testData.get("userName"), testData.get("password"));
		ormMasterPage=dashboard.navigateToOrmMaster();
	}

	// Check presence of all elements & Verfify Mandatory Field
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC02_checkPresenceofElements(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.checkPresenceofAllElements();
		ormMasterPage.verifyMandatoryField(testData.get("expectedErrorMsg"));
	}

	// Select date from calendar
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC03_selectDate(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.selectDateValue(testData.get("fromDate"), testData.get("toDate"));
	}

	// Verify ORM field
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC04_ormReferenceNumberField(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.ormReferenceNumberValidation(testData.get("ormReferenceNumber"));
	}

	// Verify Ad Code field
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC05_adCodeField(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.selectAdCodeValue(testData.get("adCode"));
	}

	// Verify IE Code field
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC06_ieCodeField(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.ieCodeValidation(testData.get("ieCode"));
	}

	// Verify Remittance status field
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC07_remittanceStatusField(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.selectRemittanceStatus(testData.get("remittanceStatus"));
	}

	// Search button & Verify Autofill To Date
	@Test
	public void TC08_clickSearchButton(Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.searchButton();
		getTestCase().log(Status.INFO,"Verified Search Button With Valid Data");
		assertNotNull(ormMasterPage.getToDate());
		getTestCase().log(Status.INFO,"To date is Not Null");
	}

	// Download ORM
	@Test
	public void TC09_downloadORM(Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.downloadORM();
		getTestCase().log(Status.INFO,"Downloaded ORM");
	}

	// Click file upload button
	@Test(dataProvider = "OrmMasterTestData", dataProviderClass = MyDataProvider.class)
	public void TC10_uploadOrmFile(Map<String, String> testData, Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.uploadOrmFile(testData.get("filePath"), testData.get("expectedErrorMsg"),
				testData.get("expectedSuccessMsg"));
		getTestCase().log(Status.INFO,"ORM File Upload Verified");
	}

	// Navigate to Add Remittance Page
	@Test
	public void TC11_navigateToAddRemittance(Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.clickAddRemittance();
		getTestCase().log(Status.INFO,"Navigated to Add Remittance page");
		ormMasterPage.backToOrmMaster();
		getTestCase().log(Status.INFO,"Back to ORM Master Page");
	}

	// View ORM Details
	@Test
	public void TC12_viewORMDetails(Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.viewORMDetails();
		getTestCase().log(Status.INFO,"Displayed ORM Deatils");
	}

	// Edit ORM
	@Test
	public void TC13_editORM(Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.editORM();
		getTestCase().log(Status.INFO,"Edited & Saved ORM");
	}

	// Cancel ORM
	@Test
	public void TC14_cancelORM(Method method) throws MyException {
		setTestCase(getParentTestCase().createNode(method.getName()));
		ormMasterPage.cancelORM();
		getTestCase().log(Status.INFO,"Cancelled ORM");
	}
}
