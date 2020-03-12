package pages;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BasePage;
import helpers.MyException;
import pageobjects.OrmMasterPageObjects;

public class OrmMasterPage extends BasePage implements OrmMasterPageObjects {

	public OrmMasterPage(WebDriver driver) throws MyException {
		super(driver);
	}

	// Navigate to Add Remittance Page
	public void clickAddRemittance() throws MyException {
		try {
			clickOn(ADDREMITTANCEBUTTON);
		} catch (Exception e) {
			throw new MyException("Failed To Navigate Add Remittance Screen");
		}
	}

	// Check the presence of an elements
	public void checkPresenceofAllElements() throws MyException {
		try {
			check(FROMDATEFIELD, "IsDisplayed");
			check(TODATEFIELD, "IsDisplayed");
			check(ORMREFERENCENUMBERFIELD, "IsDisplayed");
			check(ADCODEFIELD, "IsDisplayed");
			check(IECODEFIELD, "IsDisplayed");
			check(REMITTANCESTATUSFIELD, "IsDisplayed");
		} catch (Exception e) {
			throw new MyException("Failed To Check Presence of Elements");
		}
	}

	// ORM Reference Number Field
	public void ormReferenceNumberValidation(String ormReferenceNumber) throws MyException {
		try {
			type(ormReferenceNumber, ORMREFERENCENUMBERFIELD);
			validateCharacters(ORMREFERENCENUMBERFIELD, "ORM Reference Number");
			findElementLength(ORMREFERENCENUMBERFIELD, 30, "ORM Reference Number");
		} catch (Exception e) {
			throw new MyException("Failed To Validate ORM Reference Number");
		}
	}

	// Ad code validation
	public void adCodeValidation(String adCode) throws MyException {
		try {
			validateCharacters(ADCODEFIELD, "AD Code");
			findElementLength(ADCODEFIELD, 7, "Ad Code");
		} catch (Exception e) {
			throw new MyException("Failed To Validate Ad Code");
		}
	}

	// Select Ad Code Value
	public void selectAdCodeValue(String adCode) throws MyException {
		try {
			clickOn(ADCODESEARCHICON);
			type(adCode, ADCODESEARCHBOX);
			clickOn(ADCODESEARCHBUTTON);
			clickOn(ADCODEDATATABLE);
			adCodeValidation(adCode);

		} catch (Exception e) {
			throw new MyException("Failed To Select Ad Code Value");
		}
	}

	// IE Code Field
	public void ieCodeValidation(String ieCode) throws MyException {
		try {
			type(ieCode, IECODEFIELD);
			validateCharacters(IECODEFIELD, "IE Code");
			findElementLength(IECODEFIELD, 10, "IE Code");
			clickOn(ADCODEREFRESHBUTTON);
		} catch (Exception e) {
			throw new MyException("Failed To Validate IE Code");
		}
	}

	// Remittance Status Field
	public void selectRemittanceStatus(String remittanceStatus) throws MyException {
		try {
			selectDropdownValue(REMITTANCESTATUSFIELD, "UNUTILIZED");
		} catch (Exception e) {
			throw new MyException("Failed To Select Remittance Status");
		}
	}

	// Back to ORM Master Page
	public void backToOrmMaster() throws MyException {
		try {
			clickOn(BACKBUTTON);
		} catch (Exception e) {
			throw new MyException("Failed To Return ORM Master screen");
		}
	}

	// Upload ORM File
	public void uploadOrmFile(String filePath, String expectedErrorMsg, String expectedSuccesssMsg) throws MyException {
		try {
			File file = new File(filePath);
			String uploadFile = file.getAbsolutePath();
			type(uploadFile, UPLOADBUTTON);
			Thread.sleep(1000);
			getUploadErrorMsg(expectedErrorMsg, expectedSuccesssMsg);
		} catch (Exception e) {
			throw new MyException("Failed To Upload ORM File");
		}
	}

