package bst.cpo.automation.dm.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import bst.cpo.automation.dm.tests.BaseTest;

public class MyDocsTrash_Actions extends BaseTest
{
    
    public MyDocsTrash_Actions()
    {
    	//TODO - 
    	/*
    	 * Lots - This is currently a playground for re-usable code on results returned.
    	 * 
    	 * Copy/Paste control methods/functions are in Reusable_Actions.
    	 * 
    	 */

    }

    public void Select_All()
    {
    	//<input type="checkbox" ng-change="selectCurrentPage(pp.selectedAll)" ng-model="pp.selectedAll" class="ng-pristine ng-valid">
    	
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[1]/table/thead/tr/th[1]/label/input"));
    	element.click();
    }
    
    public String Get_Trash_Text()
    {
    	WebElement element = DMDriver.findElement(By.className("trash-settings-content-wrapper"));
    	return element.getText();
    }
    
    public Boolean Check_If_Trash_Contains_String(String strSearch)
    {
    	//This is very basic parsing and probably shouldn't be used.
    	//The use of the repeater below will bring back the index of the array it was found in.
    	logThis("Searching for '" + strSearch + "' in My log results");
    	if(Get_Trash_Text().contains(strSearch))
    	{
    		logThis("Found '" + strSearch + "' in results");
    		return true;
    	}
    	logThis("Did not find '" + strSearch + "' in results");
    	return false;
    }
    

   
    public String Get_Files_Selected()
    {
    	//both of these work.
    	WebElement element = DMDriver.findElement(By.xpath("//*[@id='app']/div/div[4]/div/div/div[2]/div[1]/div[2]/span"));
    	//WebElement element = DMDriver.findElement(By.className("txt"));
    	return element.getText().trim();
    	//return "WARNING: not yet functional...";
    }
    
 
    



    
}