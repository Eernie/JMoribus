package nl.eernie.jmoribus.parser;

import junit.framework.TestCase;
import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.Steps;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.*;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleTablesPerStoryTest extends TestCase {

    @Test
    public void testMultipleTablesPerStep() throws IOException {
        InputStream fileInputStream = getClass().getResourceAsStream("/storyWithMultipleTablesPerStep.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "Story with multiple tables per step", "storyWithMultipleTablesPerStep.story");
        Story story = StoryParser.parseStory(parseableStory);
        //Assert.assertEquals(story.getScenarios().size(), 2);
        //int numberOfSteps = story.getScenarios().get(0).getSteps().size();
        Assert.assertNull(story.getScenarios().get(0).getExamplesTable());

        Step step1 = story.getScenarios().get(0).getSteps().remove(0);
        Assert.assertEquals(StepType.GIVEN, step1.getStepType());
        Assert.assertEquals("a system state STATE1", step1.getFirstStepLine().getText());

        Step step2 = story.getScenarios().get(0).getSteps().remove(0);
        Assert.assertEquals(StepType.WHEN, step2.getStepType());
        Assert.assertEquals("I do something", step2.getFirstStepLine().getText());

        Step step3 = story.getScenarios().get(0).getSteps().remove(0);
        Assert.assertEquals(StepType.THEN, step3.getStepType());
        Assert.assertEquals("the system should have changed to STATE2", step3.getFirstStepLine().getText());

        Step step4 = story.getScenarios().get(0).getSteps().remove(0);
        Assert.assertEquals(StepType.THEN, step4.getStepType());

        Assert.assertEquals("the system should have the following state transitions:", step4.getStepLines().get(0).getText());
        Assert.assertTrue(step4.getStepLines().get(1) instanceof Table);
        Assert.assertEquals("and the following states should be present:", step4.getStepLines().get(2).getText());
        Assert.assertTrue(step4.getStepLines().get(3) instanceof Table);
    }

    @Test
    public void testMultipleTablesPerStepRun() throws IOException, InvocationTargetException, IllegalAccessException {
        InputStream fileInputStream = getClass().getResourceAsStream("/storyWithMultipleTablesPerStep.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "Story with multiple tables per step", "storyWithMultipleTablesPerStep.story");
        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.playAct(Arrays.asList(story));

    }
}