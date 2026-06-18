package testcase.register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SearchTest {
	
	 WebDriver driver ;
	 
	 @AfterMethod
	public void teardown() {

      driver.quit();
	}

    @Test
    public void verifySearchFunctionality() throws InterruptedException {

    	driver  = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://myneuron.leadows.com/auth/login");

        // Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys("heenaleadows@gmail.com");
        driver.findElement(By.name("password")).sendKeys("Password@123");
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        // Wait for search bar
        By searchBox = By.xpath("//input[@placeholder='Search users']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));

        // Enter text
        driver.findElement(searchBox).sendKeys("Heena");
        
        // Wait for dropdown suggestions
        By suggestions = By.xpath("//*[contains(text(),'Heena')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestions));
		
		  // ✅ Verify suggestions appear
        Assert.assertTrue(driver.findElements(suggestions).size() > 0);
		
		// Close dropdown
		driver.findElement(searchBox).sendKeys(Keys.ESCAPE);
		
		// Wait until dropdown disappears
		wait.until(ExpectedConditions.invisibilityOfElementLocated(suggestions));

		// Click Dashboard (go to home)
		By dashboard = By.xpath("//span[text()='Dashboard']");
		wait.until(ExpectedConditions.elementToBeClickable(dashboard)).click();

    }
}