package com.htcindia.trracs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.htcindia.trracs.base.BasePage;
import com.htcindia.trracs.helpers.MyException;
import com.htcindia.trracs.pageobjects.LoginPageObjects;

public class LoginPage extends BasePage implements LoginPageObjects {

	public LoginPage(WebDriver driver) throws MyException {
		super(driver);
	}

	public DashboardPage User_Login(String userType, String username, String passwWord) throws MyException {
		try {
			// sessionExpireHandle(); //to handle session expire on loading page
			if (identify(usernameTxtbox).isDisplayed()) {
				selectUserType(userType);
				type(username, usernameTxtbox);
				type(passwWord, passwordTxtbox);
				clickOn(loginBtn);
				Thread.sleep(2000);
				if (getURL().contains("loginExpire.xhtml")) {
					clickOn(loginExpire);
					Thread.sleep(1000);
					selectUserType(userType);
					type(username, usernameTxtbox);
					type(passwWord, passwordTxtbox);
					clickOn(loginBtn);
					myWait.until(ExpectedConditions.urlContains("idis-dashboard.xhtml"));
				}
			} else {
				clickOn(sessionExpire);
				selectUserType(userType);
				type(username, usernameTxtbox);
				type(passwWord, passwordTxtbox);
				clickOn(loginBtn);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			Assert.fail();
		}

		return new DashboardPage(lDriver);
	}

	private void selectUserType(String usertype) {
		try {
			if (usertype.equalsIgnoreCase("Export")) {
				clickOn(exportRadioBtn);
			} else if (usertype.equalsIgnoreCase("import")) {

				clickOn(importRadioBtn);
			}
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	// Login Trracs
//		public DashboardPage logInTrracs(String userName, String password, String userType) throws MyException {
//			try {
//				if (identify(USERNAME).isDisplayed()) {
//					type(userName, USERNAME);
//					enterUserCredentials(password, userType);
//					if (getURL().contains("loginExpire.xhtml")) {
//						clickOn(LOGINEXPIRE);
//						enterUserCredentials(password, userType);
//					}
//				} else {
//					clickOn(SESSIONEXPIRE);
//					type(userName, USERNAME);
//					enterUserCredentials(password, userType);
//				}
//			} catch (Exception e) {
//				throw new MyException("Failed To Login Trracs");
//			}
//			return new DashboardPage(lDriver);
//		}
//
//		// Enter User Credentials
//		public void enterUserCredentials(String password, String userType) throws MyException {
//			try {
//				type(password, PASSWORD);
//				selectUserType(userType);
//				clickOn(LOGINBUTTON);
//			} catch (Exception e) {
//				throw new MyException("Failed To Enter User Crendentials");
//			}
//		}
//
//		// Select User Type
//		private void selectUserType(String userType) throws MyException {
//			try {
//				if (userType.equalsIgnoreCase("Import"))
//					clickOn(IMPORTRADIOBUTTON);
//				else if (userType.equalsIgnoreCase("Export"))
//					clickOn(EXPORTRADIOBUTTON);
//			} catch (Exception e) {
//				throw new MyException("Failed to Select User Type");
//			}
//		}
//
		// Logout Trracs
		public void logoutTrracs() throws MyException {
			try {
				clickOn(LOGOUTBUTTON);
				System.out.println("User logout successful");
			} catch (Exception e) {
				throw new MyException("Failed To Logout Trracs");
			}
		}

}