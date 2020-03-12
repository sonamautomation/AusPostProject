package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BasePage;
import helpers.MyException;
import pageobjects.BOEExtensionPageObjects;

public class BOEExtensionPage extends BasePage implements BOEExtensionPageObjects {

	public BOEExtensionPage(WebDriver driver) throws MyException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void chooseFromDate(String dateValue) throws Exception {
		clickOn(fromDateBtn);
		webcalendar(dateValue);
		Thread.sleep(2000);
	}

	public void chooseToDate(String dateValue) throws Exception {
		clickOn(toDateBtn);
		webcalendar(dateValue);
	}

	public void clickSearch() throws Exception {
		clickOn(search);
		Thread.sleep(2000);
	}

	public String getToDateValue() throws MyException {
		return identify(toDateValue).getText();

	}

	public void enterBOENumber(String text) {
		try {
			type(text, BOENumber);
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	public void clearFields() {
		try {
			identify(BOENumber).clear();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void enableOtherBankAD() {

		try {
			clickOn(otherBankBOE);
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean adCodedisabled() throws MyException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (identify(adCodebutton).isDisplayed()) {
			return true;
		} else
			return false;
	}

	public void uploadBOEExtnFile(String filename) {
		upload(BOEExtnUpload, filename);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void downloadCSV() {

		try {
			clickOn(csvDownload);
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void chooseFirstBOE() throws MyException {
		List<WebElement> BOEnumbers = identifyAll(BOEdatatableLinks);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BOEnumbers.get(0).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Checks all components in Invoice details page and checks if invoice related
	// table is displayed
	public boolean checkBOEInvoicedetails() throws MyException {
		if (identify(invoiceDatatable).isDisplayed()) {
			return true;
		}
		return false;
	}

	public void goback() {

		try {
			clickOn(invoiceDeatilBack);
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String raiseExtensionByADbank(String extnDate) throws Exception {
		clickOn(manageResponses);
		Thread.sleep(2000);
		selectDropdownValue(extensionBy, "ad");
		clickOn(extensionDateBtn);
		Thread.sleep(2000);
		webcalendar(extnDate);
		clickOn(saveExtension);
		Thread.sleep(2000);
		if (!identify(BOEerrorMsg).isDisplayed()) {
			String text = getTextFrom(responseMessage);
			clickOn(closeExtension);
			return text;
		} else
			return getTextFrom(BOEerrorMsg);
	}

	public String cancelNflagExtension() throws Exception {
		clickOn(manageResponses);
		Thread.sleep(2000);
		clickOn(cancelBOE);
		return getTextFrom(BOEerrorMsg);
	}

}
