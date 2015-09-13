package bst.cpo.automation.dm.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;


public class SystemOverview_Actions extends BaseTest
{

    public SystemOverview_Actions()
    {
    	//TODO - 
    	/*
    	 * License Management link does not work in Alpha, add validation
    	 * Add validation of search results.
    	 * Pagination controls for search results
    	 * Add validation for email link, user in properties.
    	 * Add validation clear button works.
    	 * Note: Search for "*" returns all rows, but * does not work like a wildcard.
    	 */

    }

    public boolean Is_Enabled_Search_Button()
    {
    	//<input type="submit" class="pure-button pure-button-primary" value="Search" ng-disabled="searchForm.$invalid">
    	try
    	{
	    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[1]/form/input[2]"));
	    	if(element.isEnabled())
	    	{
	    		return true;
	    	}
	    	return false;
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    	return false;
    }
    
    public void Set_User(String strUser)
    {    	
    	//<input type="text" ng-model="pp.query" maxlength="60" required="" class="ng-dirty ng-invalid ng-invalid-required">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[1]/form/input[1]"));
    	element.click();
    	element.clear();
    	element.sendKeys(strUser);
    }

    public void Search_Click()
    {
    	//<input type="submit" class="pure-button pure-button-primary new-folder-btn" ng-disabled="groupForm.$invalid" value="Save and create" disabled="disabled">
    	if(Is_Enabled_Search_Button())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[1]/form/input[2]"));
        	element.click();    		
    	} else {
    		logThis("Search_Click called and 'Search' button was not enabled.");
    		Assert.fail("Search_Click called and 'Search' button was not enabled.");
    	}
    }
    
    public void Clear_Click()
    {
    	//<input type="reset" class="pure-button" value="Clear" ng-click="resetSearch()">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[1]/form/input[3]"));
    	element.click();  
    }
    
    public void License_Management_Click()
    {
    	logThis("Clicking License Management Link (Not currently working in Alpha)");
    	WebElement element = DMDriver.findElement(By.linkText("License Management"));
    	element.click();
    }
    
    public void User_Results_Email_Click(String strEmail)
    {
    	logThis("Nav: Clicking email link '" + strEmail + "', opening properties panel.");
    	WebElement element = DMDriver.findElement(By.linkText(strEmail));
    	element.click();
    }
    
    public boolean Is_Open_Properties_Panel()
    {
    	//right-panel, open after clicking search results email link.
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
	    	WebElement element = DMDriver.findElement(By.className("properties-panel"));
	    	if(element.isDisplayed()){
	    		return true;
	    	}
	    	return false;
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    	return false;
    }
    
    public void Close_Properties_Panel()
    {
    	logThis("Closing the properties panel");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[5]/h2/i"));
    	element.click();
    }
}
