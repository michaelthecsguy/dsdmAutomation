package bst.cpo.automation.dm.tests;

import org.testng.annotations.*;
import org.testng.Assert;

import bst.cpo.automation.dm.tests.BaseTest;
import bst.cpo.automation.dm.actions.Home_Actions;
import bst.cpo.automation.dm.actions.Login_Actions;
import bst.cpo.automation.dm.actions.MyRecentlyUpdatedDocs_Actions;
import bst.cpo.automation.dm.actions.MyRecentlyUpdatedFolders_Actions;
import bst.cpo.automation.dm.actions.OthersRecentlyUpdatedDocs_Actions;
import bst.cpo.automation.dm.actions.Settings_Actions;
import bst.cpo.automation.dm.actions.User_Actions;


public class BasicUserTest extends BaseTest
{	
    public BasicUserTest()
    {

    }
    
    @Test
    public void Login_Test() throws InterruptedException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DMDriver);
        	
        	logThis("TestNG Login Test");
            Start_DMTest();
            login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);            
            Start_DMAngular();
            
            login.User_Logout();
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
        	Login_Actions login = new Login_Actions(DMDriver);
        	Home_Actions home= new Home_Actions(DMDriver);
        	Settings_Actions settings = new Settings_Actions();
        	User_Actions user = new User_Actions();
        	
        	logThis("Navigation_Test");
    		Start_DMTest();            
    		login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		Start_DMAngular();
    		
    		home.Navigation_Links(false);       		
    		home.Nav_Settings_Click(false);   
    		
    		settings.Users_Click();    		
    		//user.Users_Search_for_Users();    		
    		user.Search_For_User("slaqa01");    		
    		user.Search_Results();    		
    		settings.My_Profile_Click();    		
    		settings.My_Profile_Elements();
    		
            login.User_Logout();
   		
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
    	Login_Actions login = new Login_Actions(DMDriver);
    	MyRecentlyUpdatedDocs_Actions myRecentDocs = new MyRecentlyUpdatedDocs_Actions(DMDriver);
    	
    	try
    	{
    		logThis("Starting Recently_UpdatedFolder_Test");
    		Start_DMTest();            
    		login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		Start_DMAngular();
    		myRecentDocs.Collapse();
    		myRecentDocs.Collapse();
    		myRecentDocs.Zone();
    		
    		login.User_Logout();
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
    	Login_Actions login = new Login_Actions(DMDriver);
    	MyRecentlyUpdatedFolders_Actions myRecentFolders = new MyRecentlyUpdatedFolders_Actions(DMDriver);
    	try
    	{
    		logThis("Starting Recently_UpdatedFolder_Test");
    		Start_DMTest();            
    		login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		Start_DMAngular();
    		myRecentFolders.Collapse();
    		myRecentFolders.Collapse();
    		myRecentFolders.Zone();
    		login.User_Logout();
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
    	Login_Actions login = new Login_Actions(DMDriver);
    	//Home_Actions home = new Home_Actions(DMDriver);
    	OthersRecentlyUpdatedDocs_Actions otherRecentDocs = new OthersRecentlyUpdatedDocs_Actions(DMDriver);
    	try
    	{
    		logThis("Starting Recently_UpdatedOther_Test");
    		Start_DMTest();            
    		login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		Start_DMAngular();
    		otherRecentDocs.Collapse();
    		otherRecentDocs.Collapse();
    		otherRecentDocs.Zone();
    		login.User_Logout();
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
    	Login_Actions login = new Login_Actions(DMDriver);
    	//Home_Actions home = new Home_Actions(DMDriver);
    	MyRecentlyUpdatedDocs_Actions myRecentDocs = new MyRecentlyUpdatedDocs_Actions(DMDriver);
    	MyRecentlyUpdatedFolders_Actions myRecentFolders = new MyRecentlyUpdatedFolders_Actions(DMDriver);
    	OthersRecentlyUpdatedDocs_Actions otherRecentDocs = new OthersRecentlyUpdatedDocs_Actions(DMDriver);
    	try
    	{
    		logThis("Starting Recently_Updated_Test");
    		Start_DMTest();            
    		login.BasicUser_Login(basicUserUnderTest, basicUserUnderTestPwd);
    		Start_DMAngular();
    		myRecentDocs.Zone();
    		myRecentFolders.Zone();
    		otherRecentDocs.Zone();    		
    		login.User_Logout();
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during test occurred : " + e.getMessage());
    		Assert.fail();
    	}
    }    
}
