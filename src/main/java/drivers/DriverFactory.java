package drivers;

import static constants.Constants.*;

import helpers.MyException;

public class DriverFactory {

	private static DriverFactory singleInstance = null;

	private DriverFactory() {
	}

	/* <---------- Create/Return Single Ton Instance ---------> */
	public static DriverFactory getInstance() {
		if (singleInstance == null) {
			singleInstance = new DriverFactory();
		}
		return singleInstance;
	}

	/* <---------- Get Local/Remote Browser Session ---------> */
	/* Parameters : Local (Or) Remote */
	/* Parameter : Driver File Path for Local */
	/* Parameter : HUB URL for Remote */
	public IDriver getDriver(String driverType, String filePathOrHubUrl) throws MyException {
		if (!driverType.isEmpty()) {
			switch (driverType) {
			case LOCAL:
				return new LocalDriver(filePathOrHubUrl);
			case REMOTE:
				return new RemoteDriver(filePathOrHubUrl);
			default:
				throw new MyException("Driver Type : " + driverType + " Not Supported");
			}
		} else {
			throw new MyException("Driver Type Is Empty");
		}
	}

}