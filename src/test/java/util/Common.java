package util;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {
    private LogUtil log = new LogUtil();
	
	public boolean isElementExsit(WebDriver driver, By locator) {  
        boolean flag = false;  
        try {  
            WebElement element = driver.findElement(locator);
            flag = true;  
        } catch (NoSuchElementException e) {
            log.info(locator+"元素不存在！");
        }  
        return flag;  
    }

    public void clickElement(AndroidDriver driver,WebElement element){
        int gestureX = element.getLocation().getX();
        int gestureY = element.getLocation().getY();
        TouchAction gesture = new TouchAction(driver).press(gestureX, gestureY).release();
        driver.performTouchAction(gesture);
    }

    public String getCurrentDateTime(String format){
        SimpleDateFormat df = new SimpleDateFormat(format,Locale.ENGLISH);
        return df.format(new Date());
    }

    public void installApp(String apk_path){
        Runtime runtime=Runtime.getRuntime();
        try{
            runtime.exec("adb install -r "+ apk_path);
        }catch(Exception e){
            System.out.println("Error!");
        }
    }

    public String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }


    public void swipeToUp(AndroidDriver driver, int during){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, during);
    }

    public void swipeToDown(AndroidDriver driver, int during) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, during);
    }

    public void swipeToLeft(AndroidDriver driver, int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        System.out.println(width);
        System.out.println(height);
        driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, during);
    }

    public void swipeToRight(AndroidDriver driver, int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, during);
    }



}
