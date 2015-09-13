package bst.cpo.automation.dm.actions;

import org.openqa.selenium.WebDriver;

// This will use the implementation for the zone actions test class
public class OthersRecentlyUpdatedDocs_Actions extends Zone_Actions
{
    static String targetController = "RecentlyUpdatedOthersDocsCtrl"; 
    static String noResults = "No others recently updated documents at the moment";
    
    public OthersRecentlyUpdatedDocs_Actions(WebDriver driver)
    {
    	super(driver, noResults, targetController);    	
    }
}
