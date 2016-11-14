package features.step_definitions;

import java.io.*;
import java.util.concurrent.TimeUnit;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import util.Common;
import util.LogUtil;
import util.ocr.*;

public class CommonSteps {
    private AndroidDriver driver = Hooks.driver;
    private Common common = Hooks.common;
    private LogUtil log = Hooks.log;

    @Then("^我应该在\"([^\"]*)\"页$")
    public void inTheRightPage(String page) throws Throwable {
        if(page.equals("登录")){
            log.info("判断是否在登录页...");
            Assert.assertTrue("当前页面不是登录页！",common.isElementExsit(driver,By.id("next")));
        }
        else if(page.equals("待报价")){
            log.info("判断是否在待报价页...");
            common.swipeToDown(driver,1000);
            driver.unlockDevice();
            Assert.assertTrue("报价tab没有在当前页面上！",common.isElementExsit(driver,By.id("quotation")));
            Assert.assertTrue("当前页面不是待报价页!",Boolean.parseBoolean(driver.findElement(By.id("quotation")).getAttribute("selected")));
        }
        else if(page.equals("已派车")){
            Assert.assertTrue("当前页面不是订单页!",common.isElementExsit(driver,By.className("android.support.v7.a.d")));
            WebElement yipaiche_tab = (WebElement)driver.findElements(By.className("android.support.v7.a.d")).get(1);
            Assert.assertTrue("当前页面不是已派车页!",Boolean.parseBoolean(yipaiche_tab.getAttribute("selected")));
        }
        else if(page.equals("已完成")){
            Assert.assertTrue("当前页面不是订单页!",common.isElementExsit(driver,By.className("android.support.v7.a.d")));
            WebElement yiwancheng_tab = (WebElement)driver.findElements(By.className("android.support.v7.a.d")).get(2);
            Assert.assertTrue("当前页面不是已派车页!",Boolean.parseBoolean(yiwancheng_tab.getAttribute("selected")));
        }
        else if(page.equals("我的报价")){
            WebElement title = (WebElement)driver.findElement(By.id("title_text"));
            Assert.assertEquals("当前页面不是我的报价页!","我的报价",title.getText());
        }
        else if(page.equals("我关注的地区")||page.equals("我关注的业务联系人")){
            WebElement title = (WebElement)driver.findElement(By.id("title_text"));
            Assert.assertEquals("当前页面不是"+page+"页!",page,title.getText());
        }
    }

    @Given("^进入\"([^\"]*)\"页面$")
    public void enterPage(String page) throws Throwable {
        WebElement my_tab = driver.findElement(By.id("my"));
        WebElement list_tab = driver.findElement(By.id("ly_tab_menu_list"));
        if(page.equals("注册")){
            log.info("点击【注册】按钮进入注册页...");
            WebElement register_button = driver.findElement(By.id("register"));
            register_button.click();
        }
        else if(page.equals("我关注的地区")){
            log.info("进入【我的】...");
            my_tab.click();
            log.info("进入【我关注的地区】...");
            WebElement my_attention_area_button = driver.findElement(By.id("my_attention_area"));
            my_attention_area_button.click();
        }
        else if(page.equals("我的业务联系人")){
            log.info("进入【我的】...");
            my_tab.click();
            log.info("进入【我的业务联系人】...");
            WebElement my_attention_contacts_tab = driver.findElement(By.id("my_attention_contacts"));
            my_attention_contacts_tab.click();
        }
        else if(page.equals("我的报价")){
            log.info("进入【我的】...");
            my_tab.click();
            log.info("进入【我的报价】...");
            WebElement my_quotation_tab = driver.findElement(By.id("my_quotation"));
            my_quotation_tab.click();
        }
        else if(page.equals("待报价")){
            log.info("进入【待报价】...");
            WebElement quotation_tab = driver.findElement(By.id("quotation"));
            quotation_tab.click();
            common.swipeToDown(driver,1000);
        }else if(page.equals("报价")){
            WebElement baojia_button = driver.findElement(By.id("quotebtn"));
            baojia_button.click();
            log.info("正在报价...");
            common.swipeToUp(driver,1000);
        }
        else if(page.equals("待派车")){
            log.info("进入【待派车】...");
            list_tab.click();
            WebElement daipaiche_tab = (WebElement)driver.findElements(By.className("android.support.v7.a.d")).get(0);
            daipaiche_tab.click();
        }
        else if(page.equals("派车")){
            log.info("进入【派车】...");
            WebElement paiche_button = driver.findElement(By.id("btn_assign_car"));
            paiche_button.click();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            common.swipeToUp(driver,1000);
        }
        else if(page.equals("已派车")){
            log.info("进入【已派车】...");
            list_tab.click();
            WebElement yipaiche_tab = (WebElement)driver.findElements(By.className("android.support.v7.a.d")).get(1);
            yipaiche_tab.click();
        }
    }

