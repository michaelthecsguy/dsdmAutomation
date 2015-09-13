package bst.cpo.automation.ds.actions;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.*;
import org.testng.Assert;
import org.testng.Reporter;

import bst.cpo.automation.ds.tests.BaseTest;
import bst.cpo.automation.framework.ReusableCode;

public class Reusable_Actions extends BaseTest
{

    WebDriver resuable_Driver;
    public ReusableCode resuable_Code;
	Screen sikuliScreen;
	  	
    public Reusable_Actions(WebDriver driver)
    {
        resuable_Driver = driver;
        resuable_Code = new ReusableCode(resuable_Driver);
        sikuliScreen = new Screen();
    }
    
    public void ExpectedFail_Test()
    {            
        try {
			sikuliScreen.click("windows8-Startbutton.png");
		} catch (FindFailed e) {
	        Reporter.log("***** Expected Fail called ");
			e.printStackTrace();
			Assert.fail();
		}
    }

    // We check if the splash pages are shown and click through if needed
    // TODO: I SHOULD DO AN ELEMENT COUNT FIRST INSTEAD OF TRY/CATCH
    public void Check_CloudPortal_Splash()
    {
        try
        {
            WebElement CPO_Splash = null;
            CPO_Splash = resuable_Code.IfElementisVisible(By.xpath("//div[@class='featureTitle cloudPortalIcon']"));
            logThis(CPO_Splash.getText());
            if (CPO_Splash != null)
            {
                CPO_Splash.click();

                CPO_Splash = resuable_Code.WaitUntilElementExists(By.xpath("//a[contains(@href, 'CloudPortalHome')]"));
                CPO_Splash.click();

                CPO_Splash = resuable_Code.WaitUntilElementExists(By.xpath("//a[contains(@href, 'CloudMenu_View')]"));
                CPO_Splash.click();
            }
        }
        catch(Exception e)
        {
            logThis("CPO Splash not encountered or error occurred");
        }
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
            logThis("TestNG config file could not be opened");
            System.exit(1);
        }
        return propertyValue;
	}

