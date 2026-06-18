package testcase;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import utils.CommonUtils;

public class RegisteUsingTab {

	WebDriver driver;

	@AfterMethod
	public void tearDown() {
	//	driver.quit();

	}

	@Test
		public void tabUsing() throws InterruptedException {
			driver =new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			
			driver.get("https://myneuron.leadows.com/auth/register/");
			
		    Actions actions = new Actions(driver);
		  
//		  for(int i=0;i<=4; i++) 
//		  {
//			  actions.sendKeys(Keys.TAB).perform();		
//			  		  
//		  }	

		    // 🔹 Click on first field to start typing
		    WebElement firstName = driver.findElement(By.name("first_name"));
		    firstName.click();

		    // 🔹 Start filling form using TAB
		    actions
		        .sendKeys("heena")                  // First Name
		        .sendKeys(Keys.TAB)

		        .sendKeys("")                       // Middle Name (skip)
		        .sendKeys(Keys.TAB)

		        .sendKeys("mansuri")                // Last Name
		        .sendKeys(Keys.TAB)

		        .sendKeys(CommonUtils.generateBrandNewMail()) // Email
		        .sendKeys(Keys.TAB)

		        .sendKeys("Password@123")           // Password
		        .sendKeys(Keys.TAB)

		        .sendKeys("Password@123")           // Confirm Password
		        .sendKeys(Keys.TAB)

		        .sendKeys(Keys.SPACE)               // Select checkbox (terms)

		        .sendKeys(Keys.ENTER)               // Submit form
		        .build()
		        .perform();

		    Thread.sleep(3000);
		  // driver.quit();
		}
}
		
		


