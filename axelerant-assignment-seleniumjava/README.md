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
3. Copy the project folder to the computer.
4. Open command prompt and navigate to the project folder.
5. Type "mvn clean test".

Below Test Cases are Automated:

URL: https://parabank.parasoft.com/parabank/index.htm

TC1: 
1. Open the above URL.
2. Enter appropriate username and password and Login.
3. Logout.

Expected Result: Login Logout should be successful.

TC2:
1. Open the above URL.
2. Enter appropriate username and password and Login.
3. Click on Open New Account link.
4. Choose CHECKING as type of account.
5. Click on open new account button.
6. Open the account details of newly created account.

Expected Result:
a. The new account creation should be successful.
b. On the account details page, the account number should be correct one.
c. On the account details page, the account type should be CHECKING.
d. On the account details page, the account balance should be $100.00.
e. On the account details page, the account available balance should be $100.00.
f. The date and transaction details should be appropriate.

TC3:
1. Open the above URL.
2. Enter appropriate username and password and Login.
3. Click on Open New Account link.
4. Choose SAVINGS as type of account.
5. Click on open new account button.
6. Open the account details of newly created account.

Expected Result:
a. The new account creation should be successful.
b. On the account details page, the account number should be correct one.
c. On the account details page, the account type should be SAVINGS.
d. On the account details page, the account balance should be $100.00.
e. On the account details page, the account available balance should be $100.00.
f. The date and transaction details should be appropriate.

TC4:
1. Open the above URL.
2. Enter appropriate username and password and Login.
3. Click on Bill Pay link.
4. Enter appropriate details and choose accounts from TC2 and TC3 and amount $200.
5. Click Send Payment.
6. Check account details of each account.

Expected Result:
a. The bill pay should be successful.
b. On the first account details the balance should be $300.
c. On the second account details the balance should be -$100.
d. The transaction details should be appropriate with date and credit, debit details.