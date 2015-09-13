package bst.cpo.automation.ds.tests;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.sikuli.script.*;

import bst.cpo.automation.dm.actions.Login_Actions;
import bst.cpo.automation.ds.tests.BaseTest;
import bst.cpo.automation.ds.actions.Folder_Actions;
import bst.cpo.automation.ds.actions.Reusable_Actions;
import bst.cpo.automation.ds.actions.DSMenu_Actions;
import bst.cpo.automation.framework.ReusableCode;
import bst.cpo.automation.framework.dm.DM_Actions;

@SuppressWarnings("unused")
public class BasicUserTest extends BaseTest
{
	Screen skulliScreen;
	DSMenu_Actions DSMenu;
	Folder_Actions DSFolder;
	Reusable_Actions DSReuse;
	
    public BasicUserTest()
    {
    	DSMenu = new DSMenu_Actions(DSDriver);
    	DSFolder = new Folder_Actions(DSDriver);    	
    	skulliScreen = new Screen();
    	DSReuse = new Reusable_Actions(DSDriver);
    }
    
    /**
     * Check to see if DS is running.  If not, start it.
     * If it is running, see if it is synced.
     */
    @Test
    public void Launch_DS_Test() throws InterruptedException, AWTException, IOException
	{
        try
        {
        	logThis("Starting Launch_DS_Test");
   
			if (DSMenu.verifyDSReady()) {
				logThis("Yep!  DS is ready.");
			} else {
				logThis("Nope!  DS is not ready.");
			}
                    
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    // Demo Pass Test
    @Test
    public void Open_Folder_Test() throws InterruptedException, AWTException, IOException
	{
        try
        {
        	logThis("Starting Open_Folder_Test test");
            Start_DSTest();
            DSDriver.close();            
            DSMenu.access_DSMenu(); // find the DS icon and right click
            DSMenu.menu_Open_Cloud_Portal_Office_Folder(); // select the open cpo folder option
            DSFolder.closeFolderWin7(); // closes generic windows 7 folder window                          
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    // Demo DM/DS Test
    @Test
    public void DSDM_Test() throws InterruptedException, AWTException, IOException
	{
        try
        {        	
        	Login_Actions login = new Login_Actions(DSDriver);
        	logThis("Starting DM and DS Test");        
            Start_DS_DMTest();
            DMBrowser.DM_User_Login(basicUserUnderTest, basicUserUnderTestPwd);            
            
            DSMenu.access_DSMenu(); // find the DS icon and right click
            DSMenu.menu_Open_Cloud_Portal_Office_Folder(); // select the open cpo folder option
            DSFolder.closeFolderShortcut(); // closes folder window              
            DMBrowser.DM_User_Logout();
            Thread.sleep(8000);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage() + " " + e.toString());
        	Assert.fail();
        }
    }
    
    // Demo Settings Test
    @Test
    public void Settings_Test()
    {
        try
        {
            logThis("Starting Settings_Test");
            DSMenu.access_DSMenu();
            DSMenu.menu_Settings();
            DSMenu.settings_General();
            DSMenu.settings_Accounts();
            DSMenu.settings_Advanced();
            DSMenu.settings_About();
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

    // Demo General Tab Test
    @Test
    public void General_Tab_Test()
    {
        try
        { 
            logThis("Starting General_Tab_Test");
            DSMenu.settings_General();
            DSMenu.settings_General_Start_On_Boot_Checked();
            //DSMenu.settings_General_Start_On_Boot_UnChecked();
            
            DSMenu.settings_General_Update_CPO_UnChecked();
            //DSMenu.settings_General_Update_CPO_Checked();
            
            DSMenu.settings_General_Report_Usage_UnChecked();
            //DSMenu.settings_General_Report_Usage_Checked();
            
            DSMenu.settings_General_Activate_Notifications_Checked();
            //DSMenu.settings_General_Activate_Notifications_UnChecked();
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    // Demo Fail Test
    @Test
    public void ExpectedFail_Test() throws InterruptedException, AWTException, IOException
    {
        try
        {
        	Start_DSTest();
            Reusable_Actions reuse = new Reusable_Actions(DSDriver);

            reuse.ExpectedFail_Test();                
        }
        catch (Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();            
        }
    }
    
    // Demo Settings Test
    @Test
    public void Disconnect_User_Test()
    {
        try
        {
            logThis("Starting Disconnect_User_Test");
            DSMenu.settings_Accounts();
            DSMenu.settings_Accounts_Disconnect();
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    // Demo Settings Test
    @Test
    public void Quota_Info_Test()
    {
        try
        {
            logThis("Starting Quota_Info_Test");
            Double dblStorage[] = DSReuse.getStorageInfo(30);
            
            logThis("Quota Used = '" + dblStorage[0] + "' GB.");
            logThis("Quota Version = '" + dblStorage[1] + "' GB.");
            logThis("Quota Trash = '" + dblStorage[2] + "' GB.");
            logThis("Quota Allocated = '" + dblStorage[3] + "' GB.");
            logThis("Quota Remaining = '" + dblStorage[4] + "' GB.");
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    // Demo Settings Test
    @Test
    public void Get_Log_Time_Test()
    {
        try
        {
            String strLastLogTime = DSReuse.getLogTimeForString("Actual storage usage", 20); 
            if(strLastLogTime != "")
            {
            	logThis("Last quota entry in logs at: " + strLastLogTime);
            }
            else
            {
            	logThis("No quota information found in last 20 lines in log file.");
            	Assert.fail();
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
}
