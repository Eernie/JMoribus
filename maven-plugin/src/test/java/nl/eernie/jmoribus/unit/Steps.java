package nl.eernie.jmoribus.unit;


import nl.eernie.jmoribus.annotation.AfterScenario;
import nl.eernie.jmoribus.annotation.AfterStory;
import nl.eernie.jmoribus.annotation.BeforeScenario;
import nl.eernie.jmoribus.annotation.BeforeStory;
import nl.eernie.jmoribus.annotation.Category;
import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.ParameterConverter;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;
import nl.eernie.jmoribus.model.Table;
import org.junit.Assert;

public class Steps
{

    @Category({"Multiple", "Test"})
    @When({"bla bla bla $integer", "another $integer"})
    public void method(Integer integer) {

    }

    @When("dddd $testvar more text")
    public void evenMore(String testVar) {
        Assert.assertEquals(1, 2);
    }

    @ParameterConverter
    public Integer convertToInt(String var) {
        return Integer.valueOf(var);
    }

    @BeforeStory
    @BeforeScenario
    @AfterScenario
    @AfterStory
    public void beforeScenario() {
        String test = "";
    }

    @Then("the system should have the following state transitions: $table and the following states should be present: $table")
    public void tabledMethod(Table tableOne, Table tableTwo) {
    }

    @Given("failing step")
    public void failingStep() {
        Assert.assertTrue(false);
    }

    @When("error step")
    public void errorStep() {
        throw new RuntimeException("trigger error hook");
    }

    @Given("a system state")
    public void successStep() throws InterruptedException
    {
        Thread.sleep(10);
    }
}
