package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.*;
import nl.eernie.jmoribus.annotation.ParameterConverter;

public class Steps {

    @When("bla bla bla $integer")
    public void method(Integer integer) {

    }

    @When("dddd $testvar more text")
    public void evenMore(String testVar) {

    }

    @ParameterConverter
    public Integer convertToInt(String var){
        return Integer.valueOf(var);
    }
}