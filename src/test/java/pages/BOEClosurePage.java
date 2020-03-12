package pages;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;
import helpers.MyException;
import pageobjects.BOEClosurePageObjects;
import pageobjects.BOEExtensionPageObjects;

public class BOEClosurePage extends BasePage implements BOEClosurePageObjects {

	public BOEClosurePage(WebDriver driver) throws MyException {
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
		waitUntilLoad();
	}

	public String getToDateValue() throws MyException {
		return identify(toDateValue).getText();

	}

	public void enterBOENumber(String text) {
		try {
			type(text,BOENumber);
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



	public void uploadBOEclosureFile(String filename) {
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
			waitUntilLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  //clicks Action button and raises closure(without closeOfBill indicator) with Approved By AD bank
	public String raiseBOEClosureByADbank(String adRefNumber,String date,String amount,String indicator,String approvedBy) throws MyException {
		try {
			clickOn(invoiceAction);
			waitUntilLoad();
			type(adRefNumber, adjustmentRefNo);
			try {
				clickOn(adjustmentDate);
				webcalendar(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			type(amount, adjustmentAmnt);
			try {
				selectDropdownValue(adjustmentIndicator, indicator);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				selectDropdownValue(BOEClosurePage.approvedBy, approvedBy);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clickOn(saveBtn);
			waitUntilLoad();
		} catch (MyException e) {
			e.printStackTrace();
		}
			return getTextFrom(addResponseMsg);
	
	}
	
	public boolean checkBOECompleted() {
		try {
			List<WebElement> rows= identify(BOEdatatable).findElements(By.tagName("tr"));
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public String cancelNflagExtension() throws Exception {
		Thread.sleep(2000);
		clickOn(cancelBOE);
		return getTextFrom(BOEerrorMsg);
	}

}
