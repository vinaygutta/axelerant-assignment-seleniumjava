package com.axelerant.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.axelerant.BaseTest;

public class LeftNavBeforeLoginPage extends BaseTest {

	@FindBy(css = "input[name='username']")
	private WebElement usernameTextbox;
	
	@FindBy(css = "input[name='password']")
	private WebElement passwordTextbox;
	
	@FindBy(css = "input.button")
	private WebElement loginButton;

	public LeftNavAfterLoginPage loginWithUnamePwd(String uname, String pwd) {
		sendKeys(usernameTextbox, uname, "Entering username as - " + uname);
		sendKeys(passwordTextbox, pwd, "Entering password");
		
		
		click(loginButton, "Clicking Login button");
		return new LeftNavAfterLoginPage();
	}

}
