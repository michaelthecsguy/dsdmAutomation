package bst.cpo.automation.dm.actions;

//import org.openqa.selenium.WebDriver;
import bst.cpo.automation.dm.tests.BaseTest;

public class MyDocs_Actions extends BaseTest
{
    
    public MyDocs_Actions()
    {

    	/**
    	 * This class might not be needed
    	 * Almost all the features are covered Reusable_Actions
    	 * The Angular repeater in My Docs is "file in files"
    	 * 
    	 * TODO
    	 * ====
    	 * Select All check box functionality.
    	 * Sort columns.
    	 * 
    	 * 
    	 * For quick reference: 
    	 * Features found in Reusable_Actions
    	 * ==================================
    	 * 
    	 * --Get_FileFolder_Row(String strRepeater, String strFileFolder)
    	 * Works as a helper function, returns the entire table row element of the file/folder.
    	 * Once you have the row, you can search for other elements within that row.
    	 * Most if not all of the below methods call this function.
    	 * 
    	 *  --FileFolder_Link_Click(String strRepeater, String strFileFolder)
    	 *  Click the file/folder link
    	 *  Functionality is different for folders (navigate to) versus files (download)
    	 *  
    	 *  FileFolder_Link_Right_Click(String strRepeater, String strFileFolder)
    	 *  Right click the file/folder link
    	 *  Brings up context menu to copy/paste/move
    	 *  
    	 *  --Select_FileFolder(String strRepeater, String strFileFolder)
    	 *  Checks/Un-checks the check box for the listed file/folder.
    	 *  
    	 *  --Is_FileFolder_Selected(String strRepeater, String strFileFolder)
    	 *  Returns a boolean value if the file/folder is currently checked.
    	 *  
    	 *  --Is_Displayed_FileFolder_Dropdown
    	 *  Returns a boolean value if the context menu is displayed.
    	 *  Used in conjunction with FileFolder_Link_Right_Click
    	 *  
    	 *  --Send_Link_Click(String strRepeater, String strFile)
    	 *  Click on the Send Link (mail) icon.
    	 *  Available for files only.
    	 *  
    	 *  --Info_Link_Click(String strRepeater, String strFileFolder)
    	 *  Click on the Info/Summary (i) link.
    	 *  
    	 *  --Get_Version(String strRepeater, String strFileFolder)
    	 *  Return the version (File only)
    	 *  
    	 *  --Get_Modified_Date(String strRepeater, String strFileFolder)
    	 *  Return the Modified ate
    	 *  
    	 *  --Get_Last_Contributor(String strRepeater, String strFileFolder)
    	 *  Return the Last Contributor
    	 *  
    	 *  --Get_File_Folder_Icon(String strRepeater, String strFileFolder)
    	 *  Icon displayed based on file type / folder.
    	 *  
    	 *  --Is_Folder_Shared(String strRepeater, String strFileFolder)
    	 *  Determine if the folder is shared.
    	 *  
    	 *  --Is_Send_Link_Active(String strRepeater, String strFileFolder)
    	 *  Determine if the send link is active / blue
    	 *  File only.
    	 *  
    	 *  
    	 *  
    	 *
    	 */
    }
}
