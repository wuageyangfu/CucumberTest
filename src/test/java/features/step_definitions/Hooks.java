package features.step_definitions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.android.AndroidDriver;
import util.Common;
import util.LogUtil;

public class Hooks {
    public static AndroidDriver driver;
	public static Common common = new Common();
	public static LogUtil log = new LogUtil();
    private File classpathRoot = new File(System.getProperty("user.dir"));
    private File appDir = new File(classpathRoot, "/apps");
    private File app = new File(appDir, "roadtrain_[wuage]_release_v1.0.1.apk");

	@Before
	public void setUp(){
        System.setProperty("javax.net.ssl.trustStore", "certification//jssecacerts");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("unicodeKeyboard", "True");
        capabilities.setCapability("resetKeyboard", "True");
        capabilities.setCapability("deviceName", "vivo");
        capabilities.setCapability("platformVersion", "5.1.1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.wuage.roadtrain");
//        capabilities.setCapability("appWaitActivity", ".mine.CustomLoginActivity");

        try {

            driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        log.info("等待App加载完毕...");
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp(){
		driver.quit();
	}
}
