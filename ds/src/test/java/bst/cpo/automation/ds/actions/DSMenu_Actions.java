package bst.cpo.automation.ds.actions;


import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.basics.*;
import org.sikuli.script.*;
import org.testng.Assert;
import org.testng.Reporter;

import bst.cpo.automation.ds.tests.BaseTest;
import bst.cpo.automation.framework.ReusableCode;

/*
 * This class contains the methods used to access and interact with the elements in the SikuliX image library.
 * Each method calls one or many SikuliX API methods to find and click on the element. 
 * If the element is not found on-screen, the methods will throw a FindFailed exception
 */

@SuppressWarnings("unused")
public class DSMenu_Actions extends BaseTest {

	WebDriver resuable_Driver;
	public ReusableCode resuable_Code;
	Screen sikuliScreen;
	private Pattern pattern;	 
	private Object y;	     // Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15
	Folder_Actions DSFolder;
	Reusable_Actions DSReuse;

	public DSMenu_Actions(WebDriver driver)
	{
		resuable_Driver = driver;
		resuable_Code = new ReusableCode(resuable_Driver);
		sikuliScreen = new Screen();	
        DSFolder = new Folder_Actions(DSDriver);
        DSReuse = new Reusable_Actions(DSDriver);
	}

	/* DS Menu Elements*/
    // Updated by Russ during code review for BSTCPO-2650
	/*
	 * This method will open the DS menu based on status.
	 * Assumptions:
	 * DS is currently running.
	 * Systray set to "Show icon and notifications" for DS.
	 */
	public void access_DSMenu() throws FindFailed
	{
		if(verify_DS_Icon_Online())
		{
			sikuliScreen.rightClick("CP_Red_Office_16x16_Online.png");
		} else if (verify_DS_Icon_Online_Synced()) {
			sikuliScreen.rightClick("CP_Red_Office_16x16_Synced.png");
		} else if (verify_DS_Icon_Online_Syncing()) {
			//This can be tricky, moving target.
			//Setting similarity lower seems to work.
			Pattern pSync = new Pattern("CP_Red_Office_16x16_Online.png").similar((float) 0.75);
			sikuliScreen.rightClick(pSync);
		} else if (verify_DS_Icon_Paused()) {
			sikuliScreen.rightClick("CP_Red_Office_16x16_Online_Pause.png");
		} else if (verify_DS_Icon_Offline()) {
			sikuliScreen.rightClick("CP_Red_Office_16x16_Offline.png");
		}
	}

	public void menu_Suspend() throws FindFailed
	{
		sikuliScreen.click("ds2_menu_options_suspend.png");
	}	
	
	// Added for BSCTPO-3013 - 2/17/15
 	public void menu_Resume() throws FindFailed
	{
        sikuliScreen.click("ds2_menu_options_resume.png");
	}
	// Added for BSCTPO-3013 - 2/17/15
    public void menu_Start() throws FindFailed
	{
        sikuliScreen.click("ds_menu_start.png");
	}
	public void menu_Open_Cloud_Portal_Office_Folder() throws FindFailed
	{
		sikuliScreen.click("ds2_menu_open_cpofolder.png");
	}

	public void menu_Open_Cloud_Portal_Office_Website() throws FindFailed
	{
		sikuliScreen.click("ds_menu_open_cpowebsite.png");
	}

	public void menu_Recently_Changed_Files() throws FindFailed
	{
		sikuliScreen.click("ds_menu_recentfiles.png");
	}	

	public void menu_Settings() throws FindFailed
	{
		//sikuliScreen.click("ds2_menu_options_settings.png");
		
		//Ugly way to get around ellipsis image not appearing
		Pattern pSettings = new Pattern("ds2_menu_options_2.png").targetOffset(139,0);
		sikuliScreen.mouseMove(pSettings);
		sikuliScreen.click(pSettings);
		sikuliScreen.wait(0.4);
		sikuliScreen.mouseMove("ds2_menu_options_settings.png");
		sikuliScreen.click("ds2_menu_options_settings.png");
	}
	
