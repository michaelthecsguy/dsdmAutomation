package bst.cpo.automation.framework.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.paulhammant.ngwebdriver.*;

import bst.cpo.automation.framework.Base;
import bst.cpo.automation.framework.ReusableCode;

public class Navigation_Elements extends Base
{
    ReusableCode helper; // Used to find elements
    public Navigation_Elements(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
        helper = new ReusableCode(driver);
    }

    public WebElement DM_Home_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//*[@id='clouddesk_home']/a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_MyDocs_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//*[@id='clouddesk_my_documents']/a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_OthersDocs_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//*[@id='clouddesk_shared_with_me']/a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_Settings_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//*[@id='clouddesk_setting']/a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public String DM_Logout_Link()
    {
    	String logout =  "className(\"addressContent\")).getText(), containsString(\"Chicago, IL\")";
    	
		return logout;
    	
    }
    public WebElement DM_Search_Field()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//*[@id='userServicesSearchForm:faceted_search_suggest_box']"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_AdvancedSearch_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//*[@id='userServicesSearchForm:searchActionsTable:0:searchActionsButton']"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_Help_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//a[contains (@href, 'help/user/en/index.htm')]"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_TermsCondintions_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//a[contains (@href, 'cloudportaloffice_terms.htm')]"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    public WebElement DM_PrivacyPolicy_Link()
    {
        WebElement element = null;
		try {
			element = helper.WaitUntilElementExists(By.xpath("//a[contains (@href, 'cloudportaloffice_policy.htm')]"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
}
