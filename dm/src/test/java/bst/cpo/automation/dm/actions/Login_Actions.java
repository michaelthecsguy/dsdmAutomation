package bst.cpo.automation.dm.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import bst.cpo.automation.dm.tests.BaseTest;

public class Login_Actions extends BaseTest
{
    WebDriver login_ActionDriver;

    Reusable_Actions reusableActions;

    public Login_Actions(WebDriver driver)
    {
        login_ActionDriver = driver;
        reusableActions = new Reusable_Actions(login_ActionDriver);
        //AngularModelAccessor ngModel = new AngularModelAccessor((JavascriptExecutor) login_ActionDriver);
    }

    /** 
     * Simple user actions to log into DM.  User and password strings passed in.
     * Can be used for regular and BA users.
     */
    public void BasicUser_Login(String user_Login, String user_Password) throws Exception
    {           
        logThis("Attempting to log in user: " + user_Login);

        WebElement user = DMDriver.findElement(By.id("dnn_ctr660_CustomLogin_View_txtUsername"));
        user.click();
        user.clear();
        user.sendKeys(user_Login);

        WebElement pwd = DMDriver.findElement(By.id("dnn_ctr660_CustomLogin_View_txtPassword"));
        pwd.click();
        pwd.clear();
        pwd.sendKeys(user_Password);

        WebElement login = DMDriver.findElement(By.id("dnn_ctr660_CustomLogin_View_cmdLogin"));
        login.click();
        Thread.sleep(1000);
    }
    
    public void User_Logout() throws InterruptedException
    {    	
        WebElement userLoggedIn = DMDriver.findElement(ng.binding("displayName"));
        logThis("Attempting to Sign Out user: " + userLoggedIn.getText());
    	WebElement logoutLink = DMDriver.findElement(By.linkText("Sign Out"));
        logoutLink.click();
    }

    /**
     * Determine is the login was a success or not.
     * If the login was a success, the element we use for validation is not shown.
     * Test if element exist before looking for LOGIN FAILED.
     */
    public Boolean Is_Bad_Login()
    {
    	//TODO - See if URL is a better way to determine login failure/success
    	// or use it in combination.
    	if(reusableActions.Exists_Element_By_Id("dnn_ctr660_CustomLogin_View_ctlMessageDialog", "Div MessageDialog"))
    	{
        	WebElement contentwrapper = DMDriver.findElement(By.id("dnn_ctr660_CustomLogin_View_ctlMessageDialog"));
        	if(contentwrapper.getText().contains("LOGIN FAILED"))
        	{
        		return true;
        	}
        	return false;  		
    	}
    	return false;
    }
    
    /** This is a quick and crude way to get past the Nuxeo screen in Dev environment*/
    public void Nuxeo_CPO_Bypass() throws InterruptedException
    {    	
    	logThis("Applying Nuxeo CPO bypass");
    	WebElement logoutLink = DMDriver.findElement(By.linkText("CPO"));
        logoutLink.click();            
    }
}