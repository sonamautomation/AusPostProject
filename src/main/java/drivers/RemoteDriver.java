package drivers;

import static constants.Constants.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import helpers.MyException;

public class RemoteDriver implements IDriver {

	DesiredCapabilities cap;
	String remoteHUBURL;
	
	public RemoteDriver(String hubURL) throws MyException {
		if (!hubURL.isEmpty()) {
			this.remoteHUBURL = hubURL;
		} else {
			throw new MyException("Driver File Path Is Empty");
		}
	}

	/* <---------- Launching Remote Browser Session ---------> */
	/* Parameters : Chrome (Or) Firefox */
	@Override
	public WebDriver launch(String browserName) throws MyException, MalformedURLException {
		if (!browserName.isEmpty()) {
			switch (browserName) {
			case CHROME:
				cap = DesiredCapabilities.chrome();
				return new RemoteWebDriver(new URL(this.remoteHUBURL), cap);
			case FIREFOX:
				cap = DesiredCapabilities.firefox();
				return new RemoteWebDriver(new URL(this.remoteHUBURL), cap);
			default:
				throw new MyException("Remote Bowser : " + browserName + " Not Supported");
			}
		} else {
			throw new MyException("Browser Name Is Empty");
		}
	}

}