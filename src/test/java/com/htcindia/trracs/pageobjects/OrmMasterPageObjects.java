package com.htcindia.trracs.pageobjects;

import org.openqa.selenium.By;

public interface OrmMasterPageObjects {
	
	// Search Page
	By ORM_MASTER = By.xpath("//span[text()='ORM Master']");
	By ADDREMITTANCEBUTTON = By.id("adClearBtn");
	By UPLOADBUTTON = By.id("uploadAdBtn_input");
	By MESSAGEBOX = By.xpath("//div[@id='j_idt28']");
	By MESSAGETEXT = By.xpath("//div[@id='j_idt28']/div/ul/li/span");
	By UPLOADSUCCESSMSG = By.xpath("//span[contains(text(),'Generated Reference No for Uploaded File')]");
	By FILE_EXIST_ERRORMSG = By.xpath("//span[contains(text(),'File already exist')]");
	By BACKBUTTON = By.id("backBtnId");
	By ORMREFERENCENUMBERFIELD = By.name("j_idt58");
	By IECODEFIELD = By.id("j_idt77");
	By REMITTANCESTATUSFIELD = By.id("status");
	By REMITTANCESTATUSDROPDOWN = By.xpath("//div[@id='status_panel']/div/ul/li[2]");
	
	// Date
	By FROMDATEFIELD = By.id("fromDateIrmId_input");
	By MONTHDROPDOWN = By.xpath("//select[@data-handler='selectMonth']/option[@value='Jan']");
	By YEARDROPDOWN = By.xpath("//select[@data-handler='selectYear']/option[@value='2020']");
	By DAYFIELD = By.xpath("//a[text()='10']");
	By TODATEFIELD = By.id("toDateId_input");
	By FROMDATERRORMSG = By.xpath("//span[contains(text(),'Please select From Date')]");
	By FROMDATEPICKER = By.xpath("//span[@id='fromDateIrmId']/button");
	By TODATEPICKER = By.xpath("//span[@id='toDateId']/button");
	By TODATEREFRESHBTN = By.xpath("//a[@id='effectiveToId']/span");
	By DATEPICKERCALENDAR = By.id("ui-datepicker-div");
	
	// Ad Code
	By ADCODEFIELD = By.id("newAdCode");
	By ADCODESEARCHICON = By.id("j_idt61");
	By ADCODESEARCHBOX = By.id("adIdisSrhIP");
	By ADCODESEARCHBUTTON = By.id("j_idt379");
	By ADCODEDATATABLE = By.xpath("//div[@id='adSrhIdisDT']/div/table/tbody/tr[2]/td[1]");
	By ADCODEREFRESHBUTTON = By.id("j_idt63");
	
	// View ORM
	By ORMSEARCHBUTTON = By.id("ormSearchBtn");
	By SEARCHDATATABLE = By.id("ormMasterList");
	By ORMDETAILS = By.xpath("//div[@id='ormMasterList']/div/table/tbody/tr[1]/descendant::a");
	By CLOSEBUTTON = By.xpath("//span[@id='displayPanel_title']/following::a[1]");
	By DOWNLOADBUTTON = By.id("j_idt87");
	By EDITBUTTON = By.id("ormMasterList:0:j_idt118");
	By CANCELBUTTON = By.id("ormMasterList:0:j_idt118");
	
	// Edit ORM
	By PURPOSECODEFIELD = By.id("purposeCodeId_label");
	By PURPOSECODEDROPDOWN = By.xpath("//div[@id='purposeCodeId_panel']/div/ul/li[2]");
	By SAVEADDREMITTANCE = By.id("saveBtnId");
}
