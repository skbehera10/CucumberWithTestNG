package com.sauce.demo.automation.steps;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.sauce.demo.automation.base.BaseInitialiser;
import com.sauce.demo.automation.locators.HomePage;
import com.sauce.demo.automation.locators.LoginPage;
import com.sauce.demo.automation.utilities.FtpUtility;

import io.cucumber.java.en.*;

public class Login extends BaseInitialiser {

	public static Logger log = Logger.getLogger(Login.class);
	HomePage homePage = new HomePage(driver);
	LoginPage login = new LoginPage(driver);
	

	@Given("Saucedemo Website")
	public void Saucedemo_Website() throws Throwable {
		Properties prop = FtpUtility.readPropertiesFile("src/test/resources/config_qa.properties");
		String url = prop.getProperty("AppURL").trim();
		driver.get(url);
	}

	@When("User enters UserName & Password")
	public void user_name_password_entered_sign_in_button_clicked() throws Throwable {
		Properties prop = FtpUtility.readPropertiesFile("src/test/resources/config_qa.properties");
		String usr = prop.getProperty("UserName").trim();
		String pwd = prop.getProperty("Pwd").trim();
		login.EnterUserNameAndPassword(usr, pwd);
	}
	
	@When("Clicks on LOGIN Button")
	public void sign_in_btton_clicked() throws Exception {
		login.login();
	}

	@Then("Home page should be displayed")
	public void sign_in_screen_should_be_displayed() throws Exception {
		homePage.HomePageVerification();
	}
	
	@When("User enters {string} & {string}")
	public void user_enters(String UserName, String Password) throws Exception {
		login.EnterUserNameAndPassword(UserName, Password);
	}


	@Then("PIN Entry Screen should be displayed")
	public void pin_entry_screen_should_be_displayed() throws Exception {
	    login.PINScreenVerification();
	}

	@Then("Invalid Password message should be displayed")
	public void invalid_password_message_should_be_displayed() {
	    login.UnsuccessfulLogin();
	}
	
}
