package nl.eernie.jmoribus.monitoring;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.reporter.Reporter;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.util.Map;

class JunitMonitoringReporter implements Reporter
{
	private final RunNotifier notifier;
	private final Map<Object, Description> knownDescriptions;

	public JunitMonitoringReporter(RunNotifier notifier, Map<Object, Description> knownDescriptions)
	{
		this.notifier = notifier;
		this.knownDescriptions = knownDescriptions;
	}

	@Override
	public void beforeStory(Story story)
	{
		notifier.fireTestStarted(knownDescriptions.get(story));
	}

	@Override
	public void beforeScenario(Scenario scenario)
	{
		notifier.fireTestStarted(knownDescriptions.get(scenario));
	}

	@Override
	public void beforeStep(Step step)
	{
	}

	@Override
	public void successStep(Step step)
	{
		notifier.fireTestStarted(knownDescriptions.get(step));
		notifier.fireTestFinished(knownDescriptions.get(step));
	}

	@Override
	public void pendingStep(Step step)
	{
		Description stepDescription = knownDescriptions.get(step);
		notifier.fireTestStarted(stepDescription);
		notifier.fireTestFailure(new Failure(stepDescription, new RuntimeException("Step is pending!")));
	}

	@Override
	public void afterScenario(Scenario scenario)
	{
		Description scenarioDescription = knownDescriptions.get(scenario);
		notifier.fireTestFinished(scenarioDescription);
	}

	@Override
	public void afterStory(Story story)
	{
		Description storyDescription = knownDescriptions.get(story);
		notifier.fireTestFinished(storyDescription);
	}

	@Override
	public void failedStep(Step step, AssertionError e)
	{
		notifier.fireTestFailure(new Failure(knownDescriptions.get(step), e));
	}

	@Override
	public void errorStep(Step step, Exception e)
	{
		notifier.fireTestFailure(new Failure(knownDescriptions.get(step), e));
	}

	@Override
	public void skipStep(Step step)
	{
		notifier.fireTestIgnored(knownDescriptions.get(step));
	}

	@Override
	public void feature(Feature feature)
	{

	}

	@Override
	public void beforePrologue(Prologue prologue)
	{
		notifier.fireTestStarted(knownDescriptions.get(prologue));
	}

	@Override
	public void afterPrologue(Prologue prologue)
	{
		Description prologueDescription = knownDescriptions.get(prologue);
		notifier.fireTestFinished(prologueDescription);
	}

	@Override
	public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
		notifier.fireTestStarted(knownDescriptions.get(scenario));
	}

	@Override
	public void afterReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
		Description referringDescription = knownDescriptions.get(scenario);
		notifier.fireTestFinished(referringDescription);
	}

	@Override
	public void beforeExamplesTable(Scenario scenario)
	{

	}

	@Override
	public void beforeExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		notifier.fireTestStarted(knownDescriptions.get(exampleRow));
	}

	@Override
	public void afterExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		notifier.fireTestFinished(knownDescriptions.get(exampleRow));
	}

	@Override
	public void afterExamplesTable(Scenario scenario)
	{

	}
}
