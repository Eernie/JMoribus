package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class PrologueTest {

    @Test
    public void testParser() throws InvocationTargetException, IllegalAccessException, IOException {

        InputStream fileInputStream = getClass().getResourceAsStream("/prologue.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "Prologue Story", "prologue.story");

        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.addSteps(Arrays.<Object>asList(new Steps()));
        configuration.addReporter(new DefaultReporter());
        JMoribus jMoribus = new JMoribus(configuration);
        jMoribus.playAct(Arrays.asList(story));
    }
}
