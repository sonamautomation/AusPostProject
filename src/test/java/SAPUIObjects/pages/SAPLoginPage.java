package SAPUIObjects.pages;

import SAPUIObjects.Utility.SAPConnection;
import SAPUIObjects.pageObjects.LoginPageObjects;
import com.jacob.activeX.ActiveXComponent;


public class SAPLoginPage extends SAPConnection implements LoginPageObjects {
    public SAPLoginPage(){}

    public void loginSAPGUI(ActiveXComponent Session,String UserNameValue, String PasswordValue) throws Exception {
        Thread.sleep(2000);
        sapGeneric.SAPGUITextFieldSet(UserName, UserNameValue);
        sapGeneric.SAPGUISetPasswordField(PasswordName, PasswordValue);
        sapGeneric.SAPGUIEnter();
        Thread.sleep(4000);
        String str2 = sapGeneric.getSAPObjectIDHelperMethod(Session, "Name", PopupWindowRadioButton2Name, "", "");
        if (str2 != null) {
            sapCustomised.SAPGUISelectOperationById(Session,str2, PopupWindowType);
            sapGeneric.SAPGUIEnter();
            Thread.sleep(2000);
        }

    }
}





/*
        String str = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name","RSYST-BNAME","Type","GuiTextField");
        arg = new Variant[2];
        arg[0] = new Variant(str);
        arg[1] = new Variant("GuiTextField");
        Obj = new ActiveXComponent(Session.invoke("FindById", arg).toDispatch());
        Obj.setProperty("text", UserNameText);
*/
        //Obj.setProperty("text", "biswalr");;     //   Obj = new ActiveXComponent(Session.invoke("FindById",
        //            "wnd[0]/usr/txtRSYST-BNAME").toDispatch());
        //   Obj.setProperty("Text", "biswalr");

        //   Obj = new ActiveXComponent(Session.invoke("FindById",
        //            "wnd[0]/usr/pwdRSYST-BCODE").toDispatch());
        //  Obj.setProperty("Text", "Welcome123");

/*        arg = new Variant[2];
        arg[0] = new Variant("RSYST-BCODE");
        arg[1] = new Variant("GuiPasswordField");
        Obj = new ActiveXComponent(win.invoke("findByName", arg).toDispatch());
        Obj.setProperty("text", "Welcome123");*/


/*        String str1 = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name","btn[0]","","");
        arg = new Variant[2];
        arg[0] = new Variant(str1);
        arg[1] = new Variant("GuiMainWindow");
        Obj = new ActiveXComponent(win.invoke("FindById", arg).toDispatch());
        Obj.invoke("press");*/
        //      Obj = new ActiveXComponent(Session.invoke("FindById",
        //             "wnd[0]").toDispatch());
        //    Obj.invoke("sendVKey", 0);

        //  sapGeneric.SAPGUIEnter();

       // Thread.sleep(4000);


        //Session.IsPopupDialog(
   /*     String str2 = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name","MULTI_LOGON_OPT2","","");
        if(str2!= null) {
            arg = new Variant[2];
            arg[0] = new Variant(str2);
            arg[1] = new Variant("GuiModalWindow");
            Obj = new ActiveXComponent(Session.invoke("FindById", arg).toDispatch());
            Obj.invoke("select");
            // sapGeneric.SAPGUIRadiobtnClick("MULTI_LOGON_OPT2");
            sapGeneric.SAPGUIEnter();

        }*/
        // Obj = new ActiveXComponent(Session.invoke("FindById",
        //         "wnd[1]/usr/radMULTI_LOGON_OPT2").toDispatch());
        //Obj.invoke("select");
        //  Obj = new ActiveXComponent(Session.invoke("FindById",
        //          "wnd[1]/tbar[0]/btn[0]").toDispatch());
        // Obj.invoke("press");
       // Thread.sleep(2000);


/*        Obj = new ActiveXComponent(Session.invoke("FindById",
                "wnd[0]/usr/cntlIMAGE_CONTAINER/shellcont/shell/shellcont[0]/shell").toDispatch());
        String text = Obj.getProperty("Type").toString();
        String id = sapGeneric.getSAPObjectIDHelperMethod(Session,"Type","GuiShell","SubType","Tree");
        // sapGeneric.SAPGUITreeExpandAndSelectNode1(id,"Favorites","Event Management");
        sapGeneric.SAPGUITreeExpandAndSelectNode(id,"Office","Folders","Inbox");
        Thread.sleep(2000);*/

   // }


