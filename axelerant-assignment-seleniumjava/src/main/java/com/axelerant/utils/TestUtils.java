package com.axelerant.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.Status;
import com.axelerant.reports.ExtentReport;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestUtils {
	public static final long WAIT = 30;
	private final String KILL = "taskkill /F /IM ";

	public HashMap<String, String> parseStringXML(InputStream file) throws Exception {
		HashMap<String, String> stringMap = new HashMap<String, String>();
		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Build Document
		Document document = builder.parse(file);

		// Normalize the XML Structure; It's just too important !!
		document.getDocumentElement().normalize();

		// Here comes the root node
		// Element root = document.getDocumentElement();

		// Get all elements
		NodeList nList = document.getElementsByTagName("string");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				// Store each element key value in map
				stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
			}
		}
		return stringMap;
	}

	public String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String dateStr() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String dateTo_mm_dd_yyyy(Date inputDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("PST"));
		String strDate = formatter.format(inputDate);

		String datNumb = strDate.split("/")[1];
		String mnthNumb = strDate.split("/")[0];
		String yreNumb = strDate.split("/")[2];

		return mnthNumb + "-" + datNumb + "-" + yreNumb;
	}

	public Logger log() {
		return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}

	public String loginViaWebService(String contentType, String url, String method, String uname, String password) {
		log().info("Logging to " + url);
		ExtentReport.getTest().log(Status.INFO, "Logging to " + url);
		String jsid = "";
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse(contentType);
		RequestBody body = RequestBody.create(mediaType, "username=" + uname + "&password=" + password);
		Request request = new Request.Builder().url(url).method(method, body).addHeader("Content-Type", contentType)
				.build();
		try {
			Response response = client.newCall(request).execute();
			String responseStr = response.toString();
			log().info("Logging response " + response.toString());
			ExtentReport.getTest().log(Status.INFO, "Logging response " + response.toString());
			int jsStart = response.toString().indexOf("jsessionid");

			jsid = responseStr.substring(jsStart + 11, responseStr.length() - 1);
			log().info("JSESSION ID is " + jsid);
			ExtentReport.getTest().log(Status.INFO, "JSESSION ID is " + jsid);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return jsid;
	}

	public String createAccountWebService(String jsessionid, String url, String method) {
		String responseStr = "";
		log().info("Creating account from " + url);
		ExtentReport.getTest().log(Status.INFO, "Creating account from " + url);
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(url).method(method, body)
				.addHeader("Cookie", "JSESSIONID=" + jsessionid).build();
		try {
			Response response = client.newCall(request).execute();
			if (response.code()!=200) {
				responseStr = "Error";
			} else {
			responseStr = response.body().string();
			}
			log().info("Logging response " + responseStr);
			ExtentReport.getTest().log(Status.INFO, "Logging response " + responseStr);
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "Error";
		}
		return responseStr;
	}

	public String billPayWebService(String jsessionid, String url, String method, String jsonData) {
		String responseStr = "";
		log().info("Bill Pay from " + url);
		ExtentReport.getTest().log(Status.INFO, "Bill Pay from " + url);
		
		log().info("Bill Pay service payload " + jsonData);
		ExtentReport.getTest().log(Status.INFO, "Bill Pay service payload " + jsonData);

		OkHttpClient client2 = new OkHttpClient().newBuilder().build();
		MediaType mediaType2 = MediaType.parse("application/json");
		RequestBody body2 = RequestBody.create(mediaType2,
				jsonData);
		Request request2 = new Request.Builder()
				.url(url)
				.method(method, body2)
				.addHeader("Cookie", "JSESSIONID="+jsessionid)
				.addHeader("Content-Type", "application/json")
				.build();
		try {
			Response response = client2.newCall(request2).execute();
			if (response.code()!=200) {
				responseStr = "Error";
			} else {
			responseStr = response.body().string();
			}
			log().info("Logging response " + responseStr);
			ExtentReport.getTest().log(Status.INFO, "Logging response " + responseStr);
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "Error";
		}

		return responseStr;
	}

	public void killProcess(String serviceName) {

		try {
			Runtime.getRuntime().exec(KILL + serviceName);
			Thread.sleep(5000);
			// System.out.println(serviceName + " killed successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
