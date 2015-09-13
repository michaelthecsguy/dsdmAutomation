package bst.cpo.automation.ds.tests;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.sikuli.script.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import bst.cpo.automation.ds.tests.BaseTest;
import bst.cpo.automation.ds.actions.Folder_Actions;
import bst.cpo.automation.ds.actions.Reusable_Actions;
import bst.cpo.automation.ds.actions.DSMenu_Actions;
import bst.cpo.automation.framework.ReusableCode;


/*
 * This is a special tutorial class for Russ.
 */

@SuppressWarnings("unused")
public class RussTest extends BaseTest
{
	Screen sikuliScreen;
	DSMenu_Actions DSMenu;
	Folder_Actions DSFolder;
	Reusable_Actions DSReuse;
	
    public RussTest()
    {
    	sikuliScreen = new Screen();
    	DSMenu = new DSMenu_Actions(DSDriver);
    	DSFolder = new Folder_Actions(DSDriver);
    	DSReuse = new Reusable_Actions(DSDriver);
    }    
    
	// Add for BSTCPO-2683 and BSTCPO-2684 - 2/6/15
    //Verify local file system behavior:
	//From Win file explorer, inside a synchronized folder, it is possible to create folders
    @Test
    public void BSTCPO_2683_Test()
    {
    	String testFolder = "BSTCPO_2683";
    	try
        {
            Start_DSTest();
            DSMenu.access_DSMenu();
            DSMenu.menu_Open_Cloud_Portal_Office_Folder();
            DSFolder.cpoFolder_OpenMyDocs();
            sikuliScreen.type(Key.ALT);
            Thread.sleep(1000);
            sikuliScreen.type("f");  						//Keyboard shortcut in Windows Explorer, open File menu
            Thread.sleep(1000);
            sikuliScreen.type("w");  										//From File menu drop down, select New.
            Thread.sleep(1000);
            sikuliScreen.type("f");  										//From New sub menu, select Folder.
            Thread.sleep(1000);
            sikuliScreen.type(testFolder);  								//Type in the name of the folder.
            Thread.sleep(1000);
        	sikuliScreen.type(Key.ENTER);									//Press Enter to save folder name.
            Thread.sleep(1000);
    		DSFolder.verifyFolderExists(cpoMyDocFolder + "\\" + testFolder);//Validate folder was created.
        	DSFolder.deleteFile(cpoMyDocFolder + "\\" + testFolder);		//Test clean up
            DSFolder.closeFolderShortcut(); // closes the window with the ALT + F4 shortcut
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
	
	// Add for BSTCPO-2684 - 2/6/15
    //Verify local file system behavior:
	//From Win file explorer, inside a synchronized folder, it is possible to delete documents
    @Test
    public void BSTCPO_2684_Test()
    {
    	//Pre-Condition:  BSTCPO_2684.txt.txt file exists in "My Docs" folder.
    	String testFile = "\\BSTCPO_2684.txt";
        DSFolder.copyFileToMyDocs("bin\\BSTCPO_2684.txt");
    	try
        {
			DSFolder.verifyFileExists(cpoMyDocFolder + testFile, true);
			DSFolder.deleteFile(cpoMyDocFolder + testFile);
			DSFolder.verifyFileDoesNotExist(cpoMyDocFolder + testFile);
        	DSMenu.ds_Wait_Sync_Complete(45);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
	// Added for BSTCPO 3187 - 2/11/15 - USING SLAQA07 to test with
    //Verify sync behavior:
	//30 second sync window is extended if file size requires longer than 30 seconds to sync.
    // ** This test is no longer needed per Thriveni.
    @Test
    public void BSTCPO_3187_Test()
    {
		//Test pre-conditions and assumptions:
		//	1. Files "20MB_File.txt" and "300MB_File.txt" exists in synced folder \\My Docs\\Automation_Files
    	//  2. Using method DSFolder.forceSync instead of adding another file as per test steps.
    	//  3. Assumption is that a 5 second window is acceptable to pass/fail extended period.
    	//  4. Test currently only accounts for upsync to DM, not downsync from DM.
    	//  5. Baseline test has 20 MB file taking ~ 60 seconds to sync, which should be sufficient for test.
    	String testFolder = cpoMyDocFolder + "\\BSTCPO_3187";  //Decided not to use a sub folder after cleanup was added.
    	String testAutomationFolder = cpoMyDocFolder + "\\Automation_Files";
    	String testFile = "\\300MB_File.txt";
    	String testFile2 = "\\20MB_File.txt";
    	File dir = new File(testFolder);
    	try
        {
            //Pre-test cleaning
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Pre-test clean up, sync before test begins.");
            	DSMenu.ds_Wait_Sync_Complete(45);
            }

            DSFolder.createDirectoryPath(testFolder);
            
    		//Copy in big file
    		DSFolder.copyFile(testAutomationFolder + testFile, testFolder + testFile);
    		DSFolder.verifyFileExists(testFolder + testFile);
 		
        	//Wait for big file to start syncing...
    		DSMenu.ds_Wait_Sync_Start(45, 1);
    		double syncStart = System.currentTimeMillis()/1000;
    		//Copy in second file after sync start for first file.
    		DSFolder.copyFile(testAutomationFolder + testFile, testFolder + testFile2);
    		DSFolder.verifyFileExists(testFolder + testFile2);    		
        	
    		//20 minute wait for 300 mb file to complete sync
    		//Baseline test took just under 15 min.
    		DSMenu.ds_Wait_Sync_Complete(1200);  
         	double syncCompleteTime = System.currentTimeMillis()/1000;
        	double syncFileTime = syncCompleteTime - syncStart;
        	logThis("It took " + syncFileTime + " seconds for the big file to sync after it started to sync.");
        	sikuliScreen.wait(1.0);

        	//Now we wait for the second file to start syncing.
    		DSMenu.ds_Wait_Sync_Start(40, 1);
        	double syncExtendedTime = System.currentTimeMillis()/1000;
        	double diff = syncExtendedTime - syncCompleteTime;
        	logThis("The extended time was " + diff + " seconds.");
        	
        	//Test clean up.  Do before conditional Assert.fail() since it acts like an exit.
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Pre-test clean up, sync before next test begins.");
            	DSMenu.ds_Wait_Sync_Complete(45);
            }
        	
        	// Pass/Fail based on 5 seconds on each side of "30 second sync window"
        	if(diff < 25)
        	{
        		logThis("FAIL: Synced again in less than 25 seconds.");
        		Assert.fail();
        	} else {
        		if(diff > 35)
        		{
        			logThis("FAIL: Sync too longer than 35 seconds.");
        			Assert.fail();
        		}
        	}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    
    // Added for BSTCPO-2650 - 2/18/15
    //Verify DS supports synchronization of all CloudDesk supported file types
    @Test
    public void BSTCPO_2650_Test()
    {
    	/*
    	 * This may also satisfy test case BSTCPO-3331.
    	 * Pre-Condition:  All files exist in a known location.
    	 * Stepping through each file type to validate.
    	 * - ran into sync issues with bulk copy.
    	 * - IE, sync began before all files were copies
    	*/
    	String strTestFolder = cpoMyDocFolder + "\\BSTCPO_2650";
    	File dir = new File(strTestFolder);
    	String strCopyFolder = "src\\test\\resources";
    	//Temporary solution for big files. Josh mentioned using AWS S3 space?
    	String strCopyBigFolder = cpoMyDocFolder + "\\Automation_Files"; 
    	String[] fileArray = {
    			"FileTypeAVG.avg",
    			"FileTypeCAT.cat",    			
    			"FileTypeDOC.doc",
    			"FileTypeDOCX.docx"
    			/*
    			"FileTypeGIF.gif",
    			"FileTypeHTML.html",
    			"FileTypeJPEG.jpeg",
    			"FileTypeJPG.jpg",
    			"FileTypeLOG.log",
    			"FileTypePDF.pdf",
    			"FileTypePNG.png",
    			"FileTypePPT.ppt",
    			"FileTypePPTX.pptx",
    			"FileTypeRTF.rtf",
    			"FileTypeSWS.sws",
    			"FileTypeTIF.tif",
    			"FileTypeTXT.txt",
    			"FileTypeXLS.xls",
    			"FileTypeXLSX.xlsx",
    			"FileTypeXML.xml",
    			"FileTypeXPS.xps",
    			"FileTypeZIP.zip"
    			*/
    	};
    	//These files may be too big.  Waiting for instruction on where to put them.  
    	String[] fileBigArray = {
    			"FileTypeDMG.dmg",  //Renamed CPO install file for Mac for testing
    			//"FileTypeEXE.exe",  //Renamed CPO install file for Win for testing
    			//"FileTypeJAR.jar",  //need to create test file
    			//"FileTypeMPEG.mpeg", //need to create test file
    			//"FileTypeWMV.wmv", //Renamed "Wildlife.wmv" file that came with Win 7.
    	};
    	
    	try
        {
            //Pre-test cleaning
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Pre-test clean up, sync before test begins.");
            	DSMenu.ds_Wait_Sync_Complete(60);
            }

            DSFolder.createDirectoryPath(strTestFolder);
            DSMenu.ds_Wait_Sync_Complete(60);
            
            //Small files
            for(int i =0; i< fileArray.length; i++)
    		{
        		logThis("Testing file: " + fileArray[i]);
        		DSFolder.copyFile(strCopyFolder + "\\" + fileArray[i], strTestFolder + "\\" + fileArray[i]);
        		DSFolder.verifyFileExists(strTestFolder + "\\" + fileArray[i]);
            	DSMenu.ds_Wait_Sync_Complete(60);    			
    		}

            //Big Files, waiting much longer for sync to complete
            for(int i =0; i< fileBigArray.length; i++)
    		{
        		logThis("Testing file: " + fileBigArray[i]);
        		DSFolder.copyFile(strCopyBigFolder + "\\" + fileBigArray[i], strTestFolder + "\\" + fileBigArray[i]);
        		DSFolder.verifyFileExists(strTestFolder + "\\" + fileBigArray[i], true);
            	DSMenu.ds_Wait_Sync_Complete(300);			
    		}
                        
            //Post-test cleaning
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Post-test clean up, wait for sync before continue.");
            	DSMenu.ds_Wait_Sync_Complete(60);
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    } 
	// Added for BSTCPO-3013 2/17/15
    //Verify pause works on Desktop DS
    @Test
    public void BSTCPO_3013_Test()
    {
    	//Pre-Condition: DS is running and is synced.
    	//Test does not yet include down sync from DM
    	Pattern pSyncComplete = new Pattern("DS_Popup_Sync_Complete.png").similar((float) 0.95);
    	
    	try
        {
    		//Pause DS
    		logThis("Pausing DS...");
    		DSMenu.access_DSMenu();
    		sikuliScreen.wait(2.0);
    		DSMenu.menu_Suspend();  //For some reason, old Pause image didn't work.
    		sikuliScreen.wait(2.0);
    		
    		//Verify DS is paused
    		if(!DSMenu.verify_DS_Icon_Paused())
    		{
    			logThis("DS icon is not 'Paused'.");
    			Assert.fail();    			
    		}

    		//Add a file to DS, verify DS doesn't sync while paused.
    		DSFolder.forceSync();
            logThis("Attempting to force sync, waiting 45 seconds.");
    		if(sikuliScreen.exists(pSyncComplete, 45.0) != null) {
            	logThis("FAIL: Sync began while DS was paused.");
            	Assert.fail();
            }

    		//Resume DS
    		logThis("Resuming DS...");
            DSMenu.access_DSMenu();
    		DSMenu.menu_Resume();
    		DSMenu.ds_Wait_Sync_Complete(45);
    		sikuliScreen.wait(2.0);
    		
    		
    		//Verify DS Status - icon running
    		//This may not be needed if sync completed...
    		if(!DSMenu.verify_DS_Icon_Online_Synced())
    		{
       			logThis("FAIL: DS status is not 'running' after Resume.");
       			Assert.fail();    			
    		}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }
    // Added for BSTCPO-2632 - 2/11/15
	//Verify DS - notification is displayed for files changed in DS local folder
    @Test
    public void BSTCPO_2632_Test()
    {
    	//For automation purposes, we can probably skip manual GUI steps, but left them in.
    	try
        {
            Start_DSTest();
            DSMenu.access_DSMenu();
            sikuliScreen.wait(1.0);

            DSMenu.menu_Open_Cloud_Portal_Office_Folder();
            sikuliScreen.wait(1.0);

            DSFolder.cpoFolder_OpenMyDocs();
            //This creates a new "Force_Sync.txt" file or updates an existing one in My Docs.
            DSFolder.forceSync();

            DSFolder.closeFolderShortcut();
            sikuliScreen.wait(1.0);
            DSMenu.ds_Wait_Sync_Complete(60);
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	// Added for BSTCPO-3732 - 2/11/15
    //Verify for syncronization status message while syncing
    @Test
    public void BSTCPO_3732_Test()
    {
    	//For automation purposes, we can probably skip manual GUI steps, but left them in.
    	try
        {
        	String testFolder = "\\BSTCPO_3732";
        	String testFolderPath = cpoMyDocFolder + testFolder;
        	File dir = new File(testFolderPath);

            //Pre-test cleaning
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Pre-test clean up, sync before test begins.");
            	DSMenu.ds_Wait_Sync_Complete(60);
            }
        	
        	Start_DSTest();
            DSMenu.access_DSMenu();
            sikuliScreen.wait(1.0);

            DSMenu.menu_Open_Cloud_Portal_Office_Folder();
            sikuliScreen.wait(1.0);
           
            //2. Copy and Paste folders/files
            DSFolder.createDirectoryPath(testFolderPath + "\\Child\\GrandChild");
            DSFolder.createFile(testFolderPath, "Test_File1.txt");
            DSFolder.createFile(testFolderPath, "Test_File2.txt");
            DSFolder.closeFolderShortcut();
            
            //3. Observe the Notification status message
            sikuliScreen.wait(1.0);
            DSMenu.ds_Wait_Sync_Complete(60);
            
            //Clean up
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Post-test clean up, sync before moving on.");
            	DSMenu.ds_Wait_Sync_Complete(60);
            }
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }    
	// Added for BSTCPO 3025 3/9/15
    /*
     * Verify Sync continues when large number of files are copied in local sync folder
     */
    @Test
    public void BSTCPO_3025_Test() throws InterruptedException, AWTException, IOException
    {
    	try
        {
            String testFolderPath = cpoMyDocFolder + "\\BSTCPO_3025";
            File dir = new File(testFolderPath);
            Integer fileCount = 125;  //number of empty files to create
            
            //Pre-Condition setup...
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Pre-test clean up, sync before test begins.");
            	DSMenu.ds_Wait_Sync_Complete(45);
            }
    		
            DSFolder.createDirectoryPath(testFolderPath);
            //wait for sync here to maximize time to create files before next sync.
            DSMenu.ds_Wait_Sync_Complete(45);

            for(int i =0; i< fileCount; i++)
    		{
        		DSFolder.createFile(testFolderPath, "TestFile_" + i + ".txt");
    		}        	
            
            //baseline of 125 zero length files took just over 2 minutes to sync.
            //files sync in alphanumeric order.  0, 1, 10, 100, 101, etc...  So 99 is last.
            //Files were all uploaded in one sync cycle.
            DSMenu.ds_Wait_Sync_Complete(300); 
            
            //FIX ME - A true test requires validation of file created on DM.
            //Cheating here.  Checking log files to see if last one created.  Lots of log lines before sleep.
            String strLastFileTime = DSReuse.getLogTimeForString("Creating remote document 'TestFile_99.txt' in folder 'BSTCPO_3025'", 60); 
            if(strLastFileTime != "")
            {
            	logThis("Last test file created remotely at " + strLastFileTime);
            }
            else
            {
            	logThis("No information for last file found in logs.");
            	Assert.fail();
            }

        	//Post-Test cleanup
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Post-test clean up, sync before next test.");
            	DSMenu.ds_Wait_Sync_Complete(60);
            }          	
        }
        catch (Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();            
        }
    }   
    
	// Added for BSTCPO 2728 3/9/15
    /*
     * Verify UI notification
     * When synchronization process is running, UI feedback appears in system tray indicating sync is happening
     * Test uses a large file so Sikuli has time to validate DS icon syncing.
     */
    @Test
    public void BSTCPO_2728_Test()
    {
        String testFolderPath = cpoMyDocFolder + "\\BSTCPO_2728";
    	String testAutomationFolder = cpoMyDocFolder + "\\Automation_Files";
    	String testFile = "\\5MB_File.txt";
        File dir = new File(testFolderPath);
        try
        {
	    	//Pre-Condition setup...
	        if(DSFolder.deleteDirectory(dir))
	        {
	        	logThis("Pre-test clean up, sync before test begins.");
	        	DSMenu.ds_Wait_Sync_Complete(45);
	        }    	

            DSFolder.createDirectoryPath(testFolderPath);
            DSFolder.copyFile(testAutomationFolder + testFile, testFolderPath + testFile);
            
            //Wait for sync and for syncing message to go away (interferes with icon find).
            DSMenu.ds_Wait_Sync_Start(45, 1);
            sikuliScreen.mouseMove(5, 5);  //crude way to get sync message to go away.
            sikuliScreen.wait(10.0);  
            
            if(!DSMenu.verify_DS_Icon_Online_Syncing())
            {
            	Assert.fail("DS Icon is NOT syncing");
            }
            
        	//Post-Test cleanup
            if(DSFolder.deleteDirectory(dir))
            {
            	logThis("Post-test clean up, sync before test begins.");
            	DSMenu.ds_Wait_Sync_Complete(45);
            }    
        }
        catch (Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();            
        }
    }
	
	// Added for BSTCPO 3572 - 3/10/15
    // Verify Desktop Sync -Pause
    @Test
    public void BSTCPO_3572_Test()
    {
    	Pattern pMenuSatusPaused = new Pattern("ds_menu_status_paused.PNG").similar((float) 0.95);
    	try
        {
            Start_DSTest();
            DSMenu.access_DSMenu();
            sikuliScreen.wait(1.0);
            DSMenu.menu_Suspend();
            sikuliScreen.wait(10.0);
            
            if(!DSMenu.verify_DS_Icon_Paused())
            {
            	logThis("DS icon is NOT paused.");
            }
            
            DSMenu.access_DSMenu();
            sikuliScreen.wait(2.0);
            if(sikuliScreen.exists(pMenuSatusPaused) != null)
            {
            	logThis("DS menu status is paused.");
            }else{
            	sikuliScreen.type(Key.ESC);  //close menu window
            	Assert.fail("DS menu status is NOT paused.");
            }
            sikuliScreen.type(Key.ESC);  //close menu window
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	// Added for BSTCPO 3573 3/10/15
    // Verify Desktop Sync -Resume
    @Test
    public void BSTCPO_3573_Test()
    {
    	Pattern pMenuSatusRunning = new Pattern("ds_menu_status_running.PNG").similar((float) 0.95);
    	try
        {
            Start_DSTest();

            if(!DSMenu.verify_DS_Icon_Paused())
            {
                logThis("DS was not paused, attempting to pause it.");
            	DSMenu.access_DSMenu();
                sikuliScreen.wait(1.0);
                DSMenu.menu_Suspend();
                sikuliScreen.wait(1.0);
            }
            
            DSMenu.access_DSMenu();
            sikuliScreen.wait(1.0);
            DSMenu.menu_Resume();
            //NOTE: Menu Status shows "Completed" until next sync, then turns to "Running"
            sikuliScreen.wait(30.0);

            if(DSMenu.verify_DS_Icon_Online() || DSMenu.verify_DS_Icon_Online_Synced() || DSMenu.verify_DS_Icon_Online_Syncing())
            {
            	logThis("DS icon is running as expected.");
            } else {
            	Assert.fail("DS icon is NOT running after resume.");
            }
            
            DSMenu.access_DSMenu();
            sikuliScreen.wait(1.0);
            if(sikuliScreen.exists(pMenuSatusRunning) != null)
            {
            	logThis("DS menu status is running as expected.");
            }else{
            	sikuliScreen.type(Key.ESC);  //close menu window
            	Assert.fail("DS menu status is NOT running.");
            }
            sikuliScreen.type(Key.ESC);  //close menu window
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    }

	// Added for BSTCPO 2635 3/11/15
    /*
     *  Verify DS - Limitation - sync only happens when user is under quota
     *  Note: This test will perform either under OR over quota test.  Not both.
     */
    @Test
    public void BSTCPO_2635_Test()
    {
    	logThis("==== BEGIN TEST - BSTCPO_2635");
    	try
        {
        	String testAutomationFolder = cpoMyDocFolder + "\\Automation_Files";
        	String testFile = "\\10MB_File.txt";
        	
    		//Pre-test cleaning
			logThis("Pre-test clean up, sync if needed before test begins.");
    		if(DSFolder.verifyFileExists(cpoMyDocFolder + "\\BSTCPO_2635.rtf"))
    		{
    			DSFolder.deleteFileFromMyDocs("\\BSTCPO_2635.rtf");
    			DSMenu.ds_Wait_Sync_Complete(45);
    		}
    		if(DSFolder.verifyFileExists(cpoMyDocFolder + testFile))
    		{
    			DSFolder.deleteFileFromMyDocs(testFile);
    			DSMenu.ds_Wait_Sync_Complete(45);
    		}
    		
            Double dblStorage[] = DSReuse.getStorageInfo(30);
            if(dblStorage[2] != null)  //Must check index for null, not just variable name.
            {
        		if(dblStorage[2] > 0)
            	{
            		logThis("==== UNDER QUOTA TEST.  " + dblStorage[2] + " GB remaining.");
            		DSFolder.createFile(cpoMyDocFolder, "BSTCPO_2635.rtf");
            		DSMenu.ds_Wait_Sync_Complete(45);
            		if(!DSFolder.verifyFileExists(cpoMyDocFolder + "\\BSTCPO_2635.rtf"))
            		{
            			Assert.fail("Under quota and test file was NOT synced to DM");
            		}
            	}
            	else
            	{
            		logThis("==== OVER QUOTA TEST.");
            		/* 
            		 * NOTES:
            		 * On DM, files in trash and history count towards quota.
            		 * Tested with user "rt3user04", which had 0.00 gigs free.  (rt3user03 has 0.01 free)
            		 * As of DS 2.0.1201, DS menu displays an error when quota is 100% used.
            		 * Empty files are synced, even if quota is maxed out.
            		*/

            		DSFolder.copyFile(testAutomationFolder + testFile, cpoMyDocFolder + testFile);
            		DSFolder.verifyFileExists(cpoMyDocFolder + testFile);
            		DSMenu.ds_Wait_Sync_Complete(300); 
            		
            		//This might change, but log file throws an error when over quota and sync to DM fails.
            		//File is put on a 5 minute "blacklist" and try again after that is up.
            		String strTime = DSReuse.getLogTimeForString("Failed to sync LastKnownState<local_folder=u'Cloud Portal Office', local_path=u'/My Docs/10MB_File.txt'", 30);
            		if(strTime != "")
            		{
            			logThis("Found log string at " + strTime.trim());
            		}
            		else
            		{
            			//Didn't find the string, assume file was uploaded.
            			Assert.fail("Over quota and test file synced to DM and remained in local file system.");
            		}
            	}
            	
        		//Post-Test cleanup
    			logThis("Post-test clean up, sync if needed before next test begins.");
        		if(DSFolder.verifyFileExists(cpoMyDocFolder + "\\BSTCPO_2635.rtf"))
        		{
        			DSFolder.deleteFileFromMyDocs("\\BSTCPO_2635.rtf");
        			DSMenu.ds_Wait_Sync_Complete(45);
        		}
        		if(DSFolder.verifyFileExists(cpoMyDocFolder + testFile))
        		{
        			DSFolder.deleteFileFromMyDocs(testFile);
        			DSMenu.ds_Wait_Sync_Complete(45);
        		}
            }
        	else
        	{
        		logThis("No storage info returned from log file");
        		Assert.fail("Unable to retrieve quota information to perform test.");
        	}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
    	logThis("==== TEST COMPLETE - BSTCPO_2635");
    }
    // 	Added for BSTCPO 2681 3/11/15
    /*
     * Verify Local file system behavior - from Win file explorer,
     * inside a synchronized folder, it is possible to create documents by pasting them to the folder.
     */
    @Test
    public void BSTCPO_2682_Test()
    {
    	logThis("==== BEGIN TEST - BSTCPO_2682");
        try
        {
        	String testFile = "BSTCPO_2682.txt";
        	String testFileCopy = "BSTCPO_2682 - Copy.txt";
        	//Pre-test cleaning
			logThis("Pre-test clean up, sync if needed before test begins.");
    		if(DSFolder.verifyFileExists(cpoMyDocFolder + "\\" + testFileCopy))
    		{
    			DSFolder.deleteFile(cpoMyDocFolder + "\\" + testFileCopy);
    			DSMenu.ds_Wait_Sync_Complete(45);
    		}
    		if(!DSFolder.verifyFileExists(cpoMyDocFolder + "\\" + testFile))
    		{
    			logThis("Create test file to copy.");
    			DSFolder.createFile(cpoMyDocFolder, testFile);
    			DSMenu.ds_Wait_Sync_Complete(45);
    		}
    		
    		sikuliScreen.wait(1.0);
            DSMenu.access_DSMenu();
            sikuliScreen.wait(1.0);
            DSMenu.menu_Open_Cloud_Portal_Office_Folder();
            sikuliScreen.wait(1.0);
            DSFolder.cpoFolder_OpenMyDocs();
            sikuliScreen.wait(1.0);
            sikuliScreen.click("2682_File.PNG");
            sikuliScreen.wait(1.0);
            sikuliScreen.type("c",KeyModifier.CTRL);
            sikuliScreen.wait(1.0);
            sikuliScreen.type("v",KeyModifier.CTRL);
            DSMenu.ds_Wait_Sync_Complete(45);
    		
    		//Post-Test cleanup
			logThis("Post-test clean up, sync if needed before next test begins.");
    		if(DSFolder.verifyFileExists(cpoMyDocFolder + "\\" + testFileCopy) || DSFolder.verifyFileExists(cpoMyDocFolder + "\\" + testFile))
    		{
    			DSFolder.deleteFile(cpoMyDocFolder + "\\" + testFileCopy);
    			DSFolder.deleteFile(cpoMyDocFolder + "\\" + testFile);
    			DSMenu.ds_Wait_Sync_Complete(45);
    		}
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        	Assert.fail();
        }
        logThis("==== TEST COMPLETE - BSTCPO_2682");
    }
    
	@Test
	public void testMe() {
		try {
			//DSReuse.killTaskByImage("firefox.exe", false);
			//DSReuse.killTaskByImage("iexplore.exe", false);
			logThis("Log Test=====" + DSReuse.getLogTimeForString("nxdrive.engine.workers HTTP Error 503", 70));
			Double dblStorage[] = DSReuse.getStorageInfo(40);
			logThis("Remaining Storage=" + dblStorage[2]);

			if (DSMenu.verifyDSReady()) {
				logThis("Yep!  DS is ready.");
			} else {
				logThis("Nope!  DS is not ready.");
			}
		} catch (Exception e) {
			logThis("whoops... "  + e.getMessage());
		}
	}

	@Test
	public void testThisPass() {
		logThis("testThisPass log info");
	}

	@Test
	public void testThisFail() {
		Assert.fail("This failed because of 1234.");
	}
	
}
