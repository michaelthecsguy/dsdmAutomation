package bst.cpo.automation.framework;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;


public abstract class Base
{
    public WebDriver driver;
	public static Logger framelog = Logger.getLogger("Framework Logger");   
	
    // Test
    public static int timeoutValue_Implicit = 1;                 // in seconds
    public int timeoutValue_sleep = 4;

    public Base(WebDriver frameworkDriver)
    {
    	frameworkDriver = this.driver;
    }
    
	protected static void logThis(String logString) {
		framelog.log(Level.INFO, logString);		
	}
} 
