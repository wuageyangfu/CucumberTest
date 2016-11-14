import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/features"},
        format = {"pretty","html:target/cucumber_report","json:target/cucumber.json"}
)

public class AppTest extends AbstractTestNGCucumberTests  {

	
}