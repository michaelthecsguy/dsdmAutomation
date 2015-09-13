package bst.cpo.automation.ds.tests;

import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;
import bst.cpo.automation.ds.tests.BaseTest;
import bst.cpo.automation.framework.dm.DM_Actions;

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

import javax.imageio.ImageIO;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.remote.Augmenter;
import org.sikuli.basics.*;
import org.sikuli.script.*;
import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
//import org.testng.TestListenerAdapter;
import org.testng.Reporter;

import com.paulhammant.ngwebdriver.ByAngular;


/*
 *  BaseTest is the super class needed to setup the environment for every test that runs
 *  Test classes will inherit from this class to access run time variables.
 */
public class BaseTest {
    
	public static WebDriver DSDriver;
    public static ByAngular ng;
	public DM_Actions DMBrowser; // Allows interaction with DM through framework
    
	public static Logger dslog = LogManager.getLogger(BaseTest.class.getName());	
    
	public static int timeoutValue_Implicit = 5;
    public static int timoutValue_MilliSec = 150;
    public static String userRunningTest =  System.getProperty("user.name");
    public static String browserUnderTest = "Firefox";
    public static String siteUnderTest;    
    public static String urlUnderTest;
    public static String resultsPath = "test-output\\screenshots\\";
    public static String basicUserUnderTest;
    public static String basicUserUnderTestPwd;
    public static String adminUserUnderTest;
    
    public String nameOfTestRunning = "";
    public String datetimeOfFailedTestCase = "";
    public String nameOfBrowserRunning = "";
    public String machineRunningtest = "";
    public String cpoMyDocFolder = "C:\\Users\\" + userRunningTest + "\\Cloud Portal Office\\My Docs";
    public String cpoOtherDocFolder = "C:\\Users\\" + userRunningTest + "\\Cloud Portal Office\\Other Docs";
    public String cpoLogFile = "C:\\Users\\" + userRunningTest + "\\AppData\\Local\\Sharp\\.cpo-drive\\logs\\clouddrive.log";
    public String cpoExePath = "C:\\Program Files (x86)\\Sharp\\Cloud Portal Office Desktop\\clouddrivew.exe";  
    
    public BaseTest(){    	
    	// Constructor
    }
    
	// Start the browser, results folder, and tool environment here
    @BeforeClass
	public static void Setup(ITestContext context) 
	{
        if(browserUnderTest == "Firefox")
            DSDriver = new FirefoxDriver();
        else if(browserUnderTest == "Chrome")
            DSDriver = new ChromeDriver();
        else if (browserUnderTest == "Internet Explorer")
            DSDriver = new InternetExplorerDriver();
        else
            Assert.fail("Browser Under Test is Not Set or is Incorrect in testng.xml");
           
        siteUnderTest = context.getCurrentXmlTest().getParameter("SiteUnderTest");
        urlUnderTest = context.getCurrentXmlTest().getParameter(siteUnderTest);
        browserUnderTest = context.getCurrentXmlTest().getParameter("BrowserUnderTest");
        basicUserUnderTest = context.getCurrentXmlTest().getParameter("BasicUserUnderTest");
        basicUserUnderTestPwd = context.getCurrentXmlTest().getParameter(basicUserUnderTest + "_Password");
        basicUserUnderTest = context.getCurrentXmlTest().getParameter(basicUserUnderTest);
        adminUserUnderTest = context.getCurrentXmlTest().getParameter("AdminUserUnderTest");

        
        // Set Sikuli settings and image location
        ImagePath.add("bin/ds2.sikuli");
        Settings.setShowActions(false);
        Settings.MinSimilarity = 0.88;
        
        // Perform a call to SikiluX's debug class
        Debug.test("SikiluX is operational!");        
        
        PropertyConfigurator.configure("bin/log4j.properties");
        
        logThis("***** Site Under Test: " + siteUnderTest);
        logThis("***** User Running Test: " + userRunningTest);

        DSDriver.manage().timeouts().implicitlyWait(timeoutValue_Implicit, TimeUnit.SECONDS);		 
        CreateFolderForResults();
	}
	
    // Placeholder if any other operations are needed to run a test
    public void Start_DSTest()
    {
    	logThis("***** Starting Test: " + nameOfTestRunning);
        
        // This test does not need Firefox
        Screen skulliScreen = new Screen();
        skulliScreen.wait(0.4);
    	skulliScreen.type(Key.SPACE, KeyModifier.ALT);
    	skulliScreen.type("n");
    }	
    
	// Start a test using both DS and DM
    public void Start_DS_DMTest()
    {
        logThis("***** Starting DS and DM Test: " + nameOfTestRunning);
        DSDriver.navigate().to(urlUnderTest);
        DSDriver.manage().window().maximize();
        DMBrowser = new DM_Actions(DSDriver);
    }	
    
    // This is needed to support Angular JS
    public void Start_DMAngular() throws InterruptedException
    {
    	Thread.sleep(4000);
        ng = new ByAngular((JavascriptExecutor) DSDriver);
        waitForAngularRequestsToFinish((JavascriptExecutor)DSDriver);
    }

	private static void CreateFolderForResults() {

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
        
    @AfterClass    
    public void Teardown()
    {
        logThis("***** Tests have Finished ");
                
        try{
            DSDriver.quit();
        }
        catch(Exception e)
        {
        	logThis("WebDriver was already closed");
        }
    }
    
    @AfterSuite
    public void EndSuite()
    {
    	logThis("***** Suite has Finished");
    }
            
	protected static void logThis(String logString) {
		dslog.log(Level.INFO, logString);		
		Reporter.log(logString);
	}
}
