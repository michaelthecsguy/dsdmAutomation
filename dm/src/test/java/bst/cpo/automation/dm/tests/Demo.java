package bst.cpo.automation.dm.tests;

//import org.openqa.selenium.Alert; //modal dialog window OK/Cancel
//import org.openqa.selenium.By;
import org.testng.annotations.*;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;
import bst.cpo.automation.dm.actions.Home_Actions;
import bst.cpo.automation.dm.actions.Login_Actions;
import bst.cpo.automation.dm.actions.MyRecentlyUpdatedDocs_Actions;
import bst.cpo.automation.dm.actions.MyRecentlyUpdatedFolders_Actions;
import bst.cpo.automation.dm.actions.OthersRecentlyUpdatedDocs_Actions;
import bst.cpo.automation.dm.actions.Search_Actions;
import bst.cpo.automation.dm.actions.Settings_Actions;
import bst.cpo.automation.dm.actions.Group_Actions;
import bst.cpo.automation.dm.actions.AddFolderFile_Actions;
import bst.cpo.automation.dm.actions.Reusable_Actions;
import bst.cpo.automation.dm.actions.SystemOverview_Actions;
import bst.cpo.automation.dm.actions.SyncComputers_Actions;
import bst.cpo.automation.dm.actions.IndexFields_Actions;
import bst.cpo.automation.dm.actions.User_Actions;
import bst.cpo.automation.dm.actions.MyProfile_Actions;
import bst.cpo.automation.dm.actions.MyLog_Actions;
import bst.cpo.automation.dm.actions.AdminLog_Actions;
import bst.cpo.automation.dm.actions.MyDocsTrash_Actions;
import bst.cpo.automation.dm.actions.Info_Actions;
import bst.cpo.automation.dm.actions.SendLink_Actions;

public class Demo extends BaseTest
{	
    public Demo()
    {

    }

    /**
     * Basic user login.
     * isUserBA is a global boolean variable - useful for other tests to determine validation of controls based on permission.
     * 
     * Angular is not used on the login page.
     * We need to wait until after login to create the ByAngular object
     */
    @Test
    public void Login_User() throws InterruptedException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	isUserBA = false;
        	
