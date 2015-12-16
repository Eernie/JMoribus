package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.exception.MissingVariablesException;
import nl.eernie.jmoribus.model.Line;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.reporter.DefaultTestReporter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

public class OutputVariableTest {


    @Test
    public void testOutputVariableError() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        DefaultTestReporter reporter = spy(new DefaultTestReporter());
        defaultConfiguration.addReporter(reporter);

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

        ArgumentCaptor<MissingVariablesException> exception = ArgumentCaptor.forClass(MissingVariablesException.class);
        verify(reporter).errorStep(eq(step), exception.capture());
        Assert.assertEquals("Missing variables: [outputVariableA]", exception.getValue().getMessage());
    }

    @Test
    public void testOutputVariableSuccess() throws InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        DefaultTestReporter reporter = spy(new DefaultTestReporter());
        defaultConfiguration.addReporter(reporter);

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

        verify(reporter, never()).errorStep(any(Step.class), any(Exception.class));
        verify(reporter, never()).failedStep(any(Step.class), any(AssertionError.class));
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


