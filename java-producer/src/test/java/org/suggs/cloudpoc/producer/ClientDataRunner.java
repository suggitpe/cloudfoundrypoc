package org.suggs.cloudpoc.producer;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features/clientdata.feature",
        glue = "org.suggs.cloudpoc.producer.stepdefinitions")
public class ClientDataRunner {
}
