package nl.eernie.jmoribus.unit;

import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.junit.JunitTestRunner;
import nl.eernie.jmoribus.parser.ParseableStory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MojoTestRunner extends JunitTestRunner
{
	@Override
	protected Configuration createConfiguration()
	{

		Configuration config = new DefaultConfiguration();
		config.addSteps(Arrays.<Object>asList(new Steps()));
		return config;
	}

	@Override
	protected List<ParseableStory> createParseAbleStories()
	{

		List<ParseableStory> parseableStories = new ArrayList<>(3);
		InputStream fileInputStream = getClass().getResourceAsStream("/multiScenario.story");
		parseableStories.add(new ParseableStory(fileInputStream, "MultiScenarioTitle"));
		fileInputStream = getClass().getResourceAsStream("/test2.story");
		parseableStories.add(new ParseableStory(fileInputStream, "testTitle"));
		fileInputStream = getClass().getResourceAsStream("/test.story");
		parseableStories.add(new ParseableStory(fileInputStream, "second title"));

		return parseableStories;
	}
}
