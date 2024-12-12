package step.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

import java.util.HashMap;
import java.util.Map;

public class ExtentReportListener implements ConcurrentEventListener {

    private static ExtentReports extent;
    private static Map<String, ExtentTest> scenarioTestMap = new HashMap<>();

    static {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/cucumber-reports/extent-report.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Cucumber Extent Report");
        htmlReporter.config().setReportName("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        ExtentTest test = extent.createTest(event.getTestCase().getName());
        scenarioTestMap.put(event.getTestCase().getId().toString(), test);
    }

    private void onTestCaseFinished(TestCaseFinished event) {
        ExtentTest test = scenarioTestMap.get(event.getTestCase().getId().toString());
        if (event.getResult().getStatus() == Status.PASSED) {
            test.pass("Test Passed");
        } else if (event.getResult().getStatus() == Status.FAILED) {
            test.fail("Test Failed: " + event.getResult().getError().getMessage());
        }
        extent.flush();
    }
}