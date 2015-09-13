package bst.cpo.automation.dm.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;
import bst.cpo.automation.dm.actions.Home_Actions;


public class Settings_Actions extends BaseTest
{
	Home_Actions homeAction = new Home_Actions(DMDriver);
    
    public Settings_Actions()
    {

    }

    public void System_Overview_Click()
    {
    	//TODO - Only available for BA users, add logic
    	//app1/cpo/#/settings/system-overview
    	logThis("Nav: Settings > System Overview");    	
    	WebElement element = DMDriver.findElement(By.linkText("System overview"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("System overview"),
        		"Failed - Navigation to 'System overview'"); 
    }
    
    public void Sync_Computers_Click()
    {
    	//app1/cpo/#/settings/sync
    	logThis("Nav: Settings > Sync Computers");    	
    	WebElement element = DMDriver.findElement(By.linkText("Sync Computers"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("Sync computers"),
        		"Failed - Navigation to 'Sync computers'"); 
    }
    
    public void Meeting_Room_Connector_Click()
    {
    	//app1/cpo/#/settings/iwb
    	//TODO - Only available for BA users, add logic
    	logThis("Nav: Settings > Meeting Room Connector");
    	WebElement element = DMDriver.findElement(By.linkText("Meeting Room Connector"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("Meeting Room Connector"),
        		"Failed - Navigation to 'Meeting Room Connector'"); 
    }
    
    public void Users_Click()
    {
    	//app1/cpo/#/settings/users
    	logThis("Nav: Settings > Users");  
    	logThis("Looking for the Users Link");    	
    	WebElement element = DMDriver.findElement(By.linkText("Users"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("Users"),
        		"Failed - Navigation to 'Users'"); 
    }

    public void Groups_Click()
    {
    	//app1/cpo/#/settings/groups
    	logThis("Nav: Settings > Groups");     	
    	WebElement element = DMDriver.findElement(By.linkText("Groups"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("Groups"),
        		"Failed - Navigation to 'Groups'"); 
    }

    public void Notifications_Click()
    {
    	//app1/cpo/#/settings/notifications
    	logThis("Nav: Settings > Notifications");     	
    	WebElement element = DMDriver.findElement(By.linkText("Notifications"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("Notifications"),
        		"Failed - Navigation to 'Notifications'"); 
    }

    public void Index_Fields_Click()
    {
    	//TODO - Only available for BA users, add logic
    	//app1/cpo/#/settings/index-fields
    	logThis("Nav: Settings > Index Fields");     	
    	WebElement element = DMDriver.findElement(By.linkText("Index Fields"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("Index fields"),
        		"Failed - Navigation to 'Index fields'"); 
    }
    
    public void My_Log_Click() throws InterruptedException
    {    	
    	//app1/cpo/#/settings/myLog
    	logThis("Nav: Settings > My Log");  
    	WebElement element = DMDriver.findElement(By.linkText("My Log"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("My log"),
        		"Failed - Navigation to 'My log'"); 
    }  

    public void Admin_Log_Click() throws InterruptedException
    {    	
    	//app1/cpo/#/settings/adminLog
    	//TODO - Only available for BA users, add logic (note, link != header)
    	logThis("Nav: Settings > Admin Log");  
    	WebElement element = DMDriver.findElement(By.linkText("Admin Log"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("My log"),
        		"Failed - Navigation to 'My log'"); 
    }  
    
    public void My_Profile_Click() throws InterruptedException
    {    	
    	//app1/cpo/#/settings/profile
    	logThis("Nav: Settings > My Profile");  
    	WebElement element = DMDriver.findElement(By.linkText("My Profile"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("My profile"),
        		"Failed - Navigation to Dashboard 'My profile'"); 
    }  

    public void My_License_Click() throws InterruptedException
    {
    	//app1/cpo/#/settings/license
    	//TODO - not avail for BA user, add logic to check.
    	logThis("Nav: Settings > My License");  
    	WebElement element = DMDriver.findElement(By.linkText("My License"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("My License"),
        		"Failed - Navigation to Dashboard 'My License'"); 
    }
    
    public void My_Docs_Trash_Click() throws InterruptedException
    {    	
    	//app1/cpo/#/settings/trash
    	logThis("Nav: Settings > My Docs Trash");  
    	WebElement element = DMDriver.findElement(By.linkText("My Docs Trash"));
    	element.click();
    	Assert.assertTrue(homeAction.Get_Main_Header().contains("My docs trash"),
        		"Failed - Navigation to Dashboard 'My docs trash'"); 
    }
    

    
    public void My_Profile_Elements() throws InterruptedException
    {    	
    	//Looks like this just returns the first element found with the name.
    	WebElement element = DMDriver.findElement(By.className("pure-control-group"));
    	logThis(element.getText());
logThis("===========");
		//While this print out each individually.
		//TODO - find away to loop through without specifically doing each index?
    	List<WebElement> we2 = DMDriver.findElements(By.className("pure-control-group"));
    	logThis("(0)=" + we2.get(0).getText());
    	logThis("(1)=" + we2.get(1).getText());
    	logThis("(2)=" + we2.get(2).getText());
    	logThis("(3)=" + we2.get(3).getText());    	
    }
    


    

}
