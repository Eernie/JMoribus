package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Category;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import nl.eernie.jmoribus.to.PossibleStepTO;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {

    @Test
    public void main() throws InvocationTargetException, IllegalAccessException {

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        Story story = createStory();
        Scenario scenario = createScenario();
        Step step = new Step("dddd Dit is een hele lange var more text", StepType.WHEN);
        Step step2 = new Step("dddd $testvar more text", StepType.THEN);
        Step step3 = new Step("bla bla bla 400", StepType.WHEN);
        scenario.getSteps().addAll(Arrays.asList(step, step2, step3));
        story.getScenarios().add(scenario);
        jMoribus.playAct(Arrays.asList(story));
    }

    @Test
    public void runStory() throws InvocationTargetException, IllegalAccessException, IOException {

        InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "Story 1", "test.story");

        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.playAct(Arrays.asList(story));

    }

    @Test
    public void testPossibleSteps(){
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();

        steps.add(new Object(){
            @Category({"User","Login"})
            @When("aMethod")
            @Then("aMethod")
            public void aMethod(){

            }
        });

        defaultConfiguration.addSteps(steps);
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        List<PossibleStepTO> possibleSteps = jMoribus.getPossibleSteps();

        Assert.assertEquals(2,possibleSteps.size());
        Assert.assertEquals(new String[]{"User","Login"},possibleSteps.get(0).getCategories());

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
