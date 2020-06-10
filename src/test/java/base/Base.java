package base;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import config.properties.ConfigHolder;
import drivers.DriverFactory;
import helpers.ExcelUtility;
import helpers.MyException;
import helpers.SnapShot;
import pages.BOEClosurePage;
import pages.BOEExtensionPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.OrmMasterPage;

public class Base {
	
	/* <---------- Page objects ---------> */
	protected LoginPage loginPage;
	protected DashboardPage dashboard;

	
	/* <---------- Log4j Instance ---------> */
	public static Logger Log;

	/* <---------- Configuration Properties Instance ---------> */
	public static ConfigHolder config;

	/* <---------- Driver Instances ---------> */
	protected DriverFactory dFactory;
	protected WebDriver webDriver;
	
	/*<--No instance required for Excel sheet reader since all methods are static. 
	 * Call methods with class name -->*/

	/* <---------- Property Variables ---------> */
	public static String typeOfBrowser;
	public static String nameOfBrowser;
	public static String driverFilesDirectory;
	public static String snapShotsDirectory;
	public static String hubURL;
	public static String container;

	public static ThreadLocal<WebDriver> browser = new ThreadLocal<WebDriver>();

	/* <---------- SnapShot Instance ---------> */
	
	public static ThreadLocal<SnapShot> snap=new ThreadLocal<SnapShot>();
	
	/* <---------- ExcelUtility Instance ---------> */
	public static ExcelUtility excelReader;

	/* <---------- Extent report Instance ---------> */
	private static String customValue;
	protected ExtentHtmlReporter htmlReporter;
	
	protected static  ExtentReports extentReport ;
	protected static ThreadLocal<ExtentTest> parentTestCase = new ThreadLocal<>();
	protected static ThreadLocal<ExtentTest> testCase = new ThreadLocal<>();
	
	File file;

	/* <---------- Set Current Date ---------> */
	protected void setDate() {
		Date lDate = new Date();
		System.setProperty("date", lDate.toString().replace(":", "_").replace(" ", "_"));
	}

	protected void setLog4j(String projName) {
		/* <---------- Initializing Log4j ---------> */
		Log = LogManager.getLogger(projName);
	}

	protected void gatherConfigProperties() throws Exception {
		Log.info("Gathering Configuration Properties...");

		/* <---------- ConfigHolder Singleton Instance ---------> */
		config = ConfigHolder.getInstance();
		/* <---------- User Properties ---------> */
		typeOfBrowser = config.properties.get("browserType");
		nameOfBrowser = config.properties.get("browserName");
		driverFilesDirectory = config.properties.get("driverFilesPath");
		snapShotsDirectory = config.properties.get("snapShotsPath");
		hubURL = config.properties.get("hubURL");
		container =  config.properties.get("containerOn");
		Log.info("Successfully Gathered Configuration Properties...");
	}
	
	public String getHubURL(String browserType,String driverPathOrHubURL) {
		String url = driverPathOrHubURL;
		if(browserType.equalsIgnoreCase("REMOTE")) {
			url = hubURL;
		}
		return url;
	}

	protected void trigger(String browserType, String browserName, String driverPathOrHubURL)
			throws MalformedURLException, MyException, InterruptedException {
		Log.info("Initialization Of Driver Factory Begins...");

		/* <---------- DriverFactory Singleton Instance ---------> */
		dFactory = DriverFactory.getInstance();
		/* <---------- Launch Local (Or) Remote Browser Session ---------> */
		driverPathOrHubURL =getHubURL(browserType, driverPathOrHubURL);
		webDriver = dFactory.getDriver(browserType, driverPathOrHubURL).launch(browserName);
		setBrowser(webDriver);
		getBrowser().manage().window().maximize();
		snap.set(new SnapShot(snapShotsDirectory, getBrowser()));	
		Log.info("Successfully Launched " + browserType + ":" + browserName);
	}
	
	protected void initializeTestReport() throws MyException {
		
		customValue=config.properties.get("reportName") + "_" + getdate();
		/* <---------- Creating extent report in specified location ---------> */
		htmlReporter = new ExtentHtmlReporter(config.properties.get("reportPath")
				+ customValue + ".html");
		htmlReporter.config().setReportName(config.properties.get("reportDocumentName"));
		htmlReporter.config().setTheme(Theme.DARK);
		
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);
	}
	
	public String getProjectPath() {
		return System.getProperty("user.dir");
	}


	public static String getdate() {
		String dateName = new SimpleDateFormat("ddMMyyyy-HH.mm.ss").format(new Date());
		return dateName;
	}
	
	private void setBrowser(WebDriver driver) {
		browser.set(driver);		
		Log.info("Thread Safe WebDriver Instance is Set...");
	}

	public WebDriver getBrowser() {
		return browser.get();
		
	}
	

	public ExtentTest getParentTestCase() {
		return parentTestCase.get();
	}

	public void setParentTestCase(ExtentTest parentTest) {
		parentTestCase.set(parentTest); 
	}

	public ExtentTest getTestCase() {
		return testCase.get();
	}

	public void setTestCase(ExtentTest test) {
		testCase.set(test);
	}
	
	@BeforeTest
	@Parameters("browser")
	public void before_Tests(String browser) throws MyException {
		try {
			try {
				try {
					trigger(typeOfBrowser, browser, driverFilesDirectory);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				loginPage = new LoginPage(getBrowser());
			} catch (MyException e) {
				Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
				e.getMessage();
			}
		} catch (MalformedURLException e){
			Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
			e.getMessage();
		}	
	}

}