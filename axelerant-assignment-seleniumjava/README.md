#Axelerant assignment selenium java

Technologies used:
1. Selenium Web Driver.
2. TestNG.
3. Maven.
4. Extent Reports.
5. Log4J.
6. Applitools

How To Run:
1. Install JDK 1.8 and setup environment variables.
2. Install Maven and setup environment variables.
3. Ensure that the Para Bank portal contains a account number "12345".
4. Copy the project folder to the computer.
5. Modify the textng.xml file appropriately.
6. Ensure that API KEY for APPLITOOLS exist.
7. Open command prompt and navigate to the project folder.
8. Type "mvn clean test -Dapplitools.api.key=63pZnpOVB5A7tQW5o57MxeyBgriwL889pPqdE1hL56M111 -Dvisual.tests=true".
	-- if visual.tests flag is set to false, visual test will not be performed.

Below are the details of the test classes in the testng.xml:

com.axelerant.tests.LoginTests: This contains one test named checkLoginWithWebService.

This test will perform the below.

 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
 2. Assert that jsession id is generated in the response.
 3. Open the browser and open the home page url and visual check using APPLITOOLS.
 
com.axelerant.tests.AccountTests: This contains 3 tests.
 
checkCreationOfCheckingAccountWithAPI

 
 This test will perform the below.
 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
 2. Assert that jsession id is generated in the response.
 3. Create a CHECKING account using Webservice.
 4. Assert the response if the account type is CHECKING.
 5. Open the browser and open the home page url login.
 6. Open the newly created CHECKING account page.
 7. Perform visual check using APPLITOOLS.
 
 checkCreationOfSavingsAccountWithAPI
 
 
 This test will perform the below.
 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
 2. Assert that jsession id is generated in the response.
 3. Create a SAVINGS account using Webservice.
 4. Assert the response if the account type is SAVINGS.
 5. Open the browser and open the home page url login.
 6. Open the newly created SAVINGS account page.
 7. Perform visual check using APPLITOOLS.
 
 
 billPayFromChkAccToSavAccWithAPI
 
 
 This test will perform the below.
 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
 2. Assert that jsession id is generated in the response.
 3. Perform a Bill Pay from CHECKING account to SAVINGS account using Webservice.
 4. Assert the response if the payee name is correct.
 5. Assert the response if the from account id is correct.
 6. Open the browser and open the home page url login.
 7. Open the SAVINGS account page.
 8. Perform visual check using APPLITOOLS.
 9. Open the CHECKING account page.
 10. Perform visual check using APPLITOOLS.
 
Below is the project structure.

│   pom.xml
│   README.md
│   testng.xml
│
├───customtestoutput
│   ├───2021-07-08
│   │   ├───ExtentReports
│   │   │       Extent_2021-07-08-14-01-29.html
│   │   │
│   │   └───Screenshots
│   │       └───firefox
│   │           ├───AccountTests
│   │           │       billPayFromChkAccToSavAcc_2021-07-08-14-01-29.png
│   │           │
│   │           └───LoginTests
│   │                   checkLoginWithWebService_2021-07-08-17-26-19.png
│   │
│   └───logs
│       ├───firefox
│       │   │   application.log
│       │   │
│       │   └───2021-07-08
│       │           application-2021-07-08-1.log
│       │
│       └───msedge
│           │   application.log
│           │
│           └───2021-07-09
│                   application-2021-07-09-1.log
│
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───axelerant
│   │   │           │   BaseTest.java
│   │   │           │
│   │   │           ├───eyes
│   │   │           │       EyesManager.java
│   │   │           │
│   │   │           ├───listeners
│   │   │           │       TestListener.java
│   │   │           │
│   │   │           ├───pages
│   │   │           │       AccountDetailsPage.java
│   │   │           │       AccountOpenedSuccessPage.java
│   │   │           │       AccountsOverviewPage.java
│   │   │           │       BillPaymentCompletePage.java
│   │   │           │       BillPaymentPage.java
│   │   │           │       LeftNavAfterLoginPage.java
│   │   │           │       LeftNavBeforeLoginPage.java
│   │   │           │       OpenNewAccountPage.java
│   │   │           │
│   │   │           ├───pojos
│   │   │           │       BillPayAddress.java
│   │   │           │       BillPayData.java
│   │   │           │
│   │   │           ├───reports
│   │   │           │       ExtentReport.java
│   │   │           │
│   │   │           └───utils
│   │   │                   TestUtils.java
│   │   │
│   │   └───resources
│   │           config.properties
│   │           log4j2.xml
│   │
│   └───test
│       ├───java
│       │   └───com
│       │       └───axelerant
│       │           └───tests
│       │                   AccountTests.java
│       │                   LoginTests.java
│       │
│       └───resources
│           ├───data
│           │       loginUsers.json
│           │
│           └───strings
│                   strings.xml
