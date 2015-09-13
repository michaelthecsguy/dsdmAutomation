package bst.cpo.automation.dm.tests;

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

public class RussTest extends BaseTest
{	
    public RussTest()
    {

    }
    
    /** 
     * Standard user login, used for primary testing
     * isUserBA - public boolean, defined in BaseTest.
     */
    @Test
    public void Login_User_1() throws InterruptedException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	isUserBA = false;
        	
        	logThis("TestNG Login Test");
            Start_DMTest();
            login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);            
            Start_DMAngular();
            /**
             * If not enough time is given, error is thrown.
             * "Exception during test occurred : angular.element(...).injector(...) is undefined"
             */
            Thread.sleep(8000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    /** 
     * Standard user login, secondary support testing
     * This user will be the owner of shared folders to primary user.
     */
    @Test
    public void Login_User_2() throws InterruptedException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	isUserBA = false;
        	
        	logThis("TestNG Login Test");
            Start_DMTest();
            login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);            
            Start_DMAngular();
            /**
             * If not enough time is given, error is thrown.
             * "Exception during test occurred : angular.element(...).injector(...) is undefined"
             */
            Thread.sleep(8000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    /**
     * BA user login.
     * Granted access to additional features.
     */
    @Test
    public void Login_User_BA() throws InterruptedException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	isUserBA = true;
        	
        	logThis("TestNG Login Test");
            Start_DMTest();
            login.BasicUser_Login(adminUserUnderTest, adminUserUnderTestPwd);            
            Start_DMAngular();
            /**
             * If not enough time is given, error is thrown.
             * "Exception during test occurred : angular.element(...).injector(...) is undefined"
             */
            Thread.sleep(7000);
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
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	login.User_Logout();
        	logThis("Thread sleep 5 seconds while signing out.");
        	Thread.sleep(5000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Bad_Login_Test() throws InterruptedException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	logThis("TestNG Bad Login Test");
            Start_DMTest();
            login.BasicUser_Login(basicUserUnderTest, "foo");            
            if(!login.Is_Bad_Login())
            {
            	logThis("Expected failure message not shown");
            	Thread.sleep(2000);
            	Assert.fail("Expected login failure, no message.");
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Navigation_Test() throws InterruptedException
    {
    	try
    	{
    		logThis("Starting Navigation_Test");
    		//Login_Actions login = new Login_Actions(DMDriver);
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	User_Actions user = new User_Actions();
        	
        	logThis("Navigation_Test");
    		//Start_DMTest();            
    		//login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		//Start_DMAngular();
    		
    		home.Navigation_Links(isUserBA);       		
    		Thread.sleep(1000);
    		home.Nav_Settings_Click(isUserBA);
    		//Thread.sleep(1000);
    		settings.Users_Click();
    		user.Search_For_User("rt9user90");    		
    		user.Search_Results();
    		Thread.sleep(2000);
    		settings.My_Profile_Click();    		
    		settings.My_Profile_Elements();
    		
            //login.User_Logout();
            //Thread.sleep(5000);
    	}
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Recently_UpdatedDocs_Test() throws Exception
    {
    	//Login_Actions login = new Login_Actions(DMDriver);
    	MyRecentlyUpdatedDocs_Actions myRecentDocs = new MyRecentlyUpdatedDocs_Actions(DMDriver);
    	Home_Actions home= new Home_Actions(DMDriver);
    	
    	try
    	{
    		//logThis("Starting Recently_UpdatedFolder_Test");
    		//Start_DMTest();            
    		//login.BasicUser_Login(basicUserUnderTest , basicUserUnderTestPwd);
    		//Start_DMAngular();
    		home.Nav_Dashboard_Click();
    		myRecentDocs.Collapse();
    		myRecentDocs.Collapse();
    		myRecentDocs.Zone();
    		
    		//login.User_Logout();
            //Thread.sleep(5000);
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    }
    
    @Test
    public void Recently_UpdatedFolder_Test() throws Exception
    {
    	//Login_Actions login = new Login_Actions(DMDriver);
    	MyRecentlyUpdatedFolders_Actions myRecentFolders = new MyRecentlyUpdatedFolders_Actions(DMDriver);
    	try
    	{
    		logThis("Starting Recently_UpdatedFolder_Test");
    		//Start_DMTest();            
    		//login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		//Start_DMAngular();
    		myRecentFolders.Collapse();
    		myRecentFolders.Collapse();
    		myRecentFolders.Zone();
    		//login.User_Logout();
            //Thread.sleep(5000);
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    }

    @Test
    public void Recently_UpdatedOther_Test() throws Exception
    {
    	//Login_Actions login = new Login_Actions(DMDriver);
    	//Home_Actions home = new Home_Actions(DMDriver);
    	OthersRecentlyUpdatedDocs_Actions otherRecentDocs = new OthersRecentlyUpdatedDocs_Actions(DMDriver);
    	try
    	{
    		logThis("Starting Recently_UpdatedOther_Test");
    		//Start_DMTest();            
    		//login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		//Start_DMAngular();
    		otherRecentDocs.Collapse();
    		otherRecentDocs.Collapse();
    		otherRecentDocs.Zone();
    		//login.User_Logout();
            //Thread.sleep(5000);
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    }

    @Test
    public void Recently_Updated_Test() throws Exception
    {
    	//Login_Actions login = new Login_Actions(DMDriver);
    	//Home_Actions home = new Home_Actions(DMDriver);
    	MyRecentlyUpdatedDocs_Actions myRecentDocs = new MyRecentlyUpdatedDocs_Actions(DMDriver);
    	MyRecentlyUpdatedFolders_Actions myRecentFolders = new MyRecentlyUpdatedFolders_Actions(DMDriver);
    	OthersRecentlyUpdatedDocs_Actions otherRecentDocs = new OthersRecentlyUpdatedDocs_Actions(DMDriver);
    	try
    	{
    		logThis("Starting Recently_Updated_Test");
    		//Start_DMTest();            
    		//login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		//Start_DMAngular();
    		logThis("***Russ myRecentDocs.Zone");
    		myRecentDocs.Zone();
    		logThis("***Russ myRecentFolders.Zone");
    		myRecentFolders.Zone();
    		logThis("***Russ otherRecentDocs.Zone");
    		otherRecentDocs.Zone();    		
    		//login.User_Logout();
            //Thread.sleep(5000);
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    } 

    @Test
    public void Settings_Navigation() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();

        	home.Nav_Settings_Click(isUserBA);
            settings.Sync_Computers_Click();
            settings.Users_Click();
            settings.Groups_Click();
            settings.Notifications_Click();
            settings.My_Log_Click();
            settings.My_Profile_Click();
            settings.My_License_Click();
            settings.My_Docs_Trash_Click();
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    }
    

	@Test
	public void Add_Folder() throws InterruptedException
	{
	    try
	    {
	    	Home_Actions home= new Home_Actions(DMDriver);
	    	AddFolderFile_Actions addfolderfile =new AddFolderFile_Actions();

        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
        		home.Nav_MyDocs_Click();
        		Thread.sleep(2000);
        	}
        	
            addfolderfile.Nav_New_Folder_Click();
            
            addfolderfile.Cancel_Add_Folder();
            
            addfolderfile.Nav_New_Folder_Click();
            
            if(addfolderfile.Is_Enabled_Create_Folder_Button()){
            	logThis("Create Button enabled");
            }else{
            	logThis("Create Button disabled");
            }
            addfolderfile.Set_Folder_Name("testing");
            if(addfolderfile.Is_Enabled_Create_Folder_Button()){
            	logThis("Create Button enabled");
            }else{
            	logThis("Create Button disabled");
            }
            addfolderfile.Set_Folder_Description("description testing");
            addfolderfile.Set_Folder_Doc_Type("Resume");
            addfolderfile.Create_Folder_Click();
            Thread.sleep(2000);


            Thread.sleep(2000);
            //addfolderfile.Add_New_Folder("russ", "", "");
            addfolderfile.Nav_Import_File_Click();
            if(addfolderfile.Is_Enabled_Import_File_Button()){
            	logThis("Import Button enabled");
            }else{
            	logThis("Import Button disabled");
            }
            addfolderfile.Set_File_Name("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg");
            if(addfolderfile.Is_Enabled_Import_File_Button()){
            	logThis("Import Button enabled");
            }else{
            	logThis("Import Button disabled");
            }
            Thread.sleep(2000);
            addfolderfile.Cancel_Import_File();
           
            //addfolderfile.Set_File_Doc_Type("Drawings");
            //addfolderfile.Import_File_Click();
            
            //addfolderfile.Import_File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg", "Generic");
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    }
	@Test
	public void Home_Links() throws InterruptedException
	{
	    try
	    {        	
	    	Home_Actions home= new Home_Actions(DMDriver);
	    	Reusable_Actions reuse = new Reusable_Actions(DMDriver);

            String strParent = reuse.Get_Browser_Handle();
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            home.Nav_Help_Click();
            Thread.sleep(3000);
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/user/en/index.htm");
            //do something to validate help?
            reuse.Close_Extra_Browser_Windows(strParent);

            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            home.Nav_Help_Link_Click();
            Thread.sleep(3000);
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/user/en/index.htm");
            //do something to validate help?
            reuse.Close_Extra_Browser_Windows(strParent);
            
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            home.Nav_Terms_Link_Click();
            Thread.sleep(3000);
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/policy/cloudportaloffice_terms.htm");
            //do something to validate help?
            reuse.Close_Extra_Browser_Windows(strParent);
            
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            home.Nav_Privacy_Link_Click();
            Thread.sleep(3000);
            logThis("Window count = " + reuse.Get_Browser_Handle_Count());
            reuse.Switch_Browser_By_URL("http://downloads.sharpb2bcloud.com.s3.amazonaws.com/qadm/help/policy/cloudportaloffice_policy.htm");
            //do something to validate help?
            reuse.Close_Extra_Browser_Windows(strParent);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	/** Some features require BA user */
    @Test
    public void Groups() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	Group_Actions group = new Group_Actions();
            
    //Create/Search Groups            
            home.Nav_Settings_Click(isUserBA);
            settings.Groups_Click();

            group.Create_Group_Click();
            logThis("Save and create button enabled = " + group.Is_Enabled_Create_Group_Button());
            
            group.Set_Group_Name("testGroup");
            logThis("Save and create button enamed = " + group.Is_Enabled_Create_Group_Button());
            group.Set_Group_Description("testGroupDesc");
            logThis("Save and create button enabled = " + group.Is_Enabled_Create_Group_Button());
            group.Add_Member_To_Group("rt9ba01");
            logThis("Save and create button enabled = " + group.Is_Enabled_Create_Group_Button());
            //group.Create_Group_Cancel();
            group.Create_Group_Submit();
           
            group.Create_Group("testGroup","testGroupDesc", "rt9ba01", null);
            Thread.sleep(2000);  
    //Search Group
            group.Search_Group("testGroup");
            logThis("Delete Panel Open =" + group.Is_Open_Delete_Group_Panel());
            group.Delete_Group_Click();
            logThis("Delete Panel Open =" + group.Is_Open_Delete_Group_Panel());
            group.Delete_Group_Cancel();
            logThis("Delete Panel Open =" + group.Is_Open_Delete_Group_Panel());
            
            
            Thread.sleep(2000);  
            group.Delete_Group_Click();
            logThis("Delete Panel Open =" + group.Is_Open_Delete_Group_Panel());
            group.Delete_Group_Confirm_Cancel_Click();
            logThis("Delete Panel Open =" + group.Is_Open_Delete_Group_Panel());
    //Delete Group
            /**
            group.Delete_Group_Click();
            group.Is_Open_Delete_Group_Panel();
            group.Delete_Group_Confirm_Delete_Click();
            group.Is_Open_Delete_Group_Panel();
             */
    //Edit Group
            logThis("Edit Group Panel open = " + group.Is_Open_Edit_Group_Panel());
            group.Search_Group("testGroup");

            logThis("Get: Description='" + group.Get_Search_Result_Description() + "'");
            logThis("Get: GroupMembers='" + group.Get_Search_Result_Member() + "'");
            logThis("Get: SubGroups='" + group.Get_Search_Result_Sub_Group() + "'");
            
            //logThis("Edit Group Panel open = " + group.Is_Open_Edit_Group_Panel());
            group.Edit_Group_Click();
            //logThis("Edit Group Panel open = " + group.Is_Open_Edit_Group_Panel());
            group.Edit_Group_Description("testGroupDescEdit Boom!");
            //settings.Edit_Group_Cancel();
            group.Edit_Group_Submit();
            //logThis("Edit Group Panel open = " + group.Is_Open_Edit_Group_Panel());

            
            logThis("Get: Description='" + group.Get_Search_Result_Description() + "'");
            logThis("Get: GroupMembers='" + group.Get_Search_Result_Member() + "'");
            logThis("Get: SubGroups='" + group.Get_Search_Result_Sub_Group() + "'");
     
/*            
            settings.Meeting_Room_Connector_Click();
            Thread.sleep(2000); 
            settings.Index_Fields_Click();
            Thread.sleep(2000); 
            settings.Admin_Log_Click();
            Thread.sleep(2000); 
*/
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
	}

    /** Requires BA user */
    @Test
    public void Meeting_Room() throws InterruptedException
	{
        try
        {
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	
        	home.Nav_Settings_Click(isUserBA);
        	
		    settings.Meeting_Room_Connector_Click();
		    Thread.sleep(2000); 
		    settings.Index_Fields_Click();
		    Thread.sleep(2000); 
		    settings.Admin_Log_Click();
		    Thread.sleep(2000); 
		}
		catch(Exception e)
		{
		    logThis("Exception during test occurred : " + e.getMessage());
			Assert.fail();
		}
	}

    /** Requires BA user */
    @Test
    public void System_Overview() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	SystemOverview_Actions overview = new SystemOverview_Actions();

          
            home.Nav_Settings_Click(isUserBA);
            
            settings.System_Overview_Click();
            Thread.sleep(2000); 
            logThis("Search button available = " + overview.Is_Enabled_Search_Button());
            overview.Set_User("rt9ba01");
            Thread.sleep(2000);
            overview.Clear_Click();
            Thread.sleep(2000);            
            overview.Set_User("rt9ba01");
            Thread.sleep(2000);
            overview.Search_Click();
            
            logThis("Properties panel open? = " + overview.Is_Open_Properties_Panel());
            overview.User_Results_Email_Click("rt1users@sla-engineering.com.test");
            logThis("Properties panel open? = " + overview.Is_Open_Properties_Panel());
            Thread.sleep(2000);
            overview.Close_Properties_Panel();
            Thread.sleep(2000);
            
            //TODO - navigate back to DM after opening LM link.
            //overview.License_Management_Click();
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Sync_Computers() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	SyncComputers_Actions sync = new SyncComputers_Actions();

         
            home.Nav_Settings_Click(isUserBA);
            settings.Sync_Computers_Click();
            
            //Currently unable to interact with dialog boxes "Save File / Cancel".
            //sync.Mac_Click();
            //sync.Windows_Click();

            //TODO Identify which one to update when possibly up to 3 listed.
            sync.Set_Description("Windows Desktop - automation edit");
            sync.Save_Description_Click();
            
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Index_fields() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	IndexFields_Actions index = new IndexFields_Actions();

            home.Nav_Settings_Click(isUserBA);
            settings.Index_Fields_Click();
            
            Thread.sleep(5000);
            logThis("Get: INDEXLIST=" + index.Get_Index_List());
            
            index.Click_Index_Link("Bill Of Lading");
            Thread.sleep(1000);
            index.Click_Index_Link("Contract");
            Thread.sleep(1000);
            index.Click_Index_Link("Drawings");
            Thread.sleep(1000);
            index.Click_Index_Link("Employment Application");
            Thread.sleep(1000);
            index.Click_Index_Link("Employee Record");
            Thread.sleep(1000);
            index.Click_Index_Link("Generic");
            Thread.sleep(1000);
            index.Click_Index_Link("Invoice");
            Thread.sleep(1000);
            index.Click_Index_Link("Legal");
            Thread.sleep(1000);
            index.Click_Index_Link("Medical Records");
            Thread.sleep(1000);
            index.Click_Index_Link("Proof of Delivery");
            Thread.sleep(1000);
            index.Click_Index_Link("Proposal");
            Thread.sleep(1000);
            index.Click_Index_Link("Resume");
            Thread.sleep(1000);
            index.Click_Index_Link("Sales/Purchase Order");
            Thread.sleep(1000);
            index.Click_Index_Link("Student Records");
            Thread.sleep(1000);
            index.Click_Index_Link("Workorder");
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    
    @Test
    public void User() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	User_Actions user = new User_Actions();

            home.Nav_Settings_Click(isUserBA);
            settings.Users_Click();

            
    		settings.Users_Click();
    		Thread.sleep(1000);
    		//user.Users_Search_for_Users();
    		Thread.sleep(1000);
    		user.Search_For_User("rt9user91");    		
    		user.Search_Results();   
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

    @Test
    public void My_Profile() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	MyProfile_Actions profile = new MyProfile_Actions();
          
            home.Nav_Settings_Click(isUserBA);
            settings.My_Profile_Click();
            
            profile.Set_Language("French");
            Thread.sleep(1000);
            profile.Select_Language_Click();
            Thread.sleep(1000);
            profile.Set_Language("English");
            profile.Select_Language_Click();
            
            settings.My_Profile_Elements();
            
            logThis("Save Enabled =" + profile.Is_Enabled_Save());
            logThis("Cancel Enabled =" + profile.Is_Enabled_Cancel());
            
            logThis("Get: First name='" + profile.Get_First_Name() + "'");
            logThis("Get: Last name='" + profile.Get_Last_Name() + "'");
            
            profile.Set_Avatar("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala_PNG_TEST.png");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }   

    @Test
    public void My_Log() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	MyLog_Actions mylog = new MyLog_Actions();

         
            home.Nav_Settings_Click(isUserBA);
            settings.My_Log_Click();
            
            logThis("Filter enabled=" + mylog.Is_Enabled_Filter());
            mylog.Set_Start_Date("2015-04-27");
            Thread.sleep(2000);
            logThis("Filter enabled=" + mylog.Is_Enabled_Filter());
            mylog.Set_End_Date("2015-04-30");
            Thread.sleep(2000);
            logThis("Filter enabled=" + mylog.Is_Enabled_Filter());
            mylog.Filter_Click();
            Thread.sleep(2000);
            mylog.Check_If_Results_Contain_String("Applesauce");
            mylog.Check_If_Results_Contain_String("YippieKaiYeah");
            
            mylog.Clear_Click();
            logThis("Filter enabled=" + mylog.Is_Enabled_Filter());
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }  

    @Test
    public void Admin_Log() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	AdminLog_Actions adminlog = new AdminLog_Actions();
          
            home.Nav_Settings_Click(isUserBA);
            settings.Admin_Log_Click();
            
            logThis("Filter enabled=" + adminlog.Is_Enabled_Filter());
            adminlog.Set_Start_Date("2015-04-27");
            Thread.sleep(2000);
            logThis("Filter enabled=" + adminlog.Is_Enabled_Filter());
            adminlog.Set_End_Date("2015-04-30");
            Thread.sleep(2000);
            logThis("Filter enabled=" + adminlog.Is_Enabled_Filter());
            adminlog.Filter_Click();
            Thread.sleep(2000);
            adminlog.Check_If_Results_Contain_String("Applesauce");
            adminlog.Check_If_Results_Contain_String("YippieKaiYeah");
            
            adminlog.Clear_Click();
            logThis("Filter enabled=" + adminlog.Is_Enabled_Filter());

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void MyDocsTrash() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	MyDocsTrash_Actions trash = new MyDocsTrash_Actions();
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);

            home.Nav_Settings_Click(isUserBA);
            settings.My_Docs_Trash_Click();
            
            trash.Check_If_Trash_Contains_String("Applesauce");
            trash.Check_If_Trash_Contains_String("abc123");
            Thread.sleep(1000);
            
/*            
            logThis("xControl Enabled = " + trash.Is_Displayed_xControl());
            trash.Select_All();
            Thread.sleep(1000);
            logThis("xControl Enabled = " + trash.Is_Displayed_xControl());
            Thread.sleep(1000);
            
            reuse.xControl_Option_Click("Cancel");
*/          
            logThis("xControl Enabled = " + reuse.Is_Displayed_xControl(false));
            reuse.Select_FileFolder("file in docsInTrash", "File5.jpg");
            logThis("xControl Enabled = " + reuse.Is_Displayed_xControl(false));

            logThis("FileCount=" + trash.Get_Files_Selected());
            Thread.sleep(1000);

            reuse.xControl_Option_Click(false, "Cancel");
            
            
            Thread.sleep(1000);
            reuse.Select_FileFolder("file in docsInTrash", "Tulips.jpg");
            reuse.xControl_Option_Click(true, "restore");
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
        	Home_Actions home= new Home_Actions(DMDriver);
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
        	SendLink_Actions sendlink = new SendLink_Actions(DMDriver);
          
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
        		home.Nav_MyDocs_Click();
        		Thread.sleep(2000);
        	}
        	else
        	{
        		//Cleans things up in case some strange panel is open.
        		home.Nav_Dashboard_Click();
        		home.Nav_MyDocs_Click();
        	}

        	logThis("Get: Active Send Link for File1.txt = " + reuse.Is_Send_Link_Active("file in files", "File1.txt"));
        	
            logThis("send link panel displayed=" + reuse.Is_Displayed_Send_File_Link_Panel());
            reuse.Send_Link_Click("file in files", "File1.txt");
            logThis("send link panel displayed=" + reuse.Is_Displayed_Send_File_Link_Panel());            
            logThis("Get: File/Folder = " + sendlink.Get_Header_Info());
            
            
            sendlink.Set_Expire("7 days");
            logThis("Get: EmailCount=" + sendlink.Get_Email_Count());
            logThis("SubmitEnabled=" + sendlink.Is_Send_Link_Enabled());
            sendlink.Set_Email("rholm@saturnsys.com");
            logThis("SubmitEnabled=" + sendlink.Is_Send_Link_Enabled());
            
            sendlink.Set_Message("my test message text!");
            
            logThis("Get: EmailCount=" + sendlink.Get_Email_Count());
            logThis("Get: PreviewExpire=" + sendlink.Get_Preview_Expire_Info());
            logThis("Get: PreviewFiles=" + sendlink.Get_Preview_Files());
            logThis("Get: PreviewMessage=" + sendlink.Get_Preview_Message());

            sendlink.Send_Link_Submit();
            Thread.sleep(3000);

            
            logThis("Get: Active Send Link for File1.txt = " + reuse.Is_Send_Link_Active("file in files", "File1.txt"));
            logThis("send link panel displayed=" + reuse.Is_Displayed_Send_File_Link_Panel());
            reuse.Send_Link_Click("file in files", "File1.txt");

            
            
            //Send Link Revoke section getters
            logThis("Get: RevokeEmail=" +  sendlink.Get_Revoke_Sent_To("rholm@saturnsys.com"));
            logThis("Get: RevokeExpire=" + sendlink.Get_Revoke_Expiration("rholm@saturnsys.com"));
            logThis("Get: RevokeVersion=" + sendlink.Get_Revoke_Version("rholm@saturnsys.com"));
            
            
            //sendlink.Revoke_Send_Link("rholm@saturnsys.com");
            Thread.sleep(3000);
            
            sendlink.Close_Send_Link_Panel();
            
            //logThis("Get: Active Send Link for File1.txt = " + reuse.Is_Send_Link_Active("file in files", "File1.txt"));

        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }


    @Test
    public void Capture_Msg() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);

         
        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
        		home.Nav_MyDocs_Click();
        		Thread.sleep(2000);
        	}
        	else
        	{
        		//Cleans things up in case some strange panel is open.
        		home.Nav_Dashboard_Click();
        		home.Nav_MyDocs_Click();
        	}

            //Test for confirmation of popup
            logThis("popup shown=" + reuse.Is_Popup_Msg_Shown());            
            reuse.Select_FileFolder("file in files", "File1.txt");
            reuse.xControl_Option_Click(false, "copy");

            logThis("popup shown=" + reuse.Is_Popup_Msg_Shown());
            reuse.Get_Popups();
            
            if(reuse.Get_Popup_Msgs().contains("document(s) copied"))
            {
            	logThis("We found a confirmaiton window for copy!");
            }
            else
            {
            	logThis("WARNING: No copy confirmation found.");
            }
            
            Thread.sleep(1000);
            reuse.xControl_Option_Click(false, "Cancel");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    @Test
    public void Simple_Search() throws InterruptedException
	{
        try
        {        	
        	Search_Actions search = new Search_Actions();
        	
		    search.Simple_Search("abc");
		    Thread.sleep(2000);
		    
		    if(search.Is_Empty_Search_Result()){
		    	logThis("No documents match your search criteria");
		    }
        	
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }       	
    
    @Test
    public void Advanced_Search() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	//Reusable_Actions reuse = new Reusable_Actions(DMDriver);
        	Search_Actions search = new Search_Actions();
		    
        	home.Nav_Dashboard_Click();
        	Thread.sleep(1000);
		    home.Nav_Advanced_Search_Click();
		    
		    search.Set_Full_Text("FullText");
		    search.Set_Title("TitleText");
		    search.Set_Description("DescText");
		    search.Set_Created_Start_Date("4/11/15");
		    search.Set_Created_End_Date("4/17/15");
		    search.Set_Modified_Start_Date("4/10/15");
		    search.Set_Modified_End_Date("4/18/15");
		    search.Set_File_Creator("rt9user91");
		    logThis("IncludeDeleted=" + search.Get_Include_Deleted());
		    search.Set_Include_Deleted(true);
		    logThis("IncludeDeleted=" + search.Get_Include_Deleted());
		    search.Set_Index_Field_Search("Resume");
		    search.Go_Click();

		    logThis("EmptyResult=" + search.Is_Empty_Search_Result());
		    
		    logThis("Get: FullText=" + search.Get_Full_Text());
		    logThis("Get: Title=" + search.Get_Title());
		    logThis("Get: Description=" + search.Get_Description());
		    logThis("Get: StartDate=" + search.Get_Created_Start_Date());
		    
		    logThis("Get: Creator=" + search.Get_File_Creator());

		    logThis("Get: IndexField=" + search.Get_Index_Field_Search());


		    search.Clear_Click();
		    
		    search.Set_Full_Text("tulips");
		    search.Go_Click();
		    
		    Thread.sleep(2000);
		    if(search.Is_Empty_Search_Result()){
		    	logThis("No documents match your search criteria");
		    }
		    else
		    {
		    	logThis("Looks like search returned results");
		    }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    @Test
    public void Info() throws InterruptedException
	{
        try
        {        	
        	Home_Actions home= new Home_Actions(DMDriver);
        	Reusable_Actions reuse = new Reusable_Actions(DMDriver);
        	Info_Actions info = new Info_Actions(DMDriver);

        	if(!home.Get_Nav_Tree_Header().contains("My docs"))
        	{
        		home.Nav_MyDocs_Click();
        		Thread.sleep(2000);
        	}
            
            logThis("Info Panel displayed =" + reuse.Is_Displayed_Properties_Panel());
            reuse.Info_Link_Click("file in files", "Tulips.jpg");
            logThis("Info Panel displayed =" + reuse.Is_Displayed_Properties_Panel());
            
            logThis("Info panel header File/Folder = " + info.Get_Info_Header());
    //Edit 
            info.Nav_Command_Click("Edit");
            Thread.sleep(2000);
            if(info.Is_Edit_Panel_Displayed())
            {
            	logThis("Description = '" + info.Get_Edit_Description() + "'");
            	info.Set_Edit_Description("Update Desc");
            	logThis("DocType = '" + info.Get_Edit_Doc_Type() + "'");
            	info.Set_Edit_Doc_Type("Generic");
            	
            	info.Save_Edit_Click();
            	
                if(reuse.Get_Popup_Msgs().contains("Document updated"))
                {
                	logThis("Pop-up 'Document updated' found!");
                }
                else
                {
                	logThis("WARNING: Did not find 'Document updated' in pop-ups");
                }
            }
            else
            {
            	logThis("Failed to open Edit panel");
            }
            Thread.sleep(2000);
    //Info            
            info.Nav_Command_Click("Infos");
            Thread.sleep(2000);
            if(info.Is_Info_Panel_Displayed())
            {
            	logThis("Get: Size = '" + info.Get_Info_Size() + "'");
            	logThis("Get: Version = '" + info.Get_Info_Version()  + "'");
            	logThis("Get: CreatedBy = '" + info.Get_Info_Created_By() + "'");
            	logThis("Get: CreatedOn = '" + info.Get_Info_Created_On()  + "'");
            	logThis("Get: LasteModBy = '" + info.Get_Info_Modified_By()  + "'");
            	logThis("Get: LastModOn = '" + info.Get_Info_Modified_On()  + "'");
            }
            else
            {
            	logThis("Failed to open Info panel");
            }
            Thread.sleep(2000);
    //Notes            
            info.Nav_Command_Click("Notes");
            Thread.sleep(2000);
            if(info.Is_Notes_Panel_Displayed())
            {
                info.Set_Note("Hello world!");
                info.Add_Note_Click();
                Thread.sleep(250);
                if(reuse.Get_Popup_Msgs().contains("Note added"))
                {
                	logThis("Pop-up 'Note added' found!");
                }
                else
                {
                	logThis("WARNING: Did not find 'Note added' in pop-ups");
                }
                logThis("Notes = '" + info.Get_Notes()  + "'");
                
                Thread.sleep(2000);
                //info.Delete_Note();
                //Note removed
            }
            else
            {
            	logThis("Failed to open Info panel");
            }
            Thread.sleep(2000);
    //History
            info.Nav_Command_Click("History");
            Thread.sleep(2000);
            if(info.Is_History_Panel_Displayed())
            {
            	logThis("Filter enabled = '" + info.Is_Filter_Enabled() + "'");
            	info.Set_History_Start_Date("2015-05-19");
            	info.Set_History_End_Date("2015-05-20");
            	logThis("Filter enabled = '" + info.Is_Filter_Enabled() + "'");
            	info.History_Filter_Click();
            	info.Get_History();
            	info.History_Clear_Click();
            }
            else
            {
            	logThis("Failed to open History panel");
            }
            Thread.sleep(2000);
    //Versions            
            info.Nav_Command_Click("Versions");
            Thread.sleep(2000);
            if(info.Is_Version_Panel_Displayed())
            {
            	
            }
            else
            {
            	logThis("Failed to open Version panel");
            }
            Thread.sleep(2000);
            
            info.Nav_Command_Click("Notifications");
            Thread.sleep(2000);
            if(info.Is_Notifications_Panel_Displayed())
            {
            	
            }
            else
            {
            	logThis("Failed to open Notifications panel");
            }
            Thread.sleep(2000);
            
    //Preview            
            info.Nav_Command_Click("Preview");
            Thread.sleep(2000);
            if(info.Is_Preview_Panel_Displayed())
            {
            	
            }
            else
            {
            	logThis("Failed to open Preview panel");
            }
            Thread.sleep(2000);

    //Lock & Unlock            
            if(info.Is_File_Locked())
            {
            	logThis("File is Locked, Unlocking it");
            	info.Nav_Command_Click("Unlock");
                if(reuse.Get_Popup_Msgs().contains("File unlocked"))
                {
                	logThis("Pop-up 'File unlocked' found!");
                }
                else
                {
                	logThis("WARNING: Did not find 'File unlocked' in pop-ups");
                }
            }
            else
            {
            	logThis("File is Unlocked, Locking it");
            	info.Nav_Command_Click("Lock");
                if(reuse.Get_Popup_Msgs().contains("File locked"))
                {
                	logThis("Pop-up 'File locked' found!");
                }
                else
                {
                	logThis("WARNING: Did not find 'File locked' in pop-ups");
                }
            }

            
            Thread.sleep(2000);
    //Delete
            //Not sure which way to go yet on Delete.
            //info.Nav_Command_Click("Delete");
            info.Delete_Click(false);
            
            //info.Close_Info_Panel();
            
            //info.Is_Info_Panel_Displayed();
            
            info.Close_Properties_Panel();
            
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
}
