package bst.cpo.automation.dm.actions;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import org.testng.Assert;
import bst.cpo.automation.dm.tests.BaseTest;

public class AdminLog_Actions extends BaseTest
{

    
    public AdminLog_Actions()
    {
    	//TODO - 
    	/*
    	 * Everything
    	 * Consider using code from MyLog_Actions
    	 * 
    	 */
    }

    /**
     * Date format is yyyy-mm-dd
     * Using a string here because sendkeys doesn't like date type.
     * @param strStartDate - String
     */
    public void Set_Start_Date(String strStartDate)
    {
    	logThis("Setting Start date to '" + strStartDate + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[2]/form/label[1]/span[2]/input"));
    	element.clear();
    	element.sendKeys(strStartDate);
    }

    /**
     * Date format is yyyy-mm-dd
     * Using a string here because sendkeys doesn't like date type.
     * @param strEndDate - String
     */
    public void Set_End_Date(String strEndDate)
    {
    	logThis("Setting End date to '" + strEndDate + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[2]/form/label[2]/span[2]/input"));
    	element.clear();
    	element.sendKeys(strEndDate);    	
    }
    
    /**
     * Determine if the Filter button is enabled.
     * @return - Boolean
     */
    public Boolean Is_Enabled_Filter()
    {
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[2]/form/input[1]"));
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
    
    public void Filter_Click()
    {
    	if(Is_Enabled_Filter())
    	{
        	logThis("Clicking 'Filter' button.");
    		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[2]/form/input[1]"));
        	element.click();
    	}
    	else
    	{
    		logThis("Attempting to click 'Filter' button while button is disabled.");
    		Assert.fail("Attempting to click 'Filter' button while button is disabled.");
    	}
    }
 
    public void Clear_Click()
    {
    	logThis("Clicking 'Clear' button.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[2]/form/input[2]"));
    	element.click();
    }
    
    public String Get_Results_Text()
    {
    	WebElement element = DMDriver.findElement(By.className("log-results"));
    	return element.getText();
    }
    
    public Boolean Check_If_Results_Contain_String(String strSearch)
    {
    	logThis("Searching for '" + strSearch + "' in My log results");
    	if(Get_Results_Text().contains(strSearch))
    	{
    		logThis("Found '" + strSearch + "' in results");
    		return true;
    	}
    	logThis("Did not find '" + strSearch + "' in results");
    	return false;
    }
}