package nl.eernie.jmoribus.monitoring;

import nl.eernie.jmoribus.configuration.Configuration;
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

class JunitMonitoringReporter implements Reporter
{
	private final RunNotifier notifier;
	private final Configuration configuration;
	private final Iterator<Description> knownDescriptions;
	private Description stepContainer;
	private Description step;

	public JunitMonitoringReporter(RunNotifier notifier, List<Description> knownDescriptions, Configuration configuration)
	{
		this.notifier = notifier;
		this.configuration = configuration;
		this.knownDescriptions = knownDescriptions.iterator();
	}

	@Override
	public void beforeStory(Story story)
	{
		notifier.fireTestStarted(knownDescriptions.next());
	}

	@Override
	public void beforeScenario(Scenario scenario)
	{
		this.stepContainer = knownDescriptions.next();
		notifier.fireTestStarted(this.stepContainer);
	}

	@Override
	public void beforeStep(Step step)
	{
		this.step = knownDescriptions.next();
	}

	@Override
	public void successStep(Step step)
	{
		notifier.fireTestStarted(this.step);
		notifier.fireTestFinished(this.step);
	}

	@Override
	public void pendingStep(Step step)
	{
		if (configuration.isFailOnPending())
		{
			notifier.fireTestStarted(this.step);
			notifier.fireTestFailure(new Failure(this.step, new RuntimeException("Pending step!!")));
		}
		else
		{
			notifier.fireTestIgnored(this.step);
		}
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
		notifier.fireTestStarted(this.step);
		notifier.fireTestFailure(new Failure(this.step, e));
	}

	@Override
	public void errorStep(Step step, Exception e)
	{
		notifier.fireTestStarted(this.step);
		notifier.fireTestFailure(new Failure(this.step, e));
	}

	@Override
	public void skipStep(Step step)
	{
		this.step = knownDescriptions.next();
		notifier.fireTestIgnored(this.step);
	}

	@Override
	public void feature(Feature feature)
	{

	}

	@Override
	public void beforePrologue(Prologue prologue)
	{
		stepContainer = knownDescriptions.next();
		notifier.fireTestStarted(stepContainer);
	}

	@Override
	public void afterPrologue(Prologue prologue)
	{
	}

	@Override
	public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
		notifier.fireTestStarted(knownDescriptions.next());
	}

	@Override
	public void afterReferringScenario(StepContainer stepContainer, Scenario scenario)
	{
	}

	@Override
	public void beforeExamplesTable(Scenario scenario)
	{
		notifier.fireTestStarted(knownDescriptions.next());
	}

	@Override
	public void beforeExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		notifier.fireTestStarted(knownDescriptions.next());
	}

	@Override
	public void afterExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
	}

	@Override
	public void afterExamplesTable(Scenario scenario)
	{
	}
}
