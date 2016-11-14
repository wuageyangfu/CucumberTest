package features.step_definitions;

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
public class AreaSteps {
    private AndroidDriver driver = Hooks.driver;
    private Common common = Hooks.common;
    private LogUtil log = Hooks.log;

    @Then("^删掉地区\"([^\"]*)\"如果该地区已被关注$")
    public void deleteAreaIfExists(String city) throws Throwable {
        WebElement areas_layout = driver.findElement(By.id("recycle_view"));
        List<WebElement> areas = areas_layout.findElements(By.className("android.widget.LinearLayout"));
        for(WebElement area : areas){
            if(area.findElement(By.id("content")).getText().equals(city)){
                log.info("删除关注的地区:"+city+"...");
                area.findElement(By.id("icon")).click();
                WebElement yes_button = driver.findElement(By.id("positive_tv"));
                yes_button.click();
                break;
            }
        }
    }

    @When("^添加关注地区\"([^\"]*)\"\"([^\"]*)\"$")
    public void addNewArea(String province_name, String city_name) throws Throwable {
        Boolean flag_province = false;
        Boolean flag_city = false;
        log.info("点击【增加】按钮...");
        WebElement add_area_button = driver.findElement(By.id("add_tv"));
        add_area_button.click();
        log.info("选择"+province_name+"...");
        List<WebElement> provinces = driver.findElements(By.className("android.widget.TextView"));
        for(WebElement province : provinces){
            if(province.getAttribute("text").equals(province_name)){
                province.click();
                flag_province = true;
                break;
            }
        }
        Assert.assertTrue("没有成功选择到省!",flag_province);
        log.info("选择"+city_name+"...");
        List<WebElement> cities = driver.findElements(By.className("android.widget.TextView"));
        for(WebElement city : cities){
            if(city.getAttribute("text").equals(city_name)){
                city.click();
                flag_city = true;
                break;
            }
        }
        Assert.assertTrue("没有成功选择到市!",flag_city);
    }


    @Then("^地区\"([^\"]*)\"应该已被关注$")
    public void verifyArea(String city) throws Throwable {
        Boolean flag = false;
        List<WebElement> areas = driver.findElements(By.id("content"));
        for(WebElement area : areas){
            if(area.getText().equals(city)){
                log.info("地区【"+city+"】已被关注成功");
                flag = true;
                break;
            }
        }
        Assert.assertTrue("地区【"+city+"】没有关注成功！",flag);
    }


}
