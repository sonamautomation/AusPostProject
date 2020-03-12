package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public interface DashboardPageObjects {

	 By menu = By.id("hamburger");
	 By billManagement = By.xpath("//a[text()='Bill Management']");
	 By boeextension = By.xpath("//span[text()='BOE Extension']");
	 By boeClosure = By.xpath("//span[text()='BOE Closure']");
	// ORM Management
		By TRRACS_MENU = By.id("hamburger");
		By ORM_MENU = By.xpath("//a[starts-with(text(),'ORM Management')]");
		By ORM_MASTER = By.xpath("//span[text()='ORM Master']");
		By ORM_CLOSURE = By.xpath("//span[text()='ORM Closure']");

}
