import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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

import com.unity.mkulikie.connect.Connect;

/**
 * Search Google example.
 *
 * @author Rahul
 */
public class MainClass {
    static Connect connect;
    static WebDriver driver;
    static Wait<WebDriver> wait;
//TESTy bez apium
    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Testy automatyczne sig co chcesz przetestowac ?\n1.urz¹dzenie fizyczne");
    	connect = new Connect(scan.nextLine());
    	driver = connect.getDriver();
    	if(driver !=null){
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
    }

    
	private static void creatApk() throws MalformedURLException {

    		try{
    		String deviceId = "LGD855170940ac" ; //"LGD855170940ac" ;//"eb368ae613398ab8";
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