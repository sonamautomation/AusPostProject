package drivers;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import helpers.MyException;

public interface IDriver {

	WebDriver launch(String browserName) throws MyException, MalformedURLException;

}