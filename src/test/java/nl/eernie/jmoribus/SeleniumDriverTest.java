package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import nl.eernie.jmoribus.reporter.Reporter;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.io.InputStream;
import java.util.Arrays;

public class SeleniumDriverTest {

    @Test
    public void testMethodSignature() {
        InputStream fileInputStream = getClass().getResourceAsStream("/selenium.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "selenium.story");

        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        Reporter reporter = Mockito.mock(DefaultReporter.class);
        defaultConfiguration.addReporter(reporter);
        defaultConfiguration.addSteps(Arrays.<Object>asList(new SeleniumSteps()));

        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        jMoribus.runStories(Arrays.asList(story));

        Mockito.verify(reporter, new Times(0)).errorStep(Mockito.<Step>any(), Mockito.<String>any());
        Mockito.verify(reporter, new Times(0)).errorStep(Mockito.<Step>any(), Mockito.<Throwable>any());
    }
}
