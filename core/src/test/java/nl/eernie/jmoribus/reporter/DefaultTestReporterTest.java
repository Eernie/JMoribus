package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.Steps;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;


public class DefaultTestReporterTest
{

    @Test
    public void testAllHooks() throws IOException {
        InputStream fileInputStream = getClass().getResourceAsStream("/reporter/reporterTestStory.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "/reporter/reporterTestStory.story");

        Story story = StoryParser.parseStory(parseableStory);
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        Reporter reporter = mock(DefaultTestReporter.class);
        defaultConfiguration.addReporter(reporter);
        ArrayList<Object> steps = new ArrayList<>();
        steps.add(new Steps());
        defaultConfiguration.addSteps(steps);
        jMoribus.runStories(Arrays.asList(story));

        verify(reporter, atLeast(1)).beforePrologue(story.getPrologue());
        verify(reporter, atLeast(1)).afterPrologue(story.getPrologue());
        verify(reporter, atLeast(1)).beforeStory(story);
        verify(reporter, atLeast(1)).afterStory(story);
        verify(reporter, atLeast(2)).beforeScenario(any(Scenario.class));
        verify(reporter, atLeast(2)).afterScenario(any(Scenario.class));
        verify(reporter, atLeast(1)).beforeReferringScenario(any(StepContainer.class), any(Scenario.class));
        verify(reporter, atLeast(1)).feature(any(Feature.class));
        verify(reporter, atLeast(1)).beforeStep(any(Step.class));
        verify(reporter, atLeast(1)).failedStep(any(Step.class), any(AssertionError.class));
        verify(reporter, atLeast(1)).errorStep(any(Step.class), any(Throwable.class));
        verify(reporter, atLeast(1)).successStep(any(Step.class));
    }
}
