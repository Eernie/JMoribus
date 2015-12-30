package nl.eernie.jmoribus.monitoring;

import nl.eernie.jmoribus.Steps;
import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.junit.JunitTestRunner;
import nl.eernie.jmoribus.parser.ParseableStory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class JunitReportRunnerTest
{

	@Test
	public void testCreationDescriptions() throws IllegalAccessException, InstantiationException, InitializationError
	{
		JunitReportRunner runner = new JunitReportRunner(JunitTestRunnerImpl.class);
		assertEquals(3, runner.getDescription().getChildren().size());

		Description multiScenarioStory = runner.getDescription().getChildren().get(0);
		assertEquals("MultiScenarioTitle", multiScenarioStory.getClassName());
		assertEquals(6, multiScenarioStory.getChildren().size());
		assertEquals(4, multiScenarioStory.getChildren().get(0).getChildren().size());

		Description referringStory = runner.getDescription().getChildren().get(1);
		assertEquals("PrologueTest", referringStory.getClassName());
		assertEquals(2, referringStory.getChildren().size());

		Description referringScenario = referringStory.getChildren().get(1);
		assertEquals(3, referringScenario.getChildren().size());
		assertEquals("scenario 2", referringScenario.getClassName());

		Description referredStep = referringScenario.getChildren().get(0);
		assertEquals("scenario 1", referredStep.getClassName());
		assertEquals(3, referredStep.getChildren().size());

		Description examplesAndPrologueStory = runner.getDescription().getChildren().get(2);
		assertEquals(3, examplesAndPrologueStory.getChildren().size());

		Description prologue = examplesAndPrologueStory.getChildren().get(0);
		assertEquals("Prologue", prologue.getClassName());
		assertEquals(1, prologue.getChildren().size());

		Description scenarioWithExamples = examplesAndPrologueStory.getChildren().get(1);
		assertEquals("Examples", scenarioWithExamples.getClassName());
		assertEquals(2, scenarioWithExamples.getChildren().size());

		Description firstExample = scenarioWithExamples.getChildren().get(0);
		assertEquals("Example: {newSystemStateName=currentState, Nameaction=nothing, systemState=currentState}", firstExample.getClassName());
		assertEquals(1, firstExample.getChildren().size());
		assertEquals("parametrized scenario", firstExample.getChildren().get(0).getClassName());
		assertEquals(3, firstExample.getChildren().get(0).getChildren().size());
	}

	@Test
	public void testJunitMonitoringReporter() throws Throwable
	{

		JunitReportRunner runner = new JunitReportRunner(JunitTestRunnerImpl.class);
		RunNotifier mock = mock(RunNotifier.class);
		Statement statement = runner.childrenInvoker(mock);
		statement.evaluate();

		verify(mock).fireTestRunStarted(runner.getDescription());
		verify(mock, times(13)).fireTestFailure(any(Failure.class));
		verify(mock, times(2)).fireTestFinished(any(Description.class));
		verify(mock, times(34)).fireTestStarted(any(Description.class));
		verify(mock, times(26)).fireTestIgnored(any(Description.class));
		verify(mock).fireTestRunFinished(any(Result.class));
	}

	@Ignore("Test used in a test, to be run on its own")
	public static class JunitTestRunnerImpl extends JunitTestRunner
	{

		@Test
		public void testRunner()
		{
			runStories();
		}

		@Override
		protected Configuration createConfiguration()
		{

			DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
			defaultConfiguration.addSteps(Collections.<Object>singletonList(new Steps()));
			return defaultConfiguration;
		}

		@Override
		protected List<ParseableStory> createParseAbleStories()
		{

			List<ParseableStory> parseableStories = new ArrayList<>(3);
			InputStream fileInputStream = getClass().getResourceAsStream("/multiScenario.story");
			parseableStories.add(new ParseableStory(fileInputStream, "MultiScenarioTitle"));
			fileInputStream = getClass().getResourceAsStream("/referring/referring.story");
			parseableStories.add(new ParseableStory(fileInputStream, "PrologueTest"));
			fileInputStream = getClass().getResourceAsStream("/storyWithExampleTable.story");
			parseableStories.add(new ParseableStory(fileInputStream, "storyWithExampleTable"));

			return parseableStories;
		}
	}
}