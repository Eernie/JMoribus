package nl.eernie.jmoribus;


import nl.eernie.jmoribus.annotation.*;
import nl.eernie.jmoribus.annotation.ParameterConverter;
import nl.eernie.jmoribus.model.Table;
import org.junit.Assert;

public class Steps {

    @Category({"Multiple", "Test"})
    @When("bla bla bla $integer")
    public void method(Integer integer) {

    }

    @When("dddd $testvar more text")
    public void evenMore(String testVar) {
        Assert.assertEquals(1,2);
    }

    @ParameterConverter
    public Integer convertToInt(String var){
        return Integer.valueOf(var);
    }

    @BeforeStory
    public void beforeScenario(){
        String test = new String();
    }

    @Then("the system should have the following state transitions: $table and the following states should be present: $table")
    public void tabledMethod(Table tableOne, Table tableTwo){


    }
}