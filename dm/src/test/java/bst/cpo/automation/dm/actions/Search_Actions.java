package bst.cpo.automation.dm.actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import bst.cpo.automation.dm.tests.BaseTest;


public class Search_Actions extends BaseTest
{

    public Search_Actions()
    {
        /**
         * Navigation to Advanced search from Home_Actions.java Nav_Advanced_Search_Click();
         * Advanced search uses an angular model, we should be able to use the ngWebDriver 
         * to make things easier but currently not using it.
         * 
         * TODO 
         *  - Add Index Field based on index type
         *  - Validate good user setting Creator
         *  - Add method to validate user (file creator) results from dropdown after 3+ chars entered.
         */
    }

    public void Simple_Search(String strSearch)
    {
    	//<input type="text" ng-model="search.searchPattern" placeholder="search folder or file" class="search-input ng-pristine ng-valid" ng-keypress="($event.which === 13)?search(search.searchPattern):0" maxlength="255">
    	//*[@id="app"]/div/div[1]/div[2]/input
    	logThis("Submit: Simple Search for '" + strSearch + "'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[1]/div[2]/input"));
    	element.clear();
    	element.sendKeys(strSearch);
    	element.sendKeys(Keys.ENTER);
    }
    
    public void Go_Click() throws InterruptedException
    {
    	//<input type="submit" class="pure-button pure-button-primary" value="Go" ng-disabled="facetedSearchForm.$invalid">
    	logThis("Submit: Clicking 'Go'");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[2]/input"));
    	element.click();
    }
    
    public void Clear_Click() throws InterruptedException
    {
    	//<button class="pure-button ng-scope" ng-click="clearAdvancedSearch()" translate="">Clear</button>
    	logThis("Clearing search fields.");
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[2]/button"));
    	element.click();
    }
    
    public void Set_Full_Text(String strText) throws InterruptedException
    {
    	//<input type="text" ng-model="advancedSearch.fullText" maxlength="255" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[1]/label[1]/input"));
    	logThis("Set: Full text = '" + strText + "'");
    	element.clear();
    	element.sendKeys(strText);
    }
    
    public String Get_Full_Text() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[1]/label[1]/input"));
    	return element.getAttribute("value");
    }

    public void Set_Title(String strTitle) throws InterruptedException
    {
    	//<input type="text" ng-model="advancedSearch.title" maxlength="255" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[1]/label[2]/input"));
    	logThis("Set: Title = '" + strTitle + "'");
    	element.clear();
    	element.sendKeys(strTitle);
    }

    public String Get_Title() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[1]/label[2]/input"));
    	return element.getAttribute("value");
    }
    
    public void Set_Description(String strDesc) throws InterruptedException
    {
    	//<input type="text" ng-model="advancedSearch.description" maxlength="512" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[1]/label[3]/input"));
    	logThis("Set: Description = '" + strDesc + "'");
    	element.clear();
    	element.sendKeys(strDesc);
    }

    public String Get_Description() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[1]/label[3]/input"));
    	return element.getAttribute("value");
    }
    
    public void Set_Created_Start_Date(String strStartDate) throws InterruptedException
    {
    	//<input type="text" bs-datepicker="" ng-model="advancedSearch.creationStartDate" maxlength="12" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[2]/label[1]/span[2]/input"));
    	logThis("Set: Created Start Date = '" + strStartDate + "'");
    	element.clear();
    	element.sendKeys(strStartDate);
    }

    public String Get_Created_Start_Date() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[2]/label[1]/span[2]/input"));
    	return element.getAttribute("value");
    }
    
    public void Set_Created_End_Date(String strEndDate) throws InterruptedException
    {
    	//<input type="text" bs-datepicker="" ng-model="advancedSearch.creationEndDate" maxlength="12" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[2]/label[2]/span[2]/input"));
    	logThis("Set: Created End Date = '" + strEndDate + "'");
    	element.clear();
    	element.sendKeys(strEndDate);
    }

    public String Get_Created_End_Date() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[2]/label[2]/span[2]/input"));
    	return element.getAttribute("value");
    }
    
    public void Set_Modified_Start_Date(String strStartDate) throws InterruptedException
    {
    	//<input type="text" bs-datepicker="" ng-model="advancedSearch.modificationStartDate" maxlength="12" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[4]/label[1]/span[2]/input"));
    	logThis("Set: Modified Start Date = '" + strStartDate + "'");
    	element.clear();
    	element.sendKeys(strStartDate);
    }

    public String Get_Modified_Start_Date() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[4]/label[1]/span[2]/input"));
    	return element.getAttribute("value");
    }
    
    public void Set_Modified_End_Date(String strEndDate) throws InterruptedException
    {
    	//<input type="text" bs-datepicker="" ng-model="advancedSearch.modificationEndDate" maxlength="12" class="ng-pristine ng-valid">
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[4]/label[2]/span[2]/input"));
    	logThis("Set: Modified End Date = '" + strEndDate + "'");
    	element.clear();
    	element.sendKeys(strEndDate);
    }

    public String Get_Modified_End_Date() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[2]/div[4]/label[2]/span[2]/input"));
    	return element.getAttribute("value");
    }
    
    public void Set_File_Creator(String strCreator) throws InterruptedException
    {
    	/*
    	 * NOTES: 
    	 * This control is not fun to work with.
    	 * Method forces input of strCreator rather than selecting from list (after 3 characters entered).
    	 * Problem when forced string doesn't find a match (false positive setting control).
    	 * 
    	 * TODO Fix clearing this field out if changing file creator.  Throws errors.  "Element must be user-editable in order to clear it"
    	 */
    	WebElement element = DMDriver.findElement(By.id("s2id_select-share-user"));
    	logThis("Set: File Creator = '" + strCreator + "'");
    	//element.clear();
    	element.click();
    	Thread.sleep(1000);
    	//element.clear();  
    	element.sendKeys(strCreator);
    	Thread.sleep(1000);
    	element.sendKeys(Keys.ENTER);
    }

    public String Get_File_Creator() throws InterruptedException
    {
    	WebElement element = DMDriver.findElement(By.id("s2id_select-share-user"));
    	return element.getText().trim();
    }
    
    public void Set_Include_Deleted(Boolean blnIncludeDeleted) throws InterruptedException
    {
    	/**<select ng-model="advancedSearch.deleted" class="ng-pristine ng-valid">
    	 * <option value="deleted" translate="" class="ng-scope">Yes</option>
    	 * <option value="" translate="" class="ng-scope">No</option>
    	 * </select>
    	 */
    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[4]/select")));
     	if(blnIncludeDeleted){
    		logThis("Set: Include delete documents to 'Yes'");
    		droplist.selectByVisibleText("Yes");
    	} else {
    		logThis("Set: Include delete documents to 'No'");
    		droplist.selectByVisibleText("No");
    	}
    }
    
    public String Get_Include_Deleted() throws InterruptedException
    {
    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[4]/select")));
    	return droplist.getFirstSelectedOption().getText();
    }

    public void Set_Index_Field_Search(String strIndexField) throws InterruptedException
    {
    	/**
    	 * <select ng-options="d.name for d in docTypes" ng-model="advancedSearch.docType" ng-change="initSearchDocTypeFields(advancedSearch.docType)" class="ng-pristine ng-valid">
    	 */
    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[5]/table/tbody/tr[2]/td/label/select")));
   		droplist.selectByVisibleText(strIndexField);
    }

    public String Get_Index_Field_Search() throws InterruptedException
    {
    	Select droplist = new Select(DMDriver.findElement(By.xpath("//*[@id='app']/div/div[3]/div/div/form/div[1]/div[5]/table/tbody/tr[2]/td/label/select")));
    	return droplist.getFirstSelectedOption().getText();
    }
    
    public Boolean Is_Empty_Search_Result() throws InterruptedException
    {
    	//Using getText for the content wrapper, only returns visible text.  Div is always there, ng-hide if results returned.
    	//TODO - WARNING - if search hasn't been submitted yet, this will give a false negative.  FIX THIS.
    	WebElement element = DMDriver.findElement(By.className("content-wrapper"));
    	if(element.getText().contains("No documents match your search criteria")){
    		return true;
    	}else{
    		return false;
    	}
    }    
   
    public String Get_Search_Result() throws InterruptedException
    {
    	//Using getText for the content wrapper, only returns visible text.
    	//This is now obsolete.  Consider using Reusable_Action.Get_FileFolder_Row("file in files", "<file/folder name>")
    	WebElement element = DMDriver.findElement(By.className("file-list-table"));
    	logThis("content-wrapper text = '" + element.getText() + "'");
   		return element.getText();
    }
    

    
}
