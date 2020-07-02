package SAPUIObjects.pages;

import SAPUIObjects.Utility.SAPGeneric;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;

public class SAPUISele {

    //-Set SapGuiAuto = GetObject("SAPGUI")---------------------------
    /*
     * Declaring the ActiveXComponent Objects
     */
    ActiveXComponent SAPROTWr, GUIApp, Connection, Session, Obj,win, SAPGIRD;
    Dispatch ROTEntry;
    Variant Value, ScriptEngine, arg[];
    Process p;
    public boolean IsPopupDialog;
    SAPGeneric sapGeneric;

    public SAPUISele() {
    }

    public  void main(String[] args) throws Exception {
        SAPUISele classobj = new SAPUISele();
        classobj.getSapSessionObject();
        classobj.loginSAPGUI();
      //  classobj.OpenTransaction();
     //   classobj.getCellValue();
      //  classobj.logOff();
    }

    /*
     * Opens the SAP Session and returns the Active Session Object
     */
/*public String searchPath(String s) throws NoSuchFieldException, IllegalAccessException {
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
}*/

/*    public  void copyFileUsingJava7Files(File source, File dest) throws IOException {
        String name = source.getName();
        File targetFile = new File(dest+"/"+name);
        FileUtils.copyFile(source, targetFile);
    }*/

    public void getSapSessionObject() throws InterruptedException {
        String cnt = "0";

        ComThread.InitSTA();

        //Opening the SAP Logon
        try {
            p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\SAPgui.exe hxaix48 00");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Thread.sleep(8000);
        SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");

        ROTEntry = SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();
        //-Set application = SapGuiAuto.GetScriptingEngine------------
        ScriptEngine = Dispatch.call(ROTEntry, "GetScriptingEngine");



        GUIApp = new ActiveXComponent(ScriptEngine.toDispatch());
        Thread.sleep(5000);

        Connection = new ActiveXComponent(GUIApp.invoke("Children",0).toDispatch());

        //-Set session = connection.Children(0)-----------------------

        Session = new ActiveXComponent(
                Connection.invoke("Children", 0).toDispatch());
        //-Set win = session.ActiveWindow()-------------------------------
        win = new ActiveXComponent(
                Session.invoke("ActiveWindow").toDispatch()
        );
        //Initialization for the SAPGeneric

        sapGeneric = new SAPGeneric(Connection);

        sapGeneric.setSession(new ActiveXComponent(sapGeneric.getSession().invoke("FindById", "wnd[0]").toDispatch()));

    }

    /*
     * Logging to SAP GUI
     */

    public void loginSAPGUI() throws Exception {
        Thread.sleep(2000);
        String str = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name","RSYST-BNAME","Type","GuiTextField");
        arg = new Variant[2];
        arg[0] = new Variant(str);
        arg[1] = new Variant("GuiTextField");
        Obj = new ActiveXComponent(Session.invoke("FindById", arg).toDispatch());
        Obj.setProperty("text", "biswalr");

        //Obj.setProperty("text", "biswalr");;     //   Obj = new ActiveXComponent(Session.invoke("FindById",
        //            "wnd[0]/usr/txtRSYST-BNAME").toDispatch());
        //   Obj.setProperty("Text", "biswalr");

        //   Obj = new ActiveXComponent(Session.invoke("FindById",
        //            "wnd[0]/usr/pwdRSYST-BCODE").toDispatch());
        //  Obj.setProperty("Text", "Welcome123");

        arg = new Variant[2];
        arg[0] = new Variant("RSYST-BCODE");
        arg[1] = new Variant("GuiPasswordField");
        Obj = new ActiveXComponent(win.invoke("findByName", arg).toDispatch());
        Obj.setProperty("text", "Welcome123");


        String str1 = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name","btn[0]","","");
        arg = new Variant[2];
        arg[0] = new Variant(str1);
        arg[1] = new Variant("GuiMainWindow");
        Obj = new ActiveXComponent(win.invoke("FindById", arg).toDispatch());
        Obj.invoke("press");
        //      Obj = new ActiveXComponent(Session.invoke("FindById",
        //             "wnd[0]").toDispatch());
        //    Obj.invoke("sendVKey", 0);

        //  sapGeneric.SAPGUIEnter();

        Thread.sleep(4000);


        //Session.IsPopupDialog(
        String str2 = sapGeneric.getSAPObjectIDHelperMethod(Session,"Name","MULTI_LOGON_OPT2","","");
        if(str2!= null) {
            arg = new Variant[2];
            arg[0] = new Variant(str2);
            arg[1] = new Variant("GuiModalWindow");
            Obj = new ActiveXComponent(Session.invoke("FindById", arg).toDispatch());
            Obj.invoke("select");
            // sapGeneric.SAPGUIRadiobtnClick("MULTI_LOGON_OPT2");
            sapGeneric.SAPGUIEnter();

        }
        // Obj = new ActiveXComponent(Session.invoke("FindById",
        //         "wnd[1]/usr/radMULTI_LOGON_OPT2").toDispatch());
        //Obj.invoke("select");
        //  Obj = new ActiveXComponent(Session.invoke("FindById",
        //          "wnd[1]/tbar[0]/btn[0]").toDispatch());
        // Obj.invoke("press");
        Thread.sleep(2000);


        Obj = new ActiveXComponent(Session.invoke("FindById",
                "wnd[0]/usr/cntlIMAGE_CONTAINER/shellcont/shell/shellcont[0]/shell").toDispatch());
        String text = Obj.getProperty("Type").toString();
        String id = sapGeneric.getSAPObjectIDHelperMethod(Session,"Type","GuiShell","SubType","Tree");
        // sapGeneric.SAPGUITreeExpandAndSelectNode1(id,"Favorites","Event Management");
      //  sapGeneric.SAPGUITreeExpandAndSelectNode(id,"Office","Folders","Inbox");
        Thread.sleep(2000);

    }


    public void OpenTransaction() throws InterruptedException {
        Thread.sleep(2000);
        /* Maximize the screen*/
        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]").toDispatch());
        Obj.invoke("maximize");

        /* Open a transaction SE38 on SAP Easy access screen and hit enter */

        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/tbar[0]/okcd").toDispatch());
        Obj.setProperty("text", "SE38");
        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]").toDispatch());
        Obj.invoke("sendVKey", 0);

        /* Execute RSPARAM report   */
        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/usr/ctxtRS38M-PROGRAMM").toDispatch());
        Obj.setProperty("text", "RSPARAM");
        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/usr/ctxtRS38M-PROGRAMM").toDispatch());
        Obj.setProperty("caretPosition", 7);
        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/tbar[1]/btn[8]").toDispatch());
        Obj.invoke("press");

        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/tbar[1]/btn[8]").toDispatch());
        Obj.invoke("press");

    }

    public void getCellValue() throws InterruptedException
    {
        Thread.sleep(2000);
        String str =null;

        Dispatch dispatch = Session.invoke("findById", "wnd[0]/usr/cntlGRID1/shellcont/shell").toDispatch();
        str = Dispatch.call(dispatch, "GetCellValue", 20,"DESCR").toString();
        System.out.println("Cell value is '"+str+"'");

    }

    public void logOff() throws InterruptedException {
        Thread.sleep(2000);

            /* Click on system from menu bar and select logoff option
            then click Ok on confirmation popup window */

        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/mbar/menu[4]/menu[12]").toDispatch());
        Obj.invoke("select");


        Obj = new ActiveXComponent(Session.invoke("findById", "wnd[1]/usr/btnSPOP-OPTION1").toDispatch());
        Obj.invoke("press");
    }



}
