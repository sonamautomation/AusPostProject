package testcases;

import SAPUIObjects.pages.SAPLogoutPage;
import SAPUIObjects.Utility.SAPConnection;
import SAPUIObjects.pages.SAPMainPage;
import SAPUIObjects.pages.SAPTransactionsPage;
import base.Base;
import SAPUIObjects.pages.SAPLoginPage;
import com.aventstack.extentreports.Status;
import com.jacob.activeX.ActiveXComponent;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


public class SAPGuiTest extends Base {

    @Test
	public void testSAPGuiExample(Method method) throws Exception
	{
         SAPLoginPage sapLoginPage = new SAPLoginPage();
         SAPMainPage sapMainPage = new SAPMainPage();
         SAPConnection sapConnection = new SAPConnection();
        SAPTransactionsPage sapTransactionPage = new SAPTransactionsPage();
        SAPLogoutPage sapLogout = new SAPLogoutPage();

        setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("SAP"));
        ActiveXComponent activeSession = sapConnection.getSapSessionObject();
        sapLoginPage.loginSAPGUI(activeSession,SAPUsername,SAPPassword);
        sapMainPage.NavigateTreeAndSelectMenu(activeSession,"Office","Folders","Inbox");

        getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO ACCESS SAP GUI ");

        sapTransactionPage.ExecutePgmOnSE38(activeSession,"se38","RSPARAM");
        getTestCase().log(Status.INFO, "PROGRAM EXECUTED SUCCESSFULLY");
        sapLogout.logOff(activeSession);
        getTestCase().log(Status.INFO,"SUCCESSFULLY LOGGED OUT FROM SAP SESSION");

	}

}
