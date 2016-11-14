package features.step_definitions;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.Common;
import util.LogUtil;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ageren on 2016/11/8.
 */
public class QuotateSteps {
    private AndroidDriver driver = Hooks.driver;
    private Common common = Hooks.common;
    private LogUtil log = Hooks.log;

    @Given("^物流后台发布一条需求$")
    public void releaseRequirements() throws Throwable {
        String fullFileName = "data/requirements_data.json";
        File file = new File(fullFileName);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("没有找到对应的requirements_data.json文件！");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        String currentTime = common.getCurrentDateTime("MMM dd, yyyy HH:mm:ss aaa");
        String requirementData = buffer.toString().replace("CurrentTime",currentTime);
        //URL url = new URL("https://back.wuage.com/logisticsorder/editsave");
        URL url = new URL("https://back.wuage.com/logisticsorder/pushOrder");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        //json文件里的数据为物流需求数据
        //推送承运商13112340000的ID为68
        out.write("LogisticsOrder="+new String(requirementData.getBytes(),"utf-8")+"&logisticsCarriersIds=68");
        out.flush();
        out.close();
        String message="";
        String result="";
        InputStream urlStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
        while ((message = reader.readLine()) != null) {
            result += message + "\r\n";
        }
        if(result.contains("error")){
            throw new Exception("后台发布需求失败！具体原因如下："+result);
            // log.error("后台发布需求失败！具体原因如下："+result);
        }
    }


    @Then("^报价:$")
    public void quotate(DataTable table){
        List<WebElement> price_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        List<String> price_info = table.raw().get(1);
        price_info_textfields.get(0).sendKeys(price_info.get(0));
        price_info_textfields.get(1).sendKeys(price_info.get(1));
        price_info_textfields.get(2).sendKeys(price_info.get(2));
        price_info_textfields.get(3).sendKeys(price_info.get(3));
        WebElement submit_button = driver.findElement(By.id("commit"));
        int gestureX = submit_button.getLocation().getX();
        int gestureY = submit_button.getLocation().getY();
        TouchAction gesture = new TouchAction(driver).press(gestureX, gestureY).release();
        driver.performTouchAction(gesture);
    }

    @Then("^输入\"([^\"]*)\"应自动计算出\"([^\"]*)\"$")
    public void calculatePriceAutomatically(String input_price, String calculated_price){
        List<WebElement> price_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        if(calculated_price.equals("含税单价")){
            price_info_textfields.get(0).sendKeys(input_price);
            price_info_textfields.get(1).click();
            Assert.assertTrue("输入含税总价后，含税单价没有自动计算！",price_info_textfields.get(1).getText()!="请输入含税价");
            log.info("已自动计算出含税单价:"+price_info_textfields.get(1).getText());
        }else if(calculated_price.equals("含税总价")){
            price_info_textfields.get(1).sendKeys(input_price);
            price_info_textfields.get(0).click();
            Assert.assertTrue("输入含税单价后，含税总价没有自动计算！",price_info_textfields.get(0).getText()!="请输入含税价");
            log.info("已自动计算出含税总价:"+price_info_textfields.get(0).getText());
        }else if(calculated_price.equals("不含税单价")){
            price_info_textfields.get(2).sendKeys(input_price);
            price_info_textfields.get(3).click();
            Assert.assertTrue("输入不含税总价后，不含税单价没有自动计算！",price_info_textfields.get(3).getText()!="请输入不含税价");
            log.info("已自动计算出不含税单价:"+price_info_textfields.get(3).getText());
        }else if(calculated_price.equals("不含税总价")){
            price_info_textfields.get(3).sendKeys(input_price);
            price_info_textfields.get(2).click();
            Assert.assertTrue("输入不含税单价后，不含税总价没有自动计算！",price_info_textfields.get(2).getText()!="请输入不含税价");
            log.info("已自动计算出不含税总价:"+price_info_textfields.get(2).getText());
        }
        log.info("为下一次验证清空所有价钱...");
        price_info_textfields.get(0).clear();
        price_info_textfields.get(1).clear();
        price_info_textfields.get(2).clear();
        price_info_textfields.get(3).clear();
    }


    @Then("^输入\"([^\"]*)\"\"([^\"]*)\"$")
    public void inputPrice(String price_type,String price) throws Exception {
        List<WebElement> price_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        if(price_type.equals("含税总价")){
            log.info("输入含税总价:"+price);
            price_info_textfields.get(0).sendKeys(price);
        }
        else if(price_type.equals("不含税总价")){
            log.info("输入不含税总价:"+price);
            price_info_textfields.get(2).sendKeys(price);
        }
        else if(price_type.equals("含税单价")){
            log.info("输入含税单价:"+price);
            price_info_textfields.get(1).sendKeys(price);
        }
        else if(price_type.equals("不含税单价")){
            log.info("输入不含税单价:"+price);
            price_info_textfields.get(3).sendKeys(price);
        }
        else{
            throw new Exception("没有对应的价钱输入框:"+price_type+"!请检查脚本！");
        }
    }

    @Then("^输入\"([^\"]*)\"\"([^\"]*)\"并检验小数点后数位$")
    public void verifyDecimalDigits(String price_type,String price) throws Exception {
        List<WebElement> price_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        if(price_type.equals("含税总价")){
            log.info("输入含税总价:"+price);
            price_info_textfields.get(0).sendKeys(price);
            Assert.assertEquals("含税总价小数点后数位错误！",price.substring(0,price.length()-1),price_info_textfields.get(0).getText());
        }
        else if(price_type.equals("不含税总价")){
            log.info("输入不含税总价:"+price);
            price_info_textfields.get(2).sendKeys(price);
            Assert.assertEquals("含税总价小数点后数位错误！",price.substring(0,price.length()-1),price_info_textfields.get(2).getText());
        }
        else if(price_type.equals("含税单价")){
            log.info("输入含税单价:"+price);
            price_info_textfields.get(1).sendKeys(price);
            Assert.assertEquals("含税总价小数点后数位错误！",price.substring(0,price.length()-1),price_info_textfields.get(1).getText());
        }
        else if(price_type.equals("不含税单价")){
            log.info("输入不含税单价:"+price);
            price_info_textfields.get(3).sendKeys(price);
            Assert.assertEquals("含税总价小数点后数位错误！",price.substring(0,price.length()-1),price_info_textfields.get(3).getText());
        }
        else{
            throw new Exception("没有对应的价钱输入框:"+price_type+"!请检查脚本！");
        }

    }

    @When("^进入修改报价页修改报价信息:$")
    public void changeQuotation(DataTable table){
        WebElement change_price_button = driver.findElement(By.id("change_price"));
        change_price_button.click();
        common.swipeToUp(driver,1000);
        List<WebElement> price_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        List<String> price_info = table.raw().get(1);
        price_info_textfields.get(0).sendKeys(price_info.get(0));
        price_info_textfields.get(1).click();
        price_info_textfields.get(2).sendKeys(price_info.get(1));
        price_info_textfields.get(3).click();
        WebElement submit_button = driver.findElement(By.id("commit"));
        submit_button.click();
    }
}
