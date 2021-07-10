package com.axelerant.pages;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class BillPaymentPage extends BaseTest {

	@FindBy(css = "input[name='payee.name']")
	private WebElement payeeNameTextBox;

	@FindBy(css = "input[name='payee.address.street']")
	private WebElement payeeAddrTextBox;

	@FindBy(css = "input[name='payee.address.city']")
	private WebElement cityTextBox;

	@FindBy(css = "input[name='payee.address.state']")
	private WebElement stateTextBox;

	@FindBy(css = "input[name='payee.address.zipCode']")
	private WebElement zipTextBox;

	@FindBy(css = "input[name='payee.phoneNumber']")
	private WebElement phoneTextBox;

	@FindBy(css = "input[name='payee.accountNumber']")
	private WebElement accountTextBox;

	@FindBy(css = "input[name='verifyAccount']")
	private WebElement verifyAccountTextBox;

	@FindBy(css = "input[name='amount']")
	private WebElement amountTextBox;

	@FindBy(css = "select[name='fromAccountId']")
	private WebElement fromAccountSelect;

	@FindBy(css = "[value='Send Payment']")
	private WebElement sendButton;

	public BillPaymentCompletePage billPay(HashMap<String, String> data) {

		sendKeys(payeeNameTextBox, data.get("payee"), "Entering payee name as " + data.get("payee"));
		sendKeys(payeeAddrTextBox, data.get("addr"), "Entering address as " + data.get("addr"));

		sendKeys(cityTextBox, data.get("city"), "Entering city as " + data.get("city"));
		sendKeys(stateTextBox, data.get("state"), "Entering state as " + data.get("state"));
		sendKeys(zipTextBox, data.get("zip"), "Entering zip as " + data.get("zip"));
		sendKeys(phoneTextBox, data.get("phone"), "Entering phone as " + data.get("phone"));
		sendKeys(accountTextBox, data.get("account"), "Entering account as " + data.get("account"));
		sendKeys(verifyAccountTextBox, data.get("account"), "Entering verify account as " + data.get("account"));
		sendKeys(amountTextBox, data.get("amount"), "Entering amount as " + data.get("amount"));

		selectByVisibleText(fromAccountSelect, data.get("fromaccount"),
				"Selecting from account as " + data.get("fromaccount"));
		
		click(sendButton, "Clicking send button");

		return new BillPaymentCompletePage();
	}

}
