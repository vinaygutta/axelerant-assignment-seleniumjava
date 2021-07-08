package com.axelerant.tests;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
import com.axelerant.utils.TestUtils;
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

	@Test(priority = 1)
	public void checkCreationOfCheckingAccount() {

		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		String act_welcomelabel = leftNavAfterLoginPage.getWelcomeLabel();
		String exp_welcomelabel = getStrings().get("welcome_label");

		Assert.assertEquals(act_welcomelabel, exp_welcomelabel);
		waitForSeconds(5);

		openNewAccountPage = leftNavAfterLoginPage.clickOpenNewAccountLink();
		waitForSeconds(5);
		accountOpenedSuccessPage = openNewAccountPage.openNewAccount("CHECKING");

		String act_congratsTxt = accountOpenedSuccessPage.getCongratsTxt();
		chkAccountNumber = accountOpenedSuccessPage.getCreatedAccountNumber();
		String exp_congratsTxt = getStrings().get("congrats_label");

		Assert.assertEquals(act_congratsTxt, exp_congratsTxt);
		waitForSeconds(5);

		accountDetailsPage = accountOpenedSuccessPage.clickNewAccountNumberLink();

		waitForSeconds(5);

		String act_accountNbr = accountDetailsPage.getaccountNumberLabelTxt();
		String act_accountType = accountDetailsPage.getaccountTypeLabelTxt();
		String act_balance = accountDetailsPage.getbalanceLabelTxt();
		String act_availbalance = accountDetailsPage.getAvailableBalanceLabelTxt();

		List<String> act_tab_header = accountDetailsPage.getAccountActivityTableHeader();
		List<String> act_tab_row1 = accountDetailsPage.getAccountActivityTableRow1();

		String exp_accountNbr = chkAccountNumber;
		String exp_accountType = getStrings().get("accountType_chk_label");
		String exp_balance = getStrings().get("balance_label");
		String exp_availbalance = getStrings().get("availableBalance_label");

		Assert.assertEquals(act_accountNbr, exp_accountNbr);
		Assert.assertEquals(act_accountType, exp_accountType);
		Assert.assertEquals(act_balance, exp_balance);
		Assert.assertEquals(act_availbalance, exp_availbalance);

		Assert.assertEquals(act_tab_header.get(0), getStrings().get("header1_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(1), getStrings().get("header2_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(2), getStrings().get("header3_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(3), getStrings().get("header4_accountactivity_table"));

		Assert.assertEquals(act_tab_row1.get(0), utils.dateTo_mm_dd_yyyy(new Date()));
		Assert.assertEquals(act_tab_row1.get(1), getStrings().get("row1col2_accountactivity_table"));
		Assert.assertEquals(act_tab_row1.get(2), "");
		Assert.assertEquals(act_tab_row1.get(3), getStrings().get("row1col4_accountactivity_table"));

		leftNavAfterLoginPage.logOut();
	}

	@Test(priority = 2)
	public void checkCreationOfSavingsAccount() {

		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		String act_welcomelabel = leftNavAfterLoginPage.getWelcomeLabel();
		String exp_welcomelabel = getStrings().get("welcome_label");

		Assert.assertEquals(act_welcomelabel, exp_welcomelabel);
		waitForSeconds(5);

		openNewAccountPage = leftNavAfterLoginPage.clickOpenNewAccountLink();
		waitForSeconds(5);
		accountOpenedSuccessPage = openNewAccountPage.openNewAccount("SAVINGS");
		String act_congratsTxt = accountOpenedSuccessPage.getCongratsTxt();
		savAccountNumber = accountOpenedSuccessPage.getCreatedAccountNumber();
		String exp_congratsTxt = getStrings().get("congrats_label");

		Assert.assertEquals(act_congratsTxt, exp_congratsTxt);
		waitForSeconds(5);

		accountDetailsPage = accountOpenedSuccessPage.clickNewAccountNumberLink();

		waitForSeconds(5);

		String act_accountNbr = accountDetailsPage.getaccountNumberLabelTxt();
		String act_accountType = accountDetailsPage.getaccountTypeLabelTxt();
		String act_balance = accountDetailsPage.getbalanceLabelTxt();
		String act_availbalance = accountDetailsPage.getAvailableBalanceLabelTxt();

		List<String> act_tab_header = accountDetailsPage.getAccountActivityTableHeader();
		List<String> act_tab_row1 = accountDetailsPage.getAccountActivityTableRow1();

		String exp_accountNbr = savAccountNumber;
		String exp_accountType = getStrings().get("accountType_sav_label");
		String exp_balance = getStrings().get("balance_label");
		String exp_availbalance = getStrings().get("availableBalance_label");

		Assert.assertEquals(act_accountNbr, exp_accountNbr);
		Assert.assertEquals(act_accountType, exp_accountType);
		Assert.assertEquals(act_balance, exp_balance);
		Assert.assertEquals(act_availbalance, exp_availbalance);

		Assert.assertEquals(act_tab_header.get(0), getStrings().get("header1_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(1), getStrings().get("header2_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(2), getStrings().get("header3_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(3), getStrings().get("header4_accountactivity_table"));

		Assert.assertEquals(act_tab_row1.get(0), utils.dateTo_mm_dd_yyyy(new Date()));
		Assert.assertEquals(act_tab_row1.get(1), getStrings().get("row1col2_accountactivity_table"));
		Assert.assertEquals(act_tab_row1.get(2), "");
		Assert.assertEquals(act_tab_row1.get(3), getStrings().get("row1col4_accountactivity_table"));

		leftNavAfterLoginPage.logOut();
	}

	@Test(priority = 3)
	public void billPayFromChkAccToSavAcc() {

		Faker fake = new Faker();
		String fn = fake.address().firstName();
		HashMap<String, String> data = new HashMap<>();

		leftNavAfterLoginPage = leftNavBeforeLoginPage.loginWithUnamePwd(
				loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

		String act_welcomelabel = leftNavAfterLoginPage.getWelcomeLabel();
		String exp_welcomelabel = getStrings().get("welcome_label");

		Assert.assertEquals(act_welcomelabel, exp_welcomelabel);
		waitForSeconds(5);

		billPaymentPage = leftNavAfterLoginPage.clickBillPay();
		waitForSeconds(2);

		data.put("payee", fn);
		data.put("addr", fake.address().streetName());
		data.put("city", fake.address().city());
		data.put("state", fake.address().state());
		data.put("zip", fake.address().zipCode());
		data.put("phone", fake.phoneNumber().cellPhone());
		data.put("account", savAccountNumber);
		data.put("amount", "200");
		data.put("fromaccount", chkAccountNumber);

		billPaymentCompletePage = billPaymentPage.billPay(data);

		String act_billPaymsg = billPaymentCompletePage.getTxtPaySuccessLabel();
		String exp_billPaymsg = getStrings().get("pay_complete_label");

		Assert.assertEquals(act_billPaymsg, exp_billPaymsg);

		accountOverviewPage = leftNavAfterLoginPage.clickAccountsOverview();

		/*
		 * The below checks the details of the checking account number after bill pay
		 * successful
		 */

		accountDetailsPage = accountOverviewPage.clickAccountNbr(chkAccountNumber);

		waitForSeconds(5);

		String act_accountNbr = accountDetailsPage.getaccountNumberLabelTxt();
		String act_accountType = accountDetailsPage.getaccountTypeLabelTxt();
		String act_balance = accountDetailsPage.getbalanceLabelTxt();
		String act_availbalance = accountDetailsPage.getAvailableBalanceLabelTxt();

		List<String> act_tab_header = accountDetailsPage.getAccountActivityTableHeader();
		List<String> act_tab_row1 = accountDetailsPage.getAccountActivityTableRow1();
		List<String> act_tab_row2 = accountDetailsPage.getAccountActivityTableRow2();

		String exp_accountNbr = chkAccountNumber;
		String exp_accountType = getStrings().get("accountType_chk_label");
		String exp_balance = "-$100.00";
		String exp_availbalance = "$0.00";

		Assert.assertEquals(act_accountNbr, exp_accountNbr);
		Assert.assertEquals(act_accountType, exp_accountType);
		Assert.assertEquals(act_balance, exp_balance);
		Assert.assertEquals(act_availbalance, exp_availbalance);

		Assert.assertEquals(act_tab_header.get(0), getStrings().get("header1_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(1), getStrings().get("header2_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(2), getStrings().get("header3_accountactivity_table"));
		Assert.assertEquals(act_tab_header.get(3), getStrings().get("header4_accountactivity_table"));

		Assert.assertEquals(act_tab_row1.get(0), utils.dateTo_mm_dd_yyyy(new Date()));
		Assert.assertEquals(act_tab_row1.get(1), getStrings().get("row1col2_accountactivity_table"));
		Assert.assertEquals(act_tab_row1.get(2), "");
		Assert.assertEquals(act_tab_row1.get(3), getStrings().get("row1col4_accountactivity_table"));

		Assert.assertEquals(act_tab_row2.get(0), utils.dateTo_mm_dd_yyyy(new Date()));
		Assert.assertEquals(act_tab_row2.get(1), "Bill Payment to " + fn);
		Assert.assertEquals(act_tab_row2.get(2), "$200.00");
		Assert.assertEquals(act_tab_row2.get(3), "");

		accountOverviewPage = leftNavAfterLoginPage.clickAccountsOverview();

		/*
		 * The below checks the details of the savings account number after bill pay
		 * successful
		 */

		accountDetailsPage = accountOverviewPage.clickAccountNbr(savAccountNumber);

		waitForSeconds(5);

		String act_savaccountNbr = accountDetailsPage.getaccountNumberLabelTxt();
		String act_savaccountType = accountDetailsPage.getaccountTypeLabelTxt();
		String act_savbalance = accountDetailsPage.getbalanceLabelTxt();
		String act_savavailbalance = accountDetailsPage.getAvailableBalanceLabelTxt();

		List<String> act_savtab_header = accountDetailsPage.getAccountActivityTableHeader();
		List<String> act_savtab_row1 = accountDetailsPage.getAccountActivityTableRow1();
		List<String> act_savtab_row2 = accountDetailsPage.getAccountActivityTableRow2();

		String exp_savaccountNbr = savAccountNumber;
		String exp_savaccountType = getStrings().get("accountType_sav_label");
		String exp_savbalance = "$300.00";
		String exp_savavailbalance = "$300.00";

		Assert.assertEquals(act_savaccountNbr, exp_savaccountNbr);
		Assert.assertEquals(act_savaccountType, exp_savaccountType);
		Assert.assertEquals(act_savbalance, exp_savbalance);
		Assert.assertEquals(act_savavailbalance, exp_savavailbalance);

		Assert.assertEquals(act_savtab_header.get(0), getStrings().get("header1_accountactivity_table"));
		Assert.assertEquals(act_savtab_header.get(1), getStrings().get("header2_accountactivity_table"));
		Assert.assertEquals(act_savtab_header.get(2), getStrings().get("header3_accountactivity_table"));
		Assert.assertEquals(act_savtab_header.get(3), getStrings().get("header4_accountactivity_table"));

		Assert.assertEquals(act_savtab_row1.get(0), utils.dateTo_mm_dd_yyyy(new Date()));
		Assert.assertEquals(act_savtab_row1.get(1), getStrings().get("row1col2_accountactivity_table"));
		Assert.assertEquals(act_savtab_row1.get(2), "");
		Assert.assertEquals(act_savtab_row1.get(3), getStrings().get("row1col4_accountactivity_table"));

		Assert.assertEquals(act_savtab_row2.get(0), utils.dateTo_mm_dd_yyyy(new Date()));
		Assert.assertEquals(act_savtab_row2.get(1), "Bill Payment from " + fn);
		Assert.assertEquals(act_savtab_row2.get(2), "");
		Assert.assertEquals(act_savtab_row2.get(3), "$200.00");

		leftNavAfterLoginPage.logOut();
	}
}
