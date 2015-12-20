package nl.eernie.jmoribus.maven.plugin;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.reporter.Reporter;

import java.util.Map;

public class MojoReporter implements Reporter
{
	long success = 0;
	long failure = 0;
	long error = 0;
	long skipped = 0;
	long pending = 0;

	@Override
	public void beforeStory(Story story)
	{
	}

	@Override
	public void beforeScenario(Scenario scenario)
	{
	}

	@Override
	public void beforeStep(Step step)
	{
	}

	@Override
	public void successStep(Step step)
	{
		success++;
	}

	@Override
	public void pendingStep(Step step)
	{
		pending++;
	}

	@Override
	public void afterScenario(Scenario scenario)
	{
	}

	@Override
	public void afterStory(Story story)
	{
	}

	@Override
	public void failedStep(Step step, AssertionError e)
	{
		failure++;
	}

	@Override
	public void errorStep(Step step, Exception e)
	{
		error++;
	}

	@Override
	public void skipStep(Step step)
	{
		skipped++;
	}

	@Override
	public void feature(Feature feature)
	{
	}

	@Override
	public void beforePrologue(Prologue prologue)
	{
	}

	@Override
	public void afterPrologue(Prologue prologue)
	{
	}

	@Override
	public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
	}

	@Override
	public void afterReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
	}

	@Override
	public void beforeExamplesTable(Scenario scenario)
	{
	}

	@Override
	public void beforeExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
	}

	@Override
	public void afterExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
	}

	@Override
	public void afterExamplesTable(Scenario scenario)
	{
	}

	public long getSuccess()
	{
		return success;
	}

	public long getFailure()
	{
		return failure;
	}

	public long getError()
	{
		return error;
	}

	public long getSkipped()
	{
		return skipped;
	}

	public long getPending()
	{
		return pending;
	}
}
