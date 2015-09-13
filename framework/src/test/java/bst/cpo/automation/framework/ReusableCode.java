package bst.cpo.automation.framework; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import bst.cpo.automation.framework.Base;

@SuppressWarnings("unused")
public class ReusableCode extends Base  
{
    public WebDriver driver;

    public ReusableCode(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

    // Verifies if an element is visible or not
    public WebElement IfElementisVisible(By by)
    {
        WebElement visibleElement = null;
        logThis("Checking for this element " + by);

        try
        {
            if (driver.findElements(by).size() > 0)
            {
                visibleElement = driver.findElement(by);
                logThis("SUCCESS - Found this element " + visibleElement.getText());
            }
            else
                logThis("Element " + by + " was not found. Continuing test...");
        }
        catch(Exception e)
        {
            logThis("Exception when looking for element " + by + ". Continuing test, but exception was " + e);
        }            
        return visibleElement;
    }

    public Boolean IfElementExists(By by)
    {

        Boolean elementExists = false;
        logThis("Checking for this element " + by);

        if (driver.findElements(by).size() > 0)
            elementExists = true;

        return elementExists;
    }

    public WebElement VerifyElementisVisible(By by)
    {
        WebElement visibleElement = null;
        logThis("Verifying this element " + by);
        try
        {
            visibleElement = driver.findElement(by);
            if (visibleElement != null)
                logThis("Found this element " + visibleElement.getText());
        }
        catch (Exception e)
        {
           // log.Error("Element " + by + " was not found. Failing test...", e);
            Assert.fail("Element " + by + " was not found Failing test...");
        }

        return visibleElement;
    }

	public WebElement WaitUntilElementExists(By by) throws Exception
    {
        logThis("Waiting for this element to exist " + by);

        WebElement element = null;
        for (int second = 0; ; second++)
        {
            if (second >= timeoutValue_sleep) 
                Assert.fail("We timed out while waiting for an element to appear onscreen. Either we are not on the correct page where the element exists or the element we are looking for cannot be found " + by);

            if (driver.findElements(by).size() > 0)
            {
                element = driver.findElement(by);
                break;
            }

            Thread.sleep(100);
        }
        if (element != null)
            logThis("SUCCESS - WaitUntilElementExists found expected element");

        return element;
    }

    public List<WebElement> WaitUntilElementsExists(By by) throws Exception
    {
        List<WebElement> listElements = new ArrayList<WebElement>();
        List<WebElement> rawList = driver.findElements(by);
        logThis("Waiting for the elements to exist " + by);

        //var rawList = driver.findElements(by);
        logThis("Waiting for the list to exist " + rawList.size());
        for (int second = 0; ; second++)
        {
            if (second >= timeoutValue_sleep)
                Assert.fail("We timed out while waiting for an element to appear onscreen. Either we are not on the correct page where the element exists or the element we are looking for cannot be found " + by);

            if (driver.findElements(by).size() > 0)
            {
                logThis("WaitUntilElementExists found expected elements");
                rawList = driver.findElements(by);
                break;
            }

            Thread.sleep(100);
        }
        if (rawList != null)
        {                
            for(Iterator<WebElement> i = rawList.iterator(); i.hasNext(); ) {
            	    WebElement individualElement = i.next();
            	    listElements.add(individualElement);
            	    logThis("Element found for list: " + individualElement.getText());            			
            }
            logThis("SUCCESS - WaitUntilElementExists found expected elements");
        }

        return listElements;
    }    
}
