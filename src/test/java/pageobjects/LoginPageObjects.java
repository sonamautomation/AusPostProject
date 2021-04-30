package pageobjects;

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
	By aboutUs = By.xpath("//span[text()='About Us']");
	By revITLnk = By.xpath("//a[@title='Revolution IT']");
	By revAboutUs = By.xpath("//h2[text()='About Revolution IT']");
	By revITCareers=By.xpath("//span[contains(text(),'Careers')]");
	By jobSearchCat=By.cssSelector("#ja-def-2525");
	By jobSearchsubCat=By.cssSelector("#ja-def-2526");
	By jobSearchloc=By.cssSelector("#ja-def-2527");
	By workType=By.cssSelector("#ja-def-2528");
	By keywords=By.cssSelector("#ja-keywords");
	By searchButton=By.className("ja-button");
	By jobRole=By.linkText("Senior Test Manager - Global Financial Institution");

	By navigateRes=By.id("nav-2");
	By enterAddress=By.xpath("//input[@placeholder='Type your address']");
	By navigateNBNco=By.id("nav-6");
	By nbnCareers=By.linkText("Careers");
	By jobs=By.xpath("(//*[contains(text(),'Browse current job openings')])[last()]");
	By searchForJobs=By.xpath("//*[contains(text(),'Search for Jobs')]");
	By searchInput=By.xpath("//input[@name='q']");
	By searchAmpion=By.xpath("//a[@href='https://www.ampion.com.au/']");
	By titleAmpion=By.xpath("(//*[@alt='ampion logo'])[2]");





}