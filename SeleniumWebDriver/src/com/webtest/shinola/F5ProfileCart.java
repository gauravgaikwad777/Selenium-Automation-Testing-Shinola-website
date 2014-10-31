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


public class F5ProfileCart {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		//Specify the browser
		getWebDriver(BrowserType.FIREFOX);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void F5ProfileCartTest() throws IOException, InterruptedException, Exception {
		/*
		 *Test Case 5
		 *Ability to like Shinola’s Facebook Page 
		 */
		
		//1. Open www.shinola.com
		driver.get("https://www.shinola.com");
		
		//2. Close PopUp
		driver.findElement(By.xpath("//button[contains(text(),'close')]")).click();
		Thread.sleep(2000);
	
		//click on login button
		driver.findElement(By.id("shinola-customer-session")).click();
		
		//path of excel sheet
		FileInputStream fis= new FileInputStream("H:\\work\\selenium.xls");
				
		//workbook
		Workbook workbook = Workbook.getWorkbook(fis); 
				
		//Sheet select
		Sheet sheet = workbook.getSheet(0);
				
		// Enter your  username
		driver.findElement(By.id("email")).sendKeys(sheet.getCell(0,0).getContents());
		Thread.sleep(2000);
		
		// Enter your gmail password
		driver.findElement(By.id("pass")).sendKeys(sheet.getCell(0,1).getContents());
		Thread.sleep(2000);
		
		// Click on Login button.
		driver.findElement(By.id("send2")).click();
		Thread.sleep(2000);
		
		// Click on watches button.
		driver.findElement(By.id("watches-top-level")).click();
		Thread.sleep(2000);
				
		// using filter to sort watches 
		driver.findElement(By.className("active")).click();
		Thread.sleep(2000);

		//ACTION CLASS
		Actions b = new Actions(driver);
				
		driver.findElement(By.xpath("//a[contains(text(),'$900 - $1000')]")).click();
		Thread.sleep(2000);
				
		//Press down
		b.sendKeys(Keys.PAGE_DOWN).perform();
		Thread.sleep(3000);
						
		// click on watch 
		driver.findElement(By.xpath("/html/body/div[4]/div/section/div/div[3]/div/div/div[2]/div/div[3]/ul[2]/li/a/span/img")).click();
		Thread.sleep(3000);
				
		// add to cart 
		driver.findElement(By.xpath("//span[contains(text(),'Add to Cart')]")).click();
		Thread.sleep(2000);

		//Check for item being added to cart
		assertTrue(driver.getPageSource().contains("THE RUNWELL CHRONO 47mm was added to your shopping cart."));
		
		//Sign Out
		driver.findElement(By.xpath("/html/body/div/div[2]/div/ul/li[3]/span/a[2]")).click();
		Thread.sleep(2000);
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