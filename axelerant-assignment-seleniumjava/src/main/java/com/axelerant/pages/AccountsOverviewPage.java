package com.axelerant.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class AccountsOverviewPage extends BaseTest {

	@FindBy(css = "table#accountTable tbody tr > td > a")
	private List<WebElement> allAccountNumbers;
	
	public AccountDetailsPage clickAccountNbr(String account) {
		
		clickElementInList(allAccountNumbers, account, "Clicking on element " + account);
		return new AccountDetailsPage();
	}

}
