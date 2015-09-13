package bst.cpo.automation.dm.actions;

import org.openqa.selenium.WebDriver;

public class MyRecentlyUpdatedFolders_Actions extends Zone_Actions
{    
    static String targetController = "RecentlyUpdatedFoldersCtrl";    
    static String noResults="No recently updated folders at the moment";
    
    public MyRecentlyUpdatedFolders_Actions(WebDriver driver)
    {
    	super(driver, noResults, targetController);    	
    }     
}