        	logThis("Begin: Login_User test");
            Start_DMTest();
            login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
            Thread.sleep(5000);
            Start_DMAngular();
            /**
             * If not enough time is given (or browser is slow to load), error is thrown.
             * "Exception during test occurred : angular.element(...).injector(...) is undefined"
             * Normally, this is just a false negative failure, but can cause all following tests to fail.
             */
            Thread.sleep(5000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    /**
     * Expected this test will fail.
     * We logged in with a standard user, but indicating the user is a BA (passing in true during navigation).
     * Anticipated seeing thead header "Admin Center", instead we see "Settings".
     * Assert failure is handled in the Nav_Settings_Click method.
     */
    @Test
    public void Settings_Admin_Fail() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	
        	home.Nav_Settings_Click(true);  //Boolean blnAdminLogin - True = BA, False = Standard User.
        	Thread.sleep(5000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    /**
     * Search for a user and return info displayed on page.
     * Demonstrates retrieving values from a page.
     * Based on the information returned, we can assert failure if expected value(s) are not returned.
     */
    @Test
    public void User_Search() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	User_Actions user = new User_Actions();
        	
        	logThis("Begin: User_Search test");
        	
        	//Here, we pass in the global variable we set in the login, which is false.
            home.Nav_Settings_Click(isUserBA);
            settings.Users_Click();
    		Thread.sleep(5000);

    		user.Search_For_User("rt9user91");    		
    		
    		Thread.sleep(5000);
    		logThis("Get: User name='" + user.Get_Username() + "'");
    		logThis("Get: User First name='" + user.Get_User_First_Name() + "'");
    		logThis("Get: User Last name='" + user.Get_User_Last_Name() + "'");
    		logThis("Get: Email='" + user.Get_User_Email() + "'");
    		logThis("Get: Company='" + user.Get_User_Company() + "'");
    		logThis("Get: TenantID='" + user.Get_User_TenantID() + "'");
    		logThis("Get: Groups='" + user.Get_User_Groups() + "'");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    /**
     * This will add the "Tulips.jpg" file to My Docs.
     * If it it exists, it increases the file version.
     * Here we can assert failures if the "Import this file" button is enabled/disabled and should/shouldn't be.
     * 
     * Note: Cheating here a little bit.  Bypassing the modal Windows File Upload window 
     * and passing the file path directly to UI element.
     */
    @Test
    public void Add_File() throws InterruptedException
	{
        try
        {        	
	    	Home_Actions home= new Home_Actions(DMDriver);
	    	AddFolderFile_Actions addfolderfile =new AddFolderFile_Actions();
	    	
	    	logThis("Begin: Add_File test");
	    	//Navigate to My Docs
	    	home.Nav_MyDocs_Click();
	    	Thread.sleep(5000);

	    	//Button validation before file selected
            addfolderfile.Nav_Import_File_Click();
            if(addfolderfile.Is_Enabled_Import_File_Button()){
            	Assert.fail("Import Button enabled - it shouldn't be before file is selected.");
            }else{
            	logThis("Check: Import Button is disabled");
            }
            
            Thread.sleep(5000);
            //Select file for import
            //Control requires full path, put only the file name is displayed after it is set.
            addfolderfile.Set_File_Name("C:\\Users\\Public\\Pictures\\Sample Pictures\\Tulips.jpg");
            Thread.sleep(5000);
            
            //Button validation after file selected
            if(addfolderfile.Is_Enabled_Import_File_Button()){
            	logThis("Check: Import Button is enabled");
            }else{
            	Assert.fail("Import Button disabled - it shouldn't be after file is selected.");
            }
            Thread.sleep(2000);
            
            //Submit
            addfolderfile.Import_File_Click();
            Thread.sleep(5000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    /**
     * Select a file/folder and validate basic copy/paste control menu
     * 
     * Demonstrates two things:
     * 1. Repeaters used in Angular / Finding dynamic content
     *    - In this case, each row in My Docs is listed as a "file in files".
     *    - Find the row we are looking for and use the appropriate command.
     *    - Reusable_Actions has the methods for (most) file/folder repeaters used in DM and the copy/paste control.
     * 
     * 2. Getting pop-up messages and using them for validation.
     *    - In this case, we log what we found, but could be used to assert fail if expected conditions not met.
     */
    @Test
    public void Select_File_Copy() throws InterruptedException
	{
        try
        {  	
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
        	
        	logThis("Begin: Select_File_Copy test");
        	
        	//Select the file "Tulips".
            reuse.Select_FileFolder("file in files", "Tulips.jpg");
            Thread.sleep(5000);
            
            //Copy the file in the context menu that appears after it is selected.
            reuse.xControl_Option_Click(false, "copy");
            
            //Log all pop-ups listed
            logThis("ChecK: Pop-up shown = " + reuse.Is_Popup_Msg_Shown());
            reuse.Get_Popups();
            
            //Validation
            if(reuse.Get_Popup_Msgs().contains("document(s) copied"))
            {
            	logThis("Check: Copy confirmation pop-up found!");
            }
            else
            {
            	logThis("WARNING: No copy confirmation pop-up found.");
            }
            
            Thread.sleep(5000);
            //Cancel the copy
            reuse.xControl_Option_Click(false, "Cancel");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    /**
     * Update file information
     * Demonstrates find and select dynamic content, validation of panel and headers open/matching,
     * Info/Summary controls / sub windows.
     */
    @Test
    public void Add_File_Note() throws InterruptedException
	{
        try
        {  	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
        	Info_Actions info = new Info_Actions(DMDriver);

        	logThis("Begin: Add_File_Note test");
        	
        	//Quick and dirty way to do 'test clean-up' for previous test failures
        	//Helps if an incorrect panel is open
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
        		home.Nav_MyDocs_Click();
        		Thread.sleep(5000);
        	}
        	else
        	{
        		home.Nav_Dashboard_Click();
        		home.Nav_MyDocs_Click();
        	}
        	
        	Thread.sleep(5000);
        	//Validating before and after if the Information/Summary panel is open.
            logThis("Check: Info Panel displayed =" + reuse.Is_Displayed_Properties_Panel());
            reuse.Info_Link_Click("file in files", "Tulips.jpg");
            logThis("Check: Info Panel displayed =" + reuse.Is_Displayed_Properties_Panel());


            //Validate Info Panel Header matches selected file/folder:
            if(info.Get_Info_Header().contains("Tulips.jpg"))
            {
            	logThis("Check: Header matches expected file name");
            }
            else
            {
            	logThis("WARNING: Header doesn't match file.  You can assert conditional fail here too.");
            }

            //Open Notes sub panel
            Thread.sleep(5000);
            info.Nav_Command_Click("Notes");
            Thread.sleep(5000);
            if(info.Is_Notes_Panel_Displayed())
            {
            	//Add note
                info.Set_Note("Hello world!");
                Thread.sleep(5000);
                info.Add_Note_Click();
                Thread.sleep(5000);
                if(reuse.Get_Popup_Msgs().contains("Note added"))
                {
                	logThis("Check: Pop-up 'Note added' found!");
                }
                else
                {
                	logThis("WARNING: Did not find 'Note added' in pop-ups");
                }
                logThis("Notes = '" + info.Get_Notes()  + "'");
            }
            else
            {
            	logThis("Failed to open Info panel");
            	Assert.fail("Info Panel ");
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    /**
     * While the Info/Summary tab is open for the selected file,
     * Delete (trash can icon) is clicked.
     * 
     * Demonstrates interaction with a modal OK/Cancel confirmation window.
     * Info_Actions contains the method called.
     * import org.openqa.selenium.Alert; needs to be added to the class
     * Passing true will trigger alert.accept(); and passing false will trigger alert.dismiss();
     */
    @Test
    public void Delete_File_Cancel() throws InterruptedException
	{
        try
        {        	
        	Info_Actions info = new Info_Actions(DMDriver);
        	
        	logThis("Begin: Delete_File_Cancel test");
        	
        	info.Delete_Click(false);  //Boolean blnDeleteOK

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    /**
     * Click on the Help icon in the left side menu.
     * 
     * Demonstrates how to handle multiple browser windows / tabs.
     */
    @Test
    public void Help_File() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
        	
        	logThis("Begin: Help_File test");
        	
        	Thread.sleep(5000);
        	//Get the handle for the parent window.
        	String strParent = reuse.Get_Browser_Handle();
        	logThis("Check: Window count = " + reuse.Get_Browser_Handle_Count());
        	home.Nav_Help_Click();
        	
        	Thread.sleep(5000);
            logThis("Check: Window count = " + reuse.Get_Browser_Handle_Count());
            
            //Change the focus of the web driver to the Help window
            reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/user/en/index.htm");
            //Here, we could perform additional tests to confirm the help file.
            
            Thread.sleep(5000);
            logThis("Closing Help windows");
            reuse.Close_Extra_Browser_Windows(strParent);
            Thread.sleep(5000);

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
}