	public void menu_Status_Running() throws FindFailed
	{
		sikuliScreen.click("ds_menu_status_running.png");
	}

	public void menu_Help() throws FindFailed
	{
		sikuliScreen.click("ds2_menu_options_help.png");
	}

	// Added with BSTCPO-3574 - 2/4/15
	public void verifyMenu_HelpEng() throws FindFailed
	{
		pattern = new Pattern("ds_menu_help_english.png");
		sikuliScreen.find(pattern);
	}

	// Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15
	public void verifyMenu_About() throws FindFailed
	{
		// Verify that the About window is displayed
		logThis("Verify About window text: ");
		pattern = new Pattern("ds_menu_about_text.png");
		sikuliScreen.find(pattern);
		// Verify that the CopyRight is 2014 in the About window
		logThis("Verify About window Copyright: 2014 ");
		pattern = new Pattern("ds_menu_about_copyright.png");
		sikuliScreen.find(pattern);
		// Verify that the Version in the About window
		logThis("Verify About window version");
		pattern = new Pattern("ds_menu_about_version.png");
		sikuliScreen.find(pattern);
		// close the about window by pressing the OK button
		sikuliScreen.click("ds_menu_about_ok.png");
	}

	public void menu_Notifcation() throws FindFailed
	{
		sikuliScreen.click("ds_menu_notification.png");
	}

	public void menu_Exit() throws FindFailed
	{
		sikuliScreen.click("ds_menu_exit.png");
	}

	/* Settings Tabs*/

	public void settings_General() throws FindFailed
	{
		sikuliScreen.mouseMove("ds2_settings_general.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.mouseDown(Button.LEFT);
		sikuliScreen.mouseUp(Button.LEFT);   
	}
	
	/**
	 * TODO - Russ
	 * This really needs an overhaul.
	 * Rename methods according to action - IE, Check_xxx()
	 * Add boolean functions to determine if boxes are checked or not.
	 */

	public void settings_General_Start_On_Boot_Checked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_start_on_boot_checked.png");
	}

