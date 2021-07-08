package com.axelerant.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class AccountOpenedSuccessPage extends BaseTest {

	@FindBy(css = ".ng-scope > p:nth-of-type(1)")
	private WebElement congratsLabel;
	
	@FindBy(css = "a#newAccountId")
	private WebElement newAccountIdLink;
	
	public String getCongratsTxt() {
		return getElementText(congratsLabel, "Getting contrats label");
	}
	
	public String getCreatedAccountNumber() {
		return getElementText(newAccountIdLink, "Getting new account number");
	}
	
	public AccountDetailsPage clickNewAccountNumberLink() {
		click(newAccountIdLink, "Clicking on newly created account id");
		return new AccountDetailsPage();
	}

}
