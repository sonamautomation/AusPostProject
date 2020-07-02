package SAPUIObjects.pageObjects;


public interface LoginPageObjects {

    String UserName = "RSYST-BNAME";
    String UserId = "wnd[0]/usr/txtRSYST-BNAME";
    String UserType = "GuiTextField";
    String PasswordName = "RSYST-BCODE";
    String PasswordType = "GuiPasswordField";
    String PasswordId = "wnd[0]/usr/pwdRSYST-BCODE";
    String EnterButtonName = "btn[0]";
    String EnterButtonType = "GuiButton";
    String EnterButtonId = "wnd[0]/tbar[0]/btn[0]";
    String PopupWindowRadioButton2Name  = "MULTI_LOGON_OPT2";
    String PopupWindowRadioButton2Id= "wnd[1]/usr/radMULTI_LOGON_OPT2";
    String PopupWindowType  =  "GuiModalWindow";

}
