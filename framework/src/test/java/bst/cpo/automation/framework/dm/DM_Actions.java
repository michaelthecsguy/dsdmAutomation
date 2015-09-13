package bst.cpo.automation.framework.dm;


import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.paulhammant.ngwebdriver.ByAngular;

import bst.cpo.automation.framework.Base;
import bst.cpo.automation.framework.ReusableCode;
import bst.cpo.automation.framework.elements.Login_Elements;

// This class can be used for DM interactions
public class DM_Actions extends Base
{
    ReusableCode resuable_Code;
    Login_Elements loginElements;
    public static ByAngular ng;
	  	
    public DM_Actions(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
        loginElements = new Login_Elements(driver);
        resuable_Code = new ReusableCode(driver); 
    }
    
    public void DM_User_Login(String user_Login, String user_Password) throws Exception
    {           
    	logThis("DM User Name " + user_Login);
    	logThis("DM Login: " + driver.getCurrentUrl());

    	try{
    		WebElement login;
    		login = loginElements.DM_Login_UserName();
    		login.click();
    		login.sendKeys(user_Login);
    		Thread.sleep(500); 
    		login = loginElements.DM_Login_Password();
    		login.click();
    		login.sendKeys(user_Password);
    		Thread.sleep(500);
    		login = loginElements.DM_Login_Button();
    		login.click();
    		Thread.sleep(10000);       

    		ng = new ByAngular((JavascriptExecutor) driver);
    		waitForAngularRequestsToFinish((JavascriptExecutor)driver);
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during framework action occurred : " + e.getMessage() + " " + e.toString());
    	}      
    }
        
    public void DM_User_Logout()
    {    	
    	try{
    		WebElement userLoggedIn = driver.findElement(ng.binding("displayName"));
    		logThis("Logging out from user " + userLoggedIn.getText());
    		WebElement logoutLink = driver.findElement(By.linkText("Sign Out"));
    		logoutLink.click();    
    	}
    	catch(Exception e)
    	{
    		logThis("Exception during framework action occurred : " + e.getMessage() + " " + e.toString());
    	}    
    }
}