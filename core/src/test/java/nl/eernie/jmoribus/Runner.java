package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Line;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.JunitReporter;
import nl.eernie.jmoribus.reporter.Reporter;
import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

public class Runner
{

	@Test
	public void main() throws InvocationTargetException, IllegalAccessException
	{
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
		JMoribus jMoribus = new JMoribus(defaultConfiguration);
		defaultConfiguration.addReporter(new JunitReporter("target"));
		ArrayList<Object> steps = new ArrayList<>();
		defaultConfiguration.addSteps(steps);
		Story story = createStory();
		Scenario scenario = createScenario();
		scenario.setStory(story);
		Step step = new Step(StepType.WHEN);
		step.setStepContainer(scenario);
		step.getStepLines().add(new Line("dddd Dit is een hele lange var more text"));

		Step step2 = new Step(StepType.THEN);
		step2.setStepContainer(scenario);
		step2.getStepLines().add(new Line("dddd $testvar more text"));

		Step step3 = new Step(StepType.WHEN);
		step3.setStepContainer(scenario);
		step3.getStepLines().add(new Line("bla bla bla 400"));

		scenario.getSteps().addAll(Arrays.asList(step, step2, step3));
		story.getScenarios().add(scenario);
		jMoribus.runStories(Collections.singletonList(story));
	}

	@Test
	public void runStory() throws InvocationTargetException, IllegalAccessException, IOException
	{
		InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
		ParseableStory parseableStory = new ParseableStory(fileInputStream, "test.story");

		Story story = StoryParser.parseStory(parseableStory);

		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
		JMoribus jMoribus = new JMoribus(defaultConfiguration);
		defaultConfiguration.addReporter(new JunitReporter("target"));
		ArrayList<Object> steps = new ArrayList<>();
		steps.add(new Steps());
		defaultConfiguration.addSteps(steps);
		jMoribus.runStories(Collections.singletonList(story));
	}

	@Test
	public void testParametrizedStory() throws IOException
	{
		InputStream fileInputStream = getClass().getResourceAsStream("/storyWithExampleTable.story");
		ParseableStory parseableStory = new ParseableStory(fileInputStream, "storyWithExampleTable.story");
		Story story = StoryParser.parseStory(parseableStory);

		Reporter reporter = mock(Reporter.class);
		Steps steps = new Steps();

		Configuration configuration = new DefaultConfiguration();
		configuration.addReporter(new JunitReporter("target"));
		configuration.addReporter(reporter);
		configuration.addSteps(Collections.<Object>singletonList(steps));
		JMoribus jMoribus = new JMoribus(configuration);
		jMoribus.runStories(Collections.singletonList(story));

		verify(reporter, new Times(1)).beforeExamplesTable(any(Scenario.class));
		verify(reporter, new Times(2)).beforeExampleRow(any(Scenario.class), anyMap());
		verify(reporter, new Times(2)).afterExampleRow(any(Scenario.class), anyMap());
		verify(reporter, new Times(1)).afterExamplesTable(any(Scenario.class));
	}

	private Scenario createScenario()
	{
		Scenario scenario = new Scenario();
		scenario.setTitle("This AwsomeScenario");
		return scenario;
	}

	private Story createStory()
	{
		Story story = new Story();
		story.setTitle("Story Titles");
		story.setUniqueIdentifier("/path/or/some/sort");
		return story;
	}
}
