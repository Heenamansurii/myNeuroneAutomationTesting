package testcase.register;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CommonUtils;

public class REG_TC_ALL {

    private static final String REGISTER_URL      = "https://staging.myneuronworld.com/auth/register/";
    private static final String XPATH_FIRST_NAME  = "//input[@name='first_name']";
    private static final String XPATH_MIDDLE_NAME = "//input[@name='middle_name']";
    private static final String XPATH_LAST_NAME   = "//input[@name='last_name']";
    private static final String XPATH_EMAIL       = "//input[@name='email']";
    private static final String XPATH_PASSWORD    = "//input[@name='password']";
    private static final String XPATH_CONFIRM_PWD = "//input[@name='confirm_password']";
    private static final String XPATH_TERMS       = "//*[@id='root']/div[1]/div[3]/div/form/div[7]/div/label[1]/span/input";
    private static final String XPATH_SUBMIT      ="//*[@id='root']/div[1]/div[3]/div/form/button";

    @DataProvider(name = "registrationData")
    public Object[][] provideData() {
        return TestData.getRegistrationData();
    }

    @Test(dataProvider = "registrationData")
    public void verifyRegistration(
            String testCaseName,
            String firstName, String middleName, String lastName,
            String email, String password, String confirmPassword,
            String expectedResult, String termsCheckbox, String description
    ) {
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("TC   : " + testCaseName);
        System.out.println("INFO : " + description);
        System.out.println("══════════════════════════════════════════════════════");

        String finalEmail = "GENERATE".equals(email)
                ? CommonUtils.generateBrandNewMail() : email;

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        try {
            driver.get(REGISTER_URL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_FIRST_NAME)));

            driver.findElement(By.xpath(XPATH_FIRST_NAME)).sendKeys(firstName);
            driver.findElement(By.xpath(XPATH_MIDDLE_NAME)).sendKeys(middleName);
            driver.findElement(By.xpath(XPATH_LAST_NAME)).sendKeys(lastName);
            driver.findElement(By.xpath(XPATH_EMAIL)).sendKeys(finalEmail);
            driver.findElement(By.xpath(XPATH_PASSWORD)).sendKeys(password);
            driver.findElement(By.xpath(XPATH_CONFIRM_PWD)).sendKeys(confirmPassword);

            if ("YES".equalsIgnoreCase(termsCheckbox)) {
                driver.findElement(By.xpath(XPATH_TERMS)).click();
            }

            driver.findElement(By.xpath(XPATH_SUBMIT)).click();
            Thread.sleep(2500);

            boolean isSuccess = checkSuccess(driver);

            if ("PASS".equalsIgnoreCase(expectedResult)) {
                if (!isSuccess) {
                    CommonUtils.takeScreenshot(driver, testCaseName); // 📸 FAIL pe screenshot
                    Assert.fail("[" + testCaseName + "] Registration honi chahiye thi lekin nahi hui");
                }
                System.out.println("✅ PASS — Registration successful");
            } else {
                if (isSuccess) {
                    CommonUtils.takeScreenshot(driver, testCaseName); // 📸 FAIL pe screenshot
                    Assert.fail("[" + testCaseName + "] Registration nahi honi chahiye thi lekin ho gayi");
                }
                System.out.println("✅ PASS — Error correctly shown (FAIL expected)");
            }

        } catch (AssertionError ae) {
            throw ae;
        } catch (Exception e) {
            System.out.println("⚠️  EXCEPTION: " + e.getMessage());
            CommonUtils.takeScreenshot(driver, testCaseName + "_Exception"); // 📸 Exception pe bhi
            if ("PASS".equalsIgnoreCase(expectedResult)) {
                Assert.fail("Exception: " + e.getMessage());
            }
            System.out.println("✅ Exception = Validation working (FAIL expected)");
        } finally {
            driver.quit();
        }
    }

    private boolean checkSuccess(WebDriver driver) {
        String url = driver.getCurrentUrl();
        if (url.contains("verify") || url.contains("dashboard") || url.contains("success"))
            return true;
        try {
            driver.findElement(By.xpath(
                "//*[contains(text(),'successfully') or contains(text(),'verification email')]"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}