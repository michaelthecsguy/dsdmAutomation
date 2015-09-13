package bst.cpo.automation.dm.actions;

import org.openqa.selenium.WebDriver;

public class MyRecentlyUpdatedDocs_Actions extends Zone_Actions
{   
    static String targetController = "RecentlyUpdatedDocsCtrl"; 
    static String noResults="No recently updated documents at the moment";
    
    public MyRecentlyUpdatedDocs_Actions(WebDriver driver)
    {
    	super(driver, noResults, targetController);    	
    }
}
