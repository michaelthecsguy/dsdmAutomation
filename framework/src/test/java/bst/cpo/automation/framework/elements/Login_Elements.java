package bst.cpo.automation.framework.elements;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import bst.cpo.automation.framework.Base;
import bst.cpo.automation.framework.ReusableCode;

public class Login_Elements extends Base
{
    ReusableCode reusableCode; 
    public WebDriver driver;

    public Login_Elements(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        reusableCode = new ReusableCode(driver);
    }
    
    public WebElement DM_Login_UserName() throws Exception
    {
    	//Dev environment
        //WebElement element = reusableCode.WaitUntilElementExists(By.xpath("//*[@id='id_username']"));
    	WebElement element = reusableCode.WaitUntilElementExists(By.xpath("//*[@id='dnn_ctr660_CustomLogin_View_txtUsername']"));
    	return element;
    }
    public WebElement DM_Login_Password() throws Exception
    {
        //Dev environment
    	//WebElement element = reusableCode.WaitUntilElementExists(By.xpath("//*[@id='id_password']"));
    	WebElement element = reusableCode.WaitUntilElementExists(By.xpath("//*[@id='dnn_ctr660_CustomLogin_View_txtPassword']"));
        return element; 
    }
    public WebElement DM_Login_Button() throws Exception
    {
        WebElement element = reusableCode.WaitUntilElementExists(By.xpath("//*[@id='dnn_ctr660_CustomLogin_View_cmdLogin']"));
        return element;
    }
}