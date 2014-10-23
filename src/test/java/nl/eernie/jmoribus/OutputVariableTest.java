package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class OutputVariableTest {

    Step lastReportedErrorStep = null;
    String lastReportedError = null;

    private void setLastReportedError(Step errorStep, String error) {
        this.lastReportedError = error;
        this.lastReportedErrorStep = errorStep;
    }

    @Test
    public void testOutputVariableError() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter() {
            @Override
            public void errorStep(final Step step, String cause) {
                super.errorStep(step, cause);
                setLastReportedError(step, cause);
            }
        });

        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new OutputVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.WHEN);
        step.getStepLines().add(new Line("step b"));

        scenario.getSteps().addAll(Arrays.asList(step));
        story.getScenarios().add(scenario);

        jMoribus.runStories(Arrays.asList(story));

        Assert.assertEquals(step, lastReportedErrorStep);
        Assert.assertEquals("Missing output variables: [outputVariableA]", lastReportedError);
    }

    @Test
    public void testOutputVariableSuccess() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter() {
            @Override
            public void errorStep(final Step step, String cause) {
                super.errorStep(step, cause);
                setLastReportedError(step, cause);
            }
        });

        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new OutputVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.WHEN);
        step.getStepLines().add(new Line("step b"));

        scenario.getSteps().addAll(Arrays.asList(step));
        story.getScenarios().add(scenario);

        defaultConfiguration.getContextProvider().set("outputVariableA", "outputVariableAValue");

        jMoribus.runStories(Arrays.asList(story));

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


