package com.htcindia.trracs.pageobjects;

import org.openqa.selenium.By;

public interface LoginPageObjects {

	/* Page objects using POM(page object model) */
	By usernameTxtbox = By.id("userName");
	By passwordTxtbox = By.id("password");
	By exportRadioBtn = By.xpath("//input[@value='edis']");
	By importRadioBtn = By.xpath("//input[@value='idis']");
	By loginBtn = By.id("loginBtn");
	By loginExpire = By.xpath("//span[text()='Yes']");
	By sessionExpire = By.xpath("//div[@id='welcome']/following::div[1]/button");
	
	/**************************************/
	/* Page objects using POM(page object model) */
	By USERNAME = By.id("userName");
	By PASSWORD = By.id("password");
	By EXPORTRADIOBUTTON = By.xpath("//input[@value='edis']");
	By IMPORTRADIOBUTTON = By.xpath("//input[@value='idis']");
	By LOGINBUTTON = By.id("loginBtn");
	By SESSIONEXPIRE = By.xpath("//div[@id='welcome']/following::div[1]/button");
	By LOGOUTBUTTON = By.xpath("//a[starts-with(text(),'Logout')]");
	By LOGINEXPIRE = By.xpath("//span[text()='Yes']");

}