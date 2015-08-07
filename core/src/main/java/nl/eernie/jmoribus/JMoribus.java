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
import nl.eernie.jmoribus.reporter.Reporter;
import nl.eernie.jmoribus.runner.StepRunner;
import nl.eernie.jmoribus.to.PossibleStepTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        MethodMatcher methodMather = createMethodMatcher();
        List<PossibleStep> possibleSteps = methodMather.getPossibleSteps();
        return PossibleStepsConverter.convert(possibleSteps);
    }

    public void runStories(List<Story> stories)
    {
        MethodMatcher methodMather = createMethodMatcher();
        this.stepRunner = new StepRunner(methodMather, config);

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
                runStepContainer(methodMather, reporter, story.getPrologue());
                reporter.afterPrologue(story.getPrologue());
            }
            for (Scenario scenario : story.getScenarios())
            {
                reporter.beforeScenario(scenario);
                stepRunner.runBeforeAfter(BeforeAfterType.BEFORE_SCENARIO);
                runStepContainer(methodMather, reporter, scenario);
                reporter.afterScenario(scenario);
                stepRunner.runBeforeAfter(BeforeAfterType.AFTER_SCENARIO);
            }
            reporter.afterStory(story);
            stepRunner.runBeforeAfter(BeforeAfterType.AFTER_STORY);
        }
    }

    private MethodMatcher createMethodMatcher()
    {
        List<Object> objects = config.getSteps();
        return new MethodMatcher(objects);
    }

    private void runStepContainer(MethodMatcher methodMather, Reporter reporter, StepContainer stepContainer)
    {
        if (stepContainer == null)
        {
            return;
        }

        for (Step step : stepContainer.getSteps())
        {
            if (step instanceof Scenario)
            {
                runReferring(methodMather, reporter, stepContainer, (Scenario) step);
                continue;
            }
            reporter.beforeStep(step);
            PossibleStep matchedStep = methodMather.findMatchedStep(step);
            if (matchedStep != null)
            {
                try
                {
                    checkMissingVariables(matchedStep.getRequiredVariables());
                    stepRunner.run(matchedStep, step);
                    checkMissingVariables(matchedStep.getOutputVariables());
                    reporter.successStep(step);
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
    }

    private void runReferring(MethodMatcher methodMather, Reporter reporter, StepContainer stepContainer, Scenario referringScenario)
    {
        reporter.beforeReferringScenario(stepContainer, referringScenario);
        runStepContainer(methodMather, reporter, referringScenario);
        reporter.afterReferringScenario(stepContainer, referringScenario);
    }

    private void checkMissingVariables(String[] variablesToCheck) throws MissingVariablesException
    {
        List<String> missingRequiredVariables = new ArrayList<>();
        if (variablesToCheck != null)
        {
            List<String> requiredVariables = Arrays.asList(variablesToCheck);

            for (String requiredVariable : requiredVariables)
            {
                if (!config.getContextProvider().isVariableSet(requiredVariable))
                {
                    missingRequiredVariables.add(requiredVariable);
                }
            }
        }
        if(!missingRequiredVariables.isEmpty())
        {
            throw new MissingVariablesException(missingRequiredVariables);
        }
    }
}

