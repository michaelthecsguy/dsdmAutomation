package bst.cpo.automation.dm.actions;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Alert;

import bst.cpo.automation.dm.tests.BaseTest;


public class SendLink_Actions extends BaseTest
{
    
    public SendLink_Actions(WebDriver driver)
    {
    	//TODO - 
    	/*
    	 * Is_Displayed_Send_File_Link_Panel() called from Reusable_Actions
    	 * 
    	 * Tests will need to use multiple emails and/or specific files to test against.
    	 * Very difficult to distinguish which Revoke record to work with.
    	 * 
    	 * Validation of email addresses - accepted by UI or not.
    	 */
    }

    public void Close_Send_Link_Panel()
    {
    	logThis("Clicking 'X' to close Send Link window.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[1]/i"));
    	element.click();
    }
    
    /**
     * Getter for the header of the Send Link panel.
     * TODO - parse out
     * @return - Header info containing the file(s) selected.
     */
    public String Get_Header_Info()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[1]/h3"));
    	return element.getText();
    }
    
    /**
     * Set the value of the "Link(s) will expire in ..." drop down
     * @param strExpire - Values "4 hours", '24 hours", "7 days"
     */
    public void Set_Expire(String strExpire)
    {
    	logThis("Set: Link(s) will expire in = '" +  strExpire + "'");
		Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[3]/div[2]/form/select")));
		//*[@id="app"]/div/div[4]/div[2]/div/span/div[3]/div[2]/form/select
	 	if(droplist != null)
	 	{
			if(strExpire == "4 hours") droplist.selectByVisibleText("4 hours");
		 	if(strExpire == "24 hours") droplist.selectByVisibleText("24 hours");
		 	if(strExpire == "7 days") droplist.selectByVisibleText("7 days");	 		
	 	}
    }

    /**
     * Add an email address.  Expecting a well-formed email address.  Emails are validated one at a time, so make seperate calls for each email.
     * @param strEmail
     */
    public void Set_Email(String strEmail)
    {
    	//<input class="input ng-valid ng-dirty" ng-model="newTag.text" ng-change="newTagChange()" ng-trim="false" ng-class="{'invalid-tag': newTag.invalid}" ti-bind-attrs="{type: options.type, placeholder: options.placeholder, tabindex: options.tabindex}" ti-autosize="" type="email" placeholder="Enter Email Ids" style="width: 95px;">
    	//*[@id="app"]/div/div[4]/div[2]/div/span/div[4]/div[2]/form/tags-input/div/div/input
    	logThis("Set: Adding email address '" + strEmail + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/tags-input/div/div/input"));
    	element.sendKeys(strEmail);
    	element.sendKeys(Keys.ENTER);
    }
    
    //Remove_Email_From_List(String strEmail)
    	/*
    	 * entered emails creates repeater - tag in tagList
    	 * <li class="tag-item ng-scope" ng-repeat="tag in tagList.items track by track(tag)" ng-class="{ selected: tag == tagList.selected }">
    	 * 		<span ng-bind="getDisplayText(tag)" class="ng-binding">rholm@saturnsys.com</span> 
    	 * 		<a class="remove-button ng-binding" ng-click="tagList.remove($index)" ng-bind="options.removeTagSymbol">×</a>
    	 * </li>
    	 */

    /**
     * Get the number of valid email addresses entered in the "Enter Email Ids" control.
     */
    public String Get_Email_Count()
    {
    	//TODO - Strip integer from string.
		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/div[1]/label"));
		if(element != null)
		{
			return element.getText();  			
		}
		else
		{
			logThis("WARNING: Unable to find emails used / count info.");
			return "";			
		}    	
    }
    
    /**
     * Optional field.  Enter the message you wish to send with the share of the file(s).
     * @param strMessage - maxlength 256
     */
    public void Set_Message(String strMessage)
    {
    	//<textarea maxlength="256" placeholder="Message (Optional)" ng-model="share.message" class="ng-valid ng-dirty"></textarea>
    	
    	logThis("Set: Message = '" + strMessage + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/div[2]/textarea"));
    	element.sendKeys(strMessage);	
    }
    
    /**
     * Determine if the Send Link button is enabled or not.
     * @return true = Send Link button is enabled.  false = Send Link button not enambled.
     */
    public Boolean Is_Send_Link_Enabled()
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/div[2]/input"));
    	if(element.isEnabled())
    	{
        	return true;
    	}
    	return false;
    }
    
    /**
     * Click on the 'Send Link' button.  Assumption is that all required information is set and button is enabled.
     */
    public void Send_Link_Submit()
    {
    	if(Is_Send_Link_Enabled())
    	{
    		logThis("Submit: 'Send Link'");
    		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/div[2]/input"));
    		element.click();
    	}
    	else
    	{
    		logThis("WARNING: Send_Link_Submit called, but input is disabled.");
    	}
    }
    
    
    /**
     * Preview of email should contain information set by user.
     * Contents of 'Link(s) will expire in' drop down should be listed in the return string.
     * @return - String 'File(s) will be available for the next <selected expiration>'.
     */
    public String Get_Preview_Expire_Info()
    {
    	//<div style="font-size:12px; font-weight:bold; color:#a91532; padding-top:10px; text-align:center;" align="center" ng-hide="expirations.hours > 24" class="ng-binding">File(s) will be available for the next 4 Hours </div>
		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/div[2]/div/div/div[1]/table/tbody/tr[2]/td"));
		if(element != null)
		{
			return element.getText(); 
		}
		else
		{
			logThis("WARNING: Unable to find expire info in preview.");
			return "";
		}
		   	
    }
    
    /**
     * TODO - Function not complete.
     * Documents are listed in repeater 'doc in sendlinkDocsList'
     * @return
     */
    public String Get_Preview_Files()
    {
    	//repeater 'doc in sendlinkDocsList'
    	return "TODO - return list in repeater 'doc in sendlinkDocsList'";
    }
    
    /**
     * Get the message portion of the email preview.
     * This should match the 'Message' the user set.
     * @return String
     */
	public String Get_Preview_Message()
    {
    	//<div style="font-size:12px; color:#4f4f4f; padding:10px 15px 10px 15px; word-wrap:break-word; word-break:break-all;white-space:pre-wrap;" class="ng-binding">hello world!</div>
		WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div[2]/div/span/div[4]/div[2]/form/div[2]/div/div/div[3]"));
		if(element != null)
		{
			return element.getText();  			
		}
		else
		{
			logThis("WARNING: Unable to find message info in preview.");
			return "";			
		}
    }
    
	public WebElement Get_Revoke_Row(String strEmail)
    {
		//TODO -  Careful using Get_Revoke_Row.  Hard to distinguish which row to return if the same email is used in each record.
		//TODO - Need to handle empty recordset better, it errors out when nothing listed.
		List<WebElement> weRow = DMDriver.findElements(ng.repeater("share in shares"));
		logThis("weRow.size=" + weRow.size());
    	if(weRow.size() > 0)
    	{
    		for(int i = 0; i < weRow.size(); i++)
        	{
        		if(weRow.get(i).getText().contains(strEmail))
        		{
            			return weRow.get(i);   				
        		}
        	}
    		logThis("Did not find '" + strEmail + "' in the list of active send file records.");
    	}
    	else
    	{
    		logThis("No elements found for repeater 'share in shares'");
    		return null;
    	}
    	return null;
    }

    public String Get_Revoke_Sent_To(String strEmail)
    {
    	WebElement elementRow = Get_Revoke_Row(strEmail);
    	if (elementRow != null)
    	{
    		List<WebElement> elementTD = elementRow.findElements(By.tagName("td"));
    		return elementTD.get(0).getText();
    	}
    	else
    	{
    		logThis("WARNING: Get_Revoke_Sent_To called for '" + strEmail + "', but table row not found.  May not exist, or has expired.");
    	}    	
    	return "";
    }

    
    public String Get_Revoke_Expiration(String strEmail)
    {
    	WebElement elementRow = Get_Revoke_Row(strEmail);
    	if (elementRow != null)
    	{
    		List<WebElement> elementTD = elementRow.findElements(By.tagName("td"));
    		return elementTD.get(1).getText();
    	}
    	else
    	{
    		logThis("WARNING: Get_Revoke_Expiration called for '" + strEmail + "', but table row not found.  May not exist, or has expired.");
    	}    	
    	return "";
    }
    
    public String Get_Revoke_Version(String strEmail)
    {
    	WebElement elementRow = Get_Revoke_Row(strEmail);
    	if (elementRow != null)
    	{
    		List<WebElement> elementTD = elementRow.findElements(By.tagName("td"));
    		return elementTD.get(2).getText();
    	}
    	else
    	{
    		logThis("WARNING: Get_Revoke_Version called for '" + strEmail + "', but table row not found.  May not exist, or has expired.");
    	}    	
    	return "";
    }
    
    public void Revoke_Send_Link(String strEmail)
    {
    	WebElement elementRow = Get_Revoke_Row(strEmail);
    	if (elementRow != null)
    	{
    		logThis("Revoking send link...");
    		List<WebElement> elementTD = elementRow.findElements(By.tagName("td"));
    		elementTD.get(3).click();
    		
            logThis("Accepting (OK) modal confirmation alert.");
			Alert alert = DMDriver.switchTo().alert();
            alert.accept();
            
            //TODO add confirmation validation
    		//Pop-up = "Document link revoked"
    	}
    	else
    	{
    		logThis("WARNING: Get_Revoke_Version called for '" + strEmail + "', but table row not found.  May not exist, or has expired.");
    	}       	

    }

}