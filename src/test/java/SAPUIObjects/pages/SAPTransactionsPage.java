package SAPUIObjects.pages;

import SAPUIObjects.Utility.SAPConnection;
import SAPUIObjects.Utility.SAPCustomised;
import SAPUIObjects.pageObjects.MainPageObjects;
import SAPUIObjects.pageObjects.SE38InitialScreenObjects;
import com.jacob.activeX.ActiveXComponent;

public class SAPTransactionsPage extends SAPConnection implements MainPageObjects, SE38InitialScreenObjects {

    public SAPTransactionsPage() {
    }

    public void ExecutePgmOnSE38 (ActiveXComponent Session, String TCode, String Program) throws Exception {

        String id = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name",okcdName,"Type",okcdType);
        sapCustomised.SAPGUIOKCodeField(Session,id,TCode);
        sapCustomised.SAPGUISendEnter(Session);
        Thread.sleep(2000L);
        sapCustomised.SAPGUISendEnter(Session);
        Thread.sleep(2000);
        sapCustomised.SAPGUICTextFieldSendKeys( ProgramTextId, Program);
        sapCustomised.SAPGUIbtnClick(ExecuteProgramId);
        sapCustomised.SAPGUIbtnClick(ExecuteProgramId);
    }


}
