package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.*;

public class RequiredVariableSteps
{
    @RequiredVariables("requiredVariableA")
    @Given("step a")
    public void methodA()
    {
        // do something with requiredVariableA here
    }

    @OutputVariables("outputVariableA")
    @When("step b")
    public void methodB()
    {
        // set the output variable here
    }
}