	public void settings_General_Start_On_Boot_UnChecked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_start_on_boot_unchecked.png");
	}

	public void settings_General_Update_CPO_Checked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_update_cpo_checked.png");
	}

	public void settings_General_Update_CPO_UnChecked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_update_cpo_unchecked.png");
	}
	
	public void settings_General_Report_Usage_Checked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_report_usage_checked.png");
	}

	public void settings_General_Report_Usage_UnChecked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_report_usage_unchecked.png");
	}
	
	public void settings_General_Activate_Notifications_Checked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_activate_notifications_checked.png");
	}

	public void settings_General_Activate_Notifications_UnChecked() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_general_activate_notifications_unchecked.png");
	}



	public void settings_Accounts() throws FindFailed 
	{
		sikuliScreen.mouseMove("ds2_settings_accounts.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.mouseDown(Button.LEFT);
		sikuliScreen.mouseUp(Button.LEFT);
	}

	public void settings_Accounts_Disconnect() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_accounts_disconnect.png");
		sikuliScreen.wait(1.0);
		sikuliScreen.click("ds2_settings_accounts_confirm.png");
	}

	public void settings_About() throws FindFailed
	{
		sikuliScreen.mouseMove("ds2_settings_about.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.mouseDown(Button.LEFT);
		sikuliScreen.mouseUp(Button.LEFT);       	
	}

	public void settings_Advanced() throws FindFailed
	{
		sikuliScreen.mouseMove("ds2_settings_advanced.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.mouseDown(Button.LEFT);
		sikuliScreen.mouseUp(Button.LEFT);  
	}
	
	public void settings_Network_UseProxyServer() throws FindFailed
	{
		sikuliScreen.click("ds_preferences_useproxy.png");
	}

	public void settings_Network_UseProxyServer_Selected() throws FindFailed
	{
		sikuliScreen.click("ds_peferences_network_useproxy_selected.png");
	}

	public void settings_Network_UseProxyServer_UnSelected() throws FindFailed
	{
		sikuliScreen.click("ds_preferences_network_useproxy_unselected.png");
	}

	// Added for BSTCPO_4613_Test 3/19/15
	public void settings_Network_UseProxyServer_Config() throws FindFailed
	{
		sikuliScreen.click("ds_peferences_network_useproxy_config.png");
	}
	// Added for BSTCPO_4613_Test 3/19/15
	public void settings_Network_UseProxyServer_Config_Server() throws FindFailed
	{
		Pattern pConfigServer = new Pattern("ds_peferences_network_useproxy_config_server.png").targetOffset(32,3);
		sikuliScreen.click(pConfigServer);
	}
	// Added for BSTCPO_4613_Test 3/19/15
	public void settings_Network_UseProxyServer_Config_Server2() throws FindFailed // this is a different view of the text box.
	{
		Pattern pConfigServer2 = new Pattern("ds_peferences_network_useproxy_config_server2.png").targetOffset(46,1);
		sikuliScreen.click(pConfigServer2);
	}
	// Added for BSTCPO_4613_Test 3/19/15
	public void settings_Network_UseProxyServer_Config_Port() throws FindFailed
	{
		Pattern pConfigPort = new Pattern("ds_peferences_network_useproxy_config_port.png").targetOffset(17,0);
		sikuliScreen.click(pConfigPort);
	}

	public void settings_Apply() throws FindFailed
	{
		sikuliScreen.click("ds2_settings_apply.png");
	}

	public void settings_Cancel() throws FindFailed
	{
		sikuliScreen.click("ds_preferences_cancel.png");
	}

	public void settings_Accounts_SelectSync() throws FindFailed
	{
		sikuliScreen.click("ds_preferences_advanced_select.png");
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_MyDocs_Open() throws FindFailed
	{
		Pattern pMyDocsOpen = new Pattern("ds_preferences_Advanced_SelectSync_MyDocs_Open.png").exact().targetOffset(-36,0);
		sikuliScreen.click(pMyDocsOpen);
		sikuliScreen.wait(0.4);
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_MyDocs_Guest_Uncheck() throws FindFailed
	{
		Pattern pGuestUncheck = new Pattern("ds_preferences_Advanced_SelectSync_MyDocs_Guest_Uncheck.png").targetOffset(-34,-2);
		sikuliScreen.click(pGuestUncheck);
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_MyDocs_Guest_Check() throws FindFailed
	{
		Pattern pGuestCheck = new Pattern("ds_preferences_Advanced_SelectSync_MyDocs_Guest_Check.png").targetOffset(-34,-2);
		sikuliScreen.click(pGuestCheck);
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_OtherDocs_UnCheck_Individual() throws FindFailed
	{
		Pattern pOtherDocsUnCheck1 = new Pattern("AllPlusShareChecked.png").targetOffset(-66,1);
		sikuliScreen.click(pOtherDocsUnCheck1);
		sikuliScreen.wait(0.4);  
		Pattern pOtherDocsUnCheck2 = new Pattern("ShareRWChecked.png").targetOffset(-44,0);
		sikuliScreen.click(pOtherDocsUnCheck2);
		sikuliScreen.wait(0.4);  
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_OtherDocs_Check_Individual() throws FindFailed
	{
		Pattern pOtherDocsCheck1 = new Pattern("AllPlusShareUnchecked.png").targetOffset(-68,1);
		sikuliScreen.click(pOtherDocsCheck1);
		sikuliScreen.wait(0.4);
		Pattern pOtherDocsCheck2 = new Pattern("ShareRWUnchecked.png").targetOffset(-46,0);
		sikuliScreen.click(pOtherDocsCheck2);
		sikuliScreen.wait(0.4);
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_MyDocs_UnCheck_Individual() throws FindFailed
	{
		Pattern pMyDocsCheck1 = new Pattern("TestFolder1Checked.png").targetOffset(-29,1);
		sikuliScreen.click(pMyDocsCheck1);
		sikuliScreen.wait(0.4);  
		Pattern pMyDocsCheck2 = new Pattern("TestFolder2Checked.png").targetOffset(-28,0);
		sikuliScreen.click(pMyDocsCheck2);
		sikuliScreen.wait(0.4);  
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void settings_Accounts_SelectSync_MyDocs_Check_Individual() throws FindFailed
	{
		Pattern pMyDocsCheck1 = new Pattern("TestFolder1UnChecked.png").targetOffset(-29,1);
		sikuliScreen.click(pMyDocsCheck1);
		sikuliScreen.wait(0.4);
		Pattern pMyDocsCheck2 = new Pattern("TestFolder2UnChecked.png").targetOffset(-32,0);
		sikuliScreen.click(pMyDocsCheck2);
		sikuliScreen.wait(0.4);
	}
	// Added for BSTCPO-3753
	public void settings_Accounts_SelectSync_MyDocs_Expand_To_Show_All() throws FindFailed
	{
		// This expands the Parent folder in the hierarchy 
		Pattern pExpandParent = new Pattern("ExpandParent.png").targetOffset(-48,0);
		sikuliScreen.click(pExpandParent);
		sikuliScreen.wait(0.4);   

		// This expands the Child folder in the hierarchy. 
		Pattern pExpandChild = new Pattern("ExpandChild.png").targetOffset(-52,0);
		sikuliScreen.click(pExpandChild);
		sikuliScreen.wait(0.4); 

		// This expands the Grandchild folder in the hierarchy.
		Pattern pExpandGrandchild = new Pattern("ExpandGrandchild.png").targetOffset(-69,0);
		sikuliScreen.click(pExpandGrandchild);
		sikuliScreen.wait(2.0); 

		if (sikuliScreen.exists(pExpandParent) !=null && sikuliScreen.exists(pExpandParent) !=null && 
				sikuliScreen.exists(pExpandParent) !=null)

			//This will create a log message letting the user know if the folder did or did not display correctly.
		{
			logThis("HJB The folders are displaying corectly in the hierarchy!");
		}

		else
		{
			logThis("HJB The folders are not displaying correctly in the hierachy!");
		}
	}
	// Added for BSTCPO-2699 - 2/10/15
	public void settings_Accounts_SelectSync_MyDocs_Expand_Check_Uncheck_Individual() throws FindFailed
	{
		Pattern pExpandParent = new Pattern("ExpandParent.png").targetOffset(-48,0); 
		sikuliScreen.click(pExpandParent);
		sikuliScreen.wait(0.4);   

		Pattern pExpandChild = new Pattern("ExpandChild.png").targetOffset(-52,0);
		sikuliScreen.click(pExpandChild);
		sikuliScreen.wait(0.4); 

		Pattern pExpandGrandchild = new Pattern("ExpandGrandchild.png").targetOffset(-69,0);
		sikuliScreen.click(pExpandGrandchild);
		sikuliScreen.wait(0.4); 


		Pattern pOtherDocsUnCheck1 = new Pattern("TestFolder1Checked.png").targetOffset(-29,1);
		sikuliScreen.click(pOtherDocsUnCheck1);
		Pattern pMyDocsCheck1 = new Pattern("TestFolder1UnChecked.png").targetOffset(-29,1);
		sikuliScreen.click(pMyDocsCheck1);
		sikuliScreen.wait(0.4);
	}


	// Added for BSTCPO-2694 3/6/15
	// If you are signed out, it will open the preferences and then go to the
	// advanced tab.
	// If you are not signed out it will go into the preferences and sign you
	// out.
	public void sign_Out_If_Signed_In() throws FindFailed {
		Pattern pSignedOut = new Pattern("CP_Red_Office_16x16_Offline.png")
				.similar((float) .95);
		if (sikuliScreen.exists(pSignedOut) != null)// looks to see if you are
													// logged out. Logs a
													// message that you are
													// already signed out,
													// or that you will be
													// signed out.
		{
			logThis("HJB You are already logged out!");
			sikuliScreen.wait(0.5);
			sikuliScreen.rightClick("CP_Red_Office_16x16_Offline.png");
			sikuliScreen.wait(.4);
			sikuliScreen.click("ds_menu_preferences.png");
			sikuliScreen.mouseMove("ds_preferences_account.png");
			sikuliScreen.wait(0.4);
		}
		else {
			logThis("HJB You are signed in, and you will be logged out.");
			sikuliScreen.wait(.4);
			sikuliScreen.rightClick("taskbar_cpoicon_online_visible.png");
			sikuliScreen.wait(.4);
			sikuliScreen.click("ds_menu_preferences.png");
			sikuliScreen.mouseMove("ds_preferences_account.png");
			sikuliScreen.wait(0.4);
			sikuliScreen.mouseDown(Button.LEFT);
			sikuliScreen.mouseUp(Button.LEFT);
			sikuliScreen.click("ds_preferences_account_signout.png");
			sikuliScreen.wait(6.0);
		}
		sikuliScreen.click("ds_preferences_apply.png");
	}
    
	// Added for BSTCPO-2694 3/6/15
    //This will change the folder location of the Cloud Portal Office directory.
    public void change_Folder_Location() throws FindFailed
    {
    	sikuliScreen.click("ds_preferences_advanced.png");
    	sikuliScreen.click("ds_preferences_advanced_change.png");
    	sikuliScreen.type("ds_preferences_advanced_change_directory.png", "C:\\Users\\hbirkholm\\Documents\\Cloud Portal Office\\");
    	sikuliScreen.click("Choose_Folder_Location.png");
    	//sikuliScreen.click("confirm_Folder_Location__Change_Yes.png");
    	sikuliScreen.click("ds_preferences_apply.png");
    }
    
    // Added for BSTCPO-2694 3/6/15
	// Also used by BSTCPO-2666
    // If you are signed out, this will sign you back in.
    public void sign_In_If_Signed_Out() throws FindFailed
    {
    	String password = basicUserUnderTestPwd;// using this because public static String was out of scope.
    	Pattern pSignedOut = new Pattern("CP_Red_Office_16x16_Offline.png").exact();
    	if (sikuliScreen.exists(pSignedOut) !=null) // looks to see if the icon is gray, and then logs the message.
    	{
    		logThis("HJB You are logged out, and now will need to be logged in!");

    		sikuliScreen.rightClick("CP_Red_Office_16x16_Offline.png");
    		sikuliScreen.wait(.4);
    		sikuliScreen.click("ds_menu_preferences.png");
    		sikuliScreen.mouseMove("ds_preferences_account.png");
    		sikuliScreen.wait(0.4);
    		sikuliScreen.mouseDown(Button.LEFT);
    		sikuliScreen.mouseUp(Button.LEFT);   
    		sikuliScreen.click("ds_preferences_account_signin.png");// Clicks the sign in button
    		sikuliScreen.wait(0.4);
    		sikuliScreen.click("ds_preferences_account_signin_enter_password.png"); //focuses on the password field.
    		sikuliScreen.wait(0.4);

    		sikuliScreen.paste(password);// enters in the default password listed in the BaseTest.java.
    		//sikuliScreen.paste(basicUserUnderTestPwd);
    		sikuliScreen.wait(0.4);
    		sikuliScreen.click("ds_preferences_account_signin_Ok.png");
        	sikuliScreen.wait(6.0); //had to increase from 2.0 to 6.0 for BSTCPO-2666
    		sikuliScreen.click("ds_preferences_apply.png");
    	}
    }

	// Added for BSTCPO-2702 3/6/15
	// checks to see that the Show Notifications checkbox is unchecked by
	// default. If it isn't a message will be logged.
	public void preferences_Check_Notifications_default() throws FindFailed {
		Pattern pShowNotificationsBox = new Pattern(
				"show_notifications_unchecked.png").similar((float) .99);
		if (sikuliScreen.exists(pShowNotificationsBox) != null) {
			logThis("HJB Notifications box is unchecked by default");
		}

		else

		{
			logThis("HJB The folder is Checked, and should not be by default!");
			sikuliScreen.click("ds_preferences_apply.png");
			Assert.fail();
		}
	}
    
	public void preferences_Advanced_SelectSync_Cancel() throws FindFailed
	{
		sikuliScreen.click("ds_preferences_advanced_select_cancel.png");
	}

	public void preferences_Advanced_SelectSync_OK() throws FindFailed
	{
		sikuliScreen.click("ds_preferences_advanced_select_ok.png");
	}

	// Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15
	// Also added for BSTCPO-2683 and BSTCPO-2684 - 2/6/15
	public void ds_Wait_Sync_Complete(Integer waitSeconds) throws FindFailed
	{   
		logThis("Waiting " + waitSeconds + " seconds for sync to complete...");
		Pattern pSyncComplete = new Pattern("DS_Popup_Sync_Complete.png").similar((float) 0.95);
		Pattern pSyncCompleteClose = new Pattern("DS_Popup_Sync_Complete.png").targetOffset(100,-10);
		sikuliScreen.wait(pSyncComplete, waitSeconds);
		if(sikuliScreen.exists(pSyncComplete) != null)
		{
			sikuliScreen.click(pSyncCompleteClose);  //Close the pop-up, might interfere with DS Icon pattern matching.

		}
		sikuliScreen.wait(0.5);
	}

	// Add for BSTCPO_3754 - 2/9/15
	public void preferences_Advanced_SelectSync_OtherDocs_Open() throws FindFailed
	{
		Pattern pOtherDocsOpen = new Pattern("ds_preferences_Advanced_SelectSync_OtherDocs_Open.png").exact().targetOffset(-39,-1);
		sikuliScreen.click(pOtherDocsOpen);
		sikuliScreen.wait(0.4);
	}
	// Add for BSTCPO_3754 - 2/9/15
	// This will check to see if the Read/Write Shared folder is displaying in the hierarchy
	public void preferences_Advanced_SelectSync_OtherDocs_RW_Exists() throws FindFailed
	{
		Pattern pRWFolder = new Pattern("RWShare.png");
		if (sikuliScreen.exists(pRWFolder) !=null)
		{
			logThis("HJB Read/Write Shared Folder exists in the folder hierarchy correctly");
		}

		else
		{
			logThis("HJB Read/Write Shared Folder DOES NOT exists in the folder hierarchy correctly");
			Assert.fail();
		}
	}

	// Add for BSTCPO_3755 - 2/9/15
	// This will check to see if the Read/Write/Delete Shared folder is displaying in the hierarchy
	public void preferences_Advanced_SelectSync_OtherDocs_RWD_Exists() throws FindFailed
	{
		Pattern pRWDFolder = new Pattern("RWDShare.png");
		if (sikuliScreen.exists(pRWDFolder) !=null)
		{
			logThis("HJB Read/Write/Delete Shared Folder exists in the folder hierarchy correctly");
		}

		else
		{
			logThis("HJB Read/Write/Delete Shared Folder DOES NOT exists in the folder hierarchy correctly");
			Assert.fail();
		}
	}

	// Add for BSTCPO_3756 - 2/9/15
	// This will check to see if the All Plus Shared folder is displaying in the hierarchy
	public void preferences_Advanced_SelectSync_OtherDocs_AllPlus_Exists() throws FindFailed
	{
		Pattern pAllPlusFolder = new Pattern("AllPlusShare.png");
		if (sikuliScreen.exists(pAllPlusFolder) !=null)
		{
			logThis("HJB AllPlus Shared Folder exists in the folder hierarchy correctly");
		}

		else
		{
			logThis("HJB AllPlus Shared Folder DOES NOT exists in the folder hierarchy correctly");
			Assert.fail();
		}	
	}

	// Added for BSTCPO-3757 2/12/15
	public void preferences_Advanced_SelectSync_OtherDocs_Blocked_Folder() throws FindFailed
	{
		Pattern pExpandRWDShare= new Pattern("RWDShare.png").targetOffset(-67,1);;
		Pattern pBlockedFolder = new Pattern("BeforeBlock.png");
		Pattern pAfterBlock = new Pattern("AfterBlockedPermissions.png");

		sikuliScreen.click(pExpandRWDShare);

		if (sikuliScreen.exists(pBlockedFolder) ==null  && sikuliScreen.exists(pAfterBlock) !=null)
		{
			logThis("HJB Folder is not displaying. Permissions have been blocked correctly");
		}
		else
		{
			logThis("HJB The child folder is still showing under the parent. Permissions have not been blocked correctly");
			Assert.fail();
		}    	    
	}

	// Added for BSTCPO 3187 - 2/11/15
	public void ds_Wait_Sync_Start(Integer waitSeconds, Integer fileCount) throws FindFailed
	{   
		//Was hoping to use switch / case logic, but pattern variables are out of scope.
		//May not be feasible to use fileCount parm for this method.
		logThis("Waiting " + waitSeconds + " seconds for sync to start...");
		if(fileCount == 1) 
		{
			Pattern pSyncStart = new Pattern("DS_Popup_Sync_Start_1_File.PNG").similar((float) 0.95);
			Pattern pSyncStartClose = new Pattern("DS_Popup_Sync_Start_1_File.PNG").targetOffset(140,-20);
			sikuliScreen.wait(pSyncStart, waitSeconds);
			if(sikuliScreen.exists(pSyncStart) != null)
			{
				sikuliScreen.click(pSyncStartClose);  //Close the pop-up, might interfere with DS Icon pattern matching.	
			}
			sikuliScreen.wait(0.5);
		} else if(fileCount == 100) {
			//Use image for syncing 100 files...
		} else {
			//set up a pattern for a catch all?
		}
		logThis("File sync has started...");
		sikuliScreen.wait(0.5);
	}
	
	// Added for BSCTPO-3013 - 2/17/15
	public boolean verify_DS_Icon_Paused()
	{
		Pattern pPaused = new Pattern("CP_Red_Office_16x16_Online_Pause.png").similar((float) 0.95);
		if(sikuliScreen.exists(pPaused) != null)
		{
			logThis("DS Icon is Paused.");
			return true;
		}
		return false;
	}

	// Added for BSCTPO-3013 - 2/17/15
	public boolean verify_DS_Icon_Online_Synced()
	{
		Pattern pSynced = new Pattern("CP_Red_Office_16x16_Synced.png").similar((float) 0.95);
		if(sikuliScreen.exists(pSynced) != null)
		{
			logThis("DS Icon is Online and Synced.");
			return true;
		}
		return false;
	}

	// Added for BSCTPO-3013 - 2/17/15
	public boolean verify_DS_Icon_Online_Syncing()
	{
		Pattern pSync1 = new Pattern("CP_Red_Office_16x16_Online_Sync_1.png").similar((float) 0.95);
		Pattern pSync2 = new Pattern("CP_Red_Office_16x16_Online_Sync_2.png").similar((float) 0.95);
		Pattern pSync3 = new Pattern("CP_Red_Office_16x16_Online_Sync_3.png").similar((float) 0.95);
		if(sikuliScreen.exists(pSync1) != null || sikuliScreen.exists(pSync2) != null || sikuliScreen.exists(pSync3) != null)
		{
			logThis("DS Icon is Syncing.");
			return true;
		}
		return false;
	}

	// Added for BSCTPO-3013 - 2/17/15
	public boolean verify_DS_Icon_Offline()
	{
		Pattern pOffline = new Pattern("CP_Red_Office_16x16_Offline.png").similar((float) 0.95);
		if(sikuliScreen.exists(pOffline) != null)
		{
			logThis("DS Icon is Offline.");
			return true;
		}
		return false;
	}

	// Added for BSCTPO-3013 - 2/17/15
	public boolean verify_DS_Icon_Online()
	{
		Pattern pOnline = new Pattern("CP_Red_Office_16x16_Online.png").similar((float) 0.95);
		if(sikuliScreen.exists(pOnline) != null)
		{
			logThis("DS Icon is Offline.");
			return true;
		}
		return false;
	}

	// Added for Russ during code review - BSCTPO-3013 - 2/17/15
	// More complete version added for Russ 3/24/15
	/*
	 * This method can be used before a test starts to ensure DS is ready.
	 * A possible place to call this might be Start_DSTest()
	 * Not sure how robust to make this / if run before every test (top heavy).
	 */
    public boolean verifyDSReady()
    {
		Pattern pProxy = new Pattern("DS_Preferences_Proxy_Titlebar.PNG").similar((float) 0.80);
		Pattern pCloseProxy = new Pattern(pProxy).targetOffset(230,-5);

		Pattern pSyncFolders = new Pattern("DS_Preferences_Synced_Folders_Titlebar.PNG").similar((float) 0.80);
		Pattern pCloseSyncFolders = new Pattern(pSyncFolders).targetOffset(225,-5);

		Pattern pPreferences = new Pattern("DS_Preferences_Titlebar.png").similar((float) 0.80);
		Pattern pClosePreference = new Pattern(pPreferences).targetOffset(235,-5);
		
    	if(!DSReuse.checkTaskRunning("clouddrivew.exe"))
    	{
    		logThis("DS is NOT running, attempting to start it.");
    		DSReuse.startDS();
    		if(DSReuse.checkTaskRunning("clouddrivew.exe"))
    		{
    			sikuliScreen.wait(10.0);
				if(verify_DS_Icon_Online_Synced())
				{
					return true;		
				}
    		}
    		else
    		{
    			logThis("Tried to launch DS and failed.");
    			return false;
    		}
    	}
    	else if(verify_DS_Icon_Online_Synced())
    	{
    		logThis("DS is synced and ready to test.");
    		return true;
    	}
    	else if(verify_DS_Icon_Paused())
    	{
    		//This just covers the basics.  Not all errors/windows are trapped for.
    		//Similarity of 80% work for both window with or without focus (red x)
    		logThis("DS is paused, attempting to get it running again...");
			
			//Close Proxy window if open
			if(sikuliScreen.exists(pProxy) != null)
			{
				logThis("Preferences>Network>Proxy window open, attempting to close.");
				try
				{
					sikuliScreen.click(pCloseProxy);
				}
				catch(Exception e)
				{
					logThis("Tried to close Proxy window but failed. " + e.getMessage());
					return false;					
				}
			}
			//Close Sync Folder window if open
			if(sikuliScreen.exists(pSyncFolders) != null)
			{
				logThis("Preferences>Advanced>Sync Folders window open, attempting to close.");
				try
				{
					sikuliScreen.click(pCloseSyncFolders);
				}
				catch(Exception e)
				{
					logThis("Tried to close Syc Folders window but failed. " + e.getMessage());
					return false;					
				}
			}
			//Close Preferences Window if open.
			if(sikuliScreen.exists(pPreferences) != null)
			{
				logThis("Preferences window was open, closing it.");
				try 
				{
					sikuliScreen.click(pClosePreference);
					if(verify_DS_Icon_Online_Synced())
					{
						return true;			
					}
				} catch (Exception e) {
					logThis("Tried to close Preferences window but failed. " + e.getMessage());
					return false;
				}
				
			} else {
				//Resume
				try {
					access_DSMenu();
					menu_Resume();
					sikuliScreen.wait(5.0);

					if(verify_DS_Icon_Online_Synced())
					{
						return true;				
					}
			
				} catch (Exception e) {
					logThis("Tried to 'Resume' DS but failed. " + e.getMessage());
					return false;				
				}
			}
		}
    	else if(verify_DS_Icon_Online_Syncing())
    	{
    		//Decided against using ds_Wait_Sync_Start()
    		//Didn't want to fail test before it starts, and how long do you wait?
    		logThis("WARNING: DS is currently syncing and may produce endesireable test results.");
			return true;		
		}
    	else if(verify_DS_Icon_Offline())
    	{
    		try 
    		{
    			sign_In_If_Signed_Out();
    			sikuliScreen.wait(10.0);
    			if(sikuliScreen.exists(pPreferences) != null)
    			{
    				sikuliScreen.click(pClosePreference);
    			}
				if(verify_DS_Icon_Online_Synced())
				{
					return true;			
				}
    		} catch (Exception e) {
    			logThis("Attempt to log into DS failed. " + e.getMessage());
    			return false;
    		}
    	}
    	else
    	{
    		logThis("WARNING: DS may not be ready, fell through logic check.");
    	}
    	return false;
    }
}
