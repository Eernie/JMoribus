package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.*;
import nl.eernie.jmoribus.annotation.ParameterConverter;

public class Steps {

    @When("bla bla bla $integer")
    public void method(Integer integer) {
        System.out.println("method invoked with Integer: "+ integer);
    }

    @When("dddd $testvar more text")
    public void evenMore(String testVar) {
        System.out.println("Method invoked " + testVar);
    }

    @ParameterConverter
    public Integer convertToInt(String var){
        return Integer.valueOf(var);
    }
}