package com.webtest.shinola;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class F2LocateStore {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		//Specify the browser
		getWebDriver(BrowserType.FIREFOX);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void F2LocateStoreTest() throws IOException, InterruptedException {
		/*
		 * Test Case 2
		*Ability to locate store nearby
		*/
		
		//1. Open www.shinola.com
		driver.get("https://www.shinola.com");
		
		//2. Close PopUp
		driver.findElement(By.xpath("//button[contains(text(),'close')]")).click();
		Thread.sleep(2000);
		
		//3. Goto Store Locator
		driver.findElement(By.linkText("Store Locator")).click();
				
		//4. Enter address
		driver.findElement(By.xpath("//input[@id='address']")).sendKeys("Chicago");
		
		//5. Drop down select 300 miles
		driver.findElement(By.id("radius")).click();
		driver.findElement(By.xpath("/html/body/div[4]/div/section/div/div/div/form/fieldset/ul/li[2]/div/select/option[5]")).click();
		
		//6. Click Submit button
		driver.findElement(By.xpath("/html/body/div[4]/div/section/div/div/div/form/fieldset/div/button/span/span")).click();
		
		//7. Check text presence
		assertTrue(driver.getPageSource().contains("Shinola Factory Headquarters"));
				
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
   public void getWebDriver(BrowserType browserType) {
			
		    switch (browserType) {
		        case FIREFOX:
		        	driver=new FirefoxDriver();
		            break;
		        case IE:
		    		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		    		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		    		File file = new File("E:\\ISU\\New folder\\selenium-2.40.0\\IEDriverServer.exe");
		    		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		    		driver = new InternetExplorerDriver(capabilities);		  
		    		break;
		        case CHROME:
		        	driver = new ChromeDriver();
		            break;
		        case HTMLUNIT:
		        	driver = new HtmlUnitDriver();
		            break;
		        default:
		            throw new RuntimeException("Browser type unsupported");
		           
		    }
		 }
			
  public enum BrowserType {
	   FIREFOX, IE, CHROME, HTMLUNIT
  }

}