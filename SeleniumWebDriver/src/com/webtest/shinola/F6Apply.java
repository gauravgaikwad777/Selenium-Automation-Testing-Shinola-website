package com.webtest.shinola;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class F6Apply {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		// Specify the browser
		getWebDriver(BrowserType.FIREFOX);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void F6ApplyTest() throws IOException, InterruptedException {
		/* 
		 * Test Case 6
		 *Ability to apply for job
		 */
		
		// 1. Open www.shinola.com
		driver.get("http://www.shinola.com/");
				
		// 2. Close PopUp
		driver.findElement(By.xpath("//button[contains(text(),'close')]")).click();
		Thread.sleep(2000);

		// 3. click on Career button
		driver.findElement(By.linkText("Careers")).click();
		Thread.sleep(2000);

		// ACTION CLASS
		Actions b = new Actions(driver);

		// 4. Goto end of page
		b.sendKeys(Keys.END).perform();
		Thread.sleep(3000);

		// 5. Click on Apply for this job
		driver.findElement(By.xpath("/html/body/div[4]/div/section/div/article/div[2]/ul/li[27]/a/span")).click();
		Thread.sleep(2000);
		
		// 6. Click on Miami's job posting at end of page
		driver.get("http://shinola.catsone.com/careers/index.php?m=portal&a=details&jobOrderID=3689864");
      
		// assert on title 
		assertTrue(driver.getPageSource().contains("Area Brand Coordinator | Shinola"));
		
		// 7. Click on Apply Now
		driver.findElement(By.linkText("Apply Now")).click();
		Thread.sleep(3000);

		//Assert Maimi as job location
		assertTrue(driver.getPageSource().contains("Miami, FL"));
		
		// 8. Send keys on Upload Resume Choose file
		driver.findElement(By.id("0_field_0")).sendKeys("H:\\work\\SeleniumRESUME.doc");
		Thread.sleep(5000);
		
		// 9. Enter First Name
		driver.findElement(By.id("0_field_2")).sendKeys("Selenium");

		// 10. Enter Last Name
		driver.findElement(By.id("0_field_3")).sendKeys("Selenium");

		// 11. Enter Email address
		driver.findElement(By.id("0_field_4")).sendKeys("selenium501@gmail.com");
		
		// Scroll Up and Down
		b.sendKeys(Keys.HOME).perform();
		b.sendKeys(Keys.END).perform();
		
		// Print Confirmation of all mandatory elements
		JavascriptExecutor jscript = (JavascriptExecutor) driver;
		jscript.executeScript("alert('All application fileds have not been filled!');");
		Thread.sleep(3000);
				
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		
		// 12. Click on Submit button
		driver.findElement(By.className("submitButton")).click();
		Thread.sleep(4000);
		
		// 13. Take Screen Shot
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("H:\\work\\F6.png"));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		// Scroll Up and Down
		b.sendKeys(Keys.END).perform();
		b.sendKeys(Keys.HOME).perform();
		
		// Print Application success message
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('Registration has been successfully completed!');");
		Thread.sleep(3000);
		
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		
		////////////////////////
		//GOTO GMAIL TO CHECK FOR APPLICATION REGISTRATION
		driver.get("http://www.gmail.com");
		
		// Enter your gmail username
		driver.findElement(By.id("Email")).sendKeys("selenium501");

		// Enter your gmail password
		driver.findElement(By.id("Passwd")).sendKeys("selenium501");

		// Click on sign In button.
		driver.findElement(By.id("signIn")).click();
		
		//Go to Inbox
		driver.findElement(By.xpath("//a[contains(text(),'Inbox')]")).click();
		Thread.sleep(2000);

		//CHECK FOR SEND MAIL
		assertTrue(driver.getPageSource().contains("noreply@shinola.com"));
			
		//Send the mail to 
		Actions action = new Actions(driver);
		WebElement source = driver.findElement(By.xpath("//span[contains(text(),'noreply@shinola.com')]"));
		action.dragAndDropBy(source, 100, -50).perform();
		Thread.sleep(5000);
		
		//Find Sign Out button
		driver.findElement(By.xpath("//div[@id='gb']/div/div/div[2]/div[5]/div/a/span")).click();
		
		//Click Sign Out
		driver.findElement(By.id("gb_71")).click();
		Thread.sleep(3000);
		
		// Print Greetings
		JavascriptExecutor Greet = (JavascriptExecutor) driver;
		Greet.executeScript("alert('Thankyou from Rahul and Gaurav');");
		Thread.sleep(3000);
				
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	public void getWebDriver(BrowserType browserType) {

		switch (browserType) {
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case IE:
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			File file = new File("H:\\work\\mylib\\IEDriverServer.exe");
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
