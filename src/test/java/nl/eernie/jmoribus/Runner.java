package nl.eernie.jmoribus;

import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

    @Test
    public void main() throws InvocationTargetException, IllegalAccessException {
        JMoribus jMoribus = new JMoribus();
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.setConfig(defaultConfiguration);
        Story story = createStory();
        Scenario scenario = createScenario();
        scenario.setStory(story);
        Step step = new Step("dddd Dit is een hele lange var more text", StepType.WHEN);
        Step step2 = new Step("dddd $testvar more text", StepType.THEN);
        Step step3 = new Step("bla bla bla 400", StepType.WHEN);
        scenario.getSteps().addAll(Arrays.asList(step, step2, step3));
        story.getScenarios().add(scenario);
        jMoribus.playAct(Arrays.asList(story));
    }

    @Test
    public void runStory() throws InvocationTargetException, IllegalAccessException {

        InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
        Story story = StoryParser.parseStory(fileInputStream, "Story 1", "test.story");

        JMoribus jMoribus = new JMoribus();
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.setConfig(defaultConfiguration);
        jMoribus.playAct(Arrays.asList(story));

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
