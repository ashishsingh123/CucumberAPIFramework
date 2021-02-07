package runner;

import io.cucumber.junit.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty", "html:target/HtmlReport",
                "html:target/HtmlReport",     //  for html result
                "pretty:target/cucumber-json-report.json" ,
                 },
        features = "src/test/resources/ApiFeatures/Pets_API.feature",
        glue = {"steps","runner"},
        monochrome = true,
        tags={"@PetsAPI"}

)
public class TestRunner {
}
