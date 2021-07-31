package com.lambda.cert.automation;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

	public RemoteWebDriver driver = null;
	String username = "anupam.bhattacharya";
	String accessKey = "7qF6HQpidQs31SZux4y4FGN32aA25K3w3j07bdeKA0570gGPiq";


	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Chrome");
		// capabilities.setAcceptInsecureCerts(true);
		capabilities.setCapability("version", "87.0");
		capabilities.setCapability("resolution","1024x768");
		capabilities.setCapability("build", "First Test");
		capabilities.setCapability("name", "Sample Test");
		capabilities.setCapability("network", true); 
		capabilities.setCapability("visual", true);
		capabilities.setCapability("video", true);
		capabilities.setCapability("console", true);


		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-notifications");
		capabilities.setCapability(ChromeOptions.CAPABILITY,options);

		try {       
			driver= new RemoteWebDriver(new URL("https://"+username+":"+accessKey+"@hub.lambdatest.com/wd/hub"), capabilities); 

		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		}
	}


	@Test
	public void testScript() throws Exception {
		try {

			driver.manage().window().maximize();
			driver.get("https://www.lambdatest.com/automation-demos/");
			driver.findElement(By.cssSelector("#username")).sendKeys("lambda");;
			driver.findElement(By.cssSelector("#password")).sendKeys("lambda123");
			driver.findElement(By.xpath("//form[@id='newapply']/div[3]/button")).click();
			Thread.sleep(2000);

			@SuppressWarnings("deprecation")
			WebDriverWait wait= new WebDriverWait(driver,5);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#developer-name")));
			driver.findElement(By.cssSelector("#developer-name")).sendKeys("anupam.bhattacharya@hcl.com");
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("#populate")).click();

			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='text-right mt-15']/span")).click();

			Thread.sleep(2000);

			driver.findElement(By.xpath("//input[@value='Once in 3 months']")).click();			
			driver.findElement(By.cssSelector("#customer-service")).click();
			driver.findElement(By.cssSelector("#discounts")).click();
			driver.findElement(By.cssSelector("#delivery-time")).click();

			Select paymentPreference= new Select(driver.findElement(By.cssSelector("#preferred-payment")));
			paymentPreference.selectByVisibleText("Cash on delivery");

			driver.findElement(By.cssSelector("#tried-ecom")).click();			

			WebElement slider= driver.findElement(By.xpath("//*[@class='disablebar']/div/div/div/div[12]"));
			Actions action= new Actions(driver);
			int width=slider.getSize().width;
			action.dragAndDropBy(slider, width*12, 0).build().perform();

			System.out.println(driver.findElement(By.xpath("//*[@class='disablebar']/div/div/div/div[12]")).getAttribute("style").contains("translate(529px, -6px)"));

			Thread.sleep(3000);

			String parent= driver.getWindowHandle();			
			driver.switchTo().newWindow(WindowType.TAB);
			Thread.sleep(1000);
			driver.get("https://www.lambdatest.com/selenium-automation");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@title='Jenkins']")));

			driver.switchTo().window(parent);

			driver.findElement(By.cssSelector("#img")).click();
			Thread.sleep(1000);
			String imageURL= System.getProperty("user.dir")+"\\Resource\\Jenkins.png";
			System.out.println(imageURL);
			String autoIT= System.getProperty("user.dir")+"\\Resource\\autoIT.exe";
			Thread.sleep(2000);
			ProcessBuilder pb= new ProcessBuilder(autoIT,imageURL);
			{
				pb.start();
				Thread.sleep(4000);
			}				
			driver.findElement(By.cssSelector("#submit-button")).click();
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='py-50 text-center']/p")).getText().contains("successfully submitted the form"));
			Thread.sleep(3000);


			driver.quit();					
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
