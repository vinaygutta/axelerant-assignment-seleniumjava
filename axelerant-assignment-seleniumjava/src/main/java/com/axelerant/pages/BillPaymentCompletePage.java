package com.axelerant.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class BillPaymentCompletePage extends BaseTest {

	@FindBy(css = "div[ng-show='showResult'] .title")
	private WebElement paySuccessLabel;
	
	public String getTxtPaySuccessLabel() {
		return getElementText(paySuccessLabel, "Getting pay success label");
	}

}
