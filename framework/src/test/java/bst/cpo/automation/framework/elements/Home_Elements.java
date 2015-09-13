package bst.cpo.automation.framework.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import bst.cpo.automation.framework.Base;
import bst.cpo.automation.framework.ReusableCode;


public class Home_Elements extends Base
{
    // Elements related to home goes here
    ReusableCode helper;

    public Home_Elements(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
        helper = new ReusableCode(driver);
    }

    // Elements specific to the Welcome Zone
    public WebElement DM_Welcome_Text()
    {
        WebElement element = null;
        try {
			element = helper.WaitUntilElementExists(By.className("subNav)"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return element;
    }
    
    // Elements specific to the My Recently Updated Docs Zone
    public WebElement DM_MyRecentDocs_Collapse()
    {
        WebElement element = null;
        try {
			element = helper.WaitUntilElementExists(By.xpath("//div[@ng-controller='RecentlyUpdatedDocsCtrl']/div"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }

    // Elements specific to the My Recently Updated Folders Zone
    public WebElement DM_MyRecentFolders_Collapse()
    {
        WebElement element = null;
        try {
			element = helper.WaitUntilElementExists(By.xpath("//div[@ng-controller='RecentlyUpdatedFoldersCtrl']/div"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }

    // Elements specific to the Other Recently Updated Docs Zone
    public WebElement DM_OtherRecentDocs_Collapse()
    {
        WebElement element = null;
        try {
			element = helper.WaitUntilElementExists(By.xpath("//div[@ng-controller='RecentlyUpdatedOthersDocsCtrl']/div"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }

    public WebElement DM_FolderDocs_NextPage(String targetController)
    {
        WebElement element = null;
        try {
			element = helper.WaitUntilElementExists(By.xpath("//div[@ng-controller='"+targetController+"']//a[@ng-click='selectPage(page + 1)']"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
    
    public WebElement DM_FolderDocs_PrevPage(String targetController)
    {
        WebElement element = null;
        try {
			element = helper.WaitUntilElementExists(By.xpath("//div[@ng-controller='"+targetController+"']//a[@ng-click='selectPage(page - 1)']"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return element;
    }
}