package bst.cpo.automation.dm.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import bst.cpo.automation.dm.tests.BaseTest;

public class FolderShare_Actions extends BaseTest
{

    public FolderShare_Actions(WebDriver driver)
    {

    	/**
    	 * TODO
    	 * ====
    	 * 
    	 */
    }
    
    //get header info
    
    /**
     * Get the name of the folder from the span within the h2 tag
     * @return - String, name of the folder
     */
    public String Get_Share_Folder_Name()
    {
    	WebElement element = DMDriver.findElement(By.className("share-folder-name"));
    	return element.getText();
    }
    
    /**
     * Get the name of the owner from the span within the h2 tag
     * @return - String, name of the folder owner
     */
    public String Get_Owner_Name()
    {
    	WebElement element = DMDriver.findElement(By.className("owner-name"));
    	return element.getText();
    }
    

    //get current rights -- repeater acl in rights.localACL.ace"
    
    /**
     * Return each row in the Current rights table.
     * @return - String: user and granted permission
     */
    public String Get_Current_Rights_Users()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[5]/div/div/div[1]/span/table/tbody[2]"));
    	return element.getText();
    }
    
    /**
     * Return each row in the Inherited rights table.
     * @return - String: user and granted permission
     */
    public String Get_Inherited_Rights_Users()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[5]/div/div/div[1]/table/tbody[2]"));
    	return element.getText();
    }
    
    /**
     * Determine if the current folder blocks permission inheritance.
     * @return - Boolean; true if checked, false if not checked.
     */
    public Boolean Is_Blocked_Share()
    {
    	
    	return false;
    }
  
    /**
     * Toggles the 'Block share permissions inheritance for this folder' check box.
     */
    public void Blocked_Share_Click()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[5]/div/div/div[1]/div/label/input"));
    	element.click();
    }
    
    /**
     * Determine if the Remove Rights button is enabled or not.
     * @return - Boolean; true if enabled, false if not.
     */
    public Boolean Is_Remove_Rights_Enabled()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[5]/div/div/div[1]/span/div/button"));
    	if(element.isEnabled())
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * Click (Submit) the Remove Rights button.
     */
    public void Remove_Rights_Submit()
    {
    	logThis("Submit: Clicking 'Remove Rights' button.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[5]/div/div/div[1]/span/div/button"));
    	element.click();
    }
    
    
}
