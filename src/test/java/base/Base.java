package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.properties.ConfigHolder;
import drivers.DriverFactory;
import helpers.ExcelUtility;
import helpers.MyException;
import helpers.PasswordEncryptDecrypt;
import helpers.SnapShot;
import messageObjects.MessageConstruct;
import messageObjects.ZephyrJiraMessaging;
import messageObjects.MessageFactory;
import messageObjects.NewAnnotation;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import pages.DashboardPage;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Base {


	/* <---------- Page objects ---------> */
	protected LoginPage loginPage;
	protected DashboardPage dashboard;


	/* <---------- Message objects ---------> */
	protected static MessageFactory messageFactory;
	protected static MessageConstruct messageConstruct;

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
	public static String hostUserName;
	public static String hostPassword;
	public static String encryptPswd;
	public static String uri;
	public static String xmlUri;
	public static String[] header;
	public static String headerKey;
	public static String headerValue;
	public static Boolean runApi;
	public static String encodedAuth;
	public static String tibcoUserId, tibcoPassword, tibcoQueue, tibcoURL;
	public static String SAPUsername;
	public static String SAPPassword;
	public static String JacobDLLFile;
	public static String appURL;
	public static String zephyrBaseUrl;
	public static String accessKey;
	public static String secretKey;
	public static String apiToken;
	public static String accountId;
	//public static String projectId;
	public static String projectKey;
	public static String cycleId;
	public static String versionId;
	public static String issueTypeId;
	public static String jiraBaseURL;
	public static String jiraUserName;
	public static String testcaseName;
	public static int rowExecuted;
	public static String mountebankStubAPI;
	public static String mountebankStubServer;
	public static String mountebankStubServerDomain;
	public static String mountebankStubConfigPath;

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
		PasswordEncryptDecrypt passwordEncryptDecrypt= new PasswordEncryptDecrypt();
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
		hostUserName = config.properties.get("hostUserName");
		encryptPswd = config.properties.get("hostPassword");
		appURL = config.properties.get("appURL");
		hostPassword = passwordEncryptDecrypt.decrypt(encryptPswd);
		uri = config.properties.get("hostURI");
		xmlUri = config.properties.get("xmlHostURI");
		encodedAuth = config.properties.get("encodedAuth");
		header =  config.properties.get("hostHeader").split(":");
		headerKey = header[0];
		headerValue = header[1];
		runApi=Boolean.valueOf(config.properties.get("runApiTests"));
		tibcoUserId=config.properties.get("PUB_USER_ID"); 
		tibcoPassword=config.properties.get("PUB_PASS"); 
		tibcoQueue=config.properties.get("TIBCO_QUEUE_NAME");
		tibcoURL=config.properties.get("TIBCO_URL");
		SAPUsername = config.properties.get("SAPUserName");
		JacobDLLFile = config.properties.get("JacobDLLFile");
		SAPPassword = config.properties.get("SAPPassword");
		zephyrBaseUrl = config.properties.get("zephyrBaseUrl");
		accessKey = config.properties.get("accessKey");
		secretKey = config.properties.get("secretKey");
		apiToken = config.properties.get("apiToken");
		accountId = config.properties.get("userAccountId");
		projectKey = config.properties.get("jiraProjectKey");
		cycleId = config.properties.get("executionCycleId");
		versionId = config.properties.get("executionVersionId");
		issueTypeId = config.properties.get("jiraIssueTypeId");
		jiraBaseURL = config.properties.get("jiraBaseURL");
		jiraUserName = config.properties.get("jiraUserName");
		mountebankStubAPI = config.properties.get("mountebankStubAPI");
		mountebankStubServer = config.properties.get("mountebankStubServer");
		mountebankStubServerDomain = config.properties.get("mountebankStubServerDomain");
		mountebankStubConfigPath = config.properties.get("stubsConfigPath");

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
			if(!runApi) {
				try {
					try {
						trigger(typeOfBrowser, browser, driverFilesDirectory);
						getBrowser().get(appURL);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					loginPage = new LoginPage(getBrowser());
				} catch (MyException e) {
					Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
					e.getMessage();
				}
			}
		} catch (MalformedURLException e){
			Log.error("Failed To Trigger Browser Session" + "\n" + e.getMessage());
			e.getMessage();
		}	
	}
	public  void copyFileUsingJava7Files(File source, File dest) throws IOException {
		String name = source.getName();
		File targetFile = new File(dest+"/"+name);
		FileUtils.copyFile(source, targetFile);
	}


	public String searchPath(String s) throws NoSuchFieldException, IllegalAccessException {
		String str= null;
		Field field = ClassLoader.class.getDeclaredField("usr_paths");
		field.setAccessible(true);
		String[] paths = (String[]) field.get(null);
		for (int i = 0; i < paths.length; i++) {
			if (paths[i].contains(s)) {
				str = paths[i];
				break;
			}
		}
		return str;
	}

	public void pasteDLLtoMavenFolder() throws NoSuchFieldException, IllegalAccessException, IOException {
		String filePath = Paths.get("resources", "executables", "lib", JacobDLLFile).toAbsolutePath().toString();
		File src = new File(filePath);
		File dest = new File(searchPath("Maven"));
		copyFileUsingJava7Files(src, dest);
	}

	@AfterMethod
	public void after_Method() {
		//Update Jira and zephyr with test results.
		ZephyrJiraActivity();
	}


	public int getStatus(String status) {
		switch (status.toUpperCase()) {
		case "PASS":
			return 1;
		case "FAIL":
			return 2;
		case "WIP":
			return 3;
		default:
			return -1;
		}
	}

	public void ZephyrJiraActivity(){
		String testID= null;

		//fetch test method name for the first time.
		for (Method method:getClass().getDeclaredMethods()) {
			if (method.getName().contains(getTestCase().getModel().getName())) {
				if (testcaseName==null){
					testcaseName = method.getName();
				}
			}
		}

		// Fetch Test case Id from Annotation Parameters if it exists.
		for (Method method:getClass().getDeclaredMethods()) {
			if (method.getName().contains(getTestCase().getModel().getName())) {
				if (method.isAnnotationPresent(NewAnnotation.TestParameters.class)) {
					NewAnnotation.TestParameters useAsTestName = method.getAnnotation(NewAnnotation.TestParameters.class);
					testID = useAsTestName.testId();
					System.out.println("Test Case ID = " + testID);
					break;
				}
			}
		}

		// Fetch which data row is being executed.Also update method name.
		for (Method method:getClass().getDeclaredMethods()) {
			if (method.getName().contains(getTestCase().getModel().getName())) {
				if (testcaseName.contentEquals(getTestCase().getModel().getName())){
					rowExecuted = rowExecuted+1;
				}else{
					testcaseName = getTestCase().getModel().getName();
					rowExecuted = 0;
					rowExecuted = rowExecuted+1;
				}
			}
		}
		System.out.println("Row ID = " + rowExecuted);

	
			try {
				ZephyrJiraMessaging.updateResultsToZephyr(getTestCase().getModel().getName(), testID, getStatus(getTestCase().getStatus().toString()), rowExecuted);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@BeforeClass
	public void before_Class() {
		rowExecuted = 0;
		testcaseName = null;
	}

}