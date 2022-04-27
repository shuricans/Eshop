package ru.geekbrains;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"html:target/cucumber-html-report"},
        features = {"classpath:features"},
        glue = {"ru.geekbrains.steps"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class LaunchTest {
}
