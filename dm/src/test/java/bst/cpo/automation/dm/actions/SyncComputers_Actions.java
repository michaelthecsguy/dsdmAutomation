package bst.cpo.automation.dm.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import bst.cpo.automation.dm.tests.BaseTest;


public class SyncComputers_Actions extends BaseTest
{
    
    public SyncComputers_Actions()
    {
    	//TODO - 
    	/*
    	 * Work with download dialog, add validation of Mac/Windows download.
    	 * --Dialog boxes overlay browser, but are not modal.  User can still navigate in browser while window up.
    	 * --Check if the solution to Alert OK/Cancel (coded elsewhere) will work here.
    	 * 
    	 * Synced computers are listed in a repeater "computer in computers".
    	 * Create something similar to Reusable_Action.Get_FileFolder_Row
    	 */

    }

    public void Mac_Click()
    {
    	logThis("Clicking Mac button for DS Download");
    	//<a class="pure-button pure-button-primary ng-scope" href="http://downloads.sharpb2bcloud.com.s3.amazonaws.com/update/qa/clouddesktop.dmg" translate="">Mac</a>
    	WebElement element = DMDriver.findElement(By.linkText("Mac"));
    	element.click();
    }
    
    public void Windows_Click()
    {
    	logThis("Clicking Windows button for DS Download");
    	//<a class="pure-button pure-button-primary ng-scope" href="http://downloads.sharpb2bcloud.com.s3.amazonaws.com/update/qa/Cloud%20Portal%20Office%20Desktop.msi" translate="">Windows</a>
    	WebElement element = DMDriver.findElement(By.linkText("Windows"));
    	element.click();
    }
    
    public void Unlink_Click()
    {
    	//<button ng-click="unlinkComputer(computer.properties['authtoken:token'])" class="pure-button ng-scope" translate="">unlink</button>
    }
    
    /** 
     * Save the description of a synced computer.
     * This only works if there is a sync computer listed (and only the first one if there are more than one).
     * See TODO info above for repeater and possible solution.
     */
    public void Save_Description_Click()
    {
    	//<button ng-click="saveDescription(computer.properties['authtoken:token'], computer.description)" ng-disabled="computerForm.$invalid" class="pure-button ng-scope" translate="">Save Description</button>
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/form/table/tbody/tr/td[4]/button[1]"));
    	element.click();
    }
    
    /** S*/
    public void Set_Description(String strDescription)
    {
    	//<input type="text" name="description" class="pure-input-1 ng-pristine ng-valid ng-valid-required" ng-model="computer.description" maxlength="255" required="">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/form/table/tbody/tr/td[3]/input"));
    	element.clear();
    	element.sendKeys(strDescription);
    }
}
