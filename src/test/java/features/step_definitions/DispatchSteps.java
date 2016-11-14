package features.step_definitions;

import cucumber.api.DataTable;
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
public class DispatchSteps {
    private AndroidDriver driver = Hooks.driver;
    private Common common = Hooks.common;
    private LogUtil log = Hooks.log;


    @Then("^派车:$")
    public void dispatch(DataTable table){
        List<String> driver_info = table.raw().get(1);
        List<WebElement> driver_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        for(int i=0;i<driver_info_textfields.size();i++){
            driver_info_textfields.get(i).sendKeys(driver_info.get(i));
        }
        WebElement submit_button = driver.findElement(By.id("button"));
        common.clickElement(driver,submit_button);
    }

    @Then("^再次进入派车页，派车信息应该如下:$")
    public void verifyCarInfo(DataTable table){
        WebElement change_carinfo_button = driver.findElement(By.id("btn_changcar_msg"));
        change_carinfo_button.click();
        common.swipeToUp(driver,1000);
        List<String> driver_info = table.raw().get(1);
        List<WebElement> driver_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        for(int i=0;i<driver_info_textfields.size();i++){
            Assert.assertEquals(table.raw().get(0).get(i)+"不符合！",driver_info.get(i),driver_info_textfields.get(i).getText());
        }
    }

    @Then("^进入派车页修改派车信息:$")
    public void changeCarInfo(DataTable table){
        WebElement change_carinfo_button = driver.findElement(By.id("btn_changcar_msg"));
        change_carinfo_button.click();
        common.swipeToUp(driver,1000);
        List<String> driver_info = table.raw().get(1);
        List<WebElement> driver_info_textfields = driver.findElements(By.className("android.widget.EditText"));
        for(int i=0;i<driver_info_textfields.size();i++){
            driver_info_textfields.get(i).sendKeys(driver_info.get(i));
        }
        WebElement submit_button = driver.findElement(By.id("button"));
        submit_button.click();
    }

    @When("^将第一个订单完成运输$")
    public void finishTransportation(){
        WebElement finish_trans_button = driver.findElement(By.id("btn_assign_car"));
        finish_trans_button.click();
        WebElement yes_button = driver.findElement(By.id("positive_tv"));
        yes_button.click();
    }


    @Then("^进行联系货主按钮功能检验$")
    public void verifyContactOwnerButton(){
        log.info("点击【联系货主】按钮...");
        WebElement contact_owner_button = driver.findElement(By.id("connect_mobile"));
        contact_owner_button.click();
        log.info("检验是否出现电话号码...");
        Assert.assertTrue("电话号码没有出现！",common.isElementExsit(driver,By.id("phone_number")));
        log.info("检验是否出现取消按钮...");
        Assert.assertTrue("取消按钮没有出现！",common.isElementExsit(driver,By.id("phone_cancel")));
    }

    @Then("^进行最多添加10个司机检验$")
    public void verifyDriverMaxNumber(){
        WebElement add_driver_button = driver.findElement(By.id("tv_add_assign_car_msg"));
        for(int i=1;i<9;i++){
            log.info("第"+i+"次点击增加按钮!");
            add_driver_button.click();
            Assert.assertTrue("第"+i+"次点击!增加按钮却已经消失！",common.isElementExsit(driver,By.id("tv_add_assign_car_msg")));
            log.info("增加按钮仍然存在!");
        }
        log.info("第9次点击增加按钮!");
        add_driver_button.click();
        Assert.assertFalse("第9次点击!增加按钮应该已经消失！",common.isElementExsit(driver,By.id("tv_add_assign_car_msg")));
        log.info("增加按钮已经消失!");
    }

}
