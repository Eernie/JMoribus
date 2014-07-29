package nl.eernie.jmoribus;


import nl.eernie.jmoribus.annotation.*;
import nl.eernie.jmoribus.annotation.ParameterConverter;
import org.junit.Assert;

public class Steps {

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
}