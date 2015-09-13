package bst.cpo.automation.dm.actions;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import bst.cpo.automation.dm.tests.BaseTest;
import bst.cpo.automation.dm.actions.Reusable_Actions;

import org.testng.Assert;

public class Home_Actions extends BaseTest
{
   
	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
    
    
    public Home_Actions(WebDriver driver)
    {

    }

    /**
     * Calls a combination of methods to navigate the menu icons: My Docs, Others Docs, Guest Folder, Settings (Admin Center), and Home.
     * @param blnAdminLogin - user to determine if user is BA (true) or standard (false).
     */
    public void Navigation_Links(Boolean blnAdminLogin) throws InterruptedException
    {
    	logThis("Visiting all side navigation links");
    	
    	Nav_MyDocs_Click();
    	Nav_OthersDocs_Click();
    	Nav_GuestFolder_Click();
    	Nav_Settings_Click(blnAdminLogin);
    	Nav_Dashboard_Click();
    }        

    /**
     * Click on the Dashboard (Home) icon link.
     * Also confirms header information after navigation.
     */
    public void Nav_Dashboard_Click()
    {
    	logThis("Nav: Home");  
    	WebElement element = DMDriver.findElement(ng.repeater("item in navItems").row(1));    	
    	element.click();
    	Assert.assertTrue(Get_Nav_Tree_Header().contains("Home"),
        		"Failed - Navigation to Dashboard 'Home'"); 
    }

    /**
     * Click on the My Docs Folder icon link.
     * Also confirms header information after navigation.
     */
    public void Nav_MyDocs_Click()
    {
    	logThis("Nav: My Docs");
    	WebElement element = DMDriver.findElement(ng.repeater("item in navItems").row(2));    	
    	element.click();
    	Assert.assertTrue(Get_Nav_Tree_Header().contains("My docs"),
    			"Failed - Navigation to 'My docs'");       
    }

    /**
     * Click on the Others Docs Folder icon link.
     * Also confirms header information after navigation.
     */
    public void Nav_OthersDocs_Click()
    {
    	logThis("Nav: Other Docs");
    	WebElement element = DMDriver.findElement(ng.repeater("item in navItems").row(3));    	
    	element.click();
    	Assert.assertTrue(Get_Nav_Tree_Header().contains("Others Docs"),
        		"Failed - Navigation to 'Others Docs'"); 
    }
    
    /**
     * Click on the Guest Folder icon link.
     * Also confirms header information after navigation.
     */
    public void Nav_GuestFolder_Click()
    {
    	logThis("Nav: Guest Folder");
    	WebElement element = DMDriver.findElement(ng.repeater("item in navItems").row(4));    	
    	element.click();
    	Assert.assertTrue(Get_Nav_Tree_Header().contains("Guest folder"),
        		"Failed - Navigation to 'Guest folder'"); 
    }    
    
    /**
     * Click on the Settings (standard user) / 'Admin Center' (BA user) icon link.
     * Also confirms header information after navigation.
     * @param blnAdminLogin - Pass in true if user is a BA.  false if standard user.
     */
    public void Nav_Settings_Click(Boolean blnAdminLogin)
    {
    	String strHeader = "";
    	logThis("Nav: Settings");
    	if(blnAdminLogin)
    	{
    		strHeader = "Admin Center";
    		logThis("");
    	} else {
    		strHeader = "Settings";
    		logThis("");
    	}
        try
        { 
	    	WebElement element = DMDriver.findElement(ng.repeater("item in navItems").row(5));    	
	    	element.click();
	    	Thread.sleep(5000);
	    	Assert.assertTrue(Get_Nav_Tree_Header().contains(strHeader),
	        		"Failed - Navigation to '" + strHeader + "'"); 
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail("Expected Nav Tree Header '" + strHeader + "' not found." );
        }
    }

	/**
	 * Opens new window (target="_blank").  Although the window is displayed in foreground,
	 * web driver stays on the parent window unless told to switch.
	 * To manage browser windows, use the following methods in Reusable_Actions.java
	 * Get_Browser_Handle()
	 * Switch_Browser_By_URL(String strURL)
	 * Close_Extra_Browser_Windows(String strKeeperGuid) 
	 */
    public void Nav_Help_Click()
    {
    	logThis("Nav: Help");
    	WebElement element = DMDriver.findElement(ng.repeater("item in navItems").row(6));    	
    	element.click();
    }

	/**
	 * Opens new window (target="_blank").  Although the window is displayed in foreground,
	 * web driver stays on the parent window unless told to switch.
	 * To manage browser windows, use the following methods in Reusable_Actions.java
	 * Get_Browser_Handle()
	 * Switch_Browser_By_URL(String strURL)
	 * Close_Extra_Browser_Windows(String strKeeperGuid) 
	 */
    public void Nav_Help_Link_Click()
    {
    	logThis("Nav: Help (link)");
    	WebElement element = DMDriver.findElement(By.linkText("Help"));    	
    	element.click();
    }
 
	/**
	 * Opens new window (target="_blank").  Although the window is displayed in foreground,
	 * web driver stays on the parent window unless told to switch.
	 * To manage browser windows, use the following methods in Reusable_Actions.java
	 * Get_Browser_Handle()
	 * Switch_Browser_By_URL(String strURL)
	 * Close_Extra_Browser_Windows(String strKeeperGuid) 
	 */
    public void Nav_Terms_Link_Click()
    {
    	logThis("Nav: Terms and conditions (link)");
    	WebElement element = DMDriver.findElement(By.linkText("Terms and conditions"));    	
    	element.click();
    }
    
	/**
	 * Opens new window (target="_blank").  Although the window is displayed in foreground,
	 * web driver stays on the parent window unless told to switch.
	 * To manage browser windows, use the following methods in Reusable_Actions.java
	 * Get_Browser_Handle()
	 * Switch_Browser_By_URL(String strURL)
	 * Close_Extra_Browser_Windows(String strKeeperGuid) 
	 */
    public void Nav_Privacy_Link_Click()
    {
    	logThis("Nav: Privacy (link)");
    	WebElement element = DMDriver.findElement(By.linkText("Privacy"));    	
    	element.click();
    }
    
    /**
     * Click the 'advanced search' link found at the top of the web page (inside 'search folder or file' box).
     */
    public void Nav_Advanced_Search_Click()
    {
    	logThis("Nav: Advanced Search");
    	WebElement element = DMDriver.findElement(By.linkText("advanced search"));    	
    	element.click();	
    	Assert.assertTrue(Get_Nav_Tree_Header().contains("Advanced search"),
        		"Failed - Navigation to 'Advanced search'"); 
    }
    
    /**
     * Validate text from Home / Dashboard.
     * Basic test, could be expanded upon.
     * Josh's code re-factored to de-couple DM from framework and apparent class name change.
     */
    public void Basic_Welcome_Zone()
    {
    	try
    	{
        	WebElement element = reuse.WaitUntilElementExists(By.className("sub-nav"));
            Assert.assertTrue(element.getText().contains("Welcome to Sharp Cloud Portal Office"),
            		"Failed - The welcome text did not contain 'Welcome to Sharp Cloud Portal Office'");       		
    	}
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }

    }
    
    /**
     * Returning the h2 text at the top of the navigation tree
     * Despite using trim() on the string, it fails == test for some reason.  
     * Use contains indexOf to evaluate return.
     * @return - Should contain text like Home, My docs, Other docs, Guest folder, Settings, etc...
     */
    public String Get_Nav_Tree_Header()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div[1]/div/h2"));
    	logThis("Nav Tree Header = '" + element.getText() + "'");		
    	return element.getText().trim();
    }

    /**
     * Returning the h2 text at the top of the main section of the page
     * Despite using trim() on the string, it fails == test for some reason.  
     * Use contains indexOf to evaluate return.
     * @return - Generally returns bread crumbs / folder navigation info OR Settings options info (Sync computers, Users, Groups, etc..)
     */
    public String Get_Main_Header()
    {
    	WebElement element = DMDriver.findElement(By.className("main-header"));
    	logThis("Main Header = '" + element.getText() + "'");		
    	return element.getText().trim();
    }
    
    /**
     * Determine if the share "arrow" icon is available.
     * Using the span around the anchor to determine.
     * @return - Boolean.  true if displayed, false if not.
     */
    public Boolean Is_Displayed_Folder_Share_Link()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[1]"));
    	if(element.getAttribute("class").contains("ng-hide"))
    	{
    		return false;
    	}
    	return true;
    }
    
    /**
     * This is for the share "arrow" icon at the top of the page (under Sign Out link).
     * Not to be confused with the method found in Reusable_Actions.
     * To validate, call Is_Displayed_Folder_Share_Panel() in Reusable_Actions.java
     */
    public void Folder_Share_Link_Click()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[1]/a"));
    	logThis("Nav: Folder Info");
    	element.click();
    }

    /**
     * Determine if the info "i" icon is available.
     * @return - Boolean.  true if displayed, false if not.
     */
    public Boolean Is_Displayed_Folder_Info_Link()
    {
    	
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[2]"));
    	if(element.getAttribute("class").contains("ng-hide"))
    	{
    		return false;
    	}
    	return true;
    }
    
    /**
     * This is for the summary icon at the top of the page (under Sign Out link).
     * Not to be confused with the method found in Reusable_Actions.
     * Both methods open the same properties panel.
     * To validate, call Is_Displayed_Properties_Panel() found in Reusable_Actions.java
     */
    public void Folder_Info_Link_Click()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[2]"));
    	logThis("Nav: Folder Info");
    	element.click();
    }

    /**
     * Determine if the notifications "bell" icon is available
     * @return - Boolean.  true if displayed, false if not.
     */
    public Boolean Is_Displayed_Folder_Notifications_Link()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[3]"));
    	if(element.getAttribute("class").contains("ng-hide"))
    	{
    		return false;
    	}
    	return true;
    }
    /**
     * This is for the notifications (bell icon) at the top of the page (under Sign Out link).
     * Not to be confused with the method Info_Actions.Nav_Command_Click("Notifications").
     * This is for folders - and there doesn't seem to be any validation of which folder you are working with in the panel.
     * Will need to use bread crumbs to validate if necessary.
     */
    public void Folder_Notifications_Link_Click()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[3]/a"));
    	logThis("Nav: Folder Notifications");
    	element.click();
    }
    
    /**
     * Determine if the history "graph" icon is available
     * @return - Boolean.  true if displayed, false if not.
     */
    public Boolean Is_Displayed_Folder_History_Link()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[4]"));
    	if(element.getAttribute("class").contains("ng-hide"))
    	{
    		return false;
    	}
    	return true;
    }
    
    /**
     * This is for the history (graph icon) at the top of the page (under Sign Out link).
     * Not to be confused with the method Info_Actions.Nav_Command_Click("Notifications").
     * This is for folders - and there doesn't seem to be any validation of which folder you are working with in the panel.
     * Will need to use bread crumbs to validate if necessary.
     */
    public void Folder_History_Link_Click()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[4]/a"));
    	logThis("Nav: Folder History");
    	element.click();
    }
    
    /**
     * Determine if the trash "garbage can" icon is available
     * @return - Boolean.  true if displayed, false if not.
     */
    public Boolean Is_Displayed_Folder_Trash_Link()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[5]"));
    	if(element.getAttribute("class").contains("ng-hide"))
    	{
    		return false;
    	}
    	return true;
    }

    /**
     * This is for the trash (garbage can icon) at the top of the page (under Sign Out link).
     * Not to be confused with the method Info_Actions.Nav_Command_Click("Delete").
     * This is for folders - and there doesn't seem to be any validation of which folder you are working with in the panel.
     * Will need to use bread crumbs to validate if necessary.
     */
    public void Folder_Trash_Link_Click()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[1]/div/div[1]/div/span[5]/a"));
    	logThis("Nav: Folder Trash");
    	element.click();    	
    }
   
}
