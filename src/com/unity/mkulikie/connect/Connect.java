package com.unity.mkulikie.connect;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;

public class Connect {
	static WebDriver driver;
    static Wait<WebDriver> wait;
    String deviceId = null;
	public Connect(String nazwa)
	{
		
		if(nazwa.equals("1"))
		try {
			getDevicesId();
			setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setup() {
	try{
		System.setProperty("webdriver.chrome.driver", "C:/Users/mkulikie/Downloads/selenium-java-2.53.1/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("androidDeviceSerial", deviceId);
        driver = new ChromeDriver(chromeOptions);
    	}catch(Exception e)
    	{
    		System.err.println("Nie wykryto urz¹dzenia");
    	}
	}
	public WebDriver getDriver()
	{	
		return driver;
	
	}
	

	public void getDevicesId() throws Exception {
	ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "adb devices");
	builder.redirectErrorStream(true);
	Process p = builder.start();
	BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	String lineFromCMD;
		while (true) {
			lineFromCMD = r.readLine();
	        if (lineFromCMD == null) { break; }
	        if(lineFromCMD.contains("device") && !lineFromCMD.contains("devices")){creatDeviceId(lineFromCMD);}
	        }
	       r.close();
	       p.destroy();
	    }
	
	private void creatDeviceId(String line) {
		line = line.replaceAll("device", "");
		deviceId = line.trim();
		System.err.println("znalezione urz¹dzenie: " +deviceId);
	}
	
}
