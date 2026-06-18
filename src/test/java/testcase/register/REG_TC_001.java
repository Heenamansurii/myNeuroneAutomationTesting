package testcase.register;

import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import utils.CommonUtils;

public class REG_TC_001 {
    @Test
	public  void verifyWithMandatoryFields(){
     WebDriver driver = new ChromeDriver();
     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
     driver.manage().window().maximize();
     driver.get("https://myneuron.leadows.com/auth/login");
     driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[3]/div/p/a")).click();
     driver.findElement(By.name("first_name")).sendKeys("heena");
     driver.findElement(By.name("middle_name")).sendKeys("");
     driver.findElement(By.name("last_name")).sendKeys("mansuri");
    driver.findElement(By.name("email")).sendKeys(CommonUtils.generateBrandNewMail());

     driver.findElement(By.name("password")).sendKeys("Password@123");
     driver.findElement(By.name("confirm_password")).sendKeys("Password@123");
     driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[3]/div/form/div[7]/div/label[1]/span/input")).click();
     driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[3]/div/form/button")).click();

     driver.quit();
    
     
	}

}
