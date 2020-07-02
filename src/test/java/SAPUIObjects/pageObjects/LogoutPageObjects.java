package SAPUIObjects.pageObjects;

public interface LogoutPageObjects {

   String MenuSystemId = "wnd[0]/mbar/menu[4]";
   String MenuSystemName = "System";
   String MenuSystemType = "GuiMenu (110)";
   String MenuSystemText = "System";
   String MenuSystemLogoffId = "wnd[0]/mbar/menu[4]/menu[12]";
   String MenuSystemLogoffName = "Log Off";
   String MenuSystemLogoffType = "GuiMenu (110)";
   String MenuSystemLogoffText = "Log Off";

   String PopWinId = "/app/con[0]/ses[0]/wnd[1]";
   String PopWinName = "wnd[1]";
   String PopWinType = "GuiModalWindow (22)";
   String PopWinText = "TE1(1)/031 Log Off";

   String PopWinYesOptId = "wnd[1]/usr/btnSPOP-OPTION1";
   String PopWinYesOptName = "SPOP-OPTION1";
   String PopWinYesOptText = "Yes";
   String PopWinYesOptType = "GuiButton (40)";

   String PopWinNoOptId = "wnd[1]/usr/btnSPOP-OPTION2";
   String PopWinNoOptName = "SPOP-OPTION2";
   String PopWinNoOptText = "No";
   String PopWinNoOptType = "GuiButton (40)";

}
