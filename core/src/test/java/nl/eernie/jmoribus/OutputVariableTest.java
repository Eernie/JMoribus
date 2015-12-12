package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.reporter.DefaultTestReporter;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class OutputVariableTest {

    Step lastReportedErrorStep = null;
    Exception lastReportedError = null;

    private void setLastReportedError(Step errorStep, Exception error) {
        this.lastReportedError = error;
        this.lastReportedErrorStep = errorStep;
    }

    @Test
    public void testOutputVariableError() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultTestReporter() {
            @Override
            public void errorStep(final Step step, Exception cause) {
                super.errorStep(step, cause);
                setLastReportedError(step, cause);
            }
        });

        ArrayList<Object> steps = new ArrayList<>();
        steps.add(new OutputVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.WHEN);
        step.getStepLines().add(new Line("step b"));
        step.setStepContainer(scenario);

        scenario.getSteps().addAll(Collections.singletonList(step));
        story.getScenarios().add(scenario);

        jMoribus.runStories(Collections.singletonList(story));

        Assert.assertEquals(step, lastReportedErrorStep);
        Assert.assertEquals("Missing variables: [outputVariableA]", lastReportedError.getMessage());
    }

    @Test
    public void testOutputVariableSuccess() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultTestReporter() {
            @Override
            public void errorStep(final Step step, Exception cause) {
                super.errorStep(step, cause);
                setLastReportedError(step, cause);
            }
        });

        ArrayList<Object> steps = new ArrayList<>();
        steps.add(new OutputVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.WHEN);
        step.getStepLines().add(new Line("step b"));
        step.setStepContainer(scenario);

        scenario.getSteps().addAll(Collections.singletonList(step));
        story.getScenarios().add(scenario);

        defaultConfiguration.getContextProvider().get().set("outputVariableA", "outputVariableAValue");

        jMoribus.runStories(Collections.singletonList(story));

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


