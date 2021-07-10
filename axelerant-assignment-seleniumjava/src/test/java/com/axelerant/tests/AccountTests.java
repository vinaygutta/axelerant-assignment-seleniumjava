package com.axelerant.tests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.axelerant.BaseTest;
import com.axelerant.pages.AccountDetailsPage;
import com.axelerant.pages.AccountOpenedSuccessPage;
import com.axelerant.pages.AccountsOverviewPage;
import com.axelerant.pages.BillPaymentCompletePage;
import com.axelerant.pages.BillPaymentPage;
import com.axelerant.pages.LeftNavAfterLoginPage;
import com.axelerant.pages.LeftNavBeforeLoginPage;
import com.axelerant.pages.OpenNewAccountPage;
import com.axelerant.pojos.BillPayAddress;
import com.axelerant.pojos.BillPayData;
import com.axelerant.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public class AccountTests extends BaseTest {
	LeftNavAfterLoginPage leftNavAfterLoginPage;
	LeftNavBeforeLoginPage leftNavBeforeLoginPage;
	AccountOpenedSuccessPage accountOpenedSuccessPage;
	OpenNewAccountPage openNewAccountPage;
	AccountDetailsPage accountDetailsPage;
	BillPaymentPage billPaymentPage;
	BillPaymentCompletePage billPaymentCompletePage;
	AccountsOverviewPage accountOverviewPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();
	String savAccountNumber;
	String chkAccountNumber;

	@BeforeClass
	public void initLogin() {
		InputStream datais = null;
		try {
			String dataFileName = "data/loginUsers.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (datais != null) {
				try {
					datais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		getEyesManager().setBatchName("Account Tests batch");

	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		utils.log().info(
				"\n\n" + "**********************\n starting test:" + m.getName() + "\n**********************" + "\n");

		leftNavBeforeLoginPage = new LeftNavBeforeLoginPage();

	}

	@AfterMethod
	public void afterMethod() {

		getEyesManager().abort();

	}
	
	/*
	 * This test will perform the below.
	 * 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
	 * 2. Assert that jsession id is generated in the response.
	 * 3. Create a CHECKING account using Webservice.
	 * 4. Assert the response if the account type is CHECKING.
	 * 5. Open the browser and open the home page url login.
	 * 6. Open the newly created CHECKING account page.
	 * 7. Perform visual check using APPLITOOLS.
	 */

	@Test(priority = 1)
	public void checkCreationOfCheckingAccountWithAPI() {

		String jseid = utils.loginViaWebService("application/x-www-form-urlencoded",
				getProps().getProperty("wsLoginURL"), "POST",
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		Assert.assertEquals(jseid.equalsIgnoreCase(""), false);
		waitForSeconds(5);

		String response = utils.createAccountWebService(jseid,
				getProps().getProperty("wsbURL")+"/createAccount?customerId=12212&newAccountType=0&fromAccountId=12345",
				"POST");
		
		Assert.assertNotEquals(response,"Error");

		JSONObject accountCreated = new JSONObject(response);

		Assert.assertEquals(accountCreated.getString("type"), "CHECKING");
		chkAccountNumber = accountCreated.get("id").toString();

		getDriver().get(getProps().getProperty("siteURL"));
		getDriver().manage().window().maximize();

		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));
		leftNavAfterLoginPage.clickAccountsOverview().clickAccountNbr(chkAccountNumber);

		waitForSeconds(5);

		getEyesManager().validateWindow();

		leftNavAfterLoginPage.logOut();
	}
	
	/*
	 * This test will perform the below.
	 * 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
	 * 2. Assert that jsession id is generated in the response.
	 * 3. Create a SAVINGS account using Webservice.
	 * 4. Assert the response if the account type is SAVINGS.
	 * 5. Open the browser and open the home page url login.
	 * 6. Open the newly created SAVINGS account page.
	 * 7. Perform visual check using APPLITOOLS.
	 */

	@Test(priority = 2)
	public void checkCreationOfSavingsAccountWithAPI() {

		String jseid = utils.loginViaWebService("application/x-www-form-urlencoded",
				getProps().getProperty("wsLoginURL"), "POST",
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		Assert.assertEquals(jseid.equalsIgnoreCase(""), false);
		waitForSeconds(5);

		String response = utils.createAccountWebService(jseid,
				getProps().getProperty("wsbURL")+"/createAccount?customerId=12212&newAccountType=1&fromAccountId=12345",
				"POST");
		
		Assert.assertNotEquals(response,"Error");

		JSONObject accountCreated = new JSONObject(response);

		Assert.assertEquals(accountCreated.getString("type"), "SAVINGS");
		savAccountNumber = accountCreated.get("id").toString();

		getDriver().get(getProps().getProperty("siteURL"));
		getDriver().manage().window().maximize();

		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));
		leftNavAfterLoginPage.clickAccountsOverview().clickAccountNbr(savAccountNumber);

		waitForSeconds(5);

		getEyesManager().validateWindow();

		leftNavAfterLoginPage.logOut();
	}
	
	/*
	 * This test will perform the below.
	 * 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
	 * 2. Assert that jsession id is generated in the response.
	 * 3. Perform a Bill Pay from CHECKING account to SAVINGS account using Webservice.
	 * 4. Assert the response if the payee name is correct.
	 * 5. Assert the response if the from account id is correct.
	 * 6. Open the browser and open the home page url login.
	 * 7. Open the SAVINGS account page.
	 * 8. Perform visual check using APPLITOOLS.
	 * 9. Open the CHECKING account page.
	 * 10. Perform visual check using APPLITOOLS.
	 */

	@Test(priority = 3)
	public void billPayFromChkAccToSavAccWithAPI() {

		Faker fake = new Faker();
		String fn = fake.address().firstName();

		String jseid = utils.loginViaWebService("application/x-www-form-urlencoded",
				getProps().getProperty("wsLoginURL"), "POST",
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		Assert.assertEquals(jseid.equalsIgnoreCase(""), false);
		waitForSeconds(5);

		BillPayAddress billPayAddress = new BillPayAddress();

		billPayAddress.setCity(fake.address().city());
		billPayAddress.setState(fake.address().state());
		billPayAddress.setStreet(fake.address().streetName());
		billPayAddress.setZipCode(fake.address().zipCode());

		BillPayData billPayData = new BillPayData();
		billPayData.setAddress(billPayAddress);
		billPayData.setName(fn);
		billPayData.setAccountNumber(savAccountNumber);
		billPayData.setPhoneNumber(fake.phoneNumber().cellPhone());

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(billPayData);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String response = utils.billPayWebService(jseid,
				getProps().getProperty("wsbURL")+"/billpay?accountId=" + chkAccountNumber
						+ "&amount=200",
				"POST", jsonInString);

		Assert.assertNotEquals(response,"Error");
		
		JSONObject billPayresponse = new JSONObject(response);
		
		Assert.assertEquals(billPayresponse.getString("payeeName"), fn);
		Assert.assertEquals(billPayresponse.get("accountId").toString(), chkAccountNumber);
		
		getDriver().get(getProps().getProperty("siteURL"));
		getDriver().manage().window().maximize();
		
		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		leftNavAfterLoginPage.clickAccountsOverview().clickAccountNbr(savAccountNumber);

		waitForSeconds(5);

		getEyesManager().validateWindow();

		leftNavAfterLoginPage.clickAccountsOverview().clickAccountNbr(chkAccountNumber);

		waitForSeconds(5);

		getEyesManager().validateWindow();

		leftNavAfterLoginPage.logOut();
	}
}
