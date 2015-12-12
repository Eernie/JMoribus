package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.Steps;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.model.Table;
import nl.eernie.jmoribus.reporter.DefaultTestReporter;
import nl.eernie.jmoribus.reporter.Reporter;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class MultipleTablesPerStoryTest {
    @Test
    public void testMultipleTablesPerStep() throws IOException {
        InputStream fileInputStream = getClass().getResourceAsStream("/storyWithMultipleTablesPerStep.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "storyWithMultipleTablesPerStep.story");
        Story story = StoryParser.parseStory(parseableStory);

        assertNull(story.getScenarios().get(0).getExamplesTable());

        Step step1 = story.getScenarios().get(0).getSteps().remove(0);
        assertEquals(StepType.GIVEN, step1.getStepType());
        assertEquals("a system state STATE1", step1.getFirstStepLine().getText());

        Step step2 = story.getScenarios().get(0).getSteps().remove(0);
        assertEquals(StepType.WHEN, step2.getStepType());
        assertEquals("I do something", step2.getFirstStepLine().getText());

        Step step3 = story.getScenarios().get(0).getSteps().remove(0);
        assertEquals(StepType.THEN, step3.getStepType());
        assertEquals("the system should have changed to STATE2", step3.getFirstStepLine().getText());

        Step step4 = story.getScenarios().get(0).getSteps().remove(0);
        assertEquals(StepType.THEN, step4.getStepType());

        assertEquals("the system should have the following state transitions:", step4.getStepLines().get(0).getText());
        assertEquals(5, step4.getLineNumber());
        assertTrue(step4.getStepLines().get(1) instanceof Table);
        assertEquals("and the following states should be present:", step4.getStepLines().get(2).getText());
        assertTrue(step4.getStepLines().get(3) instanceof Table);
    }

    @Test
    public void testMultipleTablesPerStepRun() throws IOException, InvocationTargetException, IllegalAccessException {
        InputStream fileInputStream = getClass().getResourceAsStream("/storyWithMultipleTablesPerStep.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "storyWithMultipleTablesPerStep.story");
        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        Reporter reporter = Mockito.mock(DefaultTestReporter.class);

        defaultConfiguration.addReporter(reporter);
        ArrayList<Object> steps = new ArrayList<>();

        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.runStories(Collections.singletonList(story));

        verify(reporter, new Times(0)).errorStep(Mockito.<Step>any(), Mockito.<Exception>any());
    }

    @Test
    public void testParametrizedStory() throws IOException {
        InputStream fileInputStream = getClass().getResourceAsStream("/storyWithExampleTable.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "storyWithExampleTable.story");
        Story story = StoryParser.parseStory(parseableStory);
        assertEquals(story.getScenarios().size(), 2);
        assertNotNull(story.getScenarios().get(0).getExamplesTable());
    }
}
