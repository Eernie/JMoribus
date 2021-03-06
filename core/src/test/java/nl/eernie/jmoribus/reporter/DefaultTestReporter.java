package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DefaultTestReporter implements Reporter
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTestReporter.class);

	@Override
	public void beforeStory(Story story)
	{
		LOGGER.info("Before Story");
	}

	@Override
	public void beforeScenario(Scenario scenario)
	{
		LOGGER.info("Before Scenario");
	}

	@Override
	public void beforeStep(Step step)
	{
		LOGGER.info("Before Step: {}", step.getCombinedStepLines());
	}

	@Override
	public void successStep(Step step)
	{
		LOGGER.info("Success Step: {}", step.getCombinedStepLines());
	}

	@Override
	public void pendingStep(Step step)
	{
		LOGGER.info("Pending Step: {}", step.getCombinedStepLines());
	}

	@Override
	public void afterScenario(Scenario scenario)
	{
		LOGGER.info("After Scenario: {}", scenario.getTitle());
	}

	@Override
	public void afterStory(Story story)
	{
		LOGGER.info("After Story: {}", story.getTitle());
	}

	@Override
	public void failedStep(Step step, AssertionError e)
	{
		LOGGER.error("Failed step: {}", step.getCombinedStepLines(), e);
	}

	@Override
	public void errorStep(Step step, Exception e)
	{
		LOGGER.error("Error in step: {}", step.getCombinedStepLines(), e);
	}

	@Override
	public void skipStep(Step step)
	{
		LOGGER.warn("Skip Step: {}", step.getCombinedStepLines());
	}

	@Override
	public void feature(Feature feature)
	{
		LOGGER.info("Feature: {}", feature.toString());
	}

	@Override
	public void beforePrologue(Prologue prologue)
	{
		LOGGER.info("Before Prologue.");
	}

	@Override
	public void afterPrologue(Prologue prologue)
	{
		LOGGER.info("After Prologue.");
	}

	@Override
	public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
		LOGGER.info("before Referring scenario: " + scenario.getTitle());
	}

	@Override
	public void afterReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
		LOGGER.info("after Referring scenario: " + scenario.getTitle());
	}

	@Override
	public void beforeExamplesTable(Scenario scenario)
	{
		LOGGER.info("BeforeExamplesTable");
	}

	@Override
	public void beforeExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		LOGGER.info("Before example row");
	}

	@Override
	public void afterExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		LOGGER.info("After example row");
	}

	@Override
	public void afterExamplesTable(Scenario scenario)
	{
		LOGGER.info("After examples table");
	}
}
