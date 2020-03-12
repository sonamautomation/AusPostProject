package com.htcindia.trracs.pageobjects;

import org.openqa.selenium.By;

public interface BOEExtensionPageObjects {

	// Search page
	By fromDateBtn = By.xpath("//span[@id='fromDateId']/descendant::button");
	By toDateBtn = By.xpath("//span[@id='toDateId']/descendant::button");
	By search = By.xpath("//a[@id='searchBtn']");
	By toDateValue = By.id("toDateId_input");
	By BOENumber = By.id("boeNumberId");
	By otherBankBOE = By.xpath("//div[@id='othrBnkAd']/div[2]/span");
	By adCodebutton = By.xpath("//table[@id='adCodePanelGroupId']/tbody/tr[2]/td/span");
	By BOEExtnUpload = By.id("uploadAdBtn_input");
	By csvDownload = By.id("csvDownload");
	By BOEdatatableLinks = By.xpath("//tbody[@id='auditTableId_data']/tr/td[1]/descendant::a");
	By manageResponses = By
			.xpath("//tbody[@id='auditTableId_data']/descendant::a[@aria-label='Add or Update Response']");
	By BOEerrorMsg = By.xpath("//div[@id='BillOfEntryExtMsg']/descendant::li/span");

	// Invoice details page
	By invoiceDatatable = By.id("boeDetailList");
	By invoiceDeatilBack = By.id("backBtnId");

	// Extension dialogue
	By saveExtension = By.id("extendButtonId");
	By closeExtension = By.id("extendcloseButtonId");
	By extensionBy = By.id("realizationExtensionInd");
	By extensionDateBtn = By.xpath("//span[@id='realizationExtensionPeriod']/button");
	By extensionDateValue = By.id("realizationExtensionPeriod_input");
	By responseMessage = By.xpath("//div[@id='addResponseMessage']/descendant::li/span");
	By cancelBOE = By.xpath("//tbody[@id='boePaymentTable_data']/descendant::tr[1]/td[11]/a");
}
