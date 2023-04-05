Readme


This project demonstrates how to use Selenium WebDriver and REST Assured to automate web browsers and perform REST API testing, respectively.

Configuration Properties
The following properties can be configured in the config.properties file:

browser: The web browser to use. Currently, only chrome is supported.
url: The URL to open in the browser.
screenSize: The screen size to use. Currently, only default is supported.
JUnit Platform Properties
The following properties are set in the junit-platform.properties file:

junit.jupiter.execution.parallel.enabled: Enables parallel test execution.
junit.jupiter.execution.parallel.mode.default: Sets the default parallel test execution mode to concurrent.
junit.jupiter.testinstance.lifecycle.default: Sets the default lifecycle for test instances to per_class.
junit.jupiter.execution.parallel.mode.classes.default: Sets the default mode for parallel execution of test classes to same_thread.
Maven Dependencies
This project uses the following Maven dependencies:

Selenium WebDriver: Used to automate web browsers.
REST Assured: Used for REST API testing.
JUnit Jupiter: Used for writing and running tests.
SLF4J: Used for logging.
Logback: Used for logging.
Extent Reports: Used for generating HTML reports.
Lombok: Used for generating boilerplate code.
pom.xml
The pom.xml file contains the Maven dependencies required for this project to run.

Note that you will need to have Maven installed on your machine to build and run this project.

To build and run the project, run the following command from the project directory:

Copy code
mvn clean install
This will compile the project and run the tests.
