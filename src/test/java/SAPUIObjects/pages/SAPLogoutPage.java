package SAPUIObjects.pages;

import SAPUIObjects.Utility.SAPConnection;
import SAPUIObjects.pageObjects.LogoutPageObjects;
import SAPUIObjects.pageObjects.MainPageObjects;
import com.jacob.activeX.ActiveXComponent;

public class SAPLogoutPage extends SAPConnection implements MainPageObjects,LogoutPageObjects {


    public SAPLogoutPage() {
    }

    public void logOff(ActiveXComponent Session) throws Exception {
        Thread.sleep(2000);
        String id = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name",okcdName,"Type",okcdType);
        sapCustomised.SAPGUIOKCodeField(Session,id,"/n");
        sapCustomised.SAPGUISendEnter(Session);
        Thread.sleep(3000);
        sapGeneric.SAPGuiToolbarSelectMenuItem(MenuSystemName);
        sapGeneric.SAPGuiToolbarSelectMenuItem(MenuSystemLogoffName);
        sapCustomised.SAPGUIPopWinEnter(Session);
        String str1 = sapGeneric.getSAPObjectIDHelperMethod(Session, "Name", PopWinName, "", "");
        if (str1 != null) {
            Obj = new ActiveXComponent(Session.invoke("findById", PopWinYesOptId).toDispatch());
            Obj.invoke("press");
        }
    }


}
