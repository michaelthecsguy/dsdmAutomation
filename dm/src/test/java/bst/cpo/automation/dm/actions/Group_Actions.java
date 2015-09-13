package bst.cpo.automation.dm.actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;

public class Group_Actions extends BaseTest
{
  
    public Group_Actions()
    {
    	//Navigation to Groups found in Settings_Actions Groups_Click()
    }

    public void Create_Group(String strGroupName, String strDescription, String strMember, String strSubGroup)
    {
    	/*
    	 * Pre-Conditions: 1. Logged in as BA.  2. Navigate to Settings > Groups
    	 * Group Name and Member are required.  Description and sub group are not.
    	 * Save and Create button not enabled until required fields are set.
    	 * This only supports adding one member: do the following if you need more
    	 * 1. Run the methods separately and call Add_Member_To_Group multiple times.
    	 * 2. Edit the group after creation and add a member.
    	 * Ditto for sub groups.
    	 */
    	Create_Group_Click();
    	Set_Group_Name(strGroupName);
    	Set_Group_Description(strDescription);
    	Add_Member_To_Group(strMember);
    	Add_Sub_Group_To_Group(strSubGroup);
    	Create_Group_Submit();
    }

    public void Create_Group_Click()
    {
    	//User must be a BA for this element to be available.
    	
    	logThis("Nav: Settings > Groups > Create a new group");  
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/button"));
    	element.click();
    }
    
    public void Set_Group_Name(String strGroupName)
    {
    	//<input type="text" class="pure-input-1 ng-pristine ng-invalid ng-invalid-required" ng-model="groupToCreate.groupName" required="" maxlength="128">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[3]/div/div/form/input[1]"));
    	logThis("Set: Group Name text to '" + strGroupName + "'");
    	element.sendKeys(strGroupName);
    }
    
    public void Set_Group_Description(String strDescription)
    {
    	//<textarea class="pure-input-1 ng-pristine ng-valid" ng-model="groupToCreate.groupDescription" maxlength="255"></textarea>
    	if(strDescription == null || strDescription.trim() == ""){
    		logThis("Description null or empty.  Optional field 'Group Description' not set.");
    	} else {
	    	logThis("Set: Group Description text to '" + strDescription + "'");
    		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[3]/div/div/form/textarea"));
	    	element.sendKeys(strDescription);
    	}
    }
    
    public void Add_Member_To_Group(String strMember)
    {
    	WebElement element = DMDriver.findElement(By.id("s2id_autogen2"));
    	logThis("Set: Add Member to Group '" + strMember + "'");
    	element.click();
    	element.sendKeys(strMember);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	element.sendKeys(Keys.ENTER);
    }
    
    public void Add_Sub_Group_To_Group(String strSubGroup)
    {
    	//<input type="text" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" class="select2-input" id="s2id_autogen3" placeholder="" style="width: 20px;">
    	if(strSubGroup == null || strSubGroup.trim() == ""){
    		logThis("Sub Group null or empty.  Optional field 'Sub Group' not set.");
    	} else {
        	WebElement element = DMDriver.findElement(By.id("s2id_autogen3"));
        	logThis("Set: Add Sub Group to Group '" + strSubGroup + "'");
        	element.click();
        	element.sendKeys(strSubGroup);
        	element.sendKeys(Keys.ENTER);
    	}
    }
    
    public void Create_Group_Submit()
    {
    	//<input type="submit" class="pure-button pure-button-primary new-folder-btn" ng-disabled="groupForm.$invalid" value="Save and create" disabled="disabled">
    	//TODO - popup notification "group added successfully", need to validate.
    	if(Is_Enabled_Create_Group_Button())
    	{
        	logThis("Submit: Save and create a group");
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[3]/div/div/form/input[4]"));
        	element.click();    		
    	} else {
    		logThis("Create_Group_Submit called and 'Save and create' button was not enabled.");
    		Assert.fail("Create_Group_Submit called and 'Save and create' button was not enabled.");
    	}
    }

    public void Create_Group_Cancel()
    {
    	if(!Is_Open_New_Group_Panel())
    	{
    		logThis("WARNING: Request to close/cancel New Group panel, but panel not displayed.");
    	} else {
    		logThis("Closing New Group panel");
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[3]/h2/i"));
        	element.click();    		
    	}
    }
    
    public boolean Is_Enabled_Create_Group_Button()
    {
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
	    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[3]/div/div/form/input[4]"));
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
    
    public void Search_Group(String strGroup)
    {
    	/*
    	 * Using the Div class that wraps up the search input
    	 * No minimum chars required for search.
    	 * Send text works, but enter doesn't for some reason.
    	 * Clicking on search result instead.
    	 */
    	WebElement element = DMDriver.findElement(By.id("s2id_select-share-user"));
    	
    	logThis("Submit: Search for group '" + strGroup + "'");
    	element.click(); //opens input textbox
    	element.sendKeys(strGroup);
    	element.sendKeys(Keys.ENTER);
    	
    	//TODO - This is bad way to handle selecting a group, need to fix it.
    	WebElement element2 = DMDriver.findElement(By.id("select2-results-1"));
    	element2.click();
    }
    
    public boolean Is_Open_New_Group_Panel()
    {
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
	    	WebElement element = DMDriver.findElement(By.className("add-group-panel"));
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
    
    public void Delete_Group_Click()
    {
    	//<button ng-click="openDeletePanel()" class="pure-button pure-button-primary horizontal-button ng-binding" ng-show="checkButtonAction('group_delete_button')"><i class="fa fa-trash-o"></i> Delete</button>
    	logThis("Group Delelete button clicked");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/button[1]"));
    	element.click();
    }

    public void Delete_Group_Confirm_Delete_Click()
    {
    	//After Delete_Group_Click()
    	//<button class="pure-button pure-button-primary ng-binding" ng-click="deleteGroup(groupToDisplay.groupname)">Delete</button>
    	//TODO - check confirmation pop up "Group deleted successfully"
    	logThis("Group Delete Confirm - Clicking Delete button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[5]/div/div/button[1]"));
    	element.click();
    }
    
    public void Delete_Group_Confirm_Cancel_Click()
    {
    	/*
    	 * After Delete_Group_Click()
    	 * Cancel button, not to be confused with "X" button - Delete_Group_Cancel()
    	 */
    	logThis("Group Delete Confirm - Clicking Cancel button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[5]/div/div/button[2]"));
    	element.click();

    }
    
    public void Delete_Group_Cancel()
    {
    	//After Delete_Group_Click()
    	// "X" button, not to be confused with Delete_Group_Confirm_Cancel_Click()
    	logThis("Closing Delete Confirmation window with 'X' button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[5]/h2/i"));
    	element.click();

    }

    public boolean Is_Open_Delete_Group_Panel()
    {
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
    		WebElement element = DMDriver.findElement(By.className("delete-group-panel"));
        	if(element.isDisplayed()){
        		return true;
        	}    		
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    	return false;
    }
    
    public void Edit_Group_Click()
    {
    	//<button ng-click="editGroup(groupToDisplay.groupname)" class="pure-button pure-button-primary horizontal-button ng-binding" ng-show="checkButtonAction('group_edit_button')"><i class="fa fa-edit"></i> Edit</button>
    	logThis("Nav: Edit Group - Clicking Edit button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/button[2]"));
    	element.click();   	
    }
    
    public boolean Is_Open_Edit_Group_Panel()
    {
    	//TODO - this is a "right-panel" open.  isDisplayed always = true.
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
    		WebElement element = DMDriver.findElement(By.className("edit-group-panel"));
        	//if(element.isEnabled())
        	if(element.getText().length() > 0)  //workaround found
        	{
        		return true;
        	}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    	return false;
    }

    public void Edit_Group_Description(String strDescription)
    {
    	//<textarea class="pure-input-1 ng-pristine ng-valid" ng-model="groupToEdit.groupDescription" maxlength="255"></textarea>
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[4]/div/div/form/textarea"));
    	element.click(); //opens input textbox
    	element.clear();
    	element.sendKeys(strDescription);
    }

    public void Edit_Group_Add_Member(String strMember)
    {
    	//<textarea class="pure-input-1 ng-pristine ng-valid" ng-model="groupToEdit.groupDescription" maxlength="255"></textarea>
    	
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[4]/div/div/form/textarea"));
    	element.click(); //opens input textbox
    	element.sendKeys(strMember);
    	element.sendKeys(Keys.ENTER);
    }

    public void Edit_Group_Remove_Member(String strMember)
    {
    	//Group must have at least one memeber.  Logic check needed (Popup warning given if removing last member)
    	logThis("WARNING: Feature not complete - Edit_Group_Remove_Member");
    }
    
    public void Edit_Group_Add_Sub_Group(String strGroup)
    {
    	//<textarea class="pure-input-1 ng-pristine ng-valid" ng-model="groupToEdit.groupDescription" maxlength="255"></textarea>
    	
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[4]/div/div/form/textarea"));
    	element.click(); //opens input textbox
    	element.sendKeys(strGroup);
    	element.sendKeys(Keys.ENTER);
    }
 
    public void Edit_Group_Remove_Sub_Group(String strGroup)
    {
    	logThis("WARNING: Feature not complete - Edit_Group_Remove_Sub_Group");
    }
    
    public void Edit_Group_Submit()
    {
    	//<input type="submit" class="pure-button pure-button-primary new-folder-btn" ng-disabled="groupEditForm.$invalid" value="Save">
    	//TODO - popup notification "Group updated successfully!", need to validate.
    	//panel closes after save
    	logThis("Submit: Edit Group 'Save'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[4]/div/div/form/input[3]"));
    	element.click();    		
    }
    
    public void Edit_Group_Cancel()
    {
    	logThis("Closing Edit Group window with 'X' button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[4]/h2/i"));
    	element.click(); 	
    }
    
    public String Get_Search_Result_Name()
    {
    	//TODO - add feature
    	//<label translate="" class="ng-scope ng-binding">tenant_bb7b05d0-43ba-4d6c-9c6f-19c8144db134_testGroup</label>
    	//WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[4]/h2/label"));
    	//return element.getText();
    	//TODO - add feature
    	return "WARNING - Feature not complete";
    }
    
    public String Get_Search_Result_Description()
    {
    	//Does not return value after search
    	//Returns value after edit submit/panel close.
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/div/span"));
    	return element.getText();
    }
    
    public String Get_Search_Result_Member()
    {
    	//TODO - add feature
    	//<li ng-repeat="user in groupToDisplay.memberUsers | orderBy: user" class="ng-binding ng-scope">sla1ba US QA2</li>
    	//*[@id="app"]/div/div[4]/div/div/div[2]/div[1]/div[2]/div/ul[1]/li
    	return "WARNING - Feature not complete";
    }
    
    public String Get_Search_Result_Sub_Group()
    {
    	//TODO - add feature
    	//<li ng-repeat="group in groupToDisplay.memberGroups | orderBy: group" class="ng-binding ng-scope"></li>
    	//*[@id="app"]/div/div[4]/div/div/div[2]/div[1]/div[2]/div/ul[2]/li
    	return "WARNING - Feature not complete";
    }
}
