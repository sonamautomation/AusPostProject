package pages;

import org.openqa.selenium.WebDriver;

import base.BasePage;
import helpers.MyException;
import pageobjects.DashboardPageObjects;

public class DashboardPage extends BasePage implements DashboardPageObjects {


	public DashboardPage(WebDriver driver) throws MyException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public BOEExtensionPage navigateToBoeExtension() throws MyException {
		clickOn(menu);
		clickOn(billManagement);
		clickOn(boeextension);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BOEExtensionPage(lDriver);
	}
	
	public BOEClosurePage navigateToBoeClosure() throws MyException {
		clickOn(menu);
		clickOn(billManagement);
		clickOn(boeClosure);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BOEClosurePage(lDriver);
	}
	// Navigate to ORM Master Page
		public OrmMasterPage navigateToOrmMaster() throws MyException {
			try {
				clickOn(TRRACS_MENU);
				clickOn(ORM_MENU);
				clickOn(ORM_MASTER);
			} catch (Exception e) {
				throw new MyException("Failed To Navigate ORM Master Screen");
			}
			return new OrmMasterPage(lDriver);
		}
}
