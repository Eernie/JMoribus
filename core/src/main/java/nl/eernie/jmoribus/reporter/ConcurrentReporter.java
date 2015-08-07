package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentReporter implements Reporter
{
    private static final String STORY = "story";
    private static final String SCENARIO = "scenario";
    private static final String STEP = "step";

    private List<Reporter> reporters = new ArrayList<>();

    public void addReporter(Reporter reporter)
    {
        reporters.add(reporter);
    }

    @Override
    public void beforeStory(Story story)
    {
        MDC.put(STORY, story.getUniqueIdentifier());
        for (Reporter reporter : reporters)
        {
            reporter.beforeStory(story);
        }
    }

    @Override
    public void beforeScenario(Scenario scenario)
    {
        MDC.put(SCENARIO, scenario.getTitle());
        for (Reporter reporter : reporters)
        {
            reporter.beforeScenario(scenario);
        }
    }

    @Override
    public void beforeStep(Step step)
    {
        Integer index = step.getStepContainer().getSteps().indexOf(step) + 1;
        MDC.put(STEP, index.toString());
        for (Reporter reporter : reporters)
        {
            reporter.beforeStep(step);
        }
    }

    @Override
    public void successStep(Step step)
    {
        for (Reporter reporter : reporters)
        {
            reporter.successStep(step);
        }
    }

    @Override
    public void pendingStep(Step step)
    {
        for (Reporter reporter : reporters)
        {
            reporter.pendingStep(step);
        }
    }

    @Override
    public void afterScenario(Scenario scenario)
    {
        for (Reporter reporter : reporters)
        {
            reporter.afterScenario(scenario);
        }
        MDC.remove(STEP);
        MDC.remove(SCENARIO);
    }

    @Override
    public void afterStory(Story story)
    {
        for (Reporter reporter : reporters)
        {
            reporter.afterStory(story);
        }
        MDC.remove(STORY);
    }

    @Override
    public void failedStep(Step step, AssertionError e)
    {
        for (Reporter reporter : reporters)
        {
            reporter.failedStep(step, e);
        }
    }

    @Override
    public void errorStep(Step step, Exception e)
    {
        for (Reporter reporter : reporters)
        {
            reporter.errorStep(step, e);
        }
    }

    @Override
    public void feature(Feature feature)
    {
        for (Reporter reporter : reporters)
        {
            reporter.feature(feature);
        }
    }

    @Override
    public void beforePrologue(Prologue prologue)
    {
        for (Reporter reporter : reporters)
        {
            reporter.beforePrologue(prologue);
        }
    }

    @Override
    public void afterPrologue(Prologue prologue)
    {
        for (Reporter reporter : reporters)
        {
            reporter.afterPrologue(prologue);
        }
    }

    @Override
    public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario)
    {
        for (Reporter reporter : reporters)
        {
            reporter.beforeReferringScenario(stepContainer, scenario);
        }
    }

    @Override
    public void afterReferringScenario(StepContainer stepContainer, Scenario scenario)
    {
        for (Reporter reporter : reporters)
        {
            reporter.afterReferringScenario(stepContainer, scenario);
        }
    }
}
