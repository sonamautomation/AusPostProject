package testcases;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;
import base.Base;
import dataprovider.MyDataProvider;

import static base.BasePage.jdbcMultiDBManager;


public class JDBCTest extends Base {
    @Test(dataProvider = "JDBCDataProvider", dataProviderClass = MyDataProvider.class)
    public void JDBCInsertQuery(Map<String, String> testData, Method method) throws SQLException {
        setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("Test"));
        List<String> columnNames = new ArrayList<String>();
        List<String> columnValues = new ArrayList<String>();
        String insertColumns = " ";
        String insertValues = " ";
        String inVal = " ";


        for (String key : testData.keySet()) {
            columnNames.add(key);
            columnValues.add(testData.get(key));
            System.out.println(columnNames);
        }
        if (columnNames != null && columnNames.size() > 0) {
            insertColumns += columnNames.toString().replace("[", "").replace("]", "");
            insertValues += columnValues.toString();
            inVal = "'" + insertValues.replace("[", "").replace("]", "").replace(" ", "").replace(",", "','") + "'";
            System.out.println(inVal);
        }
        //This databaseType should be as per DBUtil class- MYSQLDB or ORACLEDB or POSTGRESQL//
        jdbcMultiDBManager.establishConnection("POSTGRESQL");

        //This method accepts the insertColumns,inVal params from dataprovider//
        jdbcMultiDBManager.insertDataintoDBNew("signupdata", insertColumns, inVal);

    }

    @Test
    public void testSample2(Method method) throws SQLException {
        setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("Test"));
        jdbcMultiDBManager.establishConnection("POSTGRESQL");
        jdbcMultiDBManager.insertDataintoDB("INSERT INTO signupdata (firstname, lastname, mobile, email) Values ('test6', 'test6', '1234567891', 'test6@gmail.com')");
        jdbcMultiDBManager.updateDataintoDB("signupdata", "firstname", "somename", "firstname", "test1");
        jdbcMultiDBManager.fetchDatafromDB("signupdata");
        jdbcMultiDBManager.checkandCreateTableinDB("signupdata");
        jdbcMultiDBManager.closeConnection();

        getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO ESTABLISH THE CONNECTION ");



    }
}


