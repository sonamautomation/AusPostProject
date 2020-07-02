package SAPUIObjects.pages;

import SAPUIObjects.Utility.SAPConnection;
import SAPUIObjects.pageObjects.MainPageObjects;
import com.jacob.activeX.ActiveXComponent;

public class SAPMainPage extends SAPConnection implements MainPageObjects {

    public SAPMainPage() {
    }

    public void NavigateTreeAndSelectMenu(ActiveXComponent Session, String Node1, String Node2, String MenuToSelect) throws Exception {
        String id = sapGeneric.getSAPObjectIDHelperMethod(Session,"Type",TreeShellType,"SubType",TreeShellSubType);
        sapCustomised.SAPGUITreeExpandAndSelectNode(id,Node1,Node2,MenuToSelect);
        Thread.sleep(10000);
        id = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name",okcdName,"Type",okcdType);
        sapCustomised.SAPGUIOKCodeField(Session,id,"/n");
        sapCustomised.SAPGUISendEnter(Session);


}
}
