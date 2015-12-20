package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ReferringTest {

    @Test
    public void testParser() throws IOException {

        InputStream fileInputStream = getClass().getResourceAsStream("/referring/referring.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "referring.story");

        Story story = StoryParser.parseStory(parseableStory);
        Scenario scenario = story.getScenarios().get(1);
        Assert.assertTrue(scenario.getSteps().get(0) instanceof Scenario);
        Assert.assertEquals(story.getScenarios().get(0).getTitle(), ((Scenario)scenario.getSteps().get(0)).getTitle());
    }

    @Test
    public void testMutlipleStoryParser() {
        List<ParseableStory> parseableStories = new ArrayList<>(2);
        InputStream story1Stream = getClass().getResourceAsStream("/referring/referring.story");
        parseableStories.add(new ParseableStory(story1Stream, "referring.story"));

        InputStream story2Stream = getClass().getResourceAsStream("/referring/referringBetweenStories.story");
        parseableStories.add(new ParseableStory(story2Stream, "referring2.story"));
        List<Story> stories = StoryParser.parseStories(parseableStories);

        Assert.assertEquals(2, stories.size());
        Story story1 = stories.get(0);
        Assert.assertEquals(2, story1.getScenarios().size());
        Scenario scenario1 = story1.getScenarios().get(0);
        Scenario scenario2 = story1.getScenarios().get(1);
        Assert.assertEquals(scenario1.getTitle(), ((Scenario)scenario2.getSteps().get(0)).getTitle());

        Story story2 = stories.get(1);
        Assert.assertEquals(1, story2.getScenarios().size());
        Assert.assertEquals(scenario2.getTitle(), ((Scenario) story2.getScenarios().get(0).getSteps().get(0)).getTitle());
    }

    @Test
    public void testMutlipleStoryOrderIndependent() {
        List<ParseableStory> parseableStories = new ArrayList<>(2);
        InputStream story1Stream = getClass().getResourceAsStream("/referring/referringBetweenStories.story");
        parseableStories.add(new ParseableStory(story1Stream, "referring.story"));

        InputStream story2Stream = getClass().getResourceAsStream("/referring/referring.story");
        parseableStories.add(new ParseableStory(story2Stream, "referring2.story"));
        List<Story> stories = StoryParser.parseStories(parseableStories);

        Assert.assertEquals(2, stories.size());

        Story story1 = stories.get(0);
        Assert.assertEquals(1, story1.getScenarios().size());

        Story story2 = stories.get(1);
        Assert.assertEquals(2, story2.getScenarios().size());
        Scenario scenario1 = story2.getScenarios().get(0);
        Scenario scenario2 = story2.getScenarios().get(1);
        Assert.assertEquals(scenario1.getTitle(), ((Scenario)scenario2.getSteps().get(0)).getTitle());

        Assert.assertEquals(scenario2.getTitle(), ((Scenario) story1.getScenarios().get(0).getSteps().get(0)).getTitle());

    }

    @Test
    public void testRunner() {

        Step step = mock(Step.class);
        List<Step> steps = new ArrayList<>();
        steps.add(step);
        Scenario referring = mock(Scenario.class);
        when(referring.getSteps()).thenReturn(steps);

        Scenario scenario = mock(Scenario.class);
        when(scenario.getTitle()).thenReturn("Scenario Title");
        when(step.getStepContainer()).thenReturn(scenario);
        List<Step> referrings = new ArrayList<>();
        referrings.add(referring);
        when(scenario.getSteps()).thenReturn(referrings);

        Story story = new Story();
        story.setUniqueIdentifier("unique");
        story.getScenarios().add(scenario);

        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.addSteps(Arrays.<Object>asList(new Steps()));
        JMoribus jMoribus = new JMoribus(configuration);
        jMoribus.runStories(Arrays.asList(story));

        Mockito.verify(referring, times(1)).getSteps();
    }
}
