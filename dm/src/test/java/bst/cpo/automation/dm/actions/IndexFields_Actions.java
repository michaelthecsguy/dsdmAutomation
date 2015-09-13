package bst.cpo.automation.dm.actions;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import bst.cpo.automation.dm.tests.BaseTest;


public class IndexFields_Actions extends BaseTest
{

    public IndexFields_Actions()
    {
    	//TODO - 
    	/* Lots of work to do here.
    	 * Listed doc types/indexes use ng-model="docTypes"
    	 * 
    	 * Validate listed indexes
    	 * Check if index is checked.
    	 * Validate index fields for a given index
    	 * 
    	 * Move indexes / change sort order (drag drop?)
    	 * 
    	 * Consider using Get_Index_List to determine order they appear on screen
    	 */
    }

    public void Save_Click()
    {
    	//Works
    	logThis("Clicking Save button");
    	//<button class="pure-button pure-button-primary" ng-click="saveData()"><span translate="" class="ng-scope">Save</span></button>
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/table/thead/tr/th[3]/button[2]"));
    	element.click();
    }
    
    public void Cancel_Click()
    {
    	logThis("Clicking Cancel button");
    	//<button class="pure-button pure-button-primary" ng-click="resetSort()"><span translate="" class="ng-scope">Cancel</span></button>
    	WebElement element = DMDriver.findElement(By.linkText("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/table/thead/tr/th[3]/button[3]"));
    	element.click();
    	
    }
    
    public void Uncheck_Index(String strIndex)
    {
    	
    	logThis("WARNING: Check_Index not complete.");
    	WebElement element = DMDriver.findElement(By.linkText("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/ul/li[1]/label/input"));
    	element.click();
    }
    
    public void Check_Index(String strIndex)
    {
    	logThis("WARNING: Check_Index not complete.");
    }
    
    public void Click_Index_Link(String strIndex)
    {
    	//Works
    	WebElement element = DMDriver.findElement(By.linkText(strIndex));
    	element.click();
    }

    public String Get_Index_List()
    {
    	//Works
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/ul"));
    	return element.getText();
    }
}
