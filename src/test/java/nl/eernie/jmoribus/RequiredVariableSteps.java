package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.RequiredVariables;

public class RequiredVariableSteps {
    @RequiredVariables("requiredVariableA")
    @Given("step a")
    public void methodA() {
        // do something with requiredVariableA here
    }
}
