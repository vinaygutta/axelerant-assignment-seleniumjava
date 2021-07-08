package com.axelerant.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class LeftNavAfterLoginPage extends BaseTest {

	@FindBy(css = "div#leftPanel > .smallText")
	private WebElement welcomeLabel;
	
	@FindBy(linkText = "Open New Account")
	private WebElement openNewAccountLink;
	
	@FindBy(linkText = "Log Out")
	private WebElement logOutLink;
	
	@FindBy(linkText = "Bill Pay")
	private WebElement billPayLink;
	
	@FindBy(linkText = "Accounts Overview")
	private WebElement accountsOverviewLink;
		
	public String getWelcomeLabel() {
		return getElementText(welcomeLabel, "Getting welcome label text");
	}
	
	public LeftNavBeforeLoginPage logOut() {
		click(logOutLink, "Clicking on logout link");
		return new LeftNavBeforeLoginPage();
	}
	
	public BillPaymentPage clickBillPay() {
		click(billPayLink, "Clicking on bill pay link");
		return new BillPaymentPage();
	}
	
	public AccountsOverviewPage clickAccountsOverview() {
		click(accountsOverviewLink, "Clicking on accounts overview link");
		return new AccountsOverviewPage();
	}
	
	public OpenNewAccountPage clickOpenNewAccountLink() {
		click(openNewAccountLink, "Clicking on logout link");
		return new OpenNewAccountPage();
	}

}
