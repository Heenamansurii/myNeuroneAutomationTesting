package testcase;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class DashboardButtonTest {

	@Test
	public void verifyAllButton() throws InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		driver.get("https://myneuron.leadows.com/auth/login");
		driver.findElement(By.name("email")).sendKeys("heenaleadows@gmail.com");
		driver.findElement(By.name("password")).sendKeys("Password@123");
		driver.findElement(By.xpath("//button[text()='Log in']")).click();

		// right tab click check
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/header/div[1]/button/span")).click(); // three line
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/aside/div/ul/li[2]")).click(); // GATC
		driver.findElement(By.xpath("//a[text()='GATC 2026']")).click(); // GATC 2026
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/aside/div/ul/li[3]/span/a")).click(); // my bookshelf
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/aside/div/ul/li[4]/span/a")).click(); // impulse

		// left side button click check

//		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/header/div[3]/div[1]/button[5]")).click(); // notification
//
//		// close notification
//		WebElement closeBtn = driver.findElement(By.cssSelector(".ant-drawer-close"));
//
//		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", closeBtn);
//
//		closeBtn.click();

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/header/div[3]/div[1]/button[4]")).click(); // chat
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/header/div[3]/div[1]/button[3]")).click(); // book shelf
																											// icon
		//Thread.sleep(20);

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/header/div[3]/div[1]/button[2]")).click(); // impulse icon
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/header/div[3]/div[1]/button[1]")).click(); // home button


   
			 driver.quit();

	}
}
