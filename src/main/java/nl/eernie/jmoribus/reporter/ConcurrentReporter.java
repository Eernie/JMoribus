package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.*;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentReporter implements Reporter {

    private List<Reporter> reporters = new ArrayList<>();

    public void addReporter(Reporter reporter) {
        reporters.add(reporter);
    }

    @Override
    public void beforeStory(Story story) {
        for (Reporter reporter : reporters) {
            reporter.beforeStory(story);
        }
    }

    @Override
    public void beforeScenario(Scenario scenario) {
        for (Reporter reporter : reporters) {
            reporter.beforeScenario(scenario);
        }
    }

    @Override
    public void beforeStep(Step step) {
        for (Reporter reporter : reporters) {
            reporter.beforeStep(step);
        }
    }

    @Override
    public void successStep(Step step) {
        for (Reporter reporter : reporters) {
            reporter.successStep(step);
        }
    }

    @Override
    public void pendingStep(Step step) {
        for (Reporter reporter : reporters) {
            reporter.pendingStep(step);
        }
    }

    @Override
    public void afterScenario(Scenario scenario) {
        for (Reporter reporter : reporters) {
            reporter.afterScenario(scenario);
        }
    }

    @Override
    public void afterStory(Story story) {
        for (Reporter reporter : reporters) {
            reporter.afterStory(story);
        }
    }

    @Override
    public void failedStep(Step step, AssertionError e) {
        for (Reporter reporter : reporters) {
            reporter.failedStep(step, e);
        }
    }

    @Override
    public void errorStep(Step step, Throwable e) {
        for (Reporter reporter : reporters) {
            reporter.errorStep(step, e);
        }
    }

    @Override
    public void feature(Feature feature) {
        for (Reporter reporter : reporters) {
            reporter.feature(feature);
        }
    }

    @Override
    public void beforePrologue(Prologue prologue) {
        for (Reporter reporter : reporters) {
            reporter.beforePrologue(prologue);
        }
    }

    @Override
    public void afterPrologue(Prologue prologue) {
        for (Reporter reporter : reporters) {
            reporter.beforePrologue(prologue);
        }
    }

    @Override
    public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario) {
        for (Reporter reporter : reporters) {
            reporter.beforeReferringScenario(stepContainer, scenario);
        }
    }

    @Override
    public void afterReferringScenario(StepContainer stepContainer, Scenario scenario) {
        for (Reporter reporter : reporters) {
            reporter.afterReferringScenario(stepContainer, scenario);
        }
    }


}
