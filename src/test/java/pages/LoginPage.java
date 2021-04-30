package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.Base;
import base.BasePage;
import helpers.MyException;
import pageobjects.LoginPageObjects;

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

	public void searchRevolutionIt() throws InterruptedException, MyException 
	{
		check(revITLnk, "IsDisplayed");
		clickOn(aboutUs);
		check(revAboutUs, "IsDisplayed");
		clickOn(revITCareers);
	    choose("text","IT & Telecomms",jobSearchCat);
	    choose("text","Testing & QA",jobSearchsubCat);
	    choose("text","Melbourne",jobSearchloc);
	    choose("text","Permanent / Full Time",workType);
	    clickOn(searchButton);
		check(jobRole, "IsDisplayed");

		System.out.println("Navigated succesfully to Revit About Page and Searched for jobs");
	}
	public void nbnWebTest() throws MyException
	{
     clickOn(navigateRes);
     /* ID changes every time and updates the record in database */
		type("18 Chaparral St" + Keys.ENTER,enterAddress);
		clickOn(navigateNBNco);
		clickOn(nbnCareers);
		clickOn(jobs);
		check(searchForJobs,"IsDisplayed");

		System.out.println("Navigated succesfully to nbn WebPage and validated the page");
	}

	public void GoogleTest() throws MyException
	{
		type("ampion" + Keys.ENTER,searchInput);
		check(searchAmpion,"IsDisplayed");
		clickOn(searchAmpion);
		check(titleAmpion,"IsDisplayed");
		if (!lDriver.getTitle().contains("Ampion")){
			Assert.fail("Ampion page not loaded");
		}
	}


}