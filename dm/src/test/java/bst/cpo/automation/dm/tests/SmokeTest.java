package bst.cpo.automation.dm.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;
import bst.cpo.automation.dm.actions.Home_Actions;
import bst.cpo.automation.dm.actions.Info_Actions;
import bst.cpo.automation.dm.actions.Login_Actions;
import bst.cpo.automation.dm.actions.Search_Actions;
import bst.cpo.automation.dm.actions.Settings_Actions;
import bst.cpo.automation.dm.actions.AddFolderFile_Actions;
import bst.cpo.automation.dm.actions.Reusable_Actions;
import bst.cpo.automation.dm.actions.User_Actions;
import bst.cpo.automation.dm.actions.SendLink_Actions;
import bst.cpo.automation.dm.actions.FolderShare_Actions;
//import bst.cpo.automation.dm.actions.MyRecentlyUpdatedDocs_Actions;
//import bst.cpo.automation.dm.actions.MyRecentlyUpdatedFolders_Actions;
//import bst.cpo.automation.dm.actions.OthersRecentlyUpdatedDocs_Actions;
import bst.cpo.automation.dm.actions.SyncComputers_Actions;

public class SmokeTest extends BaseTest
{	
	/** 
	 * Add classes referenced classes here
	 * so you don't have to add them in every test.
	 */
	Login_Actions login = new Login_Actions(DMDriver);
	Home_Actions home= new Home_Actions(DMDriver);
	Settings_Actions settings = new Settings_Actions();
	User_Actions user = new User_Actions();
	AddFolderFile_Actions addfolderfile =new AddFolderFile_Actions();
	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
	Search_Actions search = new Search_Actions();
	Info_Actions info = new Info_Actions(DMDriver);
	SendLink_Actions sendlink = new SendLink_Actions(DMDriver);
	FolderShare_Actions foldershare = new FolderShare_Actions(DMDriver);
	
	SyncComputers_Actions sync = new SyncComputers_Actions();
	
    public SmokeTest()
    {
    	
    }
    
    @Test
    public void Bad_Login() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin Bad_Login");
            Start_DMTest();
            login.BasicUser_Login("badlogin", "password");
            
            if(login.Is_Bad_Login())
            {
            	logThis("Found bad login information.");
            }
            else
            {
            	Assert.fail("Bad_Login_Test - Expected bad login login information not found!");
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Good_Login() throws InterruptedException
	{
        try
        {	
        	logThis("Begin: Good_Login");
            //Start_DMTest();
            login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
        	//login.BasicUser_Login(adminUserUnderTest, adminUserUnderTestPwd);
            Thread.sleep(5000);
            isUserBA = false;
            Start_DMAngular();
            Thread.sleep(1000);
            
            //Quick validation of what should be a good login.
            if(!home.Get_Nav_Tree_Header().contains("Home"))
            {
            	logThis("WARNING: Good_Login_Test - Unable to log in.  Double check variables in xml file.");
            	Assert.fail("Unable to log in with user '" + basicUserUnderTest + "'.");
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Dashboard_Expand_Collapse_Gadgets() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Dashboard_Expand_Collapse_Gadgets");
        	logThis("WARNING: Dashboard_Expand_Collapse_Gadgets test not complete. Requires validation of actions.  Failing test until complete.");
        	logThis("Toggle 'My docs' collapse and expand");
        	//Determine state expanded/collapsed My docs
        	        	
        	reuse.FoldersDocs_Collapse("RecentlyUpdatedDocsCtrl");
        	Thread.sleep(1000);
        	
        	//Validate toggle
        	
        	reuse.FoldersDocs_Collapse("RecentlyUpdatedDocsCtrl");
        	Thread.sleep(1000);
        	
        	//Validate toggle back
        	
        	logThis("Toggle 'My folders' collapse and expand");
        	//Determine state expanded/collapsed My folders
        	
        	reuse.FoldersDocs_Collapse("RecentlyUpdatedFoldersCtrl");
        	Thread.sleep(1000);
        	
        	//Validate toggle
        	
        	reuse.FoldersDocs_Collapse("RecentlyUpdatedFoldersCtrl");
        	Thread.sleep(1000);        	
        	
        	//Validate toggle back
        	
        	logThis("Toggle 'Others docs' collapse and expand");
        	//Determine state expanded/collapsed Other folders
        	
        	reuse.FoldersDocs_Collapse("RecentlyUpdatedOthersDocsCtrl");
        	Thread.sleep(1000);
        	
        	//Validate toggle
        	
        	reuse.FoldersDocs_Collapse("RecentlyUpdatedOthersDocsCtrl");
        	Thread.sleep(1000);      	
        	
        	//Validate toggle back
        	
        	Assert.fail("Test not complete, requires action validation.");
        	
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Help_Main_Nav() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Help_Main_Nav");
        	
        	//TODO - Determine how much to validate in Help page.
        	
        	String strParent = reuse.Get_Browser_Handle();
        	logThis("Before Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	home.Nav_Help_Click();
        	Thread.sleep(1000);
        	logThis("After Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/user/en/index.htm");
        	//Help file uses frames...doing a simple check on page title.
        	if(!reuse.Get_Title().contains("Cloud Portal Office Web Interface"))
        	{
        		reuse.Close_Extra_Browser_Windows(strParent);
        		Assert.fail("Did not find expected page title 'Cloud Portal Office Web Interface'");
        	}
        	
        	logThis("Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	reuse.Close_Extra_Browser_Windows(strParent);
        	logThis("After Close Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	Thread.sleep(1000);

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Help_Footer_Link() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Help_Footer_Link");
        	
        	//TODO - Determine how much to validate in Help page.
        	
        	String strParent = reuse.Get_Browser_Handle();
        	logThis("Before Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	home.Nav_Help_Link_Click();
        	Thread.sleep(1000);
        	logThis("After Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/user/en/index.htm");
        	//Help file uses frames...doing a simple check on page title.
        	if(!reuse.Get_Title().contains("Cloud Portal Office Web Interface"))
        	{
        		reuse.Close_Extra_Browser_Windows(strParent);
        		Assert.fail("Did not find expected page title 'Cloud Portal Office Web Interface'");
        	}
        	
        	logThis("Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	reuse.Close_Extra_Browser_Windows(strParent);
        	logThis("After Close Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	Thread.sleep(1000);

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Terms_Of_Use_Footer_Link() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Terms_Of_Use_Footer_Link");
        	
        	//TODO - Determine how much to validate in Terms Of Use page.
        	
        	String strParent = reuse.Get_Browser_Handle();
        	logThis("Before Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	home.Nav_Terms_Link_Click();
        	Thread.sleep(1000);
        	logThis("After Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	reuse.Switch_Browser_By_URL("http://downloads.sharpcloudportal.com.s3.amazonaws.com/usqa2/help/policy/cloudportaloffice_terms.htm");
        	
        	WebElement element = DMDriver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td/h3"));
        	if(!element.getText().contains("Terms of Use"))
        	{
        		reuse.Close_Extra_Browser_Windows(strParent);
        		Assert.fail("New window did not contain expected header info - 'Terms of Use'");
        	}
        	
        	logThis("Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	reuse.Close_Extra_Browser_Windows(strParent);
        	logThis("After Close Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	Thread.sleep(1000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    } 
    
    @Test
    public void Privacy_Footer_Link() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Privacy_Footer_Link");

        	//TODO - Determine how much to validate in Privacy page.
        	
        	String strParent = reuse.Get_Browser_Handle();
        	logThis("Before Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	home.Nav_Privacy_Link_Click();
        	Thread.sleep(1000);
        	logThis("After Open Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	
        	reuse.Switch_Browser_By_URL("http://downloads.sharpcloudportal.com.s3.amazonaws.com/usqa2/help/policy/cloudportaloffice_policy.htm");
        	
        	WebElement element = DMDriver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td/h3"));
        	if(!element.getText().contains("Cloud Portal Privacy Policy"))
        	{
        		reuse.Close_Extra_Browser_Windows(strParent);
        		Assert.fail("New window did not contain expected header info - 'Cloud Portal Privacy Policy'");
        	}
        	
        	logThis("Closing 'Privacy' window");
        	reuse.Close_Extra_Browser_Windows(strParent);
        	logThis("After Close Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	Thread.sleep(1000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Search_User() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Search_User");
        	
        	String strUser = "rt9user90";
        	
        	home.Nav_Settings_Click(isUserBA);
        	Thread.sleep(1000);
        	settings.Users_Click();
        	Thread.sleep(1000);
        	user.Search_For_User(strUser);
    		Thread.sleep(1000);
    		
    		//TODO - We can validate more expected values, just retrieving info here.
    		logThis("Get: User name='" + user.Get_Username() + "'");
    		logThis("Get: User First name='" + user.Get_User_First_Name() + "'");
    		logThis("Get: User Last name='" + user.Get_User_Last_Name() + "'");
    		logThis("Get: Email='" + user.Get_User_Email() + "'");
    		logThis("Get: Company='" + user.Get_User_Company() + "'");
    		logThis("Get: TenantID='" + user.Get_User_TenantID() + "'");
    		logThis("Get: Groups='" + user.Get_User_Groups() + "'");

    		//Assumption here is that login user name = first name
    		if(!user.Get_User_First_Name().contains(strUser))
    		{
    			Assert.fail("Did not find expected 'First name:' info '" + strUser + "'");
    		}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Subscribe_My_Docs_Notifications() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Subscribe_My_Docs_Notifications");
        	
        	//Test clean-up/navigation..
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
        	}
        	else
        	{
	    		home.Nav_Dashboard_Click();
	    		Thread.sleep(1000);
	    		home.Nav_MyDocs_Click();
	    		Thread.sleep(1000);
        	}
        	
        	home.Folder_Notifications_Link_Click();
        	Thread.sleep(1000);
        	
        	//Folder notifications-panel right panel open.  ng-controller="NotificationsListCtrl"
        	//File properties-panel right-panel open.  (sub) file-details
        	
        	//check if panel open.
        	
        	//check if currently subscribed.
        	
        	//click subscribe if not.
        	
        	//check unsubscribe toggle
        	
        	//Repeat for Creation, Modification, Add Notes.


        	logThis("WARNING: Test case not complete");
        	Assert.fail("Test Case not finished.");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    /**
     * Add 4 folders.
     * Additional validation for first folder created.
     * TODO - add checks if folder already exists before creating .. or ..
     * Check if we created a duplicate folder "<folder> (i)"
     */
    @Test
    public void Add_Folders() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Add_Folders");
        	
        	String strFolder1 = "Folder1";
        	String strFolder1Desc = "Test Descript";
        	String strFolder1DocType = "Bill Of Lading";
        	String strFolder2 = "Folder2";
        	String strFolder3 = "Folder3";
        	String strFolder4 = "Folder4";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
        	}
        	else
        	{
	    		home.Nav_Dashboard_Click();
	    		Thread.sleep(1000);
	    		home.Nav_MyDocs_Click();
	    		Thread.sleep(1000);
        	}
        	
        	logThis("Check: 'New folder' panel should not open.");
        	if(addfolderfile.Is_Open_New_Folder_Panel())
        	{
        		Assert.fail("New folder panel is open after submit.");
        	}
        	
        	addfolderfile.Nav_New_Folder_Click();
        	Thread.sleep(1000);
        	
        	logThis("Check: 'New folder' panel should be open after 'Add a new folder' clicked.");
        	if(!addfolderfile.Is_Open_New_Folder_Panel())
        	{
        		Assert.fail("New folder panel is not open.");
        	}

        	logThis("Check: 'Create' button should be disabled when panel opens.");
        	if(addfolderfile.Is_Enabled_Create_Folder_Button())
        	{
        		Assert.fail("Create button enabled before folder name entered.");
        	}

        	addfolderfile.Set_Folder_Name(strFolder1);
        	Thread.sleep(1000);
        	
        	logThis("Check: 'Create' button should be enabled after 'Folder name' entered.");
        	if(!addfolderfile.Is_Enabled_Create_Folder_Button())
        	{
        		Assert.fail("Create button is NOT enabled after 'Folder name' entered.");
        	}

        	addfolderfile.Set_Folder_Description(strFolder1Desc);
        	Thread.sleep(1000);
        	
        	addfolderfile.Set_Folder_Doc_Type(strFolder1DocType);
        	Thread.sleep(1000);
        	
        	addfolderfile.Create_Folder_Click();
        	
        	logThis("Check: 'Folder created' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Folder created"))
	        {
            	logThis("'Folder created' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Folder created' pop-up NOT found.");
	        }
        	Thread.sleep(1000);
        	
        	logThis("Check: 'New folder' should be panel closed after submit.");
        	if(addfolderfile.Is_Open_New_Folder_Panel())
	        {
	        	Assert.fail("New folder panel is open after submit.");
	       	}


        	//If first folder created works, we can simplify creation.
        	logThis("Creating '" + strFolder2 + "'");
        	addfolderfile.Add_New_Folder(strFolder2, null, null);
        	Thread.sleep(1000);
        	logThis("Creating '" + strFolder3 + "'");
        	addfolderfile.Add_New_Folder(strFolder3, null, null);
        	Thread.sleep(1000);
        	logThis("Creating '" + strFolder4 + "'");
        	addfolderfile.Add_New_Folder(strFolder4, null, null);
        	Thread.sleep(1000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Rename_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Rename_Folder");
        	
        	String  strFolder = "Folder4";
        	String  strFolderRename = "Folder4_renamed";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	reuse.FileFolder_Link_Click("file in files", strFolder);
        	Thread.sleep(1000);
        	
        	logThis("Check: 'Properties' panel should not be open yet");
        	if(reuse.Is_Displayed_Properties_Panel())
        	{
        		Assert.fail("Properties panel should not open before Info/Summary icon clicked.");
        	}
        	
        	//TODO - Add check if info link/icon is available.
        	home.Folder_Info_Link_Click();
        	Thread.sleep(1000);
        	
        	logThis("Check: 'Properties' panel should be open.");
        	if(!reuse.Is_Displayed_Properties_Panel())
        	{
        		Assert.fail("Properties panel should by open after Info/Summary icon clicked.");
        	}
        	
        	logThis("Check: Header info before renaming folder.");
        	if(!info.Get_Info_Header().contains(strFolder))
        	{
        		Assert.fail("Expected header before change to be '" + strFolder + "', it was '" + info.Get_Info_Header() + "'");
        	}
        	
        	info.Nav_Command_Click("Edit");
        	Thread.sleep(1000);
        	
        	logThis("Check: 'Edit' panel should be open after 'Edit' clicked.");
        	if(!info.Is_Edit_Panel_Displayed())
        	{
        		Assert.fail("Edit panel not open after Edit clicked.");
        	}
        	
        	info.Set_Edit_Title(strFolderRename);
        	Thread.sleep(1000);
        	
        	info.Save_Edit_Click();
        	Thread.sleep(500);
        	logThis("Check: 'Document updated' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document updated"))
	        {
            	logThis("'Document updated' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document updated' pop-up NOT found.");
	        }
            
        	logThis("Check: Header info after submit.");
        	if(!info.Get_Info_Header().contains(strFolderRename))
        	{
        		Assert.fail("Expected header after submit to be '" + strFolderRename + "', it was '" + info.Get_Info_Header() + "'");
        	}

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Add_Description_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Add_Description_Folder");
        	
        	String strFolder = "Folder3";
        	String strDescription = "Add description test text";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	reuse.Info_Link_Click("file in files", strFolder);
        	Thread.sleep(1000);
        	
        	info.Nav_Command_Click("Edit");
        	Thread.sleep(1000);
        	
        	info.Set_Edit_Description(strDescription);
        	info.Save_Edit_Click();
        	Thread.sleep(500);
        	logThis("Check: 'Document updated' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document updated"))
	        {
            	logThis("'Document updated' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document updated' pop-up NOT found.");
	        }
            Thread.sleep(1000);
            
            //After submit, it goes back to the info window.
            if(info.Is_Info_Panel_Displayed())
            {
            	logThis("Check: Description in Info panel should be '" + strDescription + "'");
            	if(!info.Get_Info_Description().contains(strDescription))
            	{
            		Assert.fail("Expected Description after submit to be '" + strDescription + "', it was '" + info.Get_Info_Description() + "'");
            	}
            }
            else
            {
            	logThis("WARNING: Expected the Info panel is not open after description submitted.  It is not.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Add_Note_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Add_Note_Folder");
        	
        	String strFolder = "Folder2";
        	String strNote = "Add note test";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	reuse.Info_Link_Click("file in files", strFolder);
        	Thread.sleep(1000);
        	
        	logThis("Check: Notes panel should not be open before navigation click.");
        	if(info.Is_Notes_Panel_Displayed())
        	{
        		Assert.fail("Notes panel was open before navigation.");
        	}
        	
        	info.Nav_Command_Click("Notes");
        	Thread.sleep(1000);

        	logThis("Check: Notes panel should be after before navigation click.");
        	if(!info.Is_Notes_Panel_Displayed())
        	{
        		Assert.fail("Notes panel was NOT open after navigation.");
        	}
        	
        	logThis("Check: Add button should not be enabled before note entered.");
        	if(info.Is_Add_Note_Enabled())
        	{
        		Assert.fail("Add button was enabled before note entered.");
        	}
        	
        	
        	info.Set_Note(strNote);
        	Thread.sleep(1000);
        	
        	logThis("Check: Add button should be enabled after note entered.");
        	if(!info.Is_Add_Note_Enabled())
        	{
        		Assert.fail("Add button was not enabled after note entered.");
        	}
        	
        	info.Add_Note_Click();
        	Thread.sleep(500);
        	
        	logThis("Check: 'Note added' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Note added"))
	        {
            	logThis("'Note added' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Note added' pop-up NOT found.");
	        }
            Thread.sleep(1000);
        	
            //TODO - Get_Notes needs work.  Will need to get individual results based on user/time
            logThis("Check: Verify '" + strNote + "' listed in repeater 'comment in comments'");
            if(!info.Get_Notes().contains(strNote))
            {
            	Assert.fail("Expected to find '" + strNote + "' in repeater 'comment in comments' but did not.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Copy_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Copy_Folder");
        	
        	String strFolder = "Folder1";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	logThis("Check: Copy/Paste control should not be visible before file selected.");
        	if(reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control visible before file selected.");
        	}
        	
        	reuse.Select_FileFolder("file in files", strFolder);

        	logThis("Check: Copy/Paste control should be visible after file selected.");
        	if(!reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control NOT visible after file selected.");
        	}
        	
        	reuse.xControl_Option_Click(false,"copy");
        	Thread.sleep(500);

        	logThis("Check: '1 document(s) copied' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) copied"))
	        {
            	logThis("'1 document(s) copied' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) copied' pop-up NOT found.");
	        }
        	
            reuse.xControl_Option_Click(false,"paste");
            Thread.sleep(500);
            
        	logThis("Check: '1 document(s) pasted' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) pasted"))
	        {
            	logThis("'1 document(s) pasted' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) pasted' pop-up NOT found.");
	        }
            
            logThis("Check: Looking for '" + strFolder + " - Copy'");
            if(reuse.Get_FileFolder_Row("file in files", strFolder + " - Copy") == null)
            {
            	Assert.fail("Pasted folder '" + strFolder + " - Copy' was not found.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Move_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Move_Folder");
        	logThis("WARNING: Move_Folder test not complete.  Need to implement features for testing.  Failing test until complete.");
        	
        	String strFolderCopy = "Folder1 - Copy";
        	String strFolderMoveTo = "Folder1";

        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }

        	//TODO - implement right click features.
        	/**
        	 * 1. Right-Click on Folder - opens drop down menu.
        	 * 2. Select 'Copy'
        	 * 3. home.Nav_GuestFolder_Click();
        	 * 4. Right-Click on Guest Folder in the Nav Tree (need to implement Nav tree features)
        	 * 5. Select 'Move here'
        	 */
        	
        	logThis("DD shown = '" + reuse.Is_Displayed_FileFolder_Dropdown() + "'");
        	
        	reuse.FileFolder_Link_Right_Click("file in files", strFolderCopy);
        	
        	logThis("DD shown = '" + reuse.Is_Displayed_FileFolder_Dropdown() + "'");
        	
        	reuse.Get_FileFolder_Dropdown_Options();
        	
        	reuse.FileFolder_Dropdown_Option_Click("Copy");
       	
            logThis("Check: '1 document(s) copied' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) copied"))
	        {
            	logThis("'1 document(s) copied' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) copied' pop-up NOT found.");
	            logThis("Contents = '" + reuse.Get_Popup_Msgs() + "'");
	        }
        	
        	
        	reuse.FileFolder_Link_Right_Click("file in files", strFolderMoveTo);
        	Thread.sleep(500);
        	reuse.FileFolder_Dropdown_Option_Click("Move here");
        	Thread.sleep(500);
        	//1 document(s) moved
            logThis("Check: '1 document(s) moved' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) moved"))
	        {
            	logThis("'1 document(s) moved' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) moved' pop-up NOT found.");
	        }
        	
        	//TODO - This demonstrates moving copied folder to the folder it was copied from
        	//Need to move it to Guest folder, which requires work in Nav Tree.
            //Also validate move was successful - file exists in new location and does not in old.
        	Assert.fail("Move_Folder test not complete.  Need to implement features for testing.");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Upload_Image() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Upload_Image");
        	
        	String strImportFile = "Hydrangeas.jpg";
        	String strImportPath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\";
        	String strDocType = "Generic";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	logThis("Check: 'Import this file' panel should not open before 'Import a file' clicked.");
        	if(addfolderfile.Is_Open_Import_File_Panel())
        	{
        		Assert.fail("'Import this file' panel should not open before 'Import a file' clicked.");
        	}
        	
        	addfolderfile.Nav_Import_File_Click();
        	Thread.sleep(1000);
        	
        	logThis("Check: 'Import this file' panel should open after 'Import a file' clicked.");
        	if(!addfolderfile.Is_Open_Import_File_Panel())
        	{
        		Assert.fail("'Import this file' panel should open after 'Import a file' clicked.");
        	}
        	
        	logThis("Check: 'Import this file' button should not be enabled by default.");
        	if(addfolderfile.Is_Enabled_Import_File_Button())
        	{
        		Assert.fail("'Import this file' button was enabled by default.");
        	}
        	
        	addfolderfile.Set_File_Name(strImportPath + strImportFile);
        	
        	logThis("Check: 'Import this file' button should be enabled after file selected.");
        	if(!addfolderfile.Is_Enabled_Import_File_Button())
        	{
        		Assert.fail("'Import this file' button was NOT enabled after file selected.");
        	}

        	addfolderfile.Set_File_Doc_Type(strDocType);
        	Thread.sleep(1000);
        	
        	
            addfolderfile.Import_File_Click();
            Thread.sleep(1500);
            
            logThis("Check: '" + strImportFile + "' File uploaded successfully' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains(strImportFile + " File uploaded successfully"))
	        {
            	logThis("'" + strImportFile + " File uploaded successfully' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '" + strImportFile + " File uploaded successfully' pop-up NOT found.");
	        }
            
            //Give it a little extra time....
            Thread.sleep(5000);
            
            logThis("Check: Looking for '" + strImportFile + "' in repeater 'file in files'");
            if(reuse.Get_FileFolder_Row("file in files", strImportFile) == null)
            {
            	Assert.fail("Imported file '" + strImportFile + "' was not found.");
            }

        	logThis("Check: 'Import this file' panel should be closed after file imported");
        	if(addfolderfile.Is_Open_Import_File_Panel())
        	{
        		Assert.fail("'Import this file' panel was open after file imported");
        	}
        	
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Upload_File_Meta_Data() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Upload_File_Meta_Data");
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void File_Summary() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: File_Summary");
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Add_Description_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Add_Description_File");
        	
        	String strFile = "Hydrangeas.jpg";
        	String strDesc = "Add description test text";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	reuse.Info_Link_Click("file in files", strFile);
        	Thread.sleep(1000);
        	
        	info.Nav_Command_Click("Edit");
        	Thread.sleep(1000);
        	
        	info.Set_Edit_Description(strDesc);
        	info.Save_Edit_Click();
        	Thread.sleep(500);
        	logThis("Check: 'Document updated' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document updated"))
	        {
            	logThis("'Document updated' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document updated' pop-up NOT found.");
	        }
            Thread.sleep(1000);
            
            //After submit, it goes back to the info window.
            if(info.Is_Info_Panel_Displayed())
            {
            	logThis("Check: Description in Info panel should be '" + strDesc + "'");
            	if(!info.Get_Info_Description().contains(strDesc))
            	{
            		Assert.fail("Expected Description after submit to be '" + strDesc + "', it was '" + info.Get_Info_Description() + "'");
            	}
            }
            else
            {
            	logThis("WARNING: Expected the Info panel is not open after description submitted.  It is not.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    
    @Test
    public void Add_Note_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Add_Note_File");
        	
        	String strFile = "Hydrangeas.jpg";
        	String strNote = "Add note test";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	reuse.Info_Link_Click("file in files", strFile);
        	Thread.sleep(1000);
        	
        	logThis("Check: Notes panel should not be open before navigation click.");
        	if(info.Is_Notes_Panel_Displayed())
        	{
        		Assert.fail("Notes panel was open before navigation.");
        	}
        	
        	info.Nav_Command_Click("Notes");
        	Thread.sleep(1000);

        	logThis("Check: Notes panel should be after before navigation click.");
        	if(!info.Is_Notes_Panel_Displayed())
        	{
        		Assert.fail("Notes panel was NOT open after navigation.");
        	}
        	
        	logThis("Check: Add button should not be enabled before note entered.");
        	if(info.Is_Add_Note_Enabled())
        	{
        		Assert.fail("Add button was enabled before note entered.");
        	}
        	
        	
        	info.Set_Note(strNote);
        	Thread.sleep(1000);
        	
        	logThis("Check: Add button should be enabled after note entered.");
        	if(!info.Is_Add_Note_Enabled())
        	{
        		Assert.fail("Add button was not enabled after note entered.");
        	}
        	
        	info.Add_Note_Click();
        	Thread.sleep(500);
        	
        	logThis("Check: 'Note added' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Note added"))
	        {
            	logThis("'Note added' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Note added' pop-up NOT found.");
	        }
            Thread.sleep(1000);
        	
            //TODO - Get_Notes needs work.  Will need to get individual results based on user/time
            logThis("Check: Verify 'Add note test' listed in repeater 'comment in comments'");
            if(!info.Get_Notes().contains(strNote))
            {
            	Assert.fail("Expected to find '" + strNote + "' in repeater 'comment in comments' but did not.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    

    @Test
    public void Update_File_Meta_Data() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Update_File_Meta_Data");
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Subscribe_File_Notifications() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Subscribe_File_Notifications");
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Copy_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Copy_File");

        	String strFile = "Hydrangeas.jpg";
        	String strCopyFile = "Hydrangeas - Copy.jpg";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	logThis("Check: Copy/Paste control should not be visible before file selected.");
        	if(reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control visible before file selected.");
        	}
        	
        	reuse.Select_FileFolder("file in files", strFile);

        	logThis("Check: Copy/Paste control should be visible after file selected.");
        	if(!reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control NOT visible after file selected.");
        	}
        	
        	reuse.xControl_Option_Click(false,"copy");
        	Thread.sleep(500);

        	logThis("Check: '1 document(s) copied' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) copied"))
	        {
            	logThis("'1 document(s) copied' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) copied' pop-up NOT found.");
	        }
        	
            reuse.xControl_Option_Click(false, "paste");
            Thread.sleep(500);
            
        	logThis("Check: '1 document(s) pasted' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) pasted"))
	        {
            	logThis("'1 document(s) pasted' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) pasted' pop-up NOT found.");
	        }
            
            logThis("Check: Looking for '" + strCopyFile + "'");
            if(reuse.Get_FileFolder_Row("file in files", strCopyFile) == null)
            {
            	Assert.fail("Pasted folder '" + strCopyFile + "' was not found.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Move_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Move_File");
        	logThis("WARNING: Move_File test not complete.  Need to implement features for testing.  Failing test until complete.");

        	//TODO - Use a pre-existing file, not a file created / dependency of prior test case.
        	String strFile = "Hydrangeas - Copy.jpg";
        	String strDestFolder = "Folder1";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	//div class="dropdown clearfix"
        	//ul class="dropdown-menu"
        	//each li contains anchor linkgs.  Copy, Delete, Lock(file), Summary, Download(file). Contents(folder)...
        	//and...  Paste here, Move here (folder) if a previous copy was made.

        	//TODO - implement right click features.
        	/**
        	 * 1. Right-Click on File - opens drop down menu.
        	 * 2. Select 'Copy'
        	 * 3. home.Nav_GuestFolder_Click();
        	 * 4. Right-Click on Guest Folder in the Nav Tree (need to implement Nav tree features)
        	 * 5. Select 'Move here'
        	 */
        	
        	logThis("DD shown = '" + reuse.Is_Displayed_FileFolder_Dropdown() + "'");
        	
        	reuse.FileFolder_Link_Right_Click("file in files", strFile);
        	
        	logThis("DD shown = '" + reuse.Is_Displayed_FileFolder_Dropdown() + "'");
        	
        	reuse.Get_FileFolder_Dropdown_Options();
        	
        	reuse.FileFolder_Dropdown_Option_Click("Copy");
       	
            logThis("Check: '1 document(s) copied' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) copied"))
	        {
            	logThis("'1 document(s) copied' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) copied' pop-up NOT found.");
	            logThis("Contents = '" + reuse.Get_Popup_Msgs() + "'");
	        }
        	
        	
        	reuse.FileFolder_Link_Right_Click("file in files", strDestFolder);
        	Thread.sleep(500);
        	reuse.FileFolder_Dropdown_Option_Click("Move here");
        	Thread.sleep(500);
        	//1 document(s) moved
            logThis("Check: '1 document(s) moved' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("1 document(s) moved"))
	        {
            	logThis("'1 document(s) moved' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: '1 document(s) moved' pop-up NOT found.");
	        }
        	
        	//TODO - This demonstrates moving copied folder to the folder it was copied from
        	//Need to move it to Guest folder, which requires work in Nav Tree.
            //Also validate move was successful - file exists in new location and does not in old.
        	Assert.fail("Move_Folder test not complete.  Need to implement features for testing.");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Upload_Revised_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Upload_Revised_File");
        	
        	String strFile = "Tulips.jpg";
        	String strPath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }
        	
        	//Get the current version of the file.
        	String strVersion = reuse.Get_Version("file in files", strFile);
        	logThis("Current version of file '" + strFile + "' = '" + strVersion + "'");
        	
        	logThis("Check: 'Import this file' panel should not open before 'Import a file' clicked.");
        	if(addfolderfile.Is_Open_Import_File_Panel())
        	{
        		Assert.fail("'Import this file' panel should not open before 'Import a file' clicked.");
        	}
        	
        	addfolderfile.Nav_Import_File_Click();
        	Thread.sleep(1000);
        	
        	logThis("Check: 'Import this file' panel should open after 'Import a file' clicked.");
        	if(!addfolderfile.Is_Open_Import_File_Panel())
        	{
        		Assert.fail("'Import this file' panel should open after 'Import a file' clicked.");
        	}
        	
        	logThis("Check: 'Import this file' button should not be enabled by default.");
        	if(addfolderfile.Is_Enabled_Import_File_Button())
        	{
        		Assert.fail("'Import this file' button was enabled by default.");
        	}
        	
        	addfolderfile.Set_File_Name(strPath + strFile);
        	
        	logThis("Check: 'Import this file' button should be enabled after file selected.");
        	if(!addfolderfile.Is_Enabled_Import_File_Button())
        	{
        		Assert.fail("'Import this file' button was NOT enabled after file selected.");
        	}

        	addfolderfile.Set_File_Doc_Type("Generic");
        	Thread.sleep(1000);
        	
        	
            addfolderfile.Import_File_Click();
            Thread.sleep(1500);
            
            logThis("Check: '<filename> uploaded successfully' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("uploaded successfully"))
	        {
            	logThis("'uploaded successfully' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'uploaded successfully' pop-up NOT found.");
	        }
            
            //Give it a little extra time....
            Thread.sleep(5000);
            
            logThis("Check: Looking for '" + strFile + "' in repeater 'file in files'");
            if(reuse.Get_FileFolder_Row("file in files", strFile) == null)
            {
            	Assert.fail("Imported file '" + strFile + "' was not found.");
            }

        	logThis("Check: 'Import this file' panel should be closed after file imported");
        	if(addfolderfile.Is_Open_Import_File_Panel())
        	{
        		Assert.fail("'Import this file' panel was open after file imported");
        	}
        	
        	
        	String strRevisedVersion = reuse.Get_Version("file in files", strFile);
        	logThis("Revised version of file '" + strFile + "' = '" + strRevisedVersion + "'");

        	Double dblVersion = Double.parseDouble(strVersion);
        	Double dblRevisedVersion = Double.parseDouble(strRevisedVersion);
        	
        	if(dblRevisedVersion - dblVersion != 1)
        	{
        		logThis("Difference of '" + dblRevisedVersion + "' and '" + dblVersion + "' does not = 1");
        		Assert.fail("File version not incremented by 1 after revised file imported.");
        	}

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    
    @Test
    public void Revert_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Revert_File");
        	logThis("WARNING: Test not complete");
        	//TODO - Need to add features for Versions to 
        	String strFile = "Tulips.jpg";
        	
        	//Test clean-up.  Make sure we are in My Docs.
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        }        	
        	
        	reuse.Info_Link_Click("file in files", strFile);
        	info.Nav_Command_Click("Versions");
        	
        	/**
        	 * TODO - identify the version of the file to roll back to.
        	 * Need to add features to Info_Actions.java
        	 */
        	
        	logThis("WARNING: Test not complete.");
        	Assert.fail("Test not complete");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Send_Link() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Send_Link");
        	
        	String strFile = "Tulips.jpg";
        	String strEmail = "rholm@saturnsys.com";
        	String strExpire = "24 hours";  //Drop down is lower case.  Preview capitalizes.
        	String strMsg = "Hello World!";
        	
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		/**
        		 * If you are in a sub folder, clicking My Docs does not
        		 * bring you to root of My Docs.
        		 */
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        } 
        	
        	logThis("Is_Send_Link_Active = " + reuse.Is_Send_Link_Active("file in files", strFile));

        	reuse.Send_Link_Click("file in files", strFile);
        	
        	
        	logThis("Check: Send Link panel should be open after navigation click.");
        	if(!reuse.Is_Displayed_Send_File_Link_Panel())
        	{
        		Assert.fail("Send Link panel was NOT open after navigation click.");
        	}
        	
        	//TODO - Check values of expire drop down.
        	
        	sendlink.Set_Expire(strExpire);
        	Thread.sleep(1500);
        	logThis("Check: Preview information should contain expiration info.");
        	if(!sendlink.Get_Preview_Expire_Info().trim().toLowerCase().contains(strExpire))
        	{
        		logThis("Looking for: '" + strExpire + "'");
        		logThis("In this: '" + sendlink.Get_Preview_Expire_Info().trim().toLowerCase() + "'");
        		logThis("indexOf = '" + sendlink.Get_Preview_Expire_Info().trim().toLowerCase().indexOf(strExpire) + "'");
        		Assert.fail("Preview did not contain expiration info set by user.");
        	}

        	logThis("Check: 'Send Link' button should not be enabled before first email entered.");
        	if(sendlink.Is_Send_Link_Enabled())
        	{
        		logThis("WARNING: ENABLED!");
        		Assert.fail("'Send Link' button was enabled before first email entered.");
        	}
        	
        	sendlink.Set_Email(strEmail);

        	logThis("Check: 'Send Link' button should be enabled after email entered.");
        	if(!sendlink.Is_Send_Link_Enabled())
        	{
        		logThis("WARNING: DISABLED!");
        		Assert.fail("'Send Link' button was NOT enabled after email entered.");
        	}
        	
        	sendlink.Set_Message(strMsg);
        	
        	logThis("Check: Preview email should contain user Message.");
        	if(!sendlink.Get_Preview_Message().contains(strMsg))
        	{
        		Assert.fail("Preview email did not contain Message set by user.");
        	}
        	
        	sendlink.Send_Link_Submit();

        	Thread.sleep(500);
            logThis("Check: 'Document link sent' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document link sent"))
	        {
            	logThis("'Document link sent' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document link sent' pop-up NOT found.");
	        }
            
        	logThis("Check: Send Link panel should be closed after submit click.");
        	if(reuse.Is_Displayed_Send_File_Link_Panel())
        	{
        		Assert.fail("Send Link panel was NOT closed after submit click.");
        	}
        	
        	Thread.sleep(2000); //Had issues where the DOM refreshed and test failed.  2 seconds seems to fix that.
        	logThis("Check: Send Link (email icon) should indicate an active link (filled in blue)");
        	if(!reuse.Is_Send_Link_Active("file in files", strFile))
        	{
        		Assert.fail("Send Link (email icon) does NOT indicate an active link.");
        	}

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/**
	 * Andy's test seems to be looking for the folders/files (names/count) created in earlier steps.
	 * Not sure the best way to validate this yet.
	 * Things to consider:
	 * Previous test run clean up - other folders/files existing before test run for user.
	 * Pagination - folders/files existing on another page.
	 * Failure of dependent tests that create/move folders/files.
	 */
    @Test
    public void My_Docs_Contents() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: My_Docs_Contents");
        	
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/**
	 * See comments in My_Docs_Contents()
	 */
    @Test
    public void Guest_Folder_Contents() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Guest_Folder_Contents");
        	

        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/**
	 * In this case (Using Andy's excel file for example), it looks like he is looking at the guest folder.
	 * Confirm copy/move.
	 * 
	 * NOTE: History for folders is the icon at the top right corner under Sign Out.
	 * History not available if you drill down dynamic content -> info/summary page for folders.
	 * 
	 * TODO - need to add feature for navigation.
	 */
    @Test
    public void Folder_History() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Folder_History");

        	String strFolder = "Folder1";
        	
        	//Clean up navigation (change if going to Guest Folder)
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        } 
        	//For now, using Folder1 in My Docs.
        	reuse.FileFolder_Link_Click("file in files", strFolder);
        	
        	home.Folder_History_Link_Click();
        	
        	//check if window is open
        	
        	//check for content of panel - repeater "log in logs"
        	
        	
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/*
	 * In this case (Using Andy's excel file for example)
	 * It looks like the file copied and then moved to the Guest folder.
	 * Confirm Add, Revision, Revert, and Send Link entries.
	 * 
	 * Many dependencies here, might want to break this up.
	 * Move to Guest folder not complete yet, will need to revisit.
	 */
    @Test
    public void File_History() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: File_History");

        	String strFile = "Hydrangeas - Copy.jpg";
        	String strFolder = "Folder1";
        	
        	//Clean up navigation (change if going to Guest Folder)
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
	        {
	        	home.Nav_MyDocs_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_MyDocs_Click();
        		Thread.sleep(1000);
	        } 
        	
        	//Open the folder the file was copied to - Need to change this if navigating to Guest Folder.
        	reuse.FileFolder_Link_Click("file in files", strFolder);
        	Thread.sleep(1000);

        	//Click the info icon for the file
        	reuse.Info_Link_Click("file in files", strFile);
        	
        	//Open the history tab
        	info.Nav_Command_Click("History");
        	
        	//Confirm an entry
        	logThis("Check: History records should contain 'Created by copy' record.");
        	if(!info.Get_History().contains("Created by copy"))
        	{
        		Assert.fail("'Created by copy' record not listed in history.");
        	}
        	
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/**
	 * Thumbnail is the default view.
	 * Not sure how to validate image file is displayed properly or not.
	 * Use Sikuli?
	 */
    @Test
    public void Image_File_Thumbnail() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Image_File_Thumbnail");
        	

        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/**
	 * Inner text of img tag changes on rotate for both 'thumbnail' and 'medium'.  Key parts below:
	 * 		style="-webkit-transition: all 0.5s ease-out; transition: all 0.5s ease-out; 
	 * 		-webkit-transform-origin: 0px 0px 0px; 
	 * 		transform-origin: 0px 0px 0px; 
	 * 		transform: rotate(90deg) translate(0px, -100%);">
	 * 
	 * We can test for values / changes of inner text on roate...
	 * May need to use Sikuli to confirm rotation.
	 */
    @Test
    public void Image_File_Rotate() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Image_File_Rotate");
        	

        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Search_Image_File_Word() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Search_Image_File_Word");
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Search_Text_File_Word() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Search_Text_File_Word");
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Tree_View_Layout() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Tree_View_Layout");
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    /**
     * Pre-condition: File exists.
     * TODO - Add a file so we can delete it if none are listed.
     */
    @Test
    public void Delete_Guest_Folder_File() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Delete_Guest_Folder_File");
        	
        	String strFile = "Desert.jpg";
        	
        	//Clean up navigation
        	if(!home.Get_Nav_Tree_Header().contains("Guest folder"))
	        {
	        	home.Nav_GuestFolder_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_GuestFolder_Click();
        		Thread.sleep(1000);
	        }
        	//validate copy/paste control open
        	logThis("Check: Copy/Paste control closed before file select.");
        	if(reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control is displayed before file select.");
        	}
        	
        	reuse.Select_FileFolder("file in files", strFile);
        	Thread.sleep(500);

        	logThis("Check: Copy/Paste control displayed after selecting a file.");
        	if(!reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control not displayed.");
        	}
        	
        	reuse.xControl_Option_Click(false, "delete");
        	Thread.sleep(500);

            logThis("Check: 'Document(s) deleted' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document(s) deleted"))
	        {
            	logThis("'Document(s) deleted' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document(s) deleted' pop-up NOT found.");
	        }

        	logThis("Check: Copy/Paste control closed after delete performed.");
        	if(reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control is displayed after delete performed.");
        	}

        	Thread.sleep(5000);  //For some reason, if you don't wait long enough, click does not open trash.
        	home.Folder_Trash_Link_Click();

        	Thread.sleep(2000);
        	logThis("Check: Delete file '" + strFile + "' is located in trash.");
        	if(reuse.Get_FileFolder_Row("file in docsInTrash", strFile) == null)
        	{
        		Assert.fail("Deleted file not found in trash.");
        	}

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Delete_Guest_Folder_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Delete_Guest_Folder_Folder");
        	
        	String strFolder = "TestGuest";
        	
        	//Clean up navigation
        	if(!home.Get_Nav_Tree_Header().contains("Guest folder"))
	        {
	        	home.Nav_GuestFolder_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_GuestFolder_Click();
        		Thread.sleep(1000);
	        }
        	//validate copy/paste control open
        	logThis("Check: Copy/Paste control closed before file select.");
        	if(reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control is displayed before file select.");
        	}
        	
        	reuse.Select_FileFolder("file in files", strFolder);
        	Thread.sleep(500);

        	logThis("Check: Copy/Paste control displayed after selecting a file.");
        	if(!reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control not displayed.");
        	}
        	
        	reuse.xControl_Option_Click(false, "delete");
        	Thread.sleep(500);

            logThis("Check: 'Document(s) deleted' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document(s) deleted"))
	        {
            	logThis("'Document(s) deleted' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document(s) deleted' pop-up NOT found.");
	        }

        	logThis("Check: Copy/Paste control closed after delete performed.");
        	if(reuse.Is_Displayed_xControl(false))
        	{
        		Assert.fail("Copy/Paste control is displayed after delete performed.");
        	}

        	Thread.sleep(5000);  //For some reason, if you don't wait long enough, click does not open trash.
        	home.Folder_Trash_Link_Click();

        	Thread.sleep(2000);
        	logThis("Check: Delete folder '" + strFolder + "' is located in trash.");
        	if(reuse.Get_FileFolder_Row("file in docsInTrash", strFolder) == null)
        	{
        		Assert.fail("Deleted file not found in trash.");
        	}

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Delete_File_Trash() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Delete_File_Trash");
        	String strFile = "Desert.jpg";
        	
        	//Navigate to folder which had a file removed from it.
        	//Clean up navigation
        	if(!home.Get_Nav_Tree_Header().contains("Guest folder"))
	        {
	        	home.Nav_GuestFolder_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_GuestFolder_Click();
        		Thread.sleep(1000);
	        }
        	
        	//click on trash icon
        	home.Folder_Trash_Link_Click();
        	Thread.sleep(500);
        	
        	//validate trash is open.
        	logThis("Check: Folder Trash panel open after clicing Trash icon.");
        	if(!reuse.Is_Displayed_Folder_Trash_Panel())
        	{
        		Assert.fail("Folder Trash paenl is not open after clicing Trash icon.");
        	}
        	
        	//select file in trash
        	reuse.Select_FileFolder("file in docsInTrash", strFile);
        	Thread.sleep(500);
        	
        	logThis("Check: Copy/Paste control should be visible after file selected.");
        	if(!reuse.Is_Displayed_xControl(true))
        	{
        		Assert.fail("Copy/Paste control be visible after file selected.");
        	}
        	
        	//click delete - no confirmation OK/Cancel.
        	reuse.xControl_Option_Click(true, "delete");
        	Thread.sleep(500);

        	logThis("Check: Copy/Paste control should not visible after delete.");
        	if(reuse.Is_Displayed_xControl(true))
        	{
        		Assert.fail("Copy/Paste control visible after delete.");
        	}
        	
            logThis("Check: 'Document(s) deleted' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document(s) deleted"))
	        {
            	logThis("'Document(s) deleted' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document(s) deleted' pop-up NOT found.");
	        }
        	
        	//validate file is gone.
            if(reuse.Get_FileFolder_Row("file in docsInTrash", strFile) != null)
            {
            	Assert.fail("File found in Trash after delete.");
            }

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Restore_Folder() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Restore_Folder");
        	
        	String strFolder = "TestGuest";
        	
        	//Clean up navigation
        	if(!home.Get_Nav_Tree_Header().contains("Guest folder"))
	        {
	        	home.Nav_GuestFolder_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_GuestFolder_Click();
        		Thread.sleep(1000);
	        }
        	
        	//click on trash icon
        	home.Folder_Trash_Link_Click();
        	Thread.sleep(1000);
        	
        	//validate trash is open.
        	logThis("Check: Folder Trash panel open after clicing Trash icon.");
        	if(!reuse.Is_Displayed_Folder_Trash_Panel())
        	{
        		Assert.fail("Folder Trash paenl is not open after clicing Trash icon.");
        	}
        	
        	//select file in trash
        	reuse.Select_FileFolder("file in docsInTrash", strFolder);
        	
        	logThis("Check: Copy/Paste control should not visible after file selected.");
        	if(!reuse.Is_Displayed_xControl(true))
        	{
        		Assert.fail("Copy/Paste control not visible after file selected.");
        	}
        	
        	//click delete - no confirmation OK/Cancel.
        	reuse.xControl_Option_Click(true, "restore");
        	
            logThis("Check: 'Document(s) restored' pop-up should be presented to user.");
            if(reuse.Get_Popup_Msgs().contains("Document(s) restored"))
	        {
            	logThis("'Document(s) restored' pop-up found!");
	        }
            else
            {
	            //TODO - Determine if we should Assert.fail() here.
	            //Timing has caused false failures.
	            logThis("WARNING: 'Document(s) deleted' pop-up NOT found.");
	        }
        	
        	/**
        	 * Clean up navigation
        	 * This clears up any windows and refreshes content of Guest folder.
        	 * Simply closing the trash for Guest Folder does not refresh contents.
        	 * Clicking Guest folder icon (menu on left) does not refresh page if already in Guest Folder.
        	 */
        	if(!home.Get_Nav_Tree_Header().contains("Guest folder"))
	        {
	        	home.Nav_GuestFolder_Click();
	        	Thread.sleep(1000);
	        }
	        else
	        {
        		home.Nav_Dashboard_Click();
        		Thread.sleep(1000);
        		home.Nav_GuestFolder_Click();
        		Thread.sleep(1000);
	        }
        	
        	logThis("Check: Looking for folder '" + strFolder + "' in Guest Folder");
        	if(reuse.Get_FileFolder_Row("file in files", strFolder) == null)
        	{
        		Assert.fail("Did not find folder '" + strFolder + "' listed in Guest Folder.");
        	}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Unsubscribe_My_Docs_Notifications() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Unsubscribe_My_Docs_Notifications");
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Unsubscribe_File_Notifications() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Unsubscribe_File_Notifications");
        	
        	logThis("WARNING: Test not complete");
        	Assert.fail("Test not complete");

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Sign_Out() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Sign_Out");
        	
        	login.User_Logout();
        	Thread.sleep(5000);
        	
        	logThis("Need to add validation of Sign Out.");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
/**
 * "CPO 2 Smoke Test" Excel spreadsheet from Andy
Download IWB application
Download DS application for Windows and for Mac
Uncheck a metadata document type
					Create_Group
					Update_Group_Description
					Create_Group_With_Sub_Group
					
Upload a non-image File via Drag and Drop
Download the file

Share a Folder for All Share Plus with User B
Share a Folder for Read Write Delete with Group containing User B
Share a Folder for Read Write  with Group containing subgroup with User B
Share a Folder for Read with User B
Add a file to the folder with Read Write Delete share
Lock the file in the folder with Read Write Delete share
Logout as BA user
    
 */ 

    @Test
    public void Russ_Test() throws InterruptedException
	{
        try
        {        	
        	logThis("Begin: Russ_Test");
        	
        	home.Nav_MyDocs_Click();
        	Thread.sleep(1000);
        	
        	reuse.FileFolder_Link_Click("file in files", "Folder1");
        	Thread.sleep(1000);
        	
        	home.Folder_Share_Link_Click();
        	Thread.sleep(1000);
        	logThis("folder=" + foldershare.Get_Share_Folder_Name());
        	logThis("owner="+foldershare.Get_Owner_Name());
        	logThis("enabled?=" + foldershare.Is_Remove_Rights_Enabled());
        	logThis("CurrentRights=" + foldershare.Get_Current_Rights_Users());
        	logThis("InheritedRights=" + foldershare.Get_Inherited_Rights_Users());

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
}
