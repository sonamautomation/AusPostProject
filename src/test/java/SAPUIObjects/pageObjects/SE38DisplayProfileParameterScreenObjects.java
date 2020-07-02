package SAPUIObjects.pageObjects;

public interface SE38DisplayProfileParameterScreenObjects {

    /* TCode - SE38 - Program name is "RSPARAM" then execute on initial screen.
    ---Capture Objects of Display Profile Parameters screen
     */

    String TitleOfScreenId = "wnd[0]/titl";
    String TitleOfScreenName = "titl";
    String TitleOfScrennType = "GuiTitlebar (102)";
    String TitleOfScreenText = "Display Profile Parameter";

    String TableName = "shell";
    String TableId = "wnd[0]/usr/cntlGRID1/shellcont/shell";
    String TableType = "GuiShell (122) - SubType: GridView";

    String ColParameterName = "PAR_NAME";
    String ColUserDefinedValName = "PAR_USER_WERT";
    String ColSystemDefaultValName = "PAR_DEFAULT_WERT1";
    String ColSystemDefaultValUnSubFormName = "PAR_DEFAULT_WERT2";
    String ColCommentName = "DESCR";

}
