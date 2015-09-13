package bst.cpo.automation.dm.actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;


public class User_Actions extends BaseTest
{

    public User_Actions()
    {
    	//TODO - 
    	/*
    	 * Add logic for failed search.
    	 * Create an array for groups?
    	 */

    }

    public void Users_Search_for_Users()
    {
    	//legacy.  keeping this if we only want to open the drop down.
    	logThis("Opening Search for users drop down");
    	WebElement element = DMDriver.findElement(By.className("select2-arrow"));
    	element.click();
    }
    
    public void Search_For_User(String searchUser)
    {    	
    	try
    	{
        	logThis("Opening Search for users drop down");
        	WebElement element = DMDriver.findElement(By.className("select2-arrow"));
        	element.click();
        	Thread.sleep(1000);
        	logThis("Searching for user " + searchUser);
        	//WebElement element = DMDriver.findElement(By.className("select2-search"));
        	WebElement element2 = DMDriver.findElement(By.className("select2-input"));
        	element2.sendKeys(searchUser);
        	Thread.sleep(1000);
        	element2.sendKeys(Keys.ENTER);
        	Thread.sleep(1000);
    	}
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    public void Search_Results()
    {
    	//Returns all the info displayed for the user.
    	logThis("Displaying User Search Results");    	
    	WebElement element = DMDriver.findElement(By.className("faceted-fields"));
    	logThis(element.getText());
    }
    
    public String Get_Username()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[1]"));
    	return(element.getText().trim());
    }
    
    public String Get_User_First_Name()
    {
    	//*[@id="app"]/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[2]
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[2]"));
    	return(element.getText().trim());
    }
    
    public String Get_User_Last_Name()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[3]"));
    	return(element.getText().trim());
    }
    
    public String Get_User_Email()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[4]"));
    	return(element.getText().trim());
    }
    
    public String Get_User_TenantID()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[5]"));
    	return(element.getText().trim());
    }
    
    public String Get_User_Company()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[6]"));
    	return(element.getText().trim());
    }
    
    public String Get_User_Groups()
    {
    	//Returns all groups (if any).
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/ul"));
    	return(element.getText().trim());
    }
        
}
