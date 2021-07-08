package com.axelerant.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.axelerant.BaseTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ExtentReport {
	static ExtentReports extent;
	final static String folderpath = System.getProperty("outputFolder") + File.separator + new BaseTest().getDateStr()
			+ File.separator + "ExtentReports";
	final static String filePath = folderpath + File.separator + "Extent_" + new BaseTest().getDateTime()+".html";
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			ExtentSparkReporter html = new ExtentSparkReporter(filePath);
			html.config().setDocumentTitle("Test Results");
			html.config().setReportName("Axelerant Tests");
			html.config().setTheme(Theme.DARK);
			extent = new ExtentReports();
			extent.attachReporter(html);
		}

		return extent;
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = getReporter().createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}
}
