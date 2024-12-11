package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import utils.LoggerUtil;

public class Hooks {

    @Before
    public void setup() {
        LoggerUtil.logger.info("Starting the test...");
    }

    @After
    public void tearDown() {
        LoggerUtil.logger.info("Test execution completed.");
    }

}
