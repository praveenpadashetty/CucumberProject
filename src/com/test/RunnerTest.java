package com.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@CucumberOptions(plugin = {"pretty","html:./reports" ,
        "json:./reports/cucumber.json" ,
        "junit:./reports/cucumber.xml"},glue = "src/com/stepdef",features = "src/res", tags = "@Test")
@RunWith(Cucumber.class)
public class RunnerTest {

}
