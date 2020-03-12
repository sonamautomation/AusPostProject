package checkpoints;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;

import helpers.MyException;

public class CheckPoint {

	/* <---------- Stores The Result Of Every Assert Statements ---------> */
	/* Key = Test Case Name */
	/* Value = Test Case Status PASS/FAIL */
	public static HashMap<String, String> testResults = new HashMap<String, String>();

	/* <---------- Constants ---------> */
	private static String Pass = "PASS";
	private static String Fail = "FAIL";

	/* <---------- Previous Test Case Result Cleared ---------> */
	public static void clearTestResults() {
		testResults.clear();
	}

	/* <---------- Result Map Created ---------> */
	private static void setTestResult(String key, String status) {
		testResults.put(key, status);
	}

	/* <---------- Records Every CheckPoint Results ---------> */
	/* Test Case Name */
	/* Test Case Status PASS/FAIL */
	/* Verification Message */
	public static void mark(String testCaseName, boolean status, String resultMessage) throws MyException {
		testCaseName = testCaseName.toLowerCase();
		String key = testCaseName + "." + resultMessage;
		try {
			if (status==true) {
				setTestResult(key, Pass);
			} else {
				setTestResult(key, Fail);
			}
		} catch (Exception e) {
			setTestResult(key, Fail);
			throw new MyException("Failed To Mark The Test Result" + "\n" + e.getMessage());
		}
	}

	/* <---------- Records Test Case Result ---------> */
	/* Test Case Name */
	/* Test Case Status PASS/FAIL */
	/* Verification Message */
	public static void markFinal(String testCaseName, boolean status, String resultMessage) throws MyException {
		mark(testCaseName, status, resultMessage);
		ArrayList<String> results = new ArrayList<String>();
		for (String k : testResults.keySet()) {
			results.add(testResults.get(k));
		}
		for (int i = 0; i < results.size(); i++) {
			if (results.contains(Fail)) {
				Assert.assertTrue(false);
			} else {
				Assert.assertTrue(true);
			}
		}
	}

}