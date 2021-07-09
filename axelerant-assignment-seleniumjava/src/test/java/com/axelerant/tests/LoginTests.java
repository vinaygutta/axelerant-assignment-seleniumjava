package com.axelerant.tests;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.axelerant.BaseTest;
import com.axelerant.pages.LeftNavAfterLoginPage;
import com.axelerant.pages.LeftNavBeforeLoginPage;
import com.axelerant.utils.TestUtils;

public class LoginTests extends BaseTest {
	LeftNavAfterLoginPage leftNavAfterLoginPage;
	LeftNavBeforeLoginPage leftNavBeforeLoginPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();
	EyesRunner runner;
	Eyes eyes;
	static BatchInfo batch;
	final boolean runWithEyes = false;

	@BeforeClass
	public void beforeClass() throws Exception {
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
				datais.close();
			}
		}

		if (runWithEyes) {
			batch = new BatchInfo("Demo batch");
		}
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		utils.log().info(
				"\n\n" + "**********************\n starting test:" + m.getName() + "\n**********************" + "\n");

		if (runWithEyes) {
			runner = new ClassicRunner();
			eyes = new Eyes(runner);
			if (isNullOrEmpty(System.getProperty("APPLITOOLS_API_KEY"))) {
				utils.log().info(new RuntimeException(
						"No API Key found; Please set environment variable 'APPLITOOLS_API_KEY'."));
				throw new RuntimeException("No API Key found; Please set environment variable 'APPLITOOLS_API_KEY'.");
			}
			eyes.setApiKey(System.getProperty("APPLITOOLS_API_KEY"));
			eyes.setBatch(batch);
		}
	}

	@AfterMethod
	public void afterMethod() {
		if (runWithEyes) {
			eyes.abortIfNotClosed();			
			TestResultsSummary allTestResults = runner.getAllTestResults();			
			utils.log().info(allTestResults);
		}
	}

	@Test
	public void checkLoginWithWebService() {

		String jseid = utils.loginViaWebService("application/x-www-form-urlencoded",
				"https://parabank.parasoft.com/parabank/login.htm", "POST",
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		Assert.assertEquals(jseid.equalsIgnoreCase(""), false);
		waitForSeconds(5);

		if (runWithEyes) {
			eyes.open(getDriver(), "Demo App", "Login With WebService Test", new RectangleSize(1280, 657));
		}

		getDriver().get(getProps().getProperty("siteURL") + ";jsessionid=" + jseid);
		getDriver().manage().window().maximize();

		if (runWithEyes) {
			eyes.checkWindow("Logged in page");
			eyes.closeAsync();
		}
	}
}
