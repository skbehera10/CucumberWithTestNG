package com.sauce.demo.automation.steps;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.sauce.demo.automation.base.BaseInitialiser;
import com.sauce.demo.automation.base.BrowserFactory;
import com.sauce.demo.automation.projectlib.CommonUtility;
import com.sauce.demo.automation.projectlib.GlobalVariables;
import com.sauce.demo.automation.utilities.ExcelHelper;
import com.sauce.demo.automation.utilities.PropHolder;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class TestInitializeHooks extends BaseInitialiser {

	private static Logger logger = Logger.getLogger(TestInitializeHooks.class);
	public static Scenario sc;
	
	@Before
	public void beforeScenario(Scenario scenario) throws IOException {
		String browserName = PropHolder.getBrowserName();
		logger.info("Browser Used: " + browserName);
		driver = BrowserFactory.createWebDriver(browserName);
		driver.manage().deleteAllCookies();
		CommonUtility.deleteAllDirFiles(GlobalVariables.fileDownloadPath);
		logger.info("Scenario: " + scenario.getName() + ": Started");
		GlobalVariables.currentScenarioName = scenario.getName();
		sc = scenario;		
	}

	@After
	public void afterScenario(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", screenshotName);
		}
		BrowserFactory.destroyDriver();
		ExcelHelper.closeExcelInstance();
		logger.info("Driver and Test data Instance Closed");
		logger.info("__________________________________________________________________________________________\n");
	}	
	
	public static void takeScreenshot() {		
		String screenshotName = sc.getName().replaceAll(" ", "_");
		byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		sc.attach(sourcePath, "image/png", screenshotName);
		logger.info("Screen Captured and Added to Report...");
	}
	

}
