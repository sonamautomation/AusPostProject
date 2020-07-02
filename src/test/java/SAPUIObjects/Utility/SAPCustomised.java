package SAPUIObjects.Utility;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SAPCustomised extends SAPGeneric{
    public SAPCustomised(ActiveXComponent initSess) {
        super(initSess);
    }

       public void SAPGUITreeExpandAndSelectNode(String id, String treeNode1,String treeNode2, String nodeValue) throws Exception {
        Obj = new ActiveXComponent(getSession().invoke("FindById",id).toDispatch());
        Dispatch dis = Dispatch.call(this.Obj, "GetAllNodeKeys").toDispatch();
        Variant[] var = new Variant[1];
        int count = Dispatch.call(dis, "count").getInt();
        for (int i = 0; i < count; i++)
        {
            var[0] = Dispatch.call(this.Obj, "GetAllNodeKeys", new Object[] { i });
            if (Dispatch.call(Obj, "GetNodeTextByKey", new Object[] { Dispatch.call(Obj, "GetAllNodeKeys", new Object[] { i }) }).toString().contains(treeNode1))
            {
                Obj.invoke("expandNode", new Variant[] { var[0] });
                Thread.sleep(1000L);
                break;
            }
        }
        count = Dispatch.call(dis, "count").getInt();
        for (int i = 0; i < count; i++)
        {
            var[0] = Dispatch.call(this.Obj, "GetAllNodeKeys", new Object[] { i });
            if (Dispatch.call(Obj, "GetNodeTextByKey", new Object[] { Dispatch.call(Obj, "GetAllNodeKeys", new Object[] { Integer.valueOf(i) }) }).toString().contains(treeNode2))
            {
                Obj.invoke("expandNode", new Variant[] { var[0] });
                Thread.sleep(1000L);
                break;
            }
        }
        count = Dispatch.call(dis, "count").getInt();
        for (int i = 0; i < count; i++)
        {
            var[0] = Dispatch.call(this.Obj, "GetAllNodeKeys", new Object[] { Integer.valueOf(i) });
            if (Dispatch.call(Obj, "GetNodeTextByKey", new Object[] { Dispatch.call(Obj, "GetAllNodeKeys", new Object[] { Integer.valueOf(i) }) }).toString().contains(nodeValue))
            {
                Dispatch.call(Obj, "doubleClickNode", new Object[] { Dispatch.call(Obj, "GetAllNodeKeys", new Object[] { Integer.valueOf(i) })});
                Thread.sleep(1000L);
                break;
            }
        }

        Thread.sleep(1000L);

    }

    public void SAPGUITextFieldSendKeys(String lbl, String KeyValue, String LabelClassName) {

        arg[0] = new Variant(lbl);
        arg[1] = new Variant(LabelClassName);
        Session = new ActiveXComponent(Obj.invoke("FindById", arg).toDispatch());
        Session.invoke("setFocus");
        Obj.invoke("sendVKey",KeyValue);
    }
    public void SAPGUISendEnter(ActiveXComponent Session) {
        Obj = new ActiveXComponent(Session.invoke("FindById", "wnd[0]").toDispatch());
        Obj.invoke("sendVKey", 0);
    }
    public void SAPGUIPopWinEnter(ActiveXComponent Session) {
        Obj = new ActiveXComponent(Session.invoke("FindById", "wnd[1]").toDispatch());
        Obj.invoke("sendVKey", 0);
    }
    public void SAPGUIOKCodeField(ActiveXComponent Session,String Id, String value) {
        arg[0] = new Variant(Id);
        arg[1] = new Variant(SAPGuiClassName.GuiOkCodeFieldClassName);
        session = new ActiveXComponent(Session.invoke("FindById", arg).toDispatch());
        session.setProperty("text", value);

    }
    public void SAPGUISelectOperationById(ActiveXComponent Session,String lbl, String LabelClassName) {

        arg[0] = new Variant(lbl);
        arg[1] = new Variant(LabelClassName);
        session = new ActiveXComponent(Session.invoke("FindById", arg).toDispatch());
        Dispatch.call(session, "Select");
    }
    /*
     * SAPGUICTextFieldSendKeys Objective, Sendkeys in text field :
     * name of the element Parameter Label: KeyValue(ex :F1 -> 01 02->F2
     * 03 -> F3 04 ->F4 05 -> F5 06 -> F6 07 ->F7 08 ->F8 ) name created by
     * Venkata  06/01/2018
     *  F1 -> 01 02->F2  03 -> F3 04 ->F4 05 -> F5 06 -> F6 07 ->F7 08 ->F8
     */
    public void SAPGUICTextFieldSendKeys(String lbl, String KeyValue) {
        SAPGUITextFieldKeys(lbl, KeyValue, SAPGuiClassName.GuiCTextClassName);
    }
    public void SAPGUITextFieldKeys(String lbl, String KeyValue, String LabelClassName) {

        arg[0] = new Variant(lbl);
        arg[1] = new Variant(LabelClassName);
        Session = new ActiveXComponent(Obj.invoke("FindById", arg).toDispatch());
        Session.setProperty("text", KeyValue);

    }
    public void SAPGUIClick(String lbl, String LabelClassName) throws InterruptedException {
        arg[0] = new Variant(lbl);
        arg[1] = new Variant(LabelClassName);
        session = new ActiveXComponent(this.Obj.invoke("FindById", arg).toDispatch());
        Dispatch.call(session, "press");
        Thread.sleep(1000);
    }

    public void SAPGUIbtnClick(String lblname) throws InterruptedException {
        SAPGUIClick(lblname, SAPGuiClassName.GuiButtonClassName);
    }

}
