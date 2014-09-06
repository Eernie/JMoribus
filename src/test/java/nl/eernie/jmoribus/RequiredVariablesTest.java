package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Test;
import org.junit.experimental.theories.Theory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class RequiredVariablesTest {

    @Test
    public void main() throws InvocationTargetException, IllegalAccessException {

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter());

        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new RequiredVariableSteps());
        defaultConfiguration.addSteps(steps);

        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.GIVEN);
        step.getStepLines().add(new Line("step a"));

        Step step2 = new Step(StepType.WHEN);
        step2.getStepLines().add(new Line("step b"));

        Step step3 = new Step(StepType.THEN);
        step3.getStepLines().add(new Line("step c"));

        scenario.getSteps().addAll(Arrays.asList(step, step2, step3));
        story.getScenarios().add(scenario);

        jMoribus.playAct(Arrays.asList(story));
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


