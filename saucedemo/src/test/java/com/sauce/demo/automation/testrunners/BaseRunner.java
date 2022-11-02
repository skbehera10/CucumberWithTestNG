package com.sauce.demo.automation.testrunners;

import java.io.File;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sauce.demo.automation.utilities.ConfigHelper;
import com.sauce.demo.automation.utilities.ExcelHelper;
import com.sauce.demo.automation.utilities.PropHolder;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Test
@CucumberOptions(features = "src/test/resources/feature", 
				glue = "com.sauce.demo.automation.steps", 
				dryRun=false, 
				monochrome=true,
				plugin = { "junit:target/cucumber-report.xml", 
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", 
						"pretty","html:reports/test-report", 
						"html:target/selenium-reports"						
						} //, tags="@Test1"
				)

public class BaseRunner extends AbstractTestNGCucumberTests {
	private static Logger logger = Logger.getLogger(BaseRunner.class);

	@Parameters({ "browserName", "environment"})
	@BeforeTest
	public void setEnvDetails(@Optional("Chrome") String browserName, @Optional("qa") String environment) {
		// Delete existing screenshots
		String dir = System.getProperty("user.dir") + "\\Reports";
		File file = new File(dir);
		for (File f : file.listFiles()) {
			if (f.getName().endsWith(".png")) {
				f.delete();
			}
		}
		logger.info("Existing Report Cleared");
		PropHolder.setEnvironment(environment);
		PropHolder.setBrowserName(browserName);
		ExcelHelper.getInstance();
		ConfigHelper.getInstance();
		logger.info("Environment Setup Successful. Browser: " + browserName + ", Environment: " + environment);
	}
}
