package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.converter.PossibleStepsConverter;
import nl.eernie.jmoribus.exception.MissingVariablesException;
import nl.eernie.jmoribus.matcher.BeforeAfterType;
import nl.eernie.jmoribus.matcher.MethodMatcher;
import nl.eernie.jmoribus.matcher.PossibleStep;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.model.Table;
import nl.eernie.jmoribus.reporter.Reporter;
import nl.eernie.jmoribus.runner.StepRunner;
import nl.eernie.jmoribus.to.PossibleStepTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JMoribus
{
    private Configuration config;
    private StepRunner stepRunner;

    public JMoribus(Configuration config)
    {
        this.config = config;
    }

    public List<PossibleStepTO> getPossibleSteps()
    {
        MethodMatcher methodMatcher = createMethodMatcher();
        List<PossibleStep> possibleSteps = methodMatcher.getPossibleSteps();
        return PossibleStepsConverter.convert(possibleSteps);
    }

    public void runStories(List<Story> stories)
    {
        MethodMatcher methodMatcher = createMethodMatcher();
        this.stepRunner = new StepRunner(methodMatcher, config);

        Reporter reporter = config.getConcurrentReporter();

        for (Story story : stories)
        {
            reporter.beforeStory(story);
            if (story.getFeature() != null)
            {
                reporter.feature(story.getFeature());
            }
            stepRunner.runBeforeAfter(BeforeAfterType.BEFORE_STORY);
            if (story.getPrologue() != null)
            {
                reporter.beforePrologue(story.getPrologue());
                runStepContainer(methodMatcher, story.getPrologue());
                reporter.afterPrologue(story.getPrologue());
            }
            for (Scenario scenario : story.getScenarios())
            {
                if (scenario.getExamplesTable() != null)
                {
                    runExamplesTable(methodMatcher, scenario);
                }
                else
                {
                    runScenario(methodMatcher, scenario);
                }
            }
            reporter.afterStory(story);
            stepRunner.runBeforeAfter(BeforeAfterType.AFTER_STORY);
        }
    }

    private void runScenario(MethodMatcher methodMatcher, Scenario scenario)
    {
        config.getConcurrentReporter().beforeScenario(scenario);
        stepRunner.runBeforeAfter(BeforeAfterType.BEFORE_SCENARIO);
        runStepContainer(methodMatcher, scenario);
        config.getConcurrentReporter().afterScenario(scenario);
        stepRunner.runBeforeAfter(BeforeAfterType.AFTER_SCENARIO);
    }

    private MethodMatcher createMethodMatcher()
    {
        List<Object> objects = config.getSteps();
        return new MethodMatcher(objects);
    }

    private void runStepContainer(MethodMatcher methodMatcher, StepContainer stepContainer)
    {
        Reporter reporter = config.getConcurrentReporter();
		boolean success = true;
		for (Step step : stepContainer.getSteps())
		{
			if (!success)
			{
				reporter.skipStep(step);
				continue;
			}
			success = runStep(methodMatcher, stepContainer, reporter, step);
		}
	}

	private boolean runStep(MethodMatcher methodMatcher, StepContainer stepContainer, Reporter reporter, Step step)
	{
		boolean success = false;
		if (step instanceof Scenario)
		{
			runReferring(methodMatcher, stepContainer, (Scenario) step);
			success = true;
		}
		else
		{
			reporter.beforeStep(step);
			PossibleStep matchedStep = methodMatcher.findMatchedStep(step);
			if (matchedStep != null)
			{
				try
				{
					checkMissingVariables(matchedStep.getRequiredVariables());
					stepRunner.run(matchedStep, step);
					checkMissingVariables(matchedStep.getOutputVariables());
					reporter.successStep(step);
					success = true;
				}
				catch (AssertionError e)
				{
					reporter.failedStep(step, e);
				}
				catch (Exception e)
				{
					if (e.getCause() instanceof AssertionError)
					{
						reporter.failedStep(step, (AssertionError) e.getCause());
					}
					else
					{
						reporter.errorStep(step, e);
					}
				}
			}
			else
			{
				reporter.pendingStep(step);
			}
		}
		return success;
	}

	private void runExamplesTable(MethodMatcher methodMatcher, Scenario scenario)
	{
		Table examplesTable = scenario.getExamplesTable();
        config.getConcurrentReporter().beforeExamplesTable(scenario);
        for (List<String> row : examplesTable.getRows())
        {
            Map<String, String> exampleRow = toExampleRow(examplesTable.getHeader(), row);
            config.getConcurrentReporter().beforeExampleRow(scenario, exampleRow);
            config.getContextProvider().get().setCurrentExampleRow(exampleRow);
            runScenario(methodMatcher, scenario);
            config.getContextProvider().get().removeCurrentExampleRow(exampleRow);
            config.getConcurrentReporter().afterExampleRow(scenario, exampleRow);
        }
        config.getConcurrentReporter().afterExamplesTable(scenario);
    }

    private Map<String, String> toExampleRow(List<String> header, List<String> row)
    {
        Map<String, String> exampleRow = new HashMap<>(header.size());
        for (int i = 0; i < header.size(); i++)
        {
            exampleRow.put(header.get(i), row.get(i));
        }
        return exampleRow;
    }

    private void runReferring(MethodMatcher methodMatcher, StepContainer stepContainer, Scenario referringScenario)
    {
        config.getConcurrentReporter().beforeReferringScenario(stepContainer, referringScenario);
        runStepContainer(methodMatcher, referringScenario);
        config.getConcurrentReporter().afterReferringScenario(stepContainer, referringScenario);
    }

    private void checkMissingVariables(String[] variablesToCheck) throws MissingVariablesException
    {
        List<String> missingRequiredVariables = new ArrayList<>();
        if (variablesToCheck != null)
        {
            List<String> requiredVariables = Arrays.asList(variablesToCheck);

            for (String requiredVariable : requiredVariables)
            {
                if (!config.getContextProvider().get().isVariableSet(requiredVariable))
                {
                    missingRequiredVariables.add(requiredVariable);
                }
            }
        }
        if (!missingRequiredVariables.isEmpty())
        {
            throw new MissingVariablesException(missingRequiredVariables);
        }
    }
}

