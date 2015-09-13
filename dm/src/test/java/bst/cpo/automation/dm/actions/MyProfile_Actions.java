package bst.cpo.automation.dm.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import bst.cpo.automation.dm.tests.BaseTest;

public class MyProfile_Actions extends BaseTest
{

    public MyProfile_Actions()
    {
    	//TODO - 
    	/*
    	 * Add logic to change language.
    	 * Verify language change.
    	 * Add logic to verify picture change.
    	 */
    }
    
    public String Get_Full_Name()
    {
    	//First name and Last name are in two separate spans.
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[1]/span[1]"));
    	return(element.getText().trim());
    }

    public String Get_First_Name()
    {
    	//First name and Last name are in two separate spans.
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[1]/span[1]"));
    	return(element.getText().trim());
    }

    public String Get_Last_Name()
    {
    	//First name and Last name are in two separate spans.
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[1]/span[1]"));
    	return(element.getText().trim());
    }
    
    public String Get_Signin_Name()
    {
    	//*[@id="app"]/div/div[4]/div/div/div[2]/div[1]/div[2]/div/div/div/span[2]
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[2]/span"));
    	return(element.getText().trim());
    }

    public void Set_Avatar(String strImagePath)
    {
    	//TODO - getting message - "File type is not valid, authorized extensions are: .jpg, .jpeg, .png, .gif, .bmp. .tif"
    	//I am sending a valid file to the input that works in normal import file (and can be selected via dialog)
    	//C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg
    	
    	//<input type="file" file-model="fileToImport.file" ng-model="fileToImport.filename" valid-file="" accept="image/*" ng-change="checkFileType()" id="fileUpload" class="ng-pristine ng-valid">
    	//<input type="file" file-model="fileToImport.file" ng-model="fileToImport.filename" valid-file="" accept="image/*" ng-change="checkFileType()" id="fileUpload" class="ng-valid ng-dirty">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='fileUpload']"));
    	element.sendKeys("strImagePath");
    }
    
    public void Set_Language(String strLanguage)
    {

    	/*
    	 * <select ng-model="currentLanguage" ng-options="lang.label for lang in supportedLanguages" class="ng-valid ng-dirty">
    	 * <option value="0" selected="selected" label="English">English</option>
    	 * <option value="1" label="French">French</option>
    	 * </select>
    	 */
    	logThis("Selecting language '" + strLanguage + "'");
    	Select dropdown = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/div/select")));
    	dropdown.selectByVisibleText(strLanguage);
    }

    public void Select_Language_Click()
    {
    	logThis("Clicking 'Select Language' button");
    	//<button type="submit" ng-click="changeLanguage(currentLanguage.name)" translate="" class="ng-scope">Select Language</button>
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/div/button"));
    	element.click();
    }
    
    public Boolean Is_Enabled_Save()
    {
    	
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[5]/input[1]"));
	    	if(element.isEnabled())
	    	{
	    		return true;
	    	}
	    	return false;
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    	return false;
    }
    
    public Boolean Is_Enabled_Cancel()
    {
    	try
    	{
        	Thread.sleep(500);  //Was getting false positives (TestNG was faster than UI changes)
        	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[5]/input[2]"));
	    	if(element.isEnabled())
	    	{
	    		return true;
	    	}
	    	return false;
        }
        catch(Exception e)
        {
            logThis("Exception during test occurred : " + e.getMessage());
        }
    	return false;
    }
    
    public void Save_Click()
    {
    	logThis("Clicking 'Save' button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[5]/input[1]"));
    	element.click();
    }
    
    public void Cancel_Click()
    {
    	logThis("Clicking 'Cancel' button");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div/div/div/form/div[5]/input[2]"));
    	element.click();
    }
}
