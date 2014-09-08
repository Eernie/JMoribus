package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.OutputVariables;
import nl.eernie.jmoribus.annotation.RequiredVariables;
import nl.eernie.jmoribus.annotation.When;

public class OutputVariableSteps
{
    @OutputVariables("outputVariableA")
    @When("step b")
    public void methodB()
    {
        // set the output variable here
    }
}
