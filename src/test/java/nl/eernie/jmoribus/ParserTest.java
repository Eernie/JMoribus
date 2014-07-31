package nl.eernie.jmoribus;

import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.StoryParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ParserTest {

    @Test
    public void testParse() throws FileNotFoundException {
        InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
        Story story = StoryParser.parseStory(fileInputStream, "Story 1", "test.story");

        Assert.assertEquals("Story 1", story.getUniqueIdentifier());
        Assert.assertEquals("test.story", story.getTitle());
        Assert.assertEquals("Some awsome title", story.getFeature().getTitle());
        Assert.assertEquals("to realize a named business value", story.getFeature().getInOrder());
        Assert.assertEquals("explicit system actor", story.getFeature().getAsA());
        Assert.assertEquals("to gain some beneficial outcome which furthers the goal", story.getFeature().getiWant());

        Assert.assertEquals(1,story.getScenarios().size());

        Scenario scenario = story.getScenarios().get(0);
        Assert.assertEquals("scenario description", scenario.getTitle());
        Assert.assertSame(story, scenario.getStory());

        Assert.assertEquals(4,scenario.getSteps().size());

        Step step = scenario.getSteps().remove(0);
        assertStep(StepType.GIVEN, "a system state", scenario, step);
        step = scenario.getSteps().remove(0);
        assertStep(StepType.WHEN, "I do something", scenario, step);
        step = scenario.getSteps().remove(0);
        assertStep(StepType.THEN, "system is in a different state", scenario, step);
        step = scenario.getSteps().remove(0);
        assertStep(StepType.THEN, "another assertion", scenario, step);
    }

    private void assertStep(StepType type, String stepString, Scenario scenario, Step step) {
        Assert.assertSame(scenario, step.getStepTeller());
        Assert.assertEquals(type, step.getStepType());
        Assert.assertEquals(stepString, step.getValue());
    }

    @Test
    public void testMultipleScenarioParse() throws FileNotFoundException {
        InputStream fileInputStream = getClass().getResourceAsStream("/multiScenario.story");
        Story story = StoryParser.parseStory(fileInputStream, "Story 1", "test.story");

        Assert.assertEquals(6, story.getScenarios().size());

    }
}
