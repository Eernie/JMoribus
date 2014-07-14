package nl.eernie.jmoribus;

import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.reporter.DefaultReporter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
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
        Step step = new Step("dddd Dit is een hele lange var more text", Feature.StepType.WHEN);
        Step step2 = new Step("dddd $testvar more text", Feature.StepType.THEN);
        Step step3 = new Step("bla bla bla 400", Feature.StepType.WHEN);
        scenario.setSteps(Arrays.asList(step, step2, step3));
        story.setScenarios(Arrays.asList(scenario));
        jMoribus.playAct(Arrays.asList(story));
    }

    private static Scenario createScenario() {
        Scenario scenario = new Scenario();
        scenario.setTitle("This AwsomeScenario");
        return scenario;
    }

    private static Story createStory() {
        Story story = new Story();
        story.setTitle("Story Titles");
        story.setUniqueIdentifier("/path/or/some/sort");
        return story;
    }
}
