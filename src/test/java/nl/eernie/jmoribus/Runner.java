package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Line;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

    @Test
    public void main() throws InvocationTargetException, IllegalAccessException {

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        defaultConfiguration.addSteps(steps);
        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step(StepType.WHEN);
        step.setStepContainer(scenario);
        step.getStepLines().add(new Line("dddd Dit is een hele lange var more text"));

        Step step2 = new Step(StepType.THEN);
        step2.setStepContainer(scenario);
        step2.getStepLines().add(new Line("dddd $testvar more text"));

        Step step3 = new Step(StepType.WHEN);
        step3.setStepContainer(scenario);
        step3.getStepLines().add(new Line("bla bla bla 400"));

        scenario.getSteps().addAll(Arrays.asList(step, step2, step3));
        story.getScenarios().add(scenario);
        jMoribus.runStories(Arrays.asList(story));
    }

    @Test
    public void runStory() throws InvocationTargetException, IllegalAccessException, IOException {

        InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "test.story");

        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.runStories(Arrays.asList(story));

    }

    private Scenario createScenario() {
        Scenario scenario = new Scenario();
        scenario.setTitle("This AwsomeScenario");
        return scenario;
    }

    private Story createStory() {
        Story story = new Story();
        story.setTitle("Story Titles");
        story.setUniqueIdentifier("/path/or/some/sort");
        return story;
    }
}
