package SAPUIObjects.Utility;

import base.Base;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.io.IOException;

public class SAPConnection extends Base {
    /* <---------- SAPUI  objects ---------> */
    public static SAPGeneric sapGeneric;
    public static SAPCustomised sapCustomised;

    public SAPConnection(ActiveXComponent session) {
        Session = session;
    }

    public SAPConnection() {
    }
    public ActiveXComponent SAPROTWr, GUIApp, Connection, Obj, SAPGIRD;
    public ActiveXComponent Session,win;
    public Dispatch ROTEntry;
   public Variant Value, ScriptEngine, arg[];
    public Process p;




    public ActiveXComponent getSapSessionObject() throws InterruptedException{
      // pasteDLLtoMavenFolder();
        String cnt = "0";
        ComThread.InitSTA();

        //Opening the SAP Logon
        try {
            p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\SAPgui.exe hxaix48 00");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Thread.sleep(5000);
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
        sapCustomised = new SAPCustomised(Connection);
        sapCustomised.setSession(new ActiveXComponent(sapCustomised.getSession().invoke("FindById", "wnd[0]").toDispatch()));
        sapGeneric.setSession(new ActiveXComponent(sapGeneric.getSession().invoke("FindById", "wnd[0]").toDispatch()));
        return Session;

    }

}
