package features.step_definitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.TouchAction;
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
public class ContactsSteps {
    private AndroidDriver driver = Hooks.driver;
    private Common common = Hooks.common;
    private LogUtil log = Hooks.log;

    @Then("^删除可被删除的业务联系人$")
    public void deleteDeletableContacts() throws Throwable {
        log.step("删除可被删除的业务联系人...");
        while(common.isElementExsit(driver, By.id("delete_icon"))){
            driver.findElement(By.id("delete_icon")).click();
        }
    }


    @Then("^通过操作验证新增业务联系人按钮的显示$")
    public void verfiyAddContactButton() throws Throwable {
        log.info("点击【新增业务联系人】按钮...");
        WebElement add_contacts_button = driver.findElement(By.id("add_contacts_tv"));
        add_contacts_button.click();
        log.info("验证【新增业务联系人】按钮是否仍然显示...");
        Assert.assertTrue(common.isElementExsit(driver,By.id("add_contacts_tv")));
        log.info("再次点击【新增业务联系人】按钮...");
        add_contacts_button.click();
        log.info("验证【新增业务联系人】按钮是否已经被隐藏...");
        Assert.assertFalse(common.isElementExsit(driver,By.id("add_contacts_tv")));
    }


    @Then("^添加业务联系人\"([^\"]*)\"\"([^\"]*)\"(提交|不提交)$")
    public void addNewContact(String name,String phone,String submit_choice) throws Throwable {
        log.info("点击【新增业务联系人】按钮...");
        WebElement add_contacts_button = driver.findElement(By.id("add_contacts_tv"));
        add_contacts_button.click();
        log.info("填写业务联系人姓名："+name+"...");
        log.info("填写业务联系人联系方式："+phone+"...");
        List<WebElement> contacts_name_textview = driver.findElements(By.id("contacts_name"));
        List<WebElement> contacts_phone_textview = driver.findElements(By.id("contacts_phone"));
        contacts_name_textview.get(contacts_name_textview.size()/2-1).sendKeys(name);
        contacts_phone_textview.get(contacts_phone_textview.size()/2-1).sendKeys(phone);
        if(submit_choice.equals("提交")){
            log.info("提交信息...");
            WebElement submit_button = driver.findElement(By.id("commit"));
            int gestureX = submit_button.getLocation().getX();
            int gestureY = submit_button.getLocation().getY();
            TouchAction gesture = new TouchAction(driver).press(gestureX, gestureY).release();
            driver.performTouchAction(gesture);
        }
    }


    @Then("^删除业务联系人\"([^\"]*)\"\"([^\"]*)\"$")
    public void deleteContact(String name,String phone) throws Throwable {
        Boolean flag = false;
        List<WebElement> contacts = driver.findElement(By.id("container")).findElements(By.className("android.widget.LinearLayout"));
        for(WebElement contact : contacts){
            String contact_name = contact.findElement(By.id("contacts_name")).getText();
            String contact_phone = contact.findElement(By.id("contacts_phone")).getText();
            if(contact_name.equals(name)&&contact_phone.equals(phone)){
                flag = true;
                log.info("删除业务联系人:"+name+","+phone+"...");
                contact.findElement(By.id("delete_icon")).click();
                WebElement submit_button = driver.findElement(By.id("commit"));
                submit_button.click();
                log.info("验证目前是否在【我的】页面上...");
                Assert.assertTrue("当前页面不是[我的]!",common.isElementExsit(driver,By.id("my")));
                break;
            }
        }
        Assert.assertTrue("没有找到对应的业务联系人:"+name+","+phone+"!",flag);
    }

    @When("^点击新增业务联系人按钮后返回$")
    public void backWithoutSubmitContact(){
        log.info("点击【新增业务联系人】按钮...");
        WebElement add_contacts_button = driver.findElement(By.id("add_contacts_tv"));
        add_contacts_button.click();
        log.info("点击【后退】按钮...");
        WebElement back_button = driver.findElement(By.id("back_iv"));
        back_button.click();
    }
}
