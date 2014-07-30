package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentReporter implements Reporter {

    List<Reporter> reporters = new ArrayList<Reporter>();

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
            reporter.failedStep(step,e);
        }
    }

    @Override
    public void errorStep(Step step, Throwable e) {
        for (Reporter reporter : reporters) {
            reporter.errorStep(step,e);
        }
    }

    @Override
    public void feature(Feature feature) {
        for (Reporter reporter : reporters) {
            reporter.feature(feature);
        }
    }


}
