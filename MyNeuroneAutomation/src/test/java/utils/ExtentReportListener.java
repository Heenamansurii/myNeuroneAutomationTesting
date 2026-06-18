package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.ITestContext;
import org.testng.ITestListener;   // ← SAHI interface
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportListener implements ITestListener {  // ← ITestListener — SAHI

    private static ExtentReports extent;
    private static Map<Long, ExtentTest> testMap = new HashMap<>();

    @Override
    public void onStart(ITestContext context) {
        String timestamp  = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "reports/TestReport_" + timestamp + ".html";

        new File("reports").mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("MyNeuron Test Report");
        spark.config().setReportName("MyNeuron — " + timestamp);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Project",     "MyNeuron Automation");
        extent.setSystemInfo("Environment", "Staging");
        extent.setSystemInfo("Tester",      "QA Team");

        System.out.println("📊 Report banega: " + reportPath);
    }

    @Override
    public void onTestStart(ITestResult result) {
        Object[] params    = result.getParameters();
        String description = params.length > 4 ? params[4].toString() : "";
        ExtentTest test    = extent.createTest(result.getName(), description);
        testMap.put(Thread.currentThread().getId(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getTest().log(Status.PASS, "✅ PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = getTest();
        test.log(Status.FAIL, "❌ FAILED: " + result.getThrowable().getMessage());

        // Screenshot attach karo
        try {
            Object[] params = result.getParameters();
            String tcName   = params.length > 0 ? params[0].toString() : result.getName();

            File ssDir = new File("screenshots");
            if (ssDir.exists()) {
                File[] files = ssDir.listFiles((d, n) ->
                        n.startsWith(tcName) && n.endsWith(".png"));
                if (files != null && files.length > 0) {
                    test.addScreenCaptureFromPath(
                        files[files.length - 1].getAbsolutePath(), "Failure Screenshot");
                }
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Screenshot attach nahi hua: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        getTest().log(Status.SKIP, "⏭️ SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("✅ Report ready — reports/ folder mein dekho!");
    }

    private ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }
}