package bst.cpo.automation.dm.actions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Alert;

import bst.cpo.automation.dm.tests.BaseTest;


public class Info_Actions extends BaseTest
{

	public Info_Actions(WebDriver driver)
    {
    	/**
    	 * Info_Actions contain most features found when clicking the Summary/Info icon for a file or folder in DM.
    	 * 
    	 * Parent Panel: properties-panel
    	 * Is_Displayed_Properties_Panel() found in Reusable_Actions
    	 * 
    	 * Sub Panels:
    	 * Infos - file-infos-panel
    	 * Edit - file-edit-panel
    	 * Notes -
    	 * History - 
    	 * Versions -
    	 * Notifications - 
    	 * Preview - 
    	 */
    }

    
/** 
 * <div class="file-commands ctrls ng-scope">
 * Navigation controls
 * ============================================
 */

    /** 
     * Returns contents of h2 tag at the top of the properties panel
     * Useful to validate file/folder name.
     */
    public String Get_Info_Header()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[6]/h2"));
    	return element.getText();
    }
    
    /** 
     * Closes the Properties Panel
     * Consider moving this to Reusable_Actions
     */
    public void Close_Properties_Panel()
    {
    	logThis("Clicking 'X' to close Info panel.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[6]/h2/i"));
    	element.click();
    }

    /**
     * Helper method.
     * Returns the anchor tag element requested from <div class="file commands ctrls ng-scope">
     * Commands listed are dynamic based on folder / file, or user permissions.
     */
    public WebElement Get_File_Command(String strCommand)
    {
    	WebElement we = DMDriver.findElement(By.className("file-commands"));
    	List<WebElement> weCommand = we.findElements(By.tagName("a"));
    	if(weCommand.size() > 0)
    	{
    		for(int i = 0; i < weCommand.size(); i++)
        	{
    			if(weCommand.get(i).getAttribute("data-hint").contains(strCommand))
        		{
        			return weCommand.get(i);   				
        		}
        	}
    		logThis("Did not find '" + strCommand + "'.  Command may not be available based on user permission or file vs folder options.");
    	}
    	else
    	{
    		logThis("No commands found.");
    	}
    	return null;
    }

    /**
     * Click on the command issued.
     * Delete requires interaction with a confirmation modal window and should be called via "Delete_Click(Boolean blnDeleteOk)"
     * Note: Looks like there is an option for 'Send Link', but is hidden.  Need to check specs if feature should be visible.
     * @param strCommand - Valid commands are:
     * Infos, Edit, Notes, History, Versions, Notifications, Preview, Lock (and Unlock)
     */
    public void Nav_Command_Click(String strCommand)
    {
    	logThis("Clicking the '" + strCommand + "' icon");
    	WebElement element = Get_File_Command(strCommand);
    	if(element != null)
    	{
    		element.click();
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click '" + strCommand + "' and was unable to find it.");
    	}
    }

    /** 
     * Determine if the file / folder is locked 
     * Icon is a padlock
     * - When unlocked: anchor tag data-hint="Lock"    i tag class="fa fa-unlock
     * - When locked:   anchor tag data-hint="Unlock"  i tag class="fa fa-lock"
     * TODO - need to change xpath.  
     */
    public Boolean Is_File_Locked()
    {
    	WebElement weAnchor = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[6]/div/div[1]/span[10]/a"));
    	/*
    	logThis("Data-Hint='" + weAnchor.getAttribute("data-hint") + "'");
    	WebElement weIcon = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[6]/div/div[1]/span[10]/a/i"));
    	logThis("Icon-Class='" + weIcon.getAttribute("class") + "'");
		*/
    	if(weAnchor.getAttribute("data-hint").contains("Unlock"))
    	{
    		return true;
    	}
    	return false;
    }
    
    /** 
     * Delete file / folder
     * Clicking this presents user with modal OK/Cancel confirmation.
     */
    public void Delete_Click(Boolean blnDeleteOk)
    {
    	try
    	{
	    	logThis("Clicking the Delete / trash icon");
	    	WebElement element = Get_File_Command("Delete");
	    	if(element != null)
	    	{
	    		element.click();
	    		Thread.sleep(5000);
	    		if(blnDeleteOk)
	    		{
	    			logThis("Selecting 'OK' in Delete confirmation.");
					Alert alert = DMDriver.switchTo().alert();
	                alert.accept();
	    		}
	    		else
	    		{
	    			logThis("Selecting 'Cancel' in Delete confirmation.");
					Alert alert = DMDriver.switchTo().alert();
	                alert.dismiss();    			
	    		}
	    	}
	    	else
	    	{
	    		logThis("WARNING: Attempted to click 'Delete' and was unable to find it.");
	    	}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    }

/**
 * Info Tab
 * ==========================================================================
 */
    //TODO - add Is_Visible type boolean checks for fields based on file or folder viewed.

    /** Determine if the Info panel is open  */
    public Boolean Is_Info_Panel_Displayed()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }

    /** 
     * Returns the file size found on the Info tab. 
     * Only available for files.
     */
    public String Get_Info_Size()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[2]/div[2]"));
    	return element.getText();
    }

    /** 
     * Returns the file version found on the Info tab. 
     * Only available for files.
     */
    public String Get_Info_Version()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[3]/div[2]"));
    	return element.getText();
    }
    
    /** 
     * Returns the Description found on the Info tab. 
     * Only available for folders.
     */
    public String Get_Info_Description()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[4]/div[2]"));
    	return element.getText();
    }    

    /** Returns the file/folder created by user found on the Info tab.*/
    public String Get_Info_Created_By()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[5]/div[2]"));
    	return element.getText();
    }

    /** Returns the file/folder created date found on the Info tab. */
    public String Get_Info_Created_On()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[6]/div[2]"));
    	return element.getText();
    }

    /** Returns the file/folder modified by user found on the Info tab. */
    public String Get_Info_Modified_By()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[7]/div[2]"));
    	return element.getText();
    }

    /** Returns the file/folder modified date found on the Info tab.*/
    public String Get_Info_Modified_On()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-infos-panel']/div/div/div[8]/div[2]"));
    	return element.getText();
    }

    /**
     * Edit Tab
     * ==========================================================================
     */
    
    /** Determine if the Edit panel is open  */
    public Boolean Is_Edit_Panel_Displayed()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }

    /** 
     * Returns the Title found on the Edit panel.
     * Only available for folders.
     */
    public String Get_Edit_Title()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/input"));
    	return element.getAttribute("value");
    }
    
    /** Returns the Description found on the Edit panel.*/
    public String Get_Edit_Description()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/textarea"));
    	return element.getAttribute("value");
    }
 
    /** Set the Title found on the Edit panel.
     * Only available for folders
     */
    public void Set_Edit_Title(String strTitle)
    {
    	logThis("Set: Title = '" + strTitle + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/input"));
    	element.clear();
    	element.sendKeys(strTitle);
    }
    
    /** Set the Description found on the Edit panel.*/
    public void Set_Edit_Description(String strDesc)
    {
    	logThis("Set: Description = '" + strDesc + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/textarea"));
    	element.clear();
    	element.sendKeys(strDesc);
    }
    
    /** Returns the currently selected option in the Document Type drop down on the Edit panel.*/
    public String Get_Edit_Doc_Type()
    {
    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/div[1]/label/select")));
    	return droplist.getFirstSelectedOption().getText();
    }
    
    /** Set the Document Type drop down on the Edit panel.*/
    public void Set_Edit_Doc_Type(String strDocType)
    {
    	logThis("Setting Doc Type to '" + strDocType + "'");
    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/div[1]/label/select")));
    	droplist.selectByVisibleText(strDocType);
    }
    
    /** Save (submit) changes on the Edit panel. */
    public void Save_Edit_Click()
    {
    	logThis("Submit: Clicking 'Save' button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-edit-panel']/div/div/div/form/div/div[2]/input"));
    	element.click();
    }

    /**
     * Notes Tab
     * ==========================================================================
     */
    
    /** Determine if the Edit panel is open  */
    public Boolean Is_Notes_Panel_Displayed()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notes-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }
    
    /** Set the Note field on the Notes panel.*/
    public void Set_Note(String strNote)
    {
    	logThis("Setting Note to '" + strNote + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notes-panel']/div/div/form/textarea"));
    	element.clear();
    	element.sendKeys(strNote);
    }

    /** Add (submit) changes on the Notes panel. */
    public Boolean Is_Add_Note_Enabled()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notes-panel']/div/div/form/input"));
    	if(element.isEnabled())
    	{
    		return true;
    	}
		return false;
    }
    
    /** Add (submit) changes on the Notes panel. */
    public void Add_Note_Click()
    {
    	logThis("Submit: Clicking 'Add' button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notes-panel']/div/div/form/input"));
    	element.click();
    }
    
    //Notes - repeater "comment in comments"

    /**
     * Returns all listed Notes found on the Notes panel.
     * In this case, using the ta
     */
    public String Get_Notes()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notes-panel']/div/div/table/tbody"));
    	return element.getText();
    }
    
    /** 
     * Returns the **first Note found that was entered by a user found on the Notes panel.
     * If the user entered more than one note, only the first one is selected.
     */
    public String Get_Note_By_User(String strUser)
    {
    	return "TODO";
    }

    /**
     * Delete a note found on the Notes panel.
     * Click on the "x" link for a given row.
     * 
     */
    public void Delete_Note()
    {
    	
    }

    /**
     * Determine if a note exists in the Notes panel.
     * TODO:
     * -Determine if partial match of note
     * -Match combination of note, user, date?
     */
    public Boolean Exists_Note()
    {
    	
    	return false;
    }

    /**
     * History Tab
     * ==========================================================================
     */

    /** 
     * Determine if the History sub panel is open
     * Not to be confused with Is_Displayed_Folder_History_Panel() found in Reusable_Actions. 
     * @return Boolean - true if sub panel open, false if not
     */
    public Boolean Is_History_Panel_Displayed()
    {
        WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;    	
    }
    
    /**
     * Set field "Filter logs from:".  Format = YYY-MM-DD
     */
    public void Set_History_Start_Date(String strStartDate)
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/label[1]/span[2]/input"));
    	element.sendKeys(strStartDate);
    }

    /**
     * Get field "Filter logs from:".  Format = YYY-MM-DD
     */
    public String Get_History_Start_Date()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/label[1]/span[2]/input"));
    	return element.getText();
    }

    /**
     * Set field "to:" date.  Format = YYY-MM-DD
     */
    public void Set_History_End_Date(String strEndDate)
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/label[2]/span[2]/input"));
    	element.sendKeys(strEndDate);
    }

    /**  */
    public String Get_History_End_Date()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/label[2]/span[2]/input"));
    	return element.getText();
    }
    
    /** 
     * Determine if the Filter button on the History tab is enabled.
     * It is only enabled when a valid start and end date are entered.
     */
    public Boolean Is_Filter_Enabled()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/label[2]/span[2]/input"));
    	if(element.isEnabled())
    	{
    		return true;
    	}
    	return false;
    }

    /**
     * Click the 'Filter' button.
     * Submit/return records that are between start and end date.
     */
    public void History_Filter_Click()
    {
    	logThis("Submit: Clicking 'Filter' button.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/input"));
    	element.click();
    }

    /**
     * Click the 'Clear' button.
     * This removes data from the Start (Filter logs from:) / End (to:) date fields.
     */
    public void History_Clear_Click()
    {
    	logThis("Clicking 'Clear' button.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/form/button"));
    	element.click();
    }
    
    /**
     * Return all the history of a file/folder listed in the table body.
     * Results are listed in <div class="log-results">
     * Repeater for rows in the table is "log in logs"
     * Columns: Action, Date, Username, File/Folder
     */
    public String Get_History()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-history-panel']/div/div[1]/div/table/tbody"));
    	return element.getText();
    }
    
    
    /**
     * Versions Tab
     * ==========================================================================
     */    
    
    
    
    /** Determine if the Version panel is open */
    public Boolean Is_Version_Panel_Displayed()
    {
        WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-versions-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * TODO
     * repeater = 'entry in versionEntries'
     * Add 'Restore as Current'
     * Add 'Delete' link
     * Add 'Summary' link.
     */
    
    
    
    /**
     * Notifications Tab
     * ==========================================================================
     */    
    
    /** 
     * Determine if the Notifications sub panel is open
     * Not to be confused with Is_Displayed_Folder_Notifications_Panel() found in Reusable_Actions. 
     * @return Boolean - true if sub panel open, false if not
     */
    public Boolean Is_Notifications_Panel_Displayed()
    {
        WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }
    
    /** Determine if user is subscribed to Creation notifications*/
    public Boolean Is_Creation_Subscribe_Visible()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[1]/td/button[1]"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }

    /** Determine if user is subscribed to Creation notifications*/
    public Boolean Is_Creation_Unsubscribe_Visible()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[1]/td/button[2]"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }
    
    /** Subscribe user to Creation notifications */
    public void Creation_Subscribe_Click()
    {
    	if(Is_Creation_Subscribe_Visible())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[1]/td/button[1]"));
        	element.click();    		
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click Creation Subscribe button, but it was not visible.");
    	}
    }

    /** Unsubscribe user to Creation notifications */
    public void Creation_Unsubscribe_Click()
    {
    	if(Is_Creation_Unsubscribe_Visible())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[1]/td/button[2]"));
        	element.click();    		
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click Creation Unsubscribe button, but it was not visible.");    		
    	}
    }

    /** Determine if Modification Subscribe button is visible*/
    public Boolean Is_Modification_Subscribe_Visible()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[2]/td/button[1]"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }

    /** Determine if Modification Unsubscribe button is visible*/
    public Boolean Is_Modification_Unsubscribe_Visible()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[2]/td/button[2]"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }
    
    /** Subscribe user to Modification notifications */
    public void Modification_Subscribe_Click()
    {
    	if(Is_Modification_Subscribe_Visible())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[2]/td/button[1]"));
        	element.click();    		
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click Modification Subscribe button, but it was not visible.");
    	}
    }

    /** Unsubscribe user to Modification notifications */
    public void Modification_Unsubscribe_Click()
    {
    	if(Is_Modification_Unsubscribe_Visible())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[2]/td/button[2]"));
        	element.click();    		
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click Modification Unsubscribe button, but it was not visible.");    		
    	}
    } 

    /** Determine if Notes Subscribe button is visible*/
    public Boolean Is_Notes_Subscribe_Visible()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[3]/td/button[1]"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }

    /** Determine if Notes Unsubscribe button is visible*/
    public Boolean Is_Notes_Unsubscribe_Visible()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[3]/td/button[2]"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }
    
    /** Subscribe user to Notes notifications */
    public void Notes_Subscribe_Click()
    {
    	if(Is_Notes_Subscribe_Visible())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[3]/td/button[1]"));
        	element.click();    		
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click Notes Subscribe button, but it was not visible.");
    	}
    }

    /** Unsubscribe user to Notes notifications */
    public void Notes_Unsubscribe_Click()
    {
    	if(Is_Notes_Unsubscribe_Visible())
    	{
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-notifications-panel']/div/table/tbody/tr[3]/td/button[2]"));
        	element.click();    		
    	}
    	else
    	{
    		logThis("WARNING: Attempted to click Notes Unsubscribe button, but it was not visible.");    		
    	}
    }  

    /**
     * Preview Tab
     * ==========================================================================
     */
    
    /** Determine if the Notifications panel is open */
    public Boolean Is_Preview_Panel_Displayed()
    {
        WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-preview-panel']"));
    	if(element.isDisplayed())
    	{
    		return true;
    	}
    	return false;
    }    
    
    
    //Medium
    
    //Thumbnail

    /**  Rotate the image in the Preview panel to the left*/
    public void Rotate_Image_Left_Click()
    {
    	logThis("Rotating the image left in the Previe panel");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-preview-panel']/div/div/div[1]/a[1]"));
    	element.click();   	 	
    }

    /**  Rotate the image in the Preview panel to the right*/
    public void Rotate_Image_Right_Click()
    {
    	logThis("Rotating the image left in the Previe panel");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-preview-panel']/div/div/div[1]/a[2]"));
    	element.click();
    }

    /** Clickthe download icon in the Preview panel */
    public void Preview_Download_Click()
    {
    	logThis("Clicking the Download icon on the Preview panel");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-preview-panel']/div/div/div[1]/a[3]"));
    	element.click();
    }

    /** Click the Save button on the Preview panel */
    public void Preview_Save_Click()
    {
    	logThis("Clicking the Save button on the Preview panel");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='file-preview-panel']/div/div/div[1]/button"));
    	element.click();
    }

    



}