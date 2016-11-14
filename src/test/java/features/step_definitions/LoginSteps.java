package features.step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.Common;
import util.LogUtil;

import java.util.List;

/**
 * Created by ageren on 2016/11/8.
 */
public class LoginSteps {
    private AndroidDriver driver = Hooks.driver;
    private Common common = Hooks.common;
    private LogUtil log = Hooks.log;

    @Given("^用\"([^\"]*)\"和\"([^\"]*)\"登录$")
    public void loginApp(String username, String password) throws Throwable {
        log.info("清空用户名输入框...");
        List<WebElement> user_textfields = driver.findElements(By.className("android.widget.EditText"));
        user_textfields.get(0).clear();
        WebElement login_button = driver.findElement(By.id("next"));
      //  log.info("检验【登录】按钮是否可点...");
      //  Assert.assertFalse(Boolean.parseBoolean(login_button.getAttribute("enabled")));
        log.info("输入用户名:"+username+"...");
        user_textfields.get(0).sendKeys(username);
        log.info("检验【登录】按钮是否可点...");
        Assert.assertFalse(Boolean.parseBoolean(login_button.getAttribute("enabled")));
        log.info("输入密码:"+password+"...");
        user_textfields.get(1).sendKeys(password);
        log.info("检验【登录】按钮是否可点...");
        Assert.assertTrue(Boolean.parseBoolean(login_button.getAttribute("enabled")));
        log.info("点击【登录】按钮登录...");
        login_button.click();
    }


    @Then("^登录\"([^\"]*)\"$")
    public void verifyLoginResult(String result) throws Throwable {
        Thread.sleep(3000);
        if(result.equals("true")){
            log.info("登录应该成功...");
            Assert.assertTrue(common.isElementExsit(driver,By.id("my")));
        }else{
            log.info("登录应该失败...");
            Assert.assertFalse(common.isElementExsit(driver,By.id("my")));
        }

    }
    @Then("^退出app$")
    public void exitApp() throws Throwable {
        log.info("进入【我的】...");
        WebElement my_tab = driver.findElement(By.id("my"));
        my_tab.click();
        log.info("进入【设置】...");
        WebElement my_setting_button = driver.findElement(By.id("my_setting"));
        my_setting_button.click();
        log.info("点击【退出登录】...");
        WebElement login_out_button = driver.findElement(By.id("login_out"));
        login_out_button.click();
        log.info("确认退出...");
        WebElement exit_yes_button = driver.findElement(By.id("positive_tv"));
        exit_yes_button.click();
    }

    @When("^输入\"([^\"]*)\"并点击发送验证码按钮$")
    public void enterNumberAndSendMessage(String phone) throws Throwable {
        log.info("输入电话号码："+phone+"...");
        List<WebElement> textfields = driver.findElements(By.className("android.widget.EditText"));
        textfields.get(0).sendKeys(phone);
        log.info("点击【发送验证码】按钮...");
        WebElement send_message_button = driver.findElement(By.id("send"));
        send_message_button.click();
    }

}
