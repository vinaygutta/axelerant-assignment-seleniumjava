package com.axelerant.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class AccountDetailsPage extends BaseTest {

	@FindBy(css = "td#accountId")
	private WebElement accountNumberLabel;
	
	@FindBy(css = "td#accountType")
	private WebElement acountTypeLabel;
	
	@FindBy(css = "td#balance")
	private WebElement balanceLabel;
	
	@FindBy(css = "td#availableBalance")
	private WebElement availableBalanceLabel;
	
	@FindBy(css = "table#transactionTable > thead > tr > th")
	private List<WebElement> activityHeaderTable;
	
	@FindBy(css = "table#transactionTable > tbody > tr:nth-of-type(1) > td")
	private List<WebElement> activityRow1Table;
	
	@FindBy(css = "table#transactionTable > tbody > tr:nth-of-type(2) > td")
	private List<WebElement> activityRow2Table;
	
	public String getaccountNumberLabelTxt() {
		return getElementText(accountNumberLabel, "Getting account number label");
	}
	
	public String getaccountTypeLabelTxt() {
		return getElementText(acountTypeLabel, "Getting account type label");
	}
	
	public String getbalanceLabelTxt() {
		return getElementText(balanceLabel, "Getting account type label");
	}
	
	public String getAvailableBalanceLabelTxt() {
		return getElementText(availableBalanceLabel, "Getting account type label");
	}
	
	public List<String> getAccountActivityTableHeader() {
		return getTxtOfAllElementsinList(activityHeaderTable, "Getting the text of all the header columns in account activity table");
	}
	
	public List<String> getAccountActivityTableRow1() {
		return getTxtOfAllElementsinList(activityRow1Table, "Getting the text of all the first row columns in account activity table");
	}
	
	public List<String> getAccountActivityTableRow2() {
		return getTxtOfAllElementsinList(activityRow2Table, "Getting the text of all the first row columns in account activity table");
	}
	
	

}
