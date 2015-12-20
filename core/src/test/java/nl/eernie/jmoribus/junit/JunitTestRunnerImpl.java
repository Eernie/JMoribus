package nl.eernie.jmoribus.junit;

import nl.eernie.jmoribus.Steps;
import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.monitoring.JunitReportRunner;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.reporter.JunitReporter;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(JunitReportRunner.class)
public class JunitTestRunnerImpl extends JunitTestRunner {

    @Test
    public void testRunner() {
        runStories();
    }

    @Override
    protected Configuration createConfiguration() {

        Configuration config = new DefaultConfiguration();
        config.addSteps(Arrays.<Object>asList(new Steps()));
        config.addReporter(new JunitReporter("target"));
        return config;
    }

    @Override
    protected List<ParseableStory> createParseAbleStories() {

        List<ParseableStory> parseableStories = new ArrayList<>(3);
        InputStream fileInputStream = getClass().getResourceAsStream("/multiScenario.story");
        parseableStories.add(new ParseableStory(fileInputStream, "MultiScenarioTitle"));
        fileInputStream = getClass().getResourceAsStream("/test2.story");
        parseableStories.add(new ParseableStory(fileInputStream, "testTitle"));
        fileInputStream = getClass().getResourceAsStream("/referring/referring.story");
        parseableStories.add(new ParseableStory(fileInputStream, "PrologueTest"));
        fileInputStream = getClass().getResourceAsStream("/storyWithExampleTable.story");
        parseableStories.add(new ParseableStory(fileInputStream, "storyWithExampleTable"));
        fileInputStream = getClass().getResourceAsStream("/reporter/reporterTestStory.story");
        parseableStories.add(new ParseableStory(fileInputStream, "reporterTestStory"));

        return parseableStories;
    }
}
