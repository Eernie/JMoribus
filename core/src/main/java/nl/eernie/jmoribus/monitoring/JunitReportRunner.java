package nl.eernie.jmoribus.monitoring;

import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.junit.JunitTestRunner;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.model.Table;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JunitReportRunner extends BlockJUnit4ClassRunner
{
	private final Configuration configuration;
	private final List<Story> stories;
	private final Description rootDescription;
	private final JunitTestRunner junitTestRunner;
	private final Map<Object, Description> knownDescriptions = new HashMap<>();
	private final Set<String> uniqueIds = new HashSet<>();
	private int amountOfTestCases = 0;

	public JunitReportRunner(Class<? extends JunitTestRunner> clazz) throws InitializationError, IllegalAccessException, InstantiationException
	{
		super(clazz);

		junitTestRunner = clazz.newInstance();
		configuration = junitTestRunner.getConfiguration();
		stories = junitTestRunner.getStories();
		rootDescription = Description.createSuiteDescription(clazz);

		for (Story story : stories)
		{
			Description storyDescription = createStoryDescription(story);
			rootDescription.addChild(storyDescription);
		}
	}

	private Description createStoryDescription(Story story)
	{
		String title;
		if (story.getFeature() != null)
		{
			title = story.getTitle();
		}
		else
		{
			title = story.getUniqueIdentifier();
		}
		Description storyDescription = createSuiteDescription(story, title, story.getUniqueIdentifier());

		if (story.getPrologue() != null)
		{
			Description prologue = createStepContainerDescriptions(story.getUniqueIdentifier(), story.getPrologue(), "Prologue");
			storyDescription.addChild(prologue);

		}

		for (Scenario scenario : story.getScenarios())
		{
			Description stepContainerDescription = createStepContainerDescriptions(story.getUniqueIdentifier(), scenario, scenario.getTitle());
			storyDescription.addChild(stepContainerDescription);
		}
		return storyDescription;
	}

	private Description createStepContainerDescriptions(String uniqueStoryID, StepContainer scenario, String descriptionTitle)
	{
		String uniqueID = uniqueStoryID;
		if (scenario instanceof Scenario)
		{
			uniqueID = uniqueID + ":" + ((Scenario) scenario).getLineNumber();
		}
		Description scenarioDescription = createSuiteDescription(scenario, descriptionTitle, uniqueID);

		if (scenario instanceof Scenario && ((Scenario) scenario).getExamplesTable() != null)
		{
			Table examplesTable = ((Scenario) scenario).getExamplesTable();
			for (List<String> row : examplesTable.getRows())
			{
				String title = createExamplesTitle(examplesTable.getHeader(), row);
				String uniqueExamplesID = uniqueID + title;
				Description suiteDescription = createSuiteDescription(row, title, uniqueExamplesID);

				createStepDescription(uniqueStoryID, scenario, suiteDescription);
				scenarioDescription.addChild(suiteDescription);
			}
		}
		else
		{
			createStepDescription(uniqueStoryID, scenario, scenarioDescription);
		}
		return scenarioDescription;
	}

	private Description createSuiteDescription(Object object, String title, String uniqueID)
	{
		uniqueID = uniquify(uniqueID);
		Description suiteDescription = Description.createSuiteDescription(title, uniqueID);
		knownDescriptions.put(object, suiteDescription);
		return suiteDescription;
	}

	private String createExamplesTitle(List<String> header, List<String> row)
	{
		Map<String, String> example = new HashMap<>(header.size());
		for (int i = 0; i < header.size(); i++)
		{
			example.put(header.get(i), row.get(i));
		}
		return "Example: " + example.toString();
	}

	private void createStepDescription(String uniqueStoryID, StepContainer scenario, Description scenarioDescription)
	{
		for (Step step : scenario.getSteps())
		{
			if (step instanceof Scenario)
			{
				Scenario referringScenario = (Scenario) step;
				Description stepContainerDescriptions = createStepContainerDescriptions(uniqueStoryID, referringScenario, referringScenario.getTitle());
				scenarioDescription.addChild(stepContainerDescriptions);
			}
			else
			{
				String stepTitle = step.getCombinedStepLines();
				String uniqueStepID = uniqueStoryID + ":" + step.getLineNumber();
				String stepName = StringUtils.capitalize(step.getStepType().name().toLowerCase());
				Description stepDescription = createTestDescription(step, stepTitle, uniqueStepID, stepName);
				scenarioDescription.addChild(stepDescription);
			}
		}
	}

	private Description createTestDescription(Step object, String stepTitle, String uniqueID, String stepName)
	{
		uniqueID = uniquify(uniqueID);
		Description stepDescription = Description.createTestDescription(stepName, stepTitle, uniqueID);
		if(knownDescriptions.containsKey(object)){
			throw new RuntimeException("FUCKIT!");
		}
		knownDescriptions.put(object, stepDescription);
		return stepDescription;
	}

	private String uniquify(String uniqueID)
	{
		while (uniqueIds.contains(uniqueID))
		{
			uniqueID = uniqueID + '\u200B';
		}
		return uniqueID;
	}

	@Override
	public Description getDescription()
	{
		return rootDescription;
	}

	@Override
	public int testCount()
	{
		return knownDescriptions.values().size();
	}

	@Override
	protected Statement childrenInvoker(final RunNotifier notifier)
	{
		return new Statement()
		{
			@Override
			public void evaluate() throws Throwable
			{
				JunitMonitoringReporter reporter = new JunitMonitoringReporter(notifier, knownDescriptions);
				configuration.addReporter(reporter);

				notifier.fireTestRunStarted(rootDescription);
				junitTestRunner.runStories();
				notifier.fireTestRunFinished(new Result());
			}
		};
	}
}
