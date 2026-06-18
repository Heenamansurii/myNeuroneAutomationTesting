package testcase.login;

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

public class LOGIN_TC_ALL {

    private static final String LOGIN_URL      = "https://staging.myneuronworld.com/auth/login/";
    private static final String FORGOT_PWD_URL = "https://staging.myneuronworld.com/auth/forgot-password/";

    private static final String XPATH_EMAIL       = "//input[@name='email']";
    private static final String XPATH_PASSWORD    = "//input[@name='password']";
    private static final String XPATH_LOGIN_BTN   = "//button[@type='submit']";
    private static final String XPATH_FORGOT_LINK = "//a[contains(text(),'Forgot') or contains(text(),'forgot')]";

    // ── Terms & Privacy — multiple options try karega ────────────────────────
    private static final String XPATH_TERMS =
        "//a[contains(text(),'Terms') or contains(text(),'terms') or contains(@href,'terms')]";
    private static final String XPATH_PRIVACY =
        "//a[contains(text(),'Privacy') or contains(text(),'privacy') or contains(@href,'privacy') or contains(text(),'Policy')]";

    @DataProvider(name = "loginData")
    public Object[][] provideData() {
        return LoginTestData.getLoginData();
    }

    @Test(dataProvider = "loginData")
    public void verifyLogin(
            String testCaseName,
            String email,
            String password,
            String expectedResult,
            String description
    ) {
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("TC   : " + testCaseName);
        System.out.println("INFO : " + description);
        System.out.println("══════════════════════════════════════════════════════");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        try {

            // ── TC_017 & TC_026 — Normal Login ──────────────────────────────
            if (testCaseName.contains("ValidCredentials")
                    || testCaseName.contains("AfterEmailVerification")) {

                driver.get(LOGIN_URL);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_EMAIL)));
                driver.findElement(By.xpath(XPATH_EMAIL)).sendKeys(email);
                driver.findElement(By.xpath(XPATH_PASSWORD)).sendKeys(password);
                driver.findElement(By.xpath(XPATH_LOGIN_BTN)).click();
                Thread.sleep(2500);

                boolean loggedIn = checkLoginSuccess(driver);

                if ("PASS".equals(expectedResult) && !loggedIn) {
                    CommonUtils.takeScreenshot(driver, testCaseName);
                    Assert.fail("[" + testCaseName + "] Login hona chahiye tha lekin nahi hua");
                } else if ("FAIL".equals(expectedResult) && loggedIn) {
                    CommonUtils.takeScreenshot(driver, testCaseName);
                    Assert.fail("[" + testCaseName + "] Login nahi hona chahiye tha");
                }
                System.out.println("✅ Login result correct");
            }

            // ── TC_036 — Forgot Password Link ───────────────────────────────
            else if (testCaseName.contains("ForgotPasswordLink")) {

                driver.get(LOGIN_URL);
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(XPATH_FORGOT_LINK)));
                driver.findElement(By.xpath(XPATH_FORGOT_LINK)).click();
                Thread.sleep(2000);

                String url = driver.getCurrentUrl();
                if (url.contains("404")) {
                    CommonUtils.takeScreenshot(driver, testCaseName);
                    Assert.fail("Forgot Password 404 de raha hai — URL: " + url);
                }
                System.out.println("✅ Forgot Password page OK: " + url);
            }

            // ── TC_038 — Unregistered email Forgot Password ──────────────────
            else if (testCaseName.contains("UnregisteredEmail")) {

                driver.get(FORGOT_PWD_URL);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_EMAIL)));
                driver.findElement(By.xpath(XPATH_EMAIL)).sendKeys(email);
                driver.findElement(By.xpath(XPATH_LOGIN_BTN)).click();
                Thread.sleep(2000);

                boolean generic = checkGenericForgotMessage(driver);
                if (!generic) {
                    CommonUtils.takeScreenshot(driver, testCaseName);
                    Assert.fail("Specific error dikhaya — security issue!");
                }
                System.out.println("✅ Generic message shown");
            }

            // ── TC_034 — Terms & Privacy Links ──────────────────────────────
            else if (testCaseName.contains("TermsAndPrivacy")) {

                // — Terms check —
                driver.get(LOGIN_URL);
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(XPATH_TERMS)));
                driver.findElement(By.xpath(XPATH_TERMS)).click();
                Thread.sleep(1500);
                if (driver.getCurrentUrl().contains("404")) {
                    CommonUtils.takeScreenshot(driver, testCaseName + "_Terms");
                    Assert.fail("Terms link 404 de raha hai");
                }
                System.out.println("✅ Terms OK: " + driver.getCurrentUrl());

                // — Privacy check —
                driver.navigate().back();
                Thread.sleep(1000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(XPATH_PRIVACY)));
                driver.findElement(By.xpath(XPATH_PRIVACY)).click();
                Thread.sleep(1500);
                if (driver.getCurrentUrl().contains("404")) {
                    CommonUtils.takeScreenshot(driver, testCaseName + "_Privacy");
                    Assert.fail("Privacy link 404 de raha hai");
                }
                System.out.println("✅ Privacy OK: " + driver.getCurrentUrl());
            }

        } catch (AssertionError ae) {
            throw ae;
        } catch (Exception e) {
            System.out.println("⚠️  Exception: " + e.getMessage());
            CommonUtils.takeScreenshot(driver, testCaseName + "_Exception");
            Assert.fail("Exception: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    private boolean checkLoginSuccess(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return url.contains("dashboard") || url.contains("home")
                || url.contains("feed") || !url.contains("login");
    }

    private boolean checkGenericForgotMessage(WebDriver driver) {
        try {
            driver.findElement(By.xpath(
                "//*[contains(text(),'If this email') " +
                "or contains(text(),'check your inbox') " +
                "or contains(text(),'sent') " +
                "or contains(text(),'link')]"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}