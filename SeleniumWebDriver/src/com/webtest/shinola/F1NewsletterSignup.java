package com.webtest.shinola;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
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


public class F1NewsletterSignup {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		//Specify the browser
		getWebDriver(BrowserType.FIREFOX);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void F1NewsLetterTest() throws IOException, InterruptedException, Exception {
		
		/* Test Case 1
		*Ability to sign up for newsletters
		*/
		
		//1. Open www.shinola.com
		driver.get("https://www.shinola.com");
		
		//2. Close PopUp
		driver.findElement(By.xpath("//button[contains(text(),'close')]")).click();
		Thread.sleep(2000);
		
		
		//3. Click "News Letter Signup"
		driver.findElement(By.xpath("//a[contains(text(),'Newsletter Signup')]")).click();
		
		//Path of EXCEL sheet
		FileInputStream fis= new FileInputStream("H:\\work\\selenium.xls");
		//workbook
		Workbook workbook = Workbook.getWorkbook(fis); 
		//Sheet select
		Sheet sheet = workbook.getSheet(0);
				
		//4. Enter email Id for signup from excel
		driver.findElement(By.id("header-email")).sendKeys(sheet.getCell(0,0).getContents());
		
		//5. Submit signup
		driver.findElement(By.id("header-newsletter-submit")).click();
		Thread.sleep(2000);

		// assert title form home page
		assertTrue(driver.getTitle().contains("Shinola – Where American is Made | Shinola.com"));
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