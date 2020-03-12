package helpers;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class SnapShot {

	private String lSnapShotsPath;
	private WebDriver lDriver;

	/* <---------- Constructor To Initialize SnapShot Instance ---------> */
	/* Parameters : Snap Shot Directory (Or) Driver Instance */
	public SnapShot(String snapShotsPath, WebDriver driver) throws MyException {
		if (!snapShotsPath.isEmpty()) {
			if (driver != null) {
				this.lSnapShotsPath = snapShotsPath;
				this.lDriver = driver;
			} else {
				throw new MyException("WebDriver Instance Seems To Be Null");
			}
		} else {
			throw new MyException("Snap Shots Directory Pat Is Empty");
		}
	}

	/* <---------- Save The Snap Shot With Desired Name ---------> */
	/* Parameters : File Name with png (or) jpeg Formats */
	public String saveAs(String fileName) throws IOException, MyException {
		if (!fileName.isEmpty()) {
			FileHandler.copy(sourceImage(), destinationImage(fileName));
		} else {
			throw new MyException("Snap Shot Name Is Empty");
		}
		return this.lSnapShotsPath + fileName;
	}

	/* <---------- Capture Source Image ---------> */
	private File sourceImage() throws MyException {
		File sImage;
		try {
			sImage = ((TakesScreenshot) this.lDriver).getScreenshotAs(OutputType.FILE);
		} catch (Exception e) {
			throw new MyException("Failed To Capture Source Image" + "\n" + e.getMessage());
		}
		return sImage;
	}

	/* <---------- Create Destination Image ---------> */
	private File destinationImage(String imageName) throws MyException {
		File dImage;
		try {
			dImage = new File(this.lSnapShotsPath + imageName);
		} catch (Exception e) {
			throw new MyException("Failed To Create Destination Image" + "\n" + e.getMessage());
		}
		return dImage;
	}

}