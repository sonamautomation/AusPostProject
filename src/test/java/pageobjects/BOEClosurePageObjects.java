package pageobjects;

import org.openqa.selenium.By;

public interface BOEClosurePageObjects {
	// Search page
		By fromDateBtn = By.xpath("//span[@id='fromDateId']/descendant::button");
		By toDateBtn = By.xpath("//span[@id='toDateId']/descendant::button");
		By search = By.xpath("//a[@id='searchBtn']");
		By toDateValue = By.id("toDateId_input");
		By BOENumber = By.id("boeNumber");
		By otherBankBOE = By.xpath("//div[@id='othrBnkAd']/div[2]/span");
		By adCodebutton = By.xpath("//table[@id='adCodePanelGroupId']/tbody/tr[2]/td/span");
		By BOEExtnUpload = By.id("uploadAdBtn_input");
		By csvDownload = By.id("csvDownload");
		By BOEdatatableLinks = By.xpath("//tbody[@id='searchDataTableId_data']/tr/td[1]/descendant::a");
		By BOEerrorMsg = By.xpath("//div[@id='BillOfEntryExtMsg']/descendant::li/span");
		By BOEdatatable=By.xpath("//tbody[@id='auditTableId_data']");

		// Invoice details page
		By invoiceDatatable = By.id("invoiceTableList");
		By invoiceDeatilBack = By.id("goToMaster");
		By invoiceAction = By.xpath("//div[@id='invoiceTableList']/descendant::a[2]");
		
		// ADD BOE closure
		By adjustmentRefNo = By.id("adjstmentRefNo");
		By adjustmentDate = By.xpath("//span[@id='adjustmentDateId']/button");
		By adjustmentAmnt = By.id("boeAmountId");
		By adjustmentIndicator = By.id("boeAdjustmentIndicatorId");
		By approvedBy = By.id("approveBy");
		By saveBtn = By.id("saveBoeClosure");	
		By addResponseMsg = By.id("statusMessage");	
		

		// Extension dialogue
		By saveExtension = By.id("extendButtonId");
		By closeExtension = By.id("extendcloseButtonId");
		By extensionBy = By.id("realizationExtensionInd");
		By extensionDateBtn = By.xpath("//span[@id='realizationExtensionPeriod']/button");
		By extensionDateValue = By.id("realizationExtensionPeriod_input");
		By responseMessage = By.xpath("//div[@id='addResponseMessage']/descendant::li/span");
		By cancelBOE = By.xpath("//tbody[@id='boePaymentTable_data']/descendant::tr[1]/td[11]/a");
}
