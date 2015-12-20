package nl.eernie.jmoribus.configuration;


import nl.eernie.jmoribus.context.ContextProvider;
import nl.eernie.jmoribus.reporter.ConcurrentReporter;
import nl.eernie.jmoribus.reporter.Reporter;
import org.openqa.selenium.WebDriver;

import java.util.List;

public interface Configuration
{
    ConcurrentReporter getConcurrentReporter();

    void addReporter(Reporter reporter);

    List<Object> getSteps();

    void addSteps(List<Object> steps);

    ContextProvider getContextProvider();

    WebDriver getWebDriver();

    boolean isFailOnPending();

    void setFailOnPending(boolean failOnPending);
}
