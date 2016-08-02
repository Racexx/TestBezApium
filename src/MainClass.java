import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Search Google example.
 *
 * @author Rahul
 */
public class MainClass {
    volatile static WebDriver driver;
    static Wait<WebDriver> wait;
   
    
//TESTy bez apium
    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver", "C:/Users/mkulikie/Downloads/selenium-java-2.53.1/chromedriver.exe");
       // driver = new ChromeDriver();
    	
    	try {
			creatApk();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        wait = new WebDriverWait(driver, 30);
        driver.get("http://www.google.com/");
        boolean result;
        try {
            result = firstPageContainsQAANet();
        } catch(Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            driver.close();
        }

        System.out.println("Test " + (result? "passed." : "failed."));
        if (!result) {
            System.exit(1);
        }
    }

    private static void creatApk() throws MalformedURLException {
//	File classpathRoot = new File (System.getProperty("user.dir"));
//	File appDir = new File (classpathRoot , "Application");
//	File app = new File (appDir , "Demo1.apk");
//	DesiredCapabilities capabilities;
//	capabilities = "{'chromeOptions': {'androidPackage': 'com.android.chrome',} }";
////	capabilities.setCapability("device", "Android");
////	capabilities.setCapability("app", app.getAbsolutePath());
////	capabilities.setCapability("app-package", "com.maciek.testowy.demo1");
////	capabilities.setCapability("app-activity", ".RootActivity");
//	driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wud/hub") , capabilities);
    		try{
    		String deviceId = "42030a30c4536100" ; //"LGD855170940ac" ;//"eb368ae613398ab8";
    	    ChromeOptions chromeOptions = new ChromeOptions();
    	    chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
    	    chromeOptions.setExperimentalOption("androidDeviceSerial", deviceId);
    	    driver = new ChromeDriver(chromeOptions);
    		}catch(Exception e)
    		{
    			 driver = new ChromeDriver();
    		}
    	}

	private static boolean firstPageContainsQAANet() {
        //type search query
        driver.findElement(By.name("q")).sendKeys("qa automation\n");
        // click search
        driver.findElement(By.name("btnG")).click();

        // Wait for search to complete
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                System.out.println("Searching ...");
           
               // System.err.println("body :" + driver.findElement(By.tagName("body")).getText().toString());
               if(driver.findElement(By.tagName("body")).getText() != null)
            	   return true;
                return webDriver.findElement(By.id("body"))  != null;
            }
        });

        // Look for QAAutomation.net in the results
        
        return driver.findElement(By.tagName("body")).getText().contains("qaautomation.net");
    }
}