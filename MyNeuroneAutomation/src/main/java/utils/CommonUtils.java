package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Date;

public class CommonUtils {

    // ── Unique email generator ───────────────────────────────────────────────
    public static String generateBrandNewMail() {
        return "testuser_" + new Date().getTime() + "@mailinator.com";
		//return new Date().toString().replaceAll("\\s","").replaceAll("\\:","")+"@gamil.com";

    }

    // ── Screenshot — FAIL hone par automatically call hoga ──────────────────
    /**
     * @param driver      current WebDriver
     * @param testCaseName  jaise "TC_PASS_001_PasswordMismatch"
     *
     * Screenshot yahan save hoga:
     *   screenshots/TC_PASS_001_PasswordMismatch_1718123456789.png
     */
    public static void takeScreenshot(WebDriver driver, String testCaseName) {
        try {
            // screenshots folder — aapke project root mein already hai
            File destDir = new File("screenshots");
            if (!destDir.exists()) destDir.mkdirs();

            String fileName = testCaseName + "_" + new Date().getTime() + ".png";
            File destFile = new File(destDir, fileName);

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, destFile);

            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("⚠️  Screenshot le nahi paye: " + e.getMessage());
        }
    }
}