package com.axelerant.tests;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import com.axelerant.BaseTest;
import com.axelerant.pages.LeftNavAfterLoginPage;
import com.axelerant.pages.LeftNavBeforeLoginPage;
import com.axelerant.utils.TestUtils;

import java.io.InputStream;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
	LeftNavAfterLoginPage leftNavAfterLoginPage;
	LeftNavBeforeLoginPage leftNavBeforeLoginPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();

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
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		utils.log().info(
				"\n\n" + "**********************\n starting test:" + m.getName() + "\n**********************" + "\n");
		getDriver().get(getProps().getProperty("siteURL"));
		getDriver().manage().window().maximize();
		leftNavBeforeLoginPage = new LeftNavBeforeLoginPage();
	}

	@AfterMethod
	public void afterMethod() {
	}

	@Test
	public void checkLoginLogout() {
		
		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));
		String act_welcomelabel = leftNavAfterLoginPage.getWelcomeLabel();
		String exp_welcomelabel = getStrings().get("welcome_label");
		

		Assert.assertEquals(act_welcomelabel, exp_welcomelabel);
		waitForSeconds(5);
		leftNavAfterLoginPage.logOut();
	}
}