	// Get Upload Error Message
	public void getUploadErrorMsg(String expectedErrorMsg, String expectedSuccessMsg) throws MyException {
		try {
			String uploadMsg = getTextFrom(MESSAGETEXT);
			boolean msgBox = check(MESSAGEBOX, "IsDisplayed");
			if (msgBox) {
				if (uploadMsg.contains(expectedErrorMsg)) {
					String actualErrorMsg = getTextFrom(FILE_EXIST_ERRORMSG);
					if (actualErrorMsg.contains(expectedErrorMsg))
						System.out.println("File already exist. Please upload different ORM file.");
				} else {
					System.out.println("File Uploaded Successfully");
				}
			} else
				throw new MyException("Upload Error is Missing");
		} catch (Exception e) {
			throw new MyException("Failed To Display Upload File Message");
		}
	}

	// Verify Mandatory Field
	public void verifyMandatoryField(String expectedErrorMsg) throws MyException {
		try {
			clickOn(ORMSEARCHBUTTON);
			String uploadMsg = getTextFrom(MESSAGETEXT);
			boolean msgBox = check(MESSAGEBOX, "IsDisplayed");
			if (msgBox) {
				if (uploadMsg.contains(expectedErrorMsg)) {
					String actualErrorMsg = getTextFrom(FROMDATERRORMSG);
					if (actualErrorMsg.contains(expectedErrorMsg))
						System.out.println("From Date is required. Please select From Date");
				}
			} else
				throw new MyException("From Date Error is Missing");
		} catch (Exception e) {
			throw new MyException("Failed To Verify Mandatory Field");
		}
	}

	// Select Date
	public void selectDateValue(String fromDate, String toDate) throws MyException {
		try {
			clickOn(FROMDATEPICKER);
			webcalendar(fromDate);
			clickOn(TODATEPICKER);
			webcalendar(toDate);
		} catch (Exception e) {
			throw new MyException("Failed To Select Date");
		}
	}

	// Search button
	public void searchButton() throws MyException {
		try {
			clearFields();
			clickOn(ORMSEARCHBUTTON);
			WebElement dataTable = identify(SEARCHDATATABLE);
			waitForElementVisibility(dataTable);
		} catch (Exception e) {
			throw new MyException("Failed To Click Search Button With Valid Data");
		}
	}

	// Get To Date
	public String getToDate() throws MyException {
		try {
			String toDate = getTextByValueAttribute(TODATEFIELD);
			return toDate;
		} catch (Exception e) {
			throw new MyException("Failed To Click Search Button With Valid Data");
		}
	}

	// Clear Fields
	public void clearFields() throws MyException {
		try {
			clickOn(TODATEREFRESHBTN);
			clearText(ORMREFERENCENUMBERFIELD);
			clickOn(ADCODEREFRESHBUTTON);
			clearText(IECODEFIELD);
		} catch (Exception e) {
			throw new MyException("Failed To Clear Values");
		}
	}

	// View ORM Details
	public void viewORMDetails() throws MyException {
		try {
			List<WebElement> ORMDatatable = identifyAll(ORMDETAILS);
			ORMDatatable.get(0).click();
			clickOn(CLOSEBUTTON);
		} catch (Exception e) {
			throw new MyException("Failed To View ORM Details");
		}
	}

	// Download ORM
	public void downloadORM() throws MyException {
		try {
			clickOn(DOWNLOADBUTTON);
		} catch (Exception e) {
			throw new MyException("Failed To Download ORM");
		}
	}

	// Edit ORM
	public void editORM() throws MyException {
		try {
			clickOn(EDITBUTTON);
			check(PURPOSECODEFIELD, "IsDisplayed");
			clickOn(PURPOSECODEFIELD);
			clickOn(PURPOSECODEDROPDOWN);
			clickOn(SAVEADDREMITTANCE);
		} catch (Exception e) {
			throw new MyException("Failed To Edit & Save ORM");
		}
	}

	// Cancel ORM
	public void cancelORM() throws MyException {
		try {
			clickOn(CANCELBUTTON);
		} catch (Exception e) {
			throw new MyException("Failed To Cancel ORM");
		}
	}
}
