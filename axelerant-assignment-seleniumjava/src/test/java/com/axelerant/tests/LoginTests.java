package com.axelerant.tests;

import java.io.IOException;
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
import com.axelerant.eyes.EyesManager;
import com.axelerant.pages.LeftNavAfterLoginPage;
import com.axelerant.pages.LeftNavBeforeLoginPage;
import com.axelerant.utils.TestUtils;

public class LoginTests extends BaseTest {
	LeftNavAfterLoginPage leftNavAfterLoginPage;
	LeftNavBeforeLoginPage leftNavBeforeLoginPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();

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
		
	

		 getEyesManager().setBatchName("Login Tests batch");

	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		utils.log().info(
				"\n\n" + "**********************\n starting test:" + m.getName() + "\n**********************" + "\n");


		
	}

	@AfterMethod
	public void afterMethod() {

		getEyesManager().abort();


	}

	/*
	 * This test will perform the below.
	 * 1. Login to the portal using webservice/ajax mode and extract the JSESSION ID.
	 * 2. Assert that jsession id is generated in the response.
	 * 3. Open the browser and open the home page url and visual check using APPLITOOLS.
	 */
	@Test
	public void checkLoginWithWebService() {

		String jseid = utils.loginViaWebService("application/x-www-form-urlencoded",
				getProps().getProperty("wsLoginURL"), "POST",
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		Assert.assertEquals(jseid.equalsIgnoreCase(""), false);
		waitForSeconds(5);

		

		getDriver().get(getProps().getProperty("siteURL") + ";jsessionid=" + jseid);
		getDriver().manage().window().maximize();

		getEyesManager().validateWindow();


	}
}
