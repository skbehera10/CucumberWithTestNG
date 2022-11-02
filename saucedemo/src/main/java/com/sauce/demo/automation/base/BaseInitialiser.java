package com.sauce.demo.automation.base;

import org.openqa.selenium.WebDriver;

import com.sauce.demo.automation.utilities.ConfigHelper;
import com.sauce.demo.automation.utilities.ExcelHelper;

public class BaseInitialiser {
	
	public static WebDriver driver;
	public static ExcelHelper testDataHelper = ExcelHelper.getInstance();
	public ConfigHelper configHelper = ConfigHelper.getInstance();
}