    @Then("^应有toast弹出\"([^\"]*)\"$")
    public void verifyToast(String message) throws Throwable {
        Thread.sleep(2000);
        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        String path = "screenshots/"+common.getCurrentDateTime("yyyyMMdd_HHmmss")+".png";
        FileUtils.copyFile(srcFile, new File(path));
        log.info("截图："+path+"，图片文字识别开始...");
        String valCode = "";
        try {
            valCode = new OCR().recognizeText(new File(path), "png");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(valCode);
        log.info("图片文字识别结束...");
        if(message.contains("请输入联系人")||message.equals("请先输入手机号码")||message.equals("请先输入车牌号")
                || message.equals("请输入正确的手机号码")||message.equals("请输入正确的车牌号")||message.equals("含税总价超出最大范围")
                ||message.equals("不含税总价超出最大范围")||message.equals("含税单价超出最大范围")||message.equals("不含税单价超出最大范围")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains(message));
        }
        else if(message.contains("账号已存在，不能重复注册")){
           Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("不能重"));
        }
        else if(message.contains("你已关注此地区，请重新选择")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("关注此地区 , 请重新选择"));
        }
        else if(message.contains("省部分地区，是否将关注地区扩大为")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("省部分地区，是否将关注地区扩大为"));
        }
        else if(message.contains("联系人和联系电话不可用重复添加")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("联系人和联系电话不可以重复"));
        }
        else if(message.equals("含税总价不能为空")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("含税总价"));
        }
        else if(message.equals("含税单价不能为空")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("含税单价"));
        }
        else if(message.equals("不含税总价不能为空")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("不含税总价不能为"));
        }
        else if(message.equals("不含税单价不能为空")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("不含税单价不能为"));
        }
        else if(message.equals("司机姓名不能为空")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("司机姓名不"));
        }
        else if(message.equals("请输入正确的身份证号")){
            Assert.assertTrue("Toast不正确！请查看截图:"+path,valCode.contains("请输入正确 的身份证号"));
        }
        else{
            throw new Exception("没有对应toast信息，请更新自动化脚本！");
        }
    }

    @Then("^应有弹框\"([^\"]*)\"$")
    public void verfiyPopup(String message){
        Assert.assertTrue("弹框没有出现！",common.isElementExsit(driver,By.id("message")));
        WebElement popup = driver.findElement(By.id("message"));
        Assert.assertTrue("弹框信息不正确！",popup.getText().contains(message));
    }

    @Then("^点击弹框\"([^\"]*)\"按钮$")
    public void clickPopupButton(String button){
        if(button.equals("确定")){
            log.info("点击确认按钮...");
            WebElement positive_btn = driver.findElement(By.id("positive_tv"));
            positive_btn.click();
        }else if (button.equals("取消")){
            log.info("点击取消按钮...");
            WebElement cancel_btn = driver.findElement(By.id("cancel_tv"));
            cancel_btn.click();
        }
    }

    @Then("^目前至少有一个\"([^\"]*)\"的订单$")
    public void verfiyMatchOrderAppears(String matchStr) throws Throwable {
        if(matchStr.equals("待报价")){
            Assert.assertTrue("没有待报价订单！",common.isElementExsit(driver,By.id("quotebtn")));
        }
        else if(matchStr.equals("待派车")){
            Assert.assertTrue("没有待派车订单！",common.isElementExsit(driver,By.id("btn_assign_car")));
        }
        else if(matchStr.equals("已派车")){
            Assert.assertTrue("没有已派车订单！",common.isElementExsit(driver,By.id("btn_changcar_msg")));
        }
        else if(matchStr.equals("可修改报价")){
            Assert.assertTrue("没有可修改报价订单！",common.isElementExsit(driver,By.id("change_price")));
        }
    }

    @Then("^关闭并重新打开app$")
    public void relaunchApp() throws Throwable {
        log.info("切换到后台并重新打开App...");
        driver.closeApp();
        driver.launchApp();
    }
}