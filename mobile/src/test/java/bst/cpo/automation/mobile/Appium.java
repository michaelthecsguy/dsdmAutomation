package bst.cpo.automation.mobile;

import java.net.MalformedURLException;
import java.net.URL;
 


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
public class Appium {
	
	WebDriver driver;
	
	@BeforeClass
	public void setUp() throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "Android");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); //Name of mobile web browser to automate. Should be an empty string if automating an app instead.
		capabilities.setCapability(CapabilityType.VERSION, "4.4");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		capabilities.setCapability("device ID", "emulator-5554");
		capabilities.setCapability("deviceName", "Nexus 4 KitKat");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("app-package", "jp.co.sharp.printsystem.cpoffice.phone.us"); //Replace with your app's package
		capabilities.setCapability("app-activity", "jp.co.sharp.printsystem.cpoffice.phone.DataCabinetActivity"); //Replace with app's Activity
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}
	
	@Test
	public void Login_Test(){
		try {
			Thread.sleep(30000); // let the app load
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.name("Accept")).click();
		driver.findElement(By.id("loginname")).sendKeys("slaqa06");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("test123#");
		driver.findElement(By.name("Sign In")).click();		
	}
	
	@AfterClass
	public void tearDown(){
		driver.quit();
	}
 
}