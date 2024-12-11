package org.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.utilities.MyLogger;
import org.utilities.PlayWrightFactory;

import static org.utilities.MyLogger.getCurrentLog;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario){
        MyLogger.startTestCase(scenario.getName());
        PlayWrightFactory.getInstance();
    }

    @After
    public void afterScenario(Scenario scenario) {
        if(scenario.isFailed()){
            MyLogger.error("Scenario:[" + scenario.getName() + "] - Status - "+scenario.getStatus());
            scenario.attach(PlayWrightFactory.getInstance().takeScreenshot(), "image/png",scenario.getName());
        }else {
            MyLogger.info("Scenario:[" + scenario.getName() + "] - Status - "+scenario.getStatus());
        }
        MyLogger.endTestCase(scenario.getName());
    }
}
