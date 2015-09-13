package bst.cpo.automation.dm.actions;

//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;

public class AddFolderFile_Actions extends BaseTest
{

    public AddFolderFile_Actions()
    {
    	/**
    	 * Features for the "Add a new folder" and "Import a file" controls.
    	 * Controls found while on My Docs page, or Other Docs (not root) and
    	 * if user has permission (not available with Read Only folders)
    	 */
    }

    /**
     * Add a new folder.
     * Required - strFolderName
     * Optional - strFolderDescription, strFolderDocType (null or "" if not needed).
     */
    public void Add_New_Folder(String strFolderName, String strFolderDescription, String strFolderDocType)
    {
    	Nav_New_Folder_Click();
    	Set_Folder_Name(strFolderName);
   		Set_Folder_Description(strFolderDescription);
    	Set_Folder_Doc_Type(strFolderDocType);
    	Create_Folder_Click();
    }
    
    public void Nav_New_Folder_Click()
    {
    	//<a href="" ng-click="viewAddNewFolder()" ng-show="isMainActionEnabled('create_folder')" data-hint="Add a new folder" class="add-folder hint--bottom"><i class="fa fa-plus-circle"></i><span translate="" class="ng-scope">Add a new folder</span></a>
    	logThis("Nav: Add a new folder");
    	WebElement element = DMDriver.findElement(By.className("add-folder"));
    	//element might exist, but is not displayed, IE root of Other Docs.
        Assert.assertTrue(element.isDisplayed(), "Failed - 'Add a new folder' button not displayed");  
       	element.click();
    }

    public void Create_Folder_Click()
    {
    	//<input type="submit" class="pure-button pure-button-primary new-folder-btn" ng-disabled="folderForm.$invalid" value="Create">
    	logThis("Submit: 'Create'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[4]/div/div/form/input[2]"));
    	element.click();
    }
    
    public void Set_Folder_Name(String strFolderName)
    {
    	//<input type="text" name="folderName" placeholder="Folder name" class="pure-input-1 ng-pristine ng-invalid ng-invalid-required" ng-model="folderToCreate.docTitle" maxlength="255" required="">
    	logThis("Set: Folder Name = '" + strFolderName + "'");
    	WebElement element = DMDriver.findElement(By.name("folderName"));
    	element.sendKeys(strFolderName);
    }
    
    public void Set_Folder_Description(String strDesc)
    {
    	//<textarea placeholder="Describe this folder" class="pure-input-1 ng-pristine ng-valid" ng-model="folderToCreate.docDescription" maxlength="512"></textarea>
    	
    	if(strDesc == null || strDesc.trim() == ""){
    		logThis("Folder Description null or empty.  Optional field 'Description' not set.");
    	} else {
    		logThis("Set: Folder Description = '" + strDesc + "'");
    		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[4]/div/div/form/textarea"));
        	element.clear();
    		element.sendKeys(strDesc);    		
    	}
    }
    
    public void Set_Folder_Doc_Type(String strFolderDocType)
    {
    	//<select ng-options="d.name for d in docTypes" ng-model="folderToCreate.docType" class="ng-pristine ng-valid">
    	if(strFolderDocType == null || strFolderDocType.trim() == ""){
    		logThis("Folder Doc Type null or empty.  Optional field 'Doc Type' not set.");
    	} else {
    		logThis("Set: Folder Doc Type = '" + strFolderDocType + "'");
    		Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[4]/div/div/form/label[4]/select")));
	   		droplist.selectByVisibleText(strFolderDocType);
    	}
    }
    
    /**
     * Determine if the 'New folder' panel is open.
     * Div tag class is dynamic and contains "open" if it is displayed.
     */
    public boolean Is_Open_New_Folder_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("new-folder-panel"));

    	if(element.getAttribute("class").contains("open")){
    		return true;
    	}
    	return false;
    }
    
    /**
     * Determine if the 'Import this file' panel is open.
     * Div tag class is dynamic and contains "open" if it is displayed.
     */
    public boolean Is_Open_Import_File_Panel()
    {
    	WebElement element = DMDriver.findElement(By.className("import-file-panel"));
    	if(element.getAttribute("class").contains("open")){
    		return true;
    	}
    	return false;
    }
    
    public void Cancel_Add_Folder()
    {
    	if(!Is_Open_New_Folder_Panel())
    	{
    		logThis("WARNING: Request to close/cancel New Folder panel, but panel not displayed.");
    	} else {
    		logThis("Closing New Folder panel");
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[4]/h2/i"));
        	element.click();    		
    	}
    }
    
    public void Cancel_Import_File()
    {
    	if(!Is_Open_Import_File_Panel())
    	{
    		logThis("WARNING: Request to close/cancel Import File panel, but panel not displayed.");
    	} else {
    		logThis("Closing Import File panel");
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[3]/h2/i"));
        	element.click();
    	}
    }
    
    public void Import_File(String strFileName, String strFilerDocType)
    {
    	Nav_Import_File_Click();
    	Set_File_Name(strFileName);
    	Set_File_Doc_Type(strFilerDocType);
    	Import_File_Click();
    }
    
    public void Nav_Import_File_Click()
    {
    	//<a href="" ng-click="viewAddNewFolder()" ng-show="isMainActionEnabled('create_folder')" data-hint="Add a new folder" class="add-folder hint--bottom"><i class="fa fa-plus-circle"></i><span translate="" class="ng-scope">Add a new folder</span></a>
    	logThis("Nav: Import a file");
    	WebElement element = DMDriver.findElement(By.linkText("Import a file"));
    	//element might exist, but is not displayed, IE root of Other Docs.
        Assert.assertTrue(element.isDisplayed(), "Failed - 'Import a file' button not displayed");  
       	element.click();
    }

    public void Set_File_Name(String strFileName)
    {
    	//This method bypasses the dialog window.
    	//strFileName must be a full, valid path containing the filename and extension.  Example "C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg"
    	//<input type="file" file-model="fileToImport.file" ng-model="fileToImport.filename" valid-file="" id="fileUpload" class="ng-valid ng-dirty">
    	logThis("Set: Choose file = '" + strFileName + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='fileUpload'] "));
    	element.sendKeys(strFileName);
    }
  
    public void Set_File_Doc_Type(String strFileDocType)
    {
    	logThis("Set: File Type = '" + strFileDocType + "'");
    	if(strFileDocType == null || strFileDocType.trim() == ""){
    		logThis("File Doc Type null or empty.  Optional field 'Doc Type' not set.");
    	} else {
	    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[3]/div/form/div/label[3]/select")));
	   		droplist.selectByVisibleText(strFileDocType.trim());
    	}
    }
    
    public void Import_File_Click()
    {
    	//<input type="submit" ng-disabled="importForm.$invalid || progressVisible || fileToImport.file === undefined" value="Import this file" class="pure-button pure-button-primary import-file-btn">
    	logThis("Submit: 'Import this file'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[3]/div/form/div/input"));
    	element.click();
    }
    
    public boolean Is_Enabled_Create_Folder_Button()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[4]/div/div/form/input[2]"));
    	if(!element.isEnabled()){
    		return false;
    	}
    	return true;
    }
    
    public boolean Is_Enabled_Import_File_Button()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/div[3]/div/form/div/input"));
    	if(!element.isEnabled()){
    		return false;
    	}
    	return true;
    }
}
