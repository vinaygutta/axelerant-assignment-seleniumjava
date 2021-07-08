package com.axelerant.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class OpenNewAccountPage extends BaseTest {

	@FindBy(css = "select#type")
	private WebElement accountTypeListBox;
	
	@FindBy(css = "input.button")
	private WebElement openNewAccountButton;
	
	public AccountOpenedSuccessPage openNewAccount(String accountType) {
		selectByVisibleText(accountTypeListBox, accountType, "Choosing account type as " + accountType);
		click(openNewAccountButton, "Clicking on open new account button");
		return new AccountOpenedSuccessPage();
	}

}