// Added For Russ 3/24/15
	public void startDS()
	{
		//Might want to pass in string OS.
		try
		{
			Runtime.getRuntime().exec(cpoExePath);
		}
		catch(Exception e)
		{
			logThis(e.getMessage());
		}
	}
	// Added For Russ 3/24/15
	/*
	 * This function determines if a task/process is running or not.
	 * Pass in the name of the task, IE clouddrivew.exe
	 */
	public Boolean checkTaskRunning(String strTask)
	{
		try
		{
			logThis("Checking to see if " + strTask + " is running.");
			String line; Process p = Runtime.getRuntime().exec("tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			while ((line = input.readLine()) != null) 
			{
				//System.out.println(line); //-- Parse data here. }
				if(line.indexOf(strTask) >= 0)
				{
					logThis("Found Task Info: " + line);
					return true;
				}
			}
		}
		catch(Exception e)
		{
			logThis(e.getMessage());
		}
		return false;
	}
// Added For Russ 3/24/15
	/*
	 * This method returns the last 'n' lines of the CPO log.
	 * Builds array (each index is a line).
	 * Start at the end of the log file and reads backwards, character by character.
	 * When a new line is found, flip the string around, and put it in the array.
	 * Generally, the last line of the log is empty, so ask for at least 2 lines.
	 */
	public String[] getLastLogLines(Integer lineCount) throws IOException{
		String logLines[] = new String[lineCount];
		File file = new File(cpoLogFile);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        int lines = 0;
        StringBuilder builder = new StringBuilder();
        long length = file.length();
        length--;
        raf.seek(length);
        for(long seek = length; seek >= 0; --seek)
        {
        	raf.seek(seek);
            char c = (char)raf.read();
            builder.append(c);            	
            if(c == '\n')  //newline
            {
                builder = builder.reverse();
                logLines[lines] = builder.toString();
                lines++;
                builder = null;
                builder = new StringBuilder();
                if (lines == lineCount)
                {
                    break;
                }
            }
        }
        raf.close();
        return logLines;
    }

	// 	Added for BSTCPO 2635 3/11/15
	/*
	 * Returns the last storage information provided in the CPO log files.
	 * [0] = Used Space
	 * [1] = Versions
	 * [2] = Trash
	 * [3] = Total
	 * [4] = Remaining - a derived value - TODO need to verify this.
	 * Note: Might need to add logic if parsed strings are not numbers....
	 * DS1 Log entries look like this: "clouddesktop.controller.storage_controller used storage=1.53, total storage=10.0"
	 * DS2 log entries = "clouddesktop.engine.watcher.quota_watcher Actual storage usage (used/versions/trash/total) = (0.0, 0.0, 0.0, 10.0)"
	 * Returns null if nothing found, so trap for it.
	 */	
	public Double[] getStorageInfo(Integer intLines) throws IOException{
		String strInfo = "";
		String strFind = "(used/versions/trash/total) = (";
    	String strLines[] = getLastLogLines(intLines);
    	Double[] dblInfo = new Double[5];
    	
        for(int i =0; i< strLines.length; i++)
		{
        	if(strLines[i].indexOf(strFind) > 0)
        	{
        		strInfo = strLines[i].substring(strLines[i].indexOf(strFind), strLines[i].length());
        		logThis("Found quota info in log: " + strInfo);

        		strInfo = strInfo.substring(31, strInfo.length());
            	String strUsed = strInfo.substring(0, strInfo.indexOf(","));
            	
            	strInfo = strInfo.substring(strInfo.indexOf(",") + 1, strInfo.length());
            	String strVersion = strInfo.substring(0, strInfo.indexOf(","));

            	strInfo = strInfo.substring(strInfo.indexOf(",") + 1, strInfo.length());
            	String strTrash = strInfo.substring(0, strInfo.indexOf(","));
            	
            	strInfo = strInfo.substring(strInfo.indexOf(",") + 1, strInfo.length());
            	String strTotal = strInfo.substring(0, strInfo.length() - 2);

            	Double dblUsed = Double.parseDouble(strUsed);
            	Double dblVersion = Double.parseDouble(strVersion);
            	Double dblTrash = Double.parseDouble(strTrash);
            	Double dblTotal = Double.parseDouble(strTotal);
            	Double dblRemaining = dblTotal - dblUsed;

            	dblInfo[0] = dblUsed;
            	dblInfo[1] = dblVersion;
            	dblInfo[2] = dblTrash;
            	dblInfo[3] = dblTotal;
            	dblInfo[4] = dblRemaining;

            	return dblInfo;
        	}
		}
        if(strInfo == "")
        {
        	logThis("Unable to find storage info in last " + intLines + " lines of CPO log file");
        }
        return dblInfo;
	}
	
	// Added For Russ 3/24/15
	/*
	 * Returns the date/time of the first log entry the search string finds (reading last line first).  
	 * Example output: "yyyy-mm-dd hh:mm:ss,xxx"
	 * Disclaimer: USE AT OWN RISK!  Not every log entry starts with date/time.
	 */	
	public String getLogTimeForString(String strFind, Integer intLines) throws IOException{
		String strLogTime = "";
    	String strLines[] = getLastLogLines(intLines);
        logThis("Attempting to find " + strFind + " in the logs.");
    	for(int i =0; i< strLines.length; i++)
		{
        	if(strLines[i].indexOf(strFind) >= 0)
        	{
        		strLogTime = strLines[i].substring(0, 24);
        		strLogTime = strLogTime.trim();
        		return strLogTime;  //This acts as a break, returns string.
        	}
		}
        if(strLogTime == "")
        {
        	logThis("Search string '" + strFind + "' NOT FOUND in last " + intLines + " lines of CPO log file");
        }
		return strLogTime;
	}
}