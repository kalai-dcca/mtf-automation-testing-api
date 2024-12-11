package org.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features",
        glue="org/steps",
        plugin = {
                "pretty",
                "html:target/reports/AutomationReport.html",
                "json:target/reports/AutomationReport.json",
                "junit:target/reports/AutomationReport.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        publish = false,
        dryRun = false,
        tags = "@test"
)
public class Runner4Tests {
}
