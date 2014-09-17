package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.Theory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class RequiredVariablesTest {

    Step lastReportedErrorStep = null;
    String lastReportedError = null;

    private void setLastReportedError(Step errorStep, String error)
    {
        this.lastReportedError = error;
        this.lastReportedErrorStep = errorStep;
    }

    @Test
    public void testRequiredVariableError() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter()
        {
            @Override
            public void errorStep(final Step step, String cause) {
                super.errorStep(step, cause);
                setLastReportedError(step, cause);
            }
        });

        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new RequiredVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.GIVEN);
        step.getStepLines().add(new Line("step a"));

        scenario.getSteps().addAll(Arrays.asList(step));
        story.getScenarios().add(scenario);

        jMoribus.playAct(Arrays.asList(story));

        Assert.assertEquals(step, lastReportedErrorStep);
        Assert.assertEquals("Missing required variables: [requiredVariableA]", lastReportedError);
    }

    @Test
    public void testRequiredVariableSuccess() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter()
        {
            @Override
            public void errorStep(final Step step, String cause) {
                super.errorStep(step, cause);
                setLastReportedError(step, cause);
            }
        });

        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new RequiredVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.GIVEN);
        step.getStepLines().add(new Line("step a"));

        scenario.getSteps().addAll(Arrays.asList(step));
        story.getScenarios().add(scenario);

        defaultConfiguration.getContextProvider().set("requiredVariableA", "requiredVariableAValue");

        jMoribus.playAct(Arrays.asList(story));

        Assert.assertNull(lastReportedErrorStep);
        Assert.assertNull(lastReportedError);
    }

    private Scenario createScenario() {
        Scenario scenario = new Scenario();
        scenario.setTitle("This AwesomeScenario");
        return scenario;
    }

    private Story createStory() {
        Story story = new Story();
        story.setTitle("Story Titles");
        story.setUniqueIdentifier("/path/or/some/sort");
        return story;
    }
}


