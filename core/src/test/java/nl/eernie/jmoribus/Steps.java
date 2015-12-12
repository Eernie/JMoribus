package nl.eernie.jmoribus;

import java.util.List;

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
import nl.eernie.jmoribus.parser.TestObject;

import org.junit.Assert;

public class Steps
{

    @Category({ "Multiple", "Test" })
    @When({ "bla bla bla $integer", "another $integer" })
    public void method(Integer integer)
    {

    }

    @When("dddd $testvar more text")
    public void evenMore(String testVar)
    {
        Assert.assertEquals(1, 2);
    }

    @ParameterConverter
    public Integer convertToInt(String var)
    {
        return Integer.valueOf(var);
    }

    @BeforeStory
    @BeforeScenario
    @AfterScenario
    @AfterStory
    public void beforeScenario()
    {
        String test = "";
    }

    @Then("the system should have the following state transitions: $table and the following states should be present: $table")
    public void tabledMethod(Table tableOne, Table tableTwo)
    {
    }

    @Given("failing step")
    public void failingStep()
    {
        Assert.assertTrue(false);
    }

    @When("error step")
    public void errorStep()
    {
        throw new RuntimeException("trigger error hook");
    }

    @Then("success step")
    public void successStep()
    {

    }

    @Given("a system state named $name")
    public void systemName(String name)
    {

    }

    @Then("a second table $table")
    public void test(List<TestObject> list)
    {
        TestObject o = list.get(0);
        Assert.assertEquals("aaaa", o.getColumnA());
        Assert.assertEquals("bbb", o.getColumnB());
        Assert.assertSame(100, o.getColumnC());

        o = (TestObject) ((List) list).get(1);
        Assert.assertEquals("aaaa2", o.getColumnA());
        Assert.assertEquals("bbb2", o.getColumnB());
        Assert.assertSame(100, o.getColumnC());
    }
}
