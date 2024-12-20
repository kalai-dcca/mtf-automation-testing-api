package core;

import java.util.Objects;

public abstract class BaseClass {

    private static TestScenarioClass testScenarioClass;

    public static final String TEST_DATA_PATH = "src/test/resources/testData/";

    public static TestScenarioClass getTestScenarioClass(){
        if(Objects.isNull(testScenarioClass)){
            testScenarioClass = new TestScenarioClass();
            setTestScenarioClass(testScenarioClass);
        }
        return testScenarioClass;
    }

    public static void setTestScenarioClass(TestScenarioClass testScenarioClass) {
        BaseClass.testScenarioClass = testScenarioClass;
    }
}
