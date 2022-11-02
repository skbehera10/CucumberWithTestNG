package com.sauce.demo.automation.utilities;

public class PropHolder {

	private static String environment;
	private static String browserName;

	public static String getEnvironment() {
		return environment;
	}

	public static void setEnvironment(String env) {
		environment = env;
	}

	public static String getBrowserName() {
		return browserName;
	}

	public static void setBrowserName(String browser) {
		browserName = browser;
	}
}
