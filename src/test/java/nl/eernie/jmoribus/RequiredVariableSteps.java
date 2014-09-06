package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.RequiredVariables;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;

public class RequiredVariableSteps
{
    @RequiredVariables(requiredVariables = "requiredVariableA")
    @Given("step a")
    public void methodA()
    {
        // do something with requiredVariableA here
    }

    @When("step b")
    public void methodB()
    {

    }

    @Then("step c")
    public void methodC()
    {

    }

}
