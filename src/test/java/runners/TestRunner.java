package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-reports.html",
                "summary",
                "reports.ExtentReportListener"},
        tags = "@wip",
        monochrome = true,
        dryRun = false
)

public class TestRunner {
}
