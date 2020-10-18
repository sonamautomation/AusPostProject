package base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import helpers.DatabaseManager.JDBCMultiDBManager;
import helpers.DatabaseManager.MongoDBManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import helpers.*;




public class BasePage {

	protected WebDriver lDriver;
	protected WebDriverWait myWait;
	public static MongoDBManager mongoDBManager;
	public static JDBCMultiDBManager jdbcMultiDBManager;
	JavascriptExecutor js;
	public static boolean smartObjHandler = false;
	public enum LocType{id,name,tagName , className,value,title,type};
	public enum LocatorEntry{locatorValue,locatorType,url};

	protected String alphaNumeric_Without_Specialchar = "[a-zA-Z0-9]+";
	protected String numbers = "[0-9]+";

	public BasePage(WebDriver driver) throws MyException {
		if (driver != null) {
			lDriver = driver;
			myWait = new WebDriverWait(driver, 10);
			if(smartObjHandler) {
				mongoDBManager = new MongoDBManager();
			}
		} else {
			throw new MyException("Browser Instance Is Null");
		}
	}


	// Find element
	protected WebElement identify(By locator) throws MyException {
		WebElement element = null;
		HashMap<String, String> locatorEntry = null ;
		try {
			element = lDriver.findElement(locator);
			if(smartObjHandler) {
				locatorEntry = addOrUpdateDBLocatorEntry(element,locator);
			}
		}catch(NoSuchElementException exp) {
			if(smartObjHandler) {
				element = getSmartLocator(locatorEntry, exp);
			}else {
				throw new MyException("Failed To Identify The Element:" + locator);
			}

		}
		catch (Exception e) {
			throw new MyException("Failed To Identify The Element:" + locator);
		}
		return element;
	}

	// Indentify list of elements
	protected List<WebElement> identifyAll(By locator) throws MyException {
		List<WebElement> list = null;
		try {
			list = lDriver.findElements(locator);
		} catch (Exception e) {
			throw new MyException("Failed To Identify The Element Set:" + locator);
		}
		return list;
	}

	protected String getPageTitle() throws MyException {
		String pageTitle = "";
		try {
			pageTitle = lDriver.getTitle();
		} catch (Exception e) {
			throw new MyException("Failed To Fetch Page Title");
		}
		return pageTitle;
	}

	protected String getURL() throws MyException {
		String url = "";
		try {
			url = lDriver.getCurrentUrl();
		} catch (Exception e) {
			throw new MyException("Failed To Fetch Current URL");
		}
		return url;
	}

	protected void navigateBrowserBack() throws MyException {
		try {
			lDriver.navigate().back();
		} catch (Exception e) {
			throw new MyException("Failed To Navigate Back In Current Browser Session");
		}
	}

	protected void navigateBrowserForward() throws MyException {
		try {
			lDriver.navigate().forward();
		} catch (Exception e) {
			throw new MyException("Failed To Navigate Forward In Current Browser Session");
		}
	}

	protected void refresh() throws MyException {
		try {
			lDriver.navigate().refresh();
		} catch (Exception e) {
			throw new MyException("Failed To Refresh Current Browser Session");
		}
	}

	protected boolean isElementPresent(By locator) throws MyException {
		List<WebElement> list = null;
		try {
			list = identifyAll(locator);
			int size = list.size();
			if (size > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new MyException("Failed To Verify Presence Of Element:" + locator);
		}
	}

	protected boolean check(By locator, String verificationType) throws MyException {
		try {
			myWait.until(ExpectedConditions.elementToBeClickable(locator));
			switch (verificationType) {
			case "IsDisplayed":
				identify(locator).isDisplayed();
				return true;
			case "IsEnabled":
				identify(locator).isEnabled();
				return true;
			case "IsSelected":
				identify(locator).isSelected();
				return true;
			default:
				throw new MyException("Invalid Verification Type");
			}		
		} catch (Exception e) {
			throw new MyException("Failed To Check Whether " + locator + " " + verificationType);
		}
	}

	protected void choose(String choiceType, String value, By locator) throws MyException {
		Select dropDown;
		try {
			dropDown = new Select(identify(locator));
			switch (choiceType) {
			case "text":
				dropDown.selectByVisibleText(value);
				break;
			case "value":
				dropDown.selectByValue(value);
				break;
			case "index":
				dropDown.selectByIndex(Integer.parseInt(value));
				break;
			default:
				throw new MyException("Invalid Verification Type");
			}
		} catch (Exception e) {
			throw new MyException("Failed To Choose " + choiceType + ":" + value + " From:" + locator);
		}
	}

	// click
	protected void clickOn(By locator) throws MyException {
		try {
			myWait.until(ExpectedConditions.elementToBeClickable(locator));
			identify(locator).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new MyException("Failed To Click On:" + locator);
		}
	}

	// Enter value to the field
	protected void type(String text, By locator) throws MyException {
		try {
			identify(locator).clear();
			identify(locator).sendKeys(text);
		} catch (Exception e) {
			throw new MyException("Failed To Type:" + text + " Inside:" + locator);
		}
	}



	public String getElementAttributeValue(By locator, String attribute) throws MyException {
		String value = "";
		try {
			value = identify(locator).getAttribute(attribute);
		} catch (Exception e) {
			throw new MyException("Failed To Get The Value Of Attribute: " + attribute + " From:" + locator);
		}
		return value;
	}

	protected String getTextFrom(By locator) throws MyException {
		String text = null;
		try {
			text = identify(locator).getText();
			if (text.length() == 0) {
				text = getElementAttributeValue(locator, "innerText");
			}
			if (!text.isEmpty()) {
				text = text.trim();
			} else {
				throw new MyException("Text Not Found");
			}
		} catch (Exception e) {
			throw new MyException("Failed To Get The Text From: " + locator);
		}
		return text;
	}

	/*******************************************************************************************************************/
	public boolean selectByWithText(By classname, String data) throws Exception {
		boolean bReturn = false;
		try {
			Select dropdown = new Select(lDriver.findElement(classname));
			dropdown.selectByVisibleText(data);
			bReturn = true;
		} catch (Exception e) {

		}
		return bReturn;
	}

	public void webcalendar(String dateValue) throws Exception {
		try {
			if (dateValue != null && !dateValue.trim().isEmpty()
					&& dateValue.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date date1 = sdf.parse(dateValue);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date1);
				// explicitWait(yearXpath);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				selectByWithText(By.className("ui-datepicker-year"), year);
				// System.out.println(calendar.get(Calendar.YEAR));
				Thread.sleep(3000);
				// explicitWait(monthXpath);
				String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).substring(0,
						3);
				selectByWithText(By.className("ui-datepicker-month"), month);

				// explicitWait(dateXpath);
				Thread.sleep(3000);
				List<WebElement> dates = lDriver
						.findElements(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody//td"));
				int total = dates.size();
				// System.out.println(total);
				for (int i = 0; i < total; i++) {
					String date = dates.get(i).getText();

					if (date.equalsIgnoreCase(String.valueOf(calendar.get(Calendar.DATE)))) {
						// logger.info("In date click");
						dates.get(i).click();
						break;
					}
				}
			} else {

				// logger.error("Invalid date : " + " " + dateValue);
			}

		} catch (Exception e) {
			// take_screenshot();
			// logger.error("exception occured in webcalendar selection" + " " +
			// e.getMessage());
		}
	}

	public void selectDropdownValue(By element, String value) throws InterruptedException {
		String lvalue = value.substring(0).toLowerCase();
		Actions action = new Actions(lDriver);
		action.click(lDriver.findElement(element)).build().perform();
		Thread.sleep(2000);
		action.sendKeys(lvalue).build().perform();
		action.sendKeys(Keys.RETURN).build().perform();
		Thread.sleep(2000);
	}

	// Upload file
	public void upload(By elementBy, String filename) {
		File file = new File(filename);
		lDriver.findElement(elementBy).sendKeys(file.getAbsolutePath());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// wait until ajax load(Just when load image is displayed)
	public void waitUntilLoad() {
		myWait.until(ExpectedConditions
				.invisibilityOf(lDriver.findElement(By.xpath("//div[@id='ajaxLoading']/descendant::img"))));
	}

	/*********************************************************************************************************************/
	// Get text by value attribute
	protected String getTextByValueAttribute(By locator) throws MyException {
		try {
			return identify(locator).getAttribute("value");
		} catch (Exception e) {
			throw new MyException("Failed To Read Text");
		}
	}

	// Clear
	protected void clearText(By locator) throws MyException {
		try {
			identify(locator).clear();
		} catch (Exception e) {
			throw new MyException("Failed To Clear Text");
		}
	}

	// Wait for an element visibility
	protected void waitForElementVisibility(WebElement element) throws MyException {
		try {
			myWait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			throw new MyException("Failed To Wait For an Element Visibility");
		}
	}

	// Validation of characters
	protected void validateCharacters(By locator, String text) throws MyException {
		try {
			String value = getTextByValueAttribute(locator);
			if (value.matches(alphaNumeric_Without_Specialchar))
				System.out.println("Alphanumeric characters are allowed");
			else
				System.out.println("Special characters are not allowed in " + text);
		} catch (Exception e) {
			throw new MyException("Failed To Validate Alphanumeric Chararcters");
		}
	}

	// Validation for numbers
	protected void validateNumbers(By locator, String text) throws MyException {
		try {
			String value = getTextByValueAttribute(locator);
			if (value.matches(numbers))
				System.out.println("Only numbers are allowed");
			else
				System.out.println("Alphabets and Special characters are not allowed in " + text);
		} catch (Exception e) {
			throw new MyException("Failed To Validate Numbers");
		}
	}

	// Find the length
	protected void findElementLength(By locator, int actualLength, String text) throws MyException {
		try {
			String value = getTextByValueAttribute(locator);
			int size = value.length();
			if (size <= actualLength)
				System.out.println("Length of " + text + " is verified");
			else
				System.out.println(text + " should be less than or equal to " + actualLength + " characters");
		} catch (Exception e) {
			throw new MyException("Failed To Find Element Length");

		}
	}

	public String getLocator(LocatorEntry entry) {
		return entry.toString();
	}


	private void addOrUpdateDBLocatorEntry(HashMap<String,String> locatorEntry,WebElement element)
	{
		//store the fields in Database as in format id:123#name:north#tagName:tagValue#className:classValue#value:revive#Title:mytitle
		//URL  LocatorVal LocatorType LocatorAttributes

		String locatorAttr="";

		if (element.getAttribute("id") !=null && !(element.getAttribute("id").isEmpty()))
			locatorAttr = locatorAttr + "id:" + element.getAttribute("id") + "#";
		if (element.getAttribute("name") !=null && !(element.getAttribute("name").isEmpty()))
			locatorAttr= locatorAttr + "name:"+element.getAttribute("name") + "#";
		if (element.getAttribute("tagName") !=null && !(element.getAttribute("tagName").isEmpty()))
			locatorAttr= locatorAttr + "tagName:"+element.getAttribute("tagName") + "#";
		if (element.getAttribute("className") !=null && !(element.getAttribute("className").isEmpty()))
			locatorAttr= locatorAttr + "className:"+element.getAttribute("className") + "#";
		if(element.getAttribute("value") != null && !(element.getAttribute("value").isEmpty()))
			locatorAttr= locatorAttr + "value:"+element.getAttribute("value") + "#";
		if (element.getAttribute("title") !=null && !(element.getAttribute("title").isEmpty()))
			locatorAttr= locatorAttr + "title:"+element.getAttribute("title") + "#";
		if (element.getAttribute("type") !=null && !(element.getAttribute("type").isEmpty()))
			locatorAttr= locatorAttr + "type:"+element.getAttribute("type");

		MongoDBManager.createOrUpdateDBRecord(locatorEntry.get(getLocator(LocatorEntry.url)),locatorEntry.get(getLocator(LocatorEntry.locatorValue)),locatorEntry.get(getLocator(LocatorEntry.locatorType)),locatorAttr);

	} 



	private String getDBLocatorEntry(HashMap<String,String> locatorEntry)
	{
		//String locatorAttr = "id:text######type:text#";
		return MongoDBManager.getDBRecord(locatorEntry.get(getLocator(LocatorEntry.url)),locatorEntry.get(getLocator(LocatorEntry.locatorValue)),locatorEntry.get(getLocator(LocatorEntry.locatorType)));
	}

	private String createXpathFromLocatorAttr(String locatorType,String locatorValue)
	{
		String locatorVal =null;
		LocType locType = LocType.valueOf(locatorType);
		System.out.println("Value of locType " + locType);
		switch(locType)
		{
		case id:
			locatorVal  = "//*[@id='" + locatorValue + "']";
			break;
		case name:
			locatorVal  = "//*[@name='" + locatorValue + "']";
			break;
		case tagName:
			locatorVal  = "//*[@tagName='" + locatorValue + "']";
			break;
		case className:
			locatorVal  = "//*[@class='" + locatorValue + "']";
			break;
		case value:
			locatorVal  = "//*[@value='" + locatorValue + "']";
			break;
		case title:
			locatorVal  = "//*[@title='" + locatorValue + "']";
			break;
		case type:
			locatorVal  = "//*[@type='" + locatorValue + "']";
			break;
		default:
			System.out.println("No valid locator value found in DB :");

		}
		return locatorVal;
	}


	public WebElement getSmartLocator(HashMap<String,String> locatorEntry, NoSuchElementException exp) {
		WebElement element = null;
		System.out.println("Element with LocatorType   " + locatorEntry.get(getLocator(LocatorEntry.locatorType)) + " and LocatorValue  " + locatorEntry.get(getLocator(LocatorEntry.locatorValue)) + " not found in DOM");

		//Get the LocatorAttr from Database based upon URLValue, locatorType and locatorValue
		//Tokenize the String to fetch the attributes of the WebElement

		String locatorAttr = getDBLocatorEntry(locatorEntry);
		if(locatorAttr == null || locatorAttr.isEmpty()) {
			System.out.println("No DB record found and hence unable to resolve the issue ");
			exp.printStackTrace();
			return null;
		}

		StringTokenizer st = new StringTokenizer(locatorAttr,"#");
		//Search by other fields retreived from the Database looping over all available fields in DB

		for (int i=0; i < st.countTokens(); i++) 
		{ 
			String[] locatorKeyValue  = st.nextToken().split(":");
			String DBLocatorKey = locatorKeyValue[0];
			String DBLocatorValue = locatorKeyValue[1];

			//System.out.println("DBLocatorKey " + DBLocatorKey + "DBLocatorValue  " + DBLocatorValue);
			if (DBLocatorValue.trim().length() > 0 && !DBLocatorKey.equals( locatorEntry.get(getLocator(LocatorEntry.locatorType))) )
			{
				//Need to search with the locator Value from Database

				String locatorVal = createXpathFromLocatorAttr(DBLocatorKey,DBLocatorValue);
				try
				{
					element  = lDriver.findElement(By.xpath(locatorVal));

					System.out.println("Found the element in second chance");

					//Update the new fields in Database as in format tagName#className#text
					addOrUpdateDBLocatorEntry(locatorEntry,element);
					break;

				}
				catch(NoSuchElementException e)
				{
					e.printStackTrace();
					//Need to retry with next available attr from DB
				}


			}else {
				exp.printStackTrace();
			}
		}

		return element;

	}


	public HashMap<String,String> addOrUpdateDBLocatorEntry(WebElement e,By by) {

		//Check which class the by is instanceof 
		//Eg. by.toString() displays By.className: jhp
		HashMap<String, String> locatorEntry = new HashMap<String, String>() ;
		String locatorValue =null;
		String locatorType = null;
		String url = null;
		int index =  by.toString().indexOf(":");
		int locatorValLength = by.toString().trim().length();
		locatorType =by.toString().substring(0,index-1);
		locatorEntry.put(getLocator(LocatorEntry.locatorType), locatorType);		
		locatorValue = by.toString().substring(index+1,locatorValLength );
		locatorEntry.put(getLocator(LocatorEntry.locatorValue), locatorValue);		
		String[] URLStr = lDriver.getCurrentUrl().split("://");
		//Remove the domain name from the URL
		url = URLStr[1];
		locatorEntry.put(getLocator(LocatorEntry.url), url);	
		addOrUpdateDBLocatorEntry(locatorEntry,e);

		return locatorEntry;

	}




}