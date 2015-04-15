package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultReporter implements Reporter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReporter.class);

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
    public void errorStep(Step step, Throwable e)
    {
        LOGGER.error("Error in step: {}", step.getCombinedStepLines(), e);
    }

    @Override
    public void errorStep(Step step, String cause)
    {
        LOGGER.error("Error in step: {} Error: {}", step.getCombinedStepLines(), cause);
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
}
