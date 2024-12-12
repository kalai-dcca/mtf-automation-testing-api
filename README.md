# mtf-automation-testing-api

Overview
This project is a REST API automation testing framework using Java with a Behavior-Driven Development (BDD) approach, implemented with Cucumber and JUnit/TestNG. The framework facilitates robust API testing by organizing code into clear layers for configuration, utilities, step definitions, and feature files.

Key Components
1. API Layer (src/main/java/api/)
   -- BaseAPI.java: Core methods for API communication (e.g., GET, POST).
   -- Endpoints.java: Defines API endpoint URIs.
2. Configuration Layer (src/main/java/config/)
   -- Configuration.java: Manages and loads properties like base URLs, timeouts, etc.
3. Data Layer (src/main/java/data/)
   -- UserData.java: Manages test data.
4. Utilities (src/main/java/utilities/)
   -- DBUtils.java: Database connection utilities.
   -- LoggerUtil.java: Custom logging utility for debugging.
5. Test Layer
   -- Runners (src/test/java/runners/): Test runner class for executing features.
   -- Step Definitions (src/test/java/stepdefinitions/): Maps Gherkin steps to Java code.
6. Feature Files (src/test/resources/features/)
   -- Contains test scenarios written in Gherkin syntax.
7. Configuration (config.properties)
   -- Holds configurable parameters like baseURL, API keys, and timeouts.

# Pre-requisites
* Java JDK 11+
* Maven (for dependency management)
* IDE: IntelliJ IDEA, Eclipse, or any Java-supported IDE.

# How to Run the Tests
Clone the repository with following command in terminal:

```
git clone https://github.com/your-username/mtf-automation-testing-api.git
cd mtf-automation-testing-api
```

Update the config.properties file: Set the base URL and any required API keys.

Run all tests using Maven:
```
mvn test
```
Run specific features:
Use TestRunner.java or tags in the feature files to run specific tests.

# Reports
Upon execution, Cucumber generates detailed HTML reports in target/cucumber-reports directory.

# Contribution
Feel free to contribute! Fork the repository, make improvements, and submit a pull request.