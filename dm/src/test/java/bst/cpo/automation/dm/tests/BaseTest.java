package bst.cpo.automation.dm.tests;

import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.imageio.ImageIO;

import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;

import com.paulhammant.ngwebdriver.ByAngular;

public class BaseTest {
    
	public static WebDriver DMDriver;
    public static ByAngular ng;
    
	public static Logger dmlog = LogManager.getLogger(BaseTest.class.getName());
    
	public static int timeoutValue_Implicit = 7;
	public int timeoutValue_sleep = 4;
    public static int timoutValue_MilliSec = 250;
    public static String userRunningTest =  System.getProperty("user.name");
    public static String browserUnderTest = "Firefox";
    public static String siteUnderTest;    
    public static String urlUnderTest;
    public static String resultsPath = "test-output\\screenshots\\";
    public static String basicUserUnderTest;
    public static String basicUserUnderTestPwd;
    public static String adminUserUnderTest;
    public static String adminUserUnderTestPwd;
    
    public String nameOfTestRunning = "";
    public String datetimeOfFailedTestCase = "";
    public String nameOfBrowserRunning = "";
    public String machineRunningtest = "";
    public Boolean isUserBA = false;

    @BeforeClass
	public static void Setup(ITestContext context) 
	{
    	/**
    	 * Optional, if not specified, WebDriver will search your environment/system variable path for chromedriver.
    	 * System.setProperty("webdriver.chrome.driver", "C:/Users/<user>/Desktop/chromedriver.exe");
    	 */
    	if(browserUnderTest == "Firefox")
            DMDriver = new FirefoxDriver();
        else if(browserUnderTest == "Chrome")
            DMDriver = new ChromeDriver();
        else if (browserUnderTest == "Internet Explorer")
            DMDriver = new InternetExplorerDriver();
        else
            Assert.fail("Browser Under Test variable is Not Set or is Incorrect in testng.xml");
           
        siteUnderTest = context.getCurrentXmlTest().getParameter("SiteUnderTest");
        urlUnderTest = context.getCurrentXmlTest().getParameter(siteUnderTest);
        browserUnderTest = context.getCurrentXmlTest().getParameter("BrowserUnderTest");  
        basicUserUnderTest = context.getCurrentXmlTest().getParameter("BasicUserUnderTest");
        basicUserUnderTestPwd = context.getCurrentXmlTest().getParameter(basicUserUnderTest + "_Password");
        basicUserUnderTest = context.getCurrentXmlTest().getParameter(basicUserUnderTest);
        adminUserUnderTest = context.getCurrentXmlTest().getParameter("AdminUserUnderTest");
        adminUserUnderTestPwd = context.getCurrentXmlTest().getParameter(adminUserUnderTest + "_Password");
        adminUserUnderTest = context.getCurrentXmlTest().getParameter(adminUserUnderTest);

        DMDriver.manage().timeouts().implicitlyWait(timeoutValue_Implicit, TimeUnit.SECONDS);
        DMDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        
        PropertyConfigurator.configure("bin/log4j.properties");
        
        logThis("***** Site Under Test: " + siteUnderTest);
        logThis("***** User Running Test: " + userRunningTest);
        CreateFolderForResults();        
	}	

	/** Start the browser environment here */
    public void Start_DMTest()
    {
        logThis("***** Starting Test: " + nameOfTestRunning);
        DMDriver.navigate().to(urlUnderTest);
        DMDriver.manage().window().maximize();
    }	
    
    /** This is needed for Angular JS */
    public void Start_DMAngular() throws InterruptedException
    {
    	Thread.sleep(4000);
        ng = new ByAngular((JavascriptExecutor) DMDriver);
        waitForAngularRequestsToFinish((JavascriptExecutor)DMDriver);
    }

	private static void CreateFolderForResults() 
	{
		Path resultsFolder = Paths.get(resultsPath);
		
		if(Files.notExists(resultsFolder, LinkOption.NOFOLLOW_LINKS))
		{
			new File(resultsPath).mkdirs();
		}
	}
	
	public void TakeAndSaveScreenshot(ITestResult result) throws AWTException, IOException
    { 	
        logThis("Generating screenshot for failed test: " + nameOfTestRunning);      

        Date date = new Date();
        datetimeOfFailedTestCase = date.toString();
        datetimeOfFailedTestCase = datetimeOfFailedTestCase.replace(':', '-').replace('/', '-').replace(' ', '_');
        
        String screenshotName = "FAIL_" + datetimeOfFailedTestCase + "_" + siteUnderTest + "-" + browserUnderTest;

        String screenshotPath = resultsPath + screenshotName + ".png";
        logThis("Screenshot Path " + screenshotPath);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Rectangle screenRect = new Rectangle(screenSize);
        Robot robot = new Robot();
        
        BufferedImage image = robot.createScreenCapture(screenRect);
        ImageIO.write(image, "png", new File(screenshotPath));
        
        logThis("Saved screenshot for failed test");
        logThis("Now attaching to TestNG Report");
        Reporter.setCurrentTestResult(result); 
        Reporter.log("<br> <img src=screenshots\\"+screenshotName+".png /> <br>");
    }
   
    @BeforeMethod(alwaysRun=true)
    private void StoreCurrentlyRunningTestName(Method method)
    {    	
       nameOfTestRunning = method.getName();
       logThis("***** Starting Test: " + nameOfTestRunning);
    }
    
    @AfterMethod(alwaysRun=true)
    public void CleanupFailedTestcase(ITestResult result) throws AWTException, IOException
    {    	
        Date date = new Date();
        datetimeOfFailedTestCase = date.toString().replace(':', '-').replace('/', '-');
        if(!result.isSuccess())
        {
            logThis("***** TEST FAILED: " + result.getMethod().getMethodName() + " at " + datetimeOfFailedTestCase);
            TakeAndSaveScreenshot(result);
        }
        else
            logThis("***** TEST PASSED: " + result.getMethod().getMethodName() + " at " + datetimeOfFailedTestCase);
    }
        
    @AfterSuite    
    public void EndSuite()
    {
        logThis("***** Tests have Finished ");
                
        try{
//            DMDriver.quit();
        }
        catch(Exception e)
        {
        	logThis("browser already closed");
        }
        
        logThis("***** Suite has Finished");
    }
          
	protected static void logThis(String logString) {
		dmlog.log(Level.INFO, logString);		
		Reporter.log(logString);
	}
}
