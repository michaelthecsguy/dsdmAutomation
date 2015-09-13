package bst.cpo.automation.dm.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;
import bst.cpo.automation.dm.actions.Reusable_Actions;

public class Zone_Actions extends BaseTest
{
    Reusable_Actions reusableAction = new Reusable_Actions(DMDriver);


    String targetController = ""; 
    String noResults = "";
    
    public Zone_Actions(WebDriver driver, String msgNoResults, String zoneController)
    {
        noResults = msgNoResults;
        targetController = zoneController;
    }
    
    // This method is used to demo the interaction with the zone
    public void Zone()
    {                   
        Collapse();

        Collapse();

        logThis("Number of results: " + numberOfResults());
    	logThis("Page Count: " + currentPage());
    	logThis("Total Pages: " + totalPages());
    	logThis("Page Count is visible: " + paginationisDisplayed());
    	
        
    	if (fileList().contains(noResults))
    	{
    		logThis("Contents: " + fileList());
    		Assert.assertTrue(fileList().contains(noResults), 
    				"There were 0 results, but the no results message was not displayed.");
    	}
        else if(paginationisDisplayed())
        {                        
            logThis("Page Count: " + currentPage());
            logThis("Contents: " + fileList());    
            
            NextPage();

            logThis("Page Count: " + currentPage());
            logThis("Contents: " + fileList());    

            PrevPage();
            
            logThis("Page Count: " + currentPage());
            logThis("Contents: " + fileList());            
        }
        else
        {
            logThis("Page Count: " + currentPage());
            logThis("Contents: " + fileList());            
        }
    }            
    
    // Functions that interact with specific parts of the zone
    // The functions work off of the provided 'ng-controller' tag
    // Example: <div class="dashboard-table panel panel-info ng-scope" ng-controller="RecentlyUpdatedDocsCtrl">
    public void Collapse()
    {
    	reusableAction.FoldersDocs_Collapse(targetController);	
    }
    
    public String numberOfResults()
    {
    	WebElement pageElement;
    	pageElement = reusableAction.FoldersDocs_ResultsCount(targetController);	
		return pageElement.getText();    	
    }
    
    public String currentPage()
    {    	
    	WebElement pageElement;
    	pageElement = reusableAction.FoldersDocs_CurrentPage(targetController);	
		return pageElement.getText();  	
    }
    
    public Boolean paginationisDisplayed()
    {
    	WebElement pageElement;
    	pageElement = reusableAction.FoldersDocs_CurrentPage(targetController);	
		return pageElement.isDisplayed();    	
    }
    
    public String totalPages()
    {
    	WebElement pageElement;
    	pageElement = reusableAction.FoldersDocs_PageCount(targetController);
		return pageElement.getText();    	
    }
    
    public String fileList()
    {
    	WebElement pageElement;
    	pageElement = reusableAction.FolderDocs_Zone_DisplayContents(targetController);     	
    	return pageElement.getText();   	
    }
    
    public void NextPage()
    {
    	try {
			reusableAction.FolderDocs_NextPage_Click(targetController);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void PrevPage()
    {
        try {
			reusableAction.FolderDocs_PrevPage_Click(targetController);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }     
}
