package testcase.register;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LoginWithValidCredential {

	@Test
	public void verifyLogin() throws IOException, InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		driver.get("https://myneuron.leadows.com/auth/login");
		driver.findElement(By.name("email")).sendKeys("heenaleadows@gmail.com");
		driver.findElement(By.name("password")).sendKeys("Password@123");
		driver.findElement(By.xpath("//button[text()='Log in']")).click();
		
	
		
		
	    // ⏳ Wait a bit for response
	    Thread.sleep(2000);



		// 📁 Create folder if not exists
	    File folder = new File("./screenshots");
        if (!folder.exists()) {
            folder.mkdir();
        }
     // Wait for page load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        // 📸 Take screenshot
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String destinationPath = System.getProperty("user.dir")+"\\screenshots\\HomePage.png";

        FileHandler.copy(src, new File(destinationPath));

        System.out.println("Screenshot saved at: " + destinationPath);
        Thread.sleep(3000); // wait 3 seconds

        driver.quit();
    
	}
}
