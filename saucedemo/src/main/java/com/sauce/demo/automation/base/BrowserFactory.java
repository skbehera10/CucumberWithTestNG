package com.sauce.demo.automation.base;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.sauce.demo.automation.projectlib.GlobalVariables;
import com.sauce.demo.automation.utilities.AppUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

	public static WebDriver driver;
	private static Logger logger = Logger.getLogger(BrowserFactory.class);

	public static void openBrowser(String browserType) throws MalformedURLException {
		switch (browserType.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.silentOutput", "true");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
			java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
			String downloadFilepath = System.getProperty("user.dir") + "\\downloads";
			GlobalVariables.fileDownloadPath = downloadFilepath;
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("profile.default_content_setting_values.notifications", 2);
			chromePrefs.put("safebrowsing.enabled", true);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			/***------Comment the below two lines to turn off the headless mode-----****/
			// options.addArguments("headless");
			// options.addArguments("window-size=1200x600");
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(options);
			break;
		default:
			logger.info("Please provide valid browser name");
			break;
		}
	}

	/**
	 *Launch Browser
	 * @return WebDriver
	 */
	public static WebDriver createWebDriver(String browserName) {
		try {
			BrowserFactory.destroyDriver();
			AppUtility.waitForSpecificTime(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			openBrowser(browserName);
			// openBrowserNew(browserName);
			logger.info("Driver Instance Created");
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver;
	}

	public static void destroyDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		System.gc();
	}

	public static void openBrowserNew(String browserType) {
		switch (browserType.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		default:
			logger.info("Please provide valid browser name");
			break;
		}

	}
}
