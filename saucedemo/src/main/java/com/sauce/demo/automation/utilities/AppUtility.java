package com.sauce.demo.automation.utilities;

import org.apache.log4j.Logger;

public class AppUtility {
	
	private static Logger logger = Logger.getLogger(AppUtility.class);
	
	public static void waitForSpecificTime(long numOfSeconds) {
		try {
			Thread.sleep(numOfSeconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Exception caught while waiting for " + numOfSeconds + "seconds");
		}
	}

}
