package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CSV Report — Excel mein open hoga, manager ko bhej sakte ho
 * File: reports/TestReport_DATE.csv
 */
public class ReportGenerator implements ITestListener {

    private FileWriter csvWriter;
    private String reportPath;
    private int passCount = 0;
    private int failCount = 0;
    private String startTime;

    @Override
    public void onStart(ITestContext context) {
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportPath = "reports/TestReport_" + timestamp + ".csv";

            new File("reports").mkdirs();
            csvWriter = new FileWriter(reportPath);

            // CSV Header
            csvWriter.write("TC ID,Description,Status,Time,Error\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passCount++;
        writeRow(result, "PASS", "");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failCount++;
        String error = result.getThrowable().getMessage()
                .replaceAll(",", " ")   // CSV mein comma issue na ho
                .replaceAll("\n", " ")
                .substring(0, Math.min(100, result.getThrowable().getMessage().length()));
        writeRow(result, "FAIL", error);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        writeRow(result, "SKIP", "");
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            // Summary row
            csvWriter.write("\n");
            csvWriter.write("SUMMARY,,,,\n");
            csvWriter.write("Total," + (passCount + failCount) + ",,, \n");
            csvWriter.write("Pass," + passCount + ",,,\n");
            csvWriter.write("Fail," + failCount + ",,,\n");
            csvWriter.write("Run Date," + startTime + ",,,\n");
            csvWriter.write("Environment,Staging,,,\n");
            csvWriter.write("URL,https://staging.myneuronworld.com,,,\n");

            csvWriter.flush();
            csvWriter.close();

            System.out.println("\n📊 CSV Report ready: " + reportPath);
            System.out.println("   Excel mein open karo → Manager ko email karo ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeRow(ITestResult result, String status, String error) {
        try {
            Object[] params = result.getParameters();
            String tcId     = params.length > 0 ? params[0].toString() : result.getName();
            String desc     = params.length > 4 ? params[4].toString() : "";
            String time     = new SimpleDateFormat("HH:mm:ss").format(new Date());

            csvWriter.write(
                "\"" + tcId   + "\"," +
                "\"" + desc   + "\"," +
                "\"" + status + "\"," +
                "\"" + time   + "\"," +
                "\"" + error  + "\"\n"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}