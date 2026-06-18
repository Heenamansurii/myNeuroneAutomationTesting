package testcase;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import org.testng.annotations.Test;

public class LoginWithInvalidCredential {

	@Test
	public void verifyLogin() throws IOException, InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		driver.get("https://myneuron.leadows.com/auth/login");
		driver.findElement(By.name("email")).sendKeys("heenaleadows@gmail.com");
		driver.findElement(By.name("password")).sendKeys("Password@1234");
		driver.findElement(By.xpath("//button[text()='Log in']")).click();
		
	    // ⏳ Wait a bit for response
	    Thread.sleep(2000);



		// 📁 Create folder if not exists
		File folder = new File("./screenshots");
		if (!folder.exists()) {
			folder.mkdir();
		}
		


	    // 🔍 Check if error message is present
	    if (driver.findElements(By.xpath("//*[contains(text(),'Invalid credentials')]")).size() > 0) {

	        System.out.println("❌ Login failed - taking screenshot");
		// 📸 Take screenshot
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = "login_error_" + System.currentTimeMillis() + ".png";

		String destinationPath = System.getProperty("user.dir") + "\\screenshots\\"+fileName;

		FileHandler.copy(src, new File(destinationPath));

		System.out.println("Screenshot saved at: " + destinationPath);
	    } else {
	        System.out.println("✅ Login successful");
	    }

		driver.quit();
	}
}
