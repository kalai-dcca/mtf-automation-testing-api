package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "step",
        plugin = {"pretty", "html:target/cucumber-reports.html",
                "utilities.ExtentReportListener"},
        dryRun = false,
        tags = "@JSONFile-API-Test"
)

public class TestRunner {
}
