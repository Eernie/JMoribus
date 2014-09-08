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

        InputStream fileInputStream = getClass().getResourceAsStream("/referring.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "Referring Story", "referring.story");

        Story story = StoryParser.parseStory(parseableStory);
        Scenario scenario = story.getScenarios().get(1);
        Assert.assertTrue(scenario.getSteps().get(0) instanceof Scenario);
    }

    @Test
    public void testRunner() {

        Step step = mock(Step.class);
        List<Step> steps = new ArrayList<>();
        steps.add(step);
        Scenario referring = mock(Scenario.class);
        when(referring.getSteps()).thenReturn(steps);

        Scenario scenario = mock(Scenario.class);
        List<Step> referrings = new ArrayList<>();
        referrings.add(referring);
        when(scenario.getSteps()).thenReturn(referrings);

        Story story = new Story();
        story.getScenarios().add(scenario);

        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.addSteps(Arrays.<Object>asList(new Steps()));
        JMoribus jMoribus = new JMoribus(configuration);
        jMoribus.playAct(Arrays.asList(story));

        Mockito.verify(referring, times(1)).getSteps();
    }
}
