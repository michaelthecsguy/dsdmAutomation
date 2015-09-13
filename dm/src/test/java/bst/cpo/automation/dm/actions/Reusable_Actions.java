package bst.cpo.automation.dm.actions;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import bst.cpo.automation.dm.tests.BaseTest;


public class Reusable_Actions extends BaseTest
{

    WebDriver resuable_Driver;


    public Reusable_Actions(WebDriver driver)
    {
        resuable_Driver = driver;        
    }

    public void ExpectedFail_Test()
    {            
        WebElement CPO_Test;
        CPO_Test = VerifyElementisVisible(By.xpath("//div[@class='featureTitle cloudPortalIcon']"));
        CPO_Test.click();            
    }
	
	// Read the configuration file whenever needed
	public String readConfig(String property)
	{
		String propertyValue = null;
        String propFileName = "testng.xml";
        
        try {
            InputStream configFileStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            Properties p = new Properties();
            p.load(configFileStream);
            propertyValue = p.getProperty(property);
            
            configFileStream.close();
        } catch (Exception e) {  //IO or NullPointer exceptions possible in block above
            System.out.println("Useful message");
            System.exit(1);
        }
        return propertyValue;
	}

	/**
	 * Used on 'Dashboard'/Home page and toggles collapse of My docs, My folders, and Other docs.
	 * @param targetController - Div tag 'ng-controller' value.  
	 * Valid values are RecentlyUpdatedDocsCtrl, RecentlyUpdatedFoldersCtrl, and RecentlyUpdatedOthersDocsCtrl
	 */
	 public void FoldersDocs_Collapse(String targetController) {
    	WebElement element = null;
        try {
			element = DMDriver.findElement(By.xpath("//div[@ng-controller='"+targetController+"']/div"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        element.click();
	}

	 /**
	  * Determine if a control is collapsed or not on the Dashboard/Home page.
	  * @param targetController - RecentlyUpdatedDocsCtrl, RecentlyUpdatedFoldersCtrl, or RecentlyUpdatedOthersDocsCtrl
	  * @return - Boolean
	  */
	 public Boolean Is_Dashboard_Folder_Collapsed(String targetController){
     	WebElement element = null;
     	WebElement element2 = null;
		 try {

        	element = DMDriver.findElement(By.xpath("//div[@ng-controller='"+targetController+"']/div"));
			
        	//*[@id="app"]/div/div[4]/div/div[1]/div[1]/div[2]/div[2]
        	element2 = DMDriver.findElement(By.xpath("//"));
      	
		} catch (Exception e) {
			e.printStackTrace();
		}		 
  		 return false;  
	 }
	
	// Functions related to the document displays on the dashboard and document pages
    public WebElement FoldersDocs_ResultsCount(String targetController)
    {
        WebElement element = null;
        try {
			element = DMDriver.findElement(By.xpath("//div[@ng-controller='"+targetController+"']"))
					.findElement(ng.binding("resultsCount"));
			
			logThis("Results Count Found: " + element.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return element;
    }
    
    public WebElement FoldersDocs_CurrentPage(String targetController)
    {
        WebElement element = null;
        try {
			element = DMDriver.findElement(By.xpath("//div[@ng-controller='"+targetController+"']"))
					.findElement(ng.binding("(currentPageIndex+1)"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return element;
    }
    
    public WebElement FoldersDocs_PageCount(String targetController)
    {
        WebElement element = null;
        try {
			element = DMDriver.findElement(By.xpath("//div[@ng-controller='"+targetController+"']"))
					.findElement(ng.binding("numberOfPages"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return element;
    }
        
    // Zone specific reusableCode functions
    public WebElement FolderDocs_Zone_DisplayContents(String targetController)
    {
    	logThis("***FolderDocs_Zone_DisplayContents for:" + targetController);
    	WebElement element = null;
        try {
			element = DMDriver.findElement(By.xpath("//div[@ng-controller='"+targetController+"']"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return element;
    }
	    
    public void FolderDocs_NextPage_Click(String targetController) throws InterruptedException
    {
    	WebElement element = null;
    	element = DM_FolderDocs_NextPage(targetController);
    	element.click();
        Thread.sleep(300);
    }

    public void FolderDocs_PrevPage_Click(String targetController) throws InterruptedException
    {
    	WebElement element = null;;
    	element = DM_FolderDocs_PrevPage(targetController);
    	element.click();
        Thread.sleep(300);
    }
    
    /**
     * Written by Josh for use in the Dashboard page.
     * Pulled this out of framework Home_Elements.java
     * TODO - see if we can modify code to be used for all pagination controls.
     * 
     * @param targetController - 'RecentlyUpdatedDocsCtrl', 'RecentlyUpdatedFoldersCtrl', or 'RecentlyUpdatedOthersDocsCtrl'
     * @return WebElement - The Next page control '>' for the specified controller ('My docs', 'My folders', or 'Others docs')
     */
    public WebElement DM_FolderDocs_NextPage(String targetController)
    {
        WebElement element = null;
        try {
			element = WaitUntilElementExists(By.xpath("//div[@ng-controller='"+targetController+"']//a[@ng-click='selectPage(page + 1)']"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    
    /**
     * Written by Josh for use in the Dashboard page.
     * Pulled this out of framework Home_Elements.java
     * TODO - see if we can modify code to be used for all pagination controls.
     * 
     * @param targetController - 'RecentlyUpdatedDocsCtrl', 'RecentlyUpdatedFoldersCtrl', or 'RecentlyUpdatedOthersDocsCtrl'
     * @return WebElement - The Previous page control '<' for the specified controller ('My docs', 'My folders', or 'Others docs')
     */
    public WebElement DM_FolderDocs_PrevPage(String targetController)
    {
        WebElement element = null;
        try {
			element = WaitUntilElementExists(By.xpath("//div[@ng-controller='"+targetController+"']//a[@ng-click='selectPage(page - 1)']"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    
    public boolean Exists_Element_By_Link(String strLink, String strElementDescription)
    {
    	//TODO - There has got to be a better way.
    	//Consider passing back element if found, assert fail if not?
    	try
    	{
        	WebElement element = null;
    		logThis("Looking for " + strElementDescription + " By.linkText...");
    		element = DMDriver.findElement(By.linkText(strLink));
    	} catch (NoSuchElementException e) {
    		logThis("Unable to find '" + strLink + "' By.linkText");
    		return false;
    	}
    	return true;
    }

    public boolean Exists_Element_By_Id(String strId, String strElementDescription)
    {
    	//TODO - There has got to be a better way.
    	try
    	{
        	WebElement element = null;
    		logThis("Looking for " + strElementDescription + " By.id ...");
    		element = DMDriver.findElement(By.id(strId));
    	} catch (NoSuchElementException e) {
    		logThis("Unable to find '" + strId + "' By.id");
    		return false;
    	}
    	return true;
    }
    
    public boolean Exists_Element_By_Name(String strName, String strElementDescription)
    {
    	//TODO - There has got to be a better way.
    	try
    	{
        	WebElement element = null;
    		logThis("Looking for " + strElementDescription + " By.name ...");
    		element = DMDriver.findElement(By.name(strName));
    	} catch (NoSuchElementException e) {
    		logThis("Unable to find '" + strName + "' By.name");
    		return false;
    	}
    	return true;
    }

    public boolean Exists_Element_By_XPath(String strXPath, String strElementDescription)
    {
    	//TODO - There has got to be a better way.
    	try
    	{
        	WebElement element = null;
    		logThis("Looking for " + strElementDescription + " By.xpath ...");
    		element = DMDriver.findElement(By.xpath(strXPath));
    	} catch (NoSuchElementException e) {
    		logThis("Unable to find '" + strXPath + "' By.xpath");
    		return false;
    	}
    	return true;
    }
    
    public String Get_Browser_Handle()
    {
    	/*
    	 * Returns a string guid that identifies the browser used in the test
    	 * Useful if opening new browser windows and switching between them.
    	 */
    	return DMDriver.getWindowHandle();
    }
    
    /**
     * Returns a string containing the title of the web page.
     * @return String
     */
    public String Get_Title()
    {
    	return DMDriver.getTitle();
    }

    /**
     * Returns a string containing the title of the web page.
     * @return String - URL
     */
    public String Get_URL()
    {
    	return DMDriver.getCurrentUrl();
    }
    
    /**
     * Get the number of browsers handles currently open in the test run.
     * @return Integer
     */
    public int Get_Browser_Handle_Count()
    {
    	Set<String> strHandles = DMDriver.getWindowHandles();
    	return strHandles.size();
    }

	/**
	 * Closes all windows except the handle passed in (generally original browser window).
	 * Call Get_Browser_Handle to get guid before opening other windows.
	 */
    public void Close_Extra_Browser_Windows(String strKeeperGuid)
    {
    	for(String handle : DMDriver.getWindowHandles()) 
    	{
        	if (!handle.equals(strKeeperGuid)) 
        	{
        		DMDriver.switchTo().window(handle);
        		DMDriver.close();
        	}
        }
    	DMDriver.switchTo().window(strKeeperGuid);
    }

	/**
	 * If there are multiple browser windows open, this will switch to the first
	 * window it finds with the URL passed in.
	 * 
	 * TODO
	 * Consider adding logic/method to choose window by title.
	 * Maybe check current window handle so we don't switch to the same handle or
	 * pass back the handle of the one we select.
	 * Or...return boolean if we switched to the one we wanted?
	 * 
	 * @param strURL - String
	 */
    public void Switch_Browser_By_URL(String strURL)
    {
    	if(Get_Browser_Handle_Count() > 1)
    	{
        	for(String handle : DMDriver.getWindowHandles()) 
        	{
        		DMDriver.switchTo().window(handle);
        		if (DMDriver.getCurrentUrl().contains(strURL)) 
            	{
        			logThis("Switched driver to point to window with URL: '" + strURL + "'");
        			break;
            	}
            }    		
    	}
    	else
    	{
    		logThis("WARNING: Switch_Browser_By_URL - No action taken, less than 2 windows open.");
    	}

    }

    /**
     * There are two copy/paste controls.  They each have the same class.
     * Haven't figured out how to find element by ng-class attribute yet.
     * Things would be easier if the elements had an ID.
     * 
     * 1. My Docs / Other Docs  --<div ng-class="hasSelected()" class="copy-paste-delete-ctrls open">
     * 2. Folder Trash / My Docs Trash.  --<div ng-class="hasSelectedInTrash()" class="copy-paste-delete-ctrls">
     * 
     * @param IsControlInTrash - Boolean, true if working in trash, false if not.
     * @return - The entire copy/paste control
     */
    public WebElement Get_xControl_Element(Boolean IsControlInTrash)
    {
    	List<WebElement> we = DMDriver.findElements(By.className("copy-paste-delete-ctrls"));
    	if(we.size() > 0)
    	{
    		for(int i = 0; i < we.size(); i++)
    		{
    			if(IsControlInTrash)
    			{
    				//Looking for the control for Trash control
    				if(we.get(i).getAttribute("ng-class").contains("InTrash"))
    				{
    					return we.get(i);
    				}
    			}
    			else
    			{
    				//Looking for the My Docs / Other Docs control
    				if(!we.get(i).getAttribute("ng-class").contains("InTrash"))
    				{
    					return we.get(i);
    				}
    			}
    		}
    	}
    	return null;
    }
	/**
	 * Determine if the Copy/Paste control is presented to user.
	 * Control is only visible when at least one file/folder is selected.
	 * Control is hidden after an action (paste,delete,restore) is complete or all files/folders are de-selected.
	 * @param IsControlInTrash - Boolean, true if working in trash, false if not
	 */
    public Boolean Is_Displayed_xControl(Boolean IsControlInTrash)
    {
    	//WebElement element = DMDriver.findElement(By.className("copy-paste-delete-ctrls"));
    	WebElement element = Get_xControl_Element(IsControlInTrash);
    	if(element.getAttribute("class").contains("open"))
    	{
    		return true;
    	}
    	return false;
    }

	/**
	 * Available commands:
	 * 'copy'- My Docs, Other Docs  **When clicked, toggles to paste/Cancel options only.
	 * 'paste'- My Docs, Other Docs  **When clicked, toggles back to pre-copy controls.
	 * 'delete' - My Docs, Other Docs, My Docs Trash, Trash  **Confirmation window ***Modal, OK/Cancel ("x" to close).
	 * 'restore' - My Docs Trash, Trash.
	 * 'send file link'	- My Docs, Other Docs  **Check availability in Other Docs vs. folder permission.
	 * 'Cancel' - My Docs, Other Docs, My Docs Trash, Trash.
	 * @param IsControlInTrash - Boolean, true if in Folder Trash, false if not
	 * @param strOption - copy, paste, delete, restore, send file link, Cancel
	 */
    public void xControl_Option_Click(Boolean IsControlInTrash, String strOption)
    {
    	//TODO - find a better name for this.
    	if(Is_Displayed_xControl(IsControlInTrash))
    	{
        	//WebElement we = DMDriver.findElement(By.className("copy-paste-delete-ctrls"));
    		WebElement we = Get_xControl_Element(IsControlInTrash);
        	List<WebElement> we2 = we.findElements(By.tagName("div"));
        	if(we2.size() > 0)
        	{
    	    	for(int i = 0; i < we2.size(); i++)
    	    	{
    	    		if(we2.get(i).getText().contains(strOption))
    		    	{
    		    		logThis("Clicking '" + strOption + "' in Copy/Paste control.");
    	    			we2.get(i).click();
    	    			logThis("strOption=" + strOption);
    	    			if(strOption == "delete" && !IsControlInTrash)
    	    			{
    	    				//Confirmation for delete.  Not required in trash.
       	    				Alert alert = DMDriver.switchTo().alert();
   	    	                alert.accept();    	    					
    	    			}
    	    			break;
    		    	}
    	    	}
    	    	//logThis("xControl option not found");
        	}
        	else
        	{
        		logThis("WARNING: No div tags found in Copy/Paste control");
        	}    		
    	}
    	else
    	{
    		logThis("WARNING: Copy/Paste control '" + strOption + "' click requested when copy-paste control not displayed");
    	}
    }

    public Boolean Is_Displayed_xControl_Option(Boolean IsControlInTrash, String strOption)
    {
    	//Validation of options in Copy/Paste control are displayed or not.
    	if(Is_Displayed_xControl(IsControlInTrash))
    	{
        	//WebElement we = DMDriver.findElement(By.className("copy-paste-delete-ctrls"));
    		WebElement we = Get_xControl_Element(IsControlInTrash);
        	List<WebElement> we2 = we.findElements(By.tagName("div"));
        	if(we2.size() > 0)
        	{
    	    	for(int i = 0; i < we2.size(); i++)
    	    	{
    	    		if(we2.get(i).getText().contains(strOption))
    		    	{
    	    			if(we2.get(i).isDisplayed())
    	    			{
        		    		logThis("Found option '" + strOption + "' in Copy/Paste control and it is displayed.");
    	    				return true;
    	    			}
    	    			else
    	    			{
        		    		logThis("Found option '" + strOption + "' in Copy/Paste control and it is NOT displayed.");
    	    				return false;
    	    			}
    		    	}
    	    	}
    	    	logThis("WARNING: Option '" + strOption + "' not found.  Check for UI code change or option spelling (capitalization).");
    	    	return false;
        	}
        	else
        	{
        		logThis("WARNING: No div tags found in Copy/Paste control.  Check for UI code change.");
        	}    		
    	}
    	else
    	{
    		logThis("Copy/Paste control is not displayed.  Check for option ignored.");
    		return false;
    	}
    	return false;
    }
    
	/**
	 * This function returns the table row of the file/folder searched for.
	 * Repeaters used by app to display file/folders:
	 * @param strRepeater - name of the repeater, usually 'file in files'.
	 * "file in docsInTrash" - MyDocsTrash / Trash
	 * "file in files" - MyDocs / OtherDocs / Search Results
     * @param strFileFolder -File or folder name.
	 */
    public WebElement Get_FileFolder_Row(String strRepeater, String strFileFolder)
    {
    	List<WebElement> weRow = DMDriver.findElements(ng.repeater(strRepeater));
    	if(weRow.size() > 0)
    	{
    		for(int i = 0; i < weRow.size(); i++)
        	{
        		if(weRow.get(i).getText().contains(strFileFolder))
        		{
        			//Needed to prevent partial matches, IE Folder1 vs Folder12, so comparing length.
        			List<WebElement> weTD = weRow.get(i).findElements(By.tagName("td"));
        			//TODO - hard coded index for third cell in row.
        			String strTD = weTD.get(2).getText();
        			if(strFileFolder.length() == strTD.length())
        			{
            			return weRow.get(i);   				
        			}
        		}
        	}
    		logThis("Did not find '" + strFileFolder + "'.  It may be on another page or does not exist.");
    	}
    	else
    	{
    		logThis("No elements found for repeater '" + strRepeater + "'");
    	}
    	return null;
    }   

    /**
     * Click on a listed file or folder in a repeater control.
     * Files and Folders behave differently.  Post click validation handled in test case.
	 * @param strRepeater - name of the repeater, usually 'file in files'.
     * @param strFileFolder -File or folder name.
     */
    public void FileFolder_Link_Click(String strRepeater, String strFileFolder)
    {
    	WebElement element = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (element != null)
    	{
    		WebElement link = element.findElement(By.linkText(strFileFolder));
    		logThis("Nav: Clicking link for '" + strFileFolder + "'");
    		link.click();
    	}
    	else
    	{
    		logThis("WARNING: FileFolder_Link_Click called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    }

    /**
     * Right click on a listed file or folder in a repeater control to open context menu.
	 * @param strRepeater - name of the repeater, usually 'file in files'.
     * @param strFileFolder -File or folder name.
     */
    public void FileFolder_Link_Right_Click(String strRepeater, String strFileFolder)
    {
    	/*
    	 * This currently toggles the check box for a listed Folder/File
    	 * Consider passing in another argument to not toggle if desired value already set.
    	 */
    	WebElement element = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (element != null)
    	{
    		WebElement link = element.findElement(By.linkText(strFileFolder));
    		logThis("Right licking link for '" + strFileFolder + "'");
    		
    		Actions clicker = new Actions(DMDriver); 
    		clicker.contextClick(link).perform(); 
    	}
    	else
    	{
    		logThis("WARNING: FileFolder_Link_Right_Click called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    }
    
	/**
	 * This currently toggles the check box for a listed Folder/File.
	 * TODO - Consider passing in another argument to not toggle if desired value already set.
	 * @param strRepeater - name of the repeater, usually 'file in files'.
     * @param strFileFolder -File or folder name.
	 */
    public void Select_FileFolder(String strRepeater, String strFileFolder)
    {
    	WebElement element = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (element != null)
    	{
    		WebElement checkbox = element.findElement(By.tagName("input"));
    		if(checkbox.getAttribute("checked") == null)
    		{
    			logThis(strFileFolder + " checkbox was previously not checked.");
    		}
    		else
    		{
    			logThis(strFileFolder + " checkbox was previously checked.");
    		}
    		logThis("Clicking '" + strFileFolder + "' checkbox.");  //null if not
    		checkbox.click();
    		if(checkbox.getAttribute("checked") == null)
    		{
    			logThis(strFileFolder + " checkbox is now not checked.");
    		}
    		else
    		{
    			logThis(strFileFolder + " checkbox is now checked.");
    		}
    	}
    	else
    	{
    		logThis("WARNING: Select_File_Folder called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    }

    /**
     * Determine if the file or folder has been selected (checkbox checked).
     * @param strRepeater - repeater for table row - IE 'file in files'
     * @param strFileFolder - name of folder or file
     * @return - Boolean - true if selected, false if not.
     */
    public Boolean Is_FileFolder_Selected(String strRepeater, String strFileFolder)
    {
    	WebElement element = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (element != null)
    	{
    		WebElement checkbox = element.findElement(By.tagName("input"));
    		/*
    		 * This seems to work.  Returns string value "true" if checked (not a boolean value), 
    		 * String == true did not work and neither did contains.
    		 * Returns Null if element not checked, easier to work with.
    		 */
    		if(checkbox.getAttribute("checked") != null)
    		{
    			return true;
    		}
   			return false;
    	}
    	logThis("WARNING: Is_File_Folder_Selected called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	return false;
    }
    
    /**
     * Drop down when file/folder is right clicked.
     * Drop down only available in My Docs, Others Docs, and Trash (not Settings/My docs trash)
     * Element does not exist (not hidden) until right click performed.
     * If function times out looking for element it returns false.
     * @return Boolean - True if drop down is found, False if not.
     */
    public Boolean Is_Displayed_FileFolder_Dropdown()
    {
    	try
    	{
        	WebElement element = DMDriver.findElement(By.className("dropdown-menu"));
        	if (element != null)
        	{
        		return true;
        	}
    	} catch (NoSuchElementException e) {
    		return false;
    	}
		return false;
    }
    
    /**
     * Get the options of the context menu after right clicking a file/folder.
     * @return String containing the options available in the drop down.
     */
    public String Get_FileFolder_Dropdown_Options()
    {
    	if(Is_Displayed_FileFolder_Dropdown())
    	{
    		WebElement element = DMDriver.findElement(By.className("dropdown-menu"));
        	return element.getText();		
    	}
    	return "";
    }
    
    /**
     * Drop down when file/folder is right clicked.
     * Select an option to click.
     * @param strOption - 'Copy', 'Delete', 'Lock'(file), 'Summary', 'Download'(file). 'Contents'(folder).
     * 'Paste here' (folder) and 'Move here' (folder) are available when a file/folder has been copied.
     */
    public void FileFolder_Dropdown_Option_Click(String strOption)
    {
    	if(Is_Displayed_FileFolder_Dropdown())
    	{
    		
    		WebElement element = DMDriver.findElement(By.className("dropdown-menu"));
    		if(element.getText().trim().contains(strOption))
    		{
        		WebElement element2 = element.findElement(By.linkText(strOption));
        		logThis("Clicking '" + strOption + "' option in FileFolder drop down.");
        		element2.click();
    		}
    		else
    		{
    			logThis("WARNING: Attempted to click '" + strOption + "' and was unable to find option in the dropdown / Context Menu.");
    		}

    	}
    	else
    	{
    		logThis("WARNING: FileFolder dropdown / Context Menu not found.");
    	}
    }

	/**
	 * Not to be confused with Copy/Paste control send link feature/call -- xControl_Option_Click("send file link")
	 * This is for clicking the email link for a specific file.
	 * @param strRepeater - table row repeater name - IE 'file in files'
     * @param strFile - name of the file
	 */
    public void Send_Link_Click(String strRepeater, String strFile)
    {
    	logThis("Nav: Clicking Send Link for '" + strFile + "'");
    	WebElement elementRow = Get_FileFolder_Row(strRepeater, strFile);
    	if (elementRow != null)
    	{
    		logThis("Found repeater row");
    		List<WebElement> elementA = elementRow.findElements(By.tagName("a"));
        	if(elementA.size() > 0)
        	{
        		for(int i = 0; i < elementA.size(); i++)
            	{
        			logThis("AnchorIndex=" + i);
        			//null pointer exception check
        			if(elementA.get(i).getAttribute("ng-click") != null)
        			{
            			logThis("ng-click found");
        				if(elementA.get(i).getAttribute("ng-click").contains("viewSendFileLink(file)"))
                		{
                			logThis("Found 'viewSendFileLink(file)' at index " + i);
                			if(elementA.get(i).isDisplayed())
                			{
                				elementA.get(i).click();
                			}
                			else
                			{
                				logThis("WARNING: Send_Link_Click called for '" + strFile + "', found element, but anchor tag is not displayed."); 
                			}
                		}   				
        			}
            	}
        	}
        	else
        	{
            	logThis("WARNING: Send_Link_Click called for '" + strFile + "', but no anchor tags found in table row.");        		
        	}
    	}
    	else
    	{
    		logThis("WARNING: Send_Link_Click called for '" + strFile + "', but table row not found.  May not exist, or is on another page.");
    	}
    }
    
    /**
     * Not to be confused with the Info "i" icon near the top right of the page (main-content / main-header / ctrls)
     * Opens the Info/Summary panel for the selected file or folder.
     * @param strRepeater - table row repeater name - IE 'file in files'
     * @param strFileFolder - name of the file or folder
     */
    public void Info_Link_Click(String strRepeater, String strFileFolder)
    {
    	WebElement we = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (we != null)
    	{
    		List<WebElement> we2 = we.findElements(By.tagName("a"));
        	if(we2.size() > 0)
        	{
        		for(int i = 0; i < we2.size(); i++)
            	{
        			//null pointer exception check
        			if(we2.get(i).getAttribute("ng-click") != null)
        			{
            			if(we2.get(i).getAttribute("ng-click").contains("viewSummary(file)"))
                		{
                			logThis("Found 'viewSummary(file)' at index " + i);
                			we2.get(i).click();
                		}        				
        			}
            	}
        	}
        	else
        	{
        		logThis("WARNING: Info_Link_Click called for '" + strFileFolder + "', but no anchor tags found in table row.");
        	}
    	}
    	else
    	{
    		logThis("WARNING: Info_Link_Click called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    }
    
    /**
     * Get the version of the file selected.
   	 * Element is not available in trash.
     * @param strRepeater - table row repeater name - IE 'file in files'
     * @param strFileFolder - name of the file
     * @return - String - version of the file selected.
     */
    public String Get_Version(String strRepeater, String strFileFolder)
    {
    	WebElement we = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (we != null)
    	{
    		WebElement we2 = we.findElement(ng.binding("version"));
    		if(we2 != null)
    		{
        		if(we2.getText() != null)
        		{
        			return we2.getText();
        		}
        		return "";
    		}
    		else
    		{
    			logThis("WARNING: Get_Version called for '" + strFileFolder + "', but version not found in table row.  May have found a folder or searching in Trash.");
    			return "";
    		}
    	}
    	else
    	{
    		logThis("WARNING: Get_Version called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    	return "";
    }
    
    public String Get_Modified_Date(String strRepeater, String strFileFolder)
    {
    	/*
    	 * For some reason, finding element does not work for last-modified binding.  Unknown why.
    	 * If viewing files in trash, version table cell is missing, so index is off.
    	 * Ugly use of intIndex to get around this....
    	 */
    	Integer intIndex = 4;
    	if(strRepeater == "file in docsInTrash") intIndex = 3;
    	
    	WebElement we = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (we != null)
    	{
    		List<WebElement> we2 = we.findElements(By.tagName("td"));
    		return we2.get(intIndex).getText();
/*
    		WebElement we2 = we.findElement(ng.binding("last-modified"));
    		if( we2 != null)
    		{
        		if(we2.getText() != null)
        		{
        			return we2.getText();
        		}       			
    		}
    		else
    		{
    			return "get_modified_date is null";
    		}
*/ 		
    	}
    	else
    	{
    		logThis("WARNING: Get_Modified_Date called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    	return "";
    }
    
    public String Get_Last_Contributor(String strRepeater, String strFileFolder)
    {
    	/*
    	 * For some reason, finding element does not work for last-contributor binding.  Unknown why.
    	 * If viewing files in trash, version table cell is missing, so index is off.
    	 * Ugly use of intIndex to get around this....
    	 */
    	Integer intIndex = 5;
    	if(strRepeater == "file in docsInTrash") intIndex = 4;
    	
    	WebElement we = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (we != null)
    	{
    		//binding works for version, not contributor.  unknown why not.
    		List<WebElement> we2 = we.findElements(By.tagName("td"));
    		return we2.get(intIndex).getText();
/*    		
    		WebElement we2 = we.findElement(ng.binding("last-contributor"));
    		if(we2 !=null)
    		{
        		if(we2.getText() != null)
        		{
        			return we2.getText();
        		} 	    			
    		}
    		else
    		{
    			return "Get_Last_Contributor is null";
    		}
*/
    	}
    	else
    	{
    		logThis("WARNING: Get_Last_Contributor called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    	return "";
    }
    
    public String Get_File_Folder_Icon(String strRepeater, String strFileFolder)
    {
    	/*
    	 * Return the icon for the file/folder displayed.
    	 * Assumption: Image is in the second column of row.
    	 * Parsing the url string to return just the file at the end.
    	 */
    	WebElement elementRow = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (elementRow != null)
    	{
    		List<WebElement> elementTd = elementRow.findElements(By.tagName("td"));
    		if (elementTd != null)
    		{
    			//TODO - Don't use hard coded index.
        		WebElement elementImg = elementTd.get(1).findElement(By.tagName("img"));
        		if(elementImg != null)
        		{
        			String strURL = elementImg.getAttribute("src");
        			//https://usqa2-dm.sharpcloudportal.com/app1/cpo/icons/image.gif
        			String strImg = strURL.substring(strURL.lastIndexOf("/") + 1);
        			
        			return strImg;
        		}
        		else
        		{
        			logThis("WARNING: Get_File_Folder_Icon called for '" + strFileFolder + "', but img info not found.");
        		}
    		}
    		else
    		{
    			logThis("WARNING: Get_File_Folder_Icon called for '" + strFileFolder + "', but row column not found");
    		}

    	}
    	else
    	{
    		logThis("WARNING: Get_File_Folder_Icon called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    	return "";
    }
    
    public Boolean Is_Folder_Shared(String strRepeater, String strFileFolder)
    {
    	/*
    	 * The only difference between displaying the icon or not is in the anchor tag.
    	 * If not displayed, class="ng-hide".  If displayed, class="".
    	 * 
    	 * Files listed in My Docs never not display the icon.
    	 * Files listed in Other Docs always display the shared icon.
    	 */
    	WebElement elementRow = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (elementRow != null)
    	{
    		List<WebElement> elementA = elementRow.findElements(By.tagName("a"));
        	if(elementA.size() > 0)
        	{
        		for(int i = 0; i < elementA.size(); i++)
            	{
        			//null pointer exception check
        			if(elementA.get(i).getAttribute("ng-show") != null)
        			{
        				if(elementA.get(i).getAttribute("ng-show").contains("isShared(file)"))
                		{
                			logThis("Found 'isShared(file)' at index " + i + ". Checking if displayed.");
                			if(!elementA.get(i).getAttribute("class").contains("ng-hide"))
                			{
                				return true;
                			}
                			else
                			{
                				return false;
                			}
                		}   				
        			}
            	}
            	logThis("WARNING: Is_Folder_Shared called for '" + strFileFolder + "', but unable to find the ng-show='isShared(file)' anchor tag.");
            	return false;
        	}
        	else
        	{
            	logThis("WARNING: Is_Folder_Shared called for '" + strFileFolder + "', but no anchor tags found in table row.");
            	return false;
        	}
    	}
    	else
    	{
    		logThis("WARNING: Is_File_Folder_Shared called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    	}
    	logThis("fell through");
    	return false;
    }

    public Boolean Is_Send_Link_Active(String strRepeater, String strFileFolder)
    {
    	/*
    	 * Email icon is filled in if there is an active send link
    	 * For the inner html of the anchor tag:
    	 * <i class="fa fa-envelope-o"></i> - Not Shared
    	 * <i class="fa fa-envelope"></i> - Shared
    	 */
    	WebElement elementRow = Get_FileFolder_Row(strRepeater, strFileFolder);
    	if (elementRow != null)
    	{
    		List<WebElement> elementA = elementRow.findElements(By.tagName("a"));
        	if(elementA.size() > 0)
        	{
        		for(int i = 0; i < elementA.size(); i++)
            	{
        			//null pointer exception check
        			if(elementA.get(i).getAttribute("ng-click") != null)
        			{
            			if(elementA.get(i).getAttribute("ng-click").contains("viewSendFileLink(file)"))
                		{
                			logThis("Found 'viewSendFileLink(file)' at index " + i + ".  Checking if active.");
                			WebElement elementI = elementA.get(i).findElement(By.tagName("i"));
                			if(elementI.getAttribute("class").contains("envelope-o"))
                			{
                				return false;
                			}
                			else
                			{
                				return true;
                			}
                		}   				
        			}
            	}
        	}
        	else
        	{
            	logThis("WARNING: Is_Send_Link_Active called for '" + strFileFolder + "', but no anchor tags found in table row.");
            	return false;
        	}
    	}
    	else
    	{
    		logThis("WARNING: Is_Send_Link_Active called for '" + strFileFolder + "', but table row not found.  May not exist, or is on another page.");
    		return false;
    	}
    	return false;
    }
    
    /**
     * Checks to see if the Info / Summary panel is open
     * <div class="properties-panel right-panel open>
     * "open" text is in the div when displayed and text is missing when panel not displayed.
     */
    public Boolean Is_Displayed_Properties_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("properties-panel"));
    	if(element != null)
    	{
    		if(element.getAttribute("class").indexOf("open") > 0)
    		{
    			return true;
    		}
    	}
    	return false;
    }

	/**
	 * Checks to see if the "Send Link" panel is open.
	 * <div class="send-file-link" ng-controller="SendFileLInkCtrl">
	 * For some reason, isDisplayed always returns true for this control, doesn't behave like other panels.
	 * class attribute toggles between "send-file-link" and "send-file-link  open"
	 */
    public Boolean Is_Displayed_Send_File_Link_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("send-file-link"));
    	if(element != null)
    	{
    		//if(element.isDisplayed())
    		if(element.getAttribute("class").indexOf("open") > -1)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Determine if the Folder Share panel is open.
     * @return Boolean - true if open, false if not.
     */
    public Boolean Is_Displayed_Folder_Share_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("share-panel"));
    	if(element != null)
    	{
    		if(element.getAttribute("class").indexOf("open") > -1)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Determine if the Folder notifications panel is open.
     * Not to be confused with 'file-notifications-panel', which is a sub panel of the Properties (info/summary) panel.
     * @return Boolean - true if open, false if not.
     */
    public Boolean Is_Displayed_Folder_Notifications_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("notifications-panel"));
    	if(element != null)
    	{
    		if(element.getAttribute("class").indexOf("open") > -1)
    		{
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Determine if the Folder history panel is open.
     * Not to be confused with 'file-history-panel', which is a sub panel of the Properties (info/summary) panel.
     * @return Boolean - true if open, false if not.
     */
    public Boolean Is_Displayed_Folder_History_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("history-panel"));
    	if(element != null)
    	{
    		if(element.getAttribute("class").indexOf("open") > -1)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Determine if the Folder trash panel is open.
     * @return Boolean - true if open, false if not.
     */
    public Boolean Is_Displayed_Folder_Trash_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("trash-panel"));
    	if(element != null)
    	{
    		if(element.getAttribute("class").indexOf("open") > -1)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
	/*
	 * This method puts all pop up messages into an array.
	 * 
	 * TODO - This is just sample code to loop through multiple pop up messages.
	 * From here, we can get individual messages, or close them if we wanted to.
	 * 
	 * It works if there is at least one message displayed.
	 * Will need to determine if this is useful and what to return
	 * 
	 * Get_Popup_Msgs() Might be more practical.
	 */
    public void Get_Popups()
    {
    	String strRepeater = "msg in infoMessages";
    	if(Is_Popup_Msg_Shown())
    	{
        	List<WebElement> weMsg = DMDriver.findElements(ng.repeater(strRepeater));
        	if(weMsg.size() > 0)
        	{
        		for(int i = 0; i < weMsg.size(); i++)
            	{
        			logThis("Msg index '" + i + "' = '" + weMsg.get(i).getText() + "'");
            	}
        	}
        	else
        	{
        		logThis("No elements found for repeater '" + strRepeater + "'");
        	}    		
    	}
    }

	/**
	 * Determine if there is a pop up message in top right corner of browser.
	 * Errors are shown until closed 
	 * 	- Example: 404 or 500 level errors
	 * Informative pop ups are only listed for a second or two.
	 * 	- Example: document(s) copied, File uploaded successfully, etc..
	 */
    public Boolean Is_Popup_Msg_Shown()
    {
    	try
    	{
        	WebElement we = DMDriver.findElement(By.className("user-bar"));
        	WebElement we2 = we.findElement(By.tagName("div"));
    		if(we2.isDisplayed())
    		{
        		return true;    			
    		}
    	} catch (NoSuchElementException e) {
    		return false;
    	}
		return false;
    }

	/**
	 * Returns all the text within div class="user-bar"
	 * Currently, the only text this div contains are from pop up messages.
	 * 
	 * WARNING!!! Timing could be an issue when using this function.
	 * The inner HTML of the div is dynamic,
	 * repeater "msg in infoMessages" is only in the DOM when a pop up is shown.
	 */
    public String Get_Popup_Msgs()
    {
    	WebElement element = DMDriver.findElement(By.className("user-bar"));
    	return element.getText().trim();
    }
    

    /**
     * Code taken from Framework.ReusableCode.java
     * Copied over in case needed after we de-couple DM.
     * ====================================================================================================
     */
    
    // Verifies if an element is visible or not
    public WebElement IfElementisVisible(By by)
    {
        WebElement visibleElement = null;
        logThis("Checking for this element " + by);

        try
        {
            if (DMDriver.findElements(by).size() > 0)
            {
                visibleElement = DMDriver.findElement(by);
                logThis("SUCCESS - Found this element " + visibleElement.getText());
            }
            else
                logThis("Element " + by + " was not found. Continuing test...");
        }
        catch(Exception e)
        {
            logThis("Exception when looking for element " + by + ". Continuing test, but exception was " + e);
        }            
        return visibleElement;
    }

    public Boolean IfElementExists(By by)
    {

        Boolean elementExists = false;
        logThis("Checking for this element " + by);

        if (DMDriver.findElements(by).size() > 0)
            elementExists = true;

        return elementExists;
    }

    public WebElement VerifyElementisVisible(By by)
    {
        WebElement visibleElement = null;
        logThis("Verifying this element " + by);
        try
        {
            visibleElement = DMDriver.findElement(by);
            if (visibleElement != null)
                logThis("Found this element " + visibleElement.getText());
        }
        catch (Exception e)
        {
           // log.Error("Element " + by + " was not found. Failing test...", e);
            Assert.fail("Element " + by + " was not found Failing test...");
        }

        return visibleElement;
    }

	public WebElement WaitUntilElementExists(By by) throws Exception
    {
        logThis("Waiting for this element to exist " + by);

        WebElement element = null;
        for (int second = 0; ; second++)
        {
            if (second >= timeoutValue_sleep) 
                Assert.fail("We timed out while waiting for an element to appear onscreen. Either we are not on the correct page where the element exists or the element we are looking for cannot be found " + by);

            if (DMDriver.findElements(by).size() > 0)
            {
                element = DMDriver.findElement(by);
                break;
            }

            Thread.sleep(100);
        }
        if (element != null)
            logThis("SUCCESS - WaitUntilElementExists found expected element");

        return element;
    }

    public List<WebElement> WaitUntilElementsExists(By by) throws Exception
    {
        List<WebElement> listElements = new ArrayList<WebElement>();
        List<WebElement> rawList = DMDriver.findElements(by);
        logThis("Waiting for the elements to exist " + by);

        //var rawList = driver.findElements(by);
        logThis("Waiting for the list to exist " + rawList.size());
        for (int second = 0; ; second++)
        {
            if (second >= timeoutValue_sleep)
                Assert.fail("We timed out while waiting for an element to appear onscreen. Either we are not on the correct page where the element exists or the element we are looking for cannot be found " + by);

            if (DMDriver.findElements(by).size() > 0)
            {
                logThis("WaitUntilElementExists found expected elements");
                rawList = DMDriver.findElements(by);
                break;
            }

            Thread.sleep(100);
        }
        if (rawList != null)
        {                
            for(Iterator<WebElement> i = rawList.iterator(); i.hasNext(); ) {
            	    WebElement individualElement = i.next();
            	    listElements.add(individualElement);
            	    logThis("Element found for list: " + individualElement.getText());            			
            }
            logThis("SUCCESS - WaitUntilElementExists found expected elements");
        }

        return listElements;
    }

}