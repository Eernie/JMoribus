package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;

import java.util.Map;

public interface Reporter
{
	void beforeStory(Story story);

	void beforeScenario(Scenario scenario);

	void beforeStep(Step step);

	void successStep(Step step);

	void pendingStep(Step step);

	void afterScenario(Scenario scenario);

	void afterStory(Story story);

	void failedStep(Step step, AssertionError e);

	void errorStep(Step step, Exception e);

	void skipStep(Step step);

	void feature(Feature feature);

	void beforePrologue(Prologue prologue);

	void afterPrologue(Prologue prologue);

	void beforeReferringScenario(StepContainer stepContainer, Scenario scenario);

	void afterReferringScenario(StepContainer stepContainer, Scenario scenario);

	void beforeExamplesTable(Scenario scenario);

	void beforeExampleRow(Scenario scenario, Map<String, String> exampleRow);

	void afterExampleRow(Scenario scenario, Map<String, String> exampleRow);

	void afterExamplesTable(Scenario scenario);
}
