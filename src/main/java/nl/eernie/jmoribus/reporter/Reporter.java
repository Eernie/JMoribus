package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;

public interface Reporter {

    void beforeStory(Story story);

    void beforeScenario(Scenario scenario);

    void beforeStep(Step step);

    void successStep(Step step);

    void pendingStep(Step step);

    void afterScenario(Scenario scenario);

    void afterStory(Story story);

    void failedStep(Step step, AssertionError e);

    void errorStep(Step step, Throwable e);

    void errorStep(Step step, String cause);

    void feature(Feature feature);

    void beforePrologue(Prologue prologue);

    void afterPrologue(Prologue prologue);

    void beforeReferringScenario(StepContainer stepContainer, Scenario scenario);

    void afterReferringScenario(StepContainer stepContainer, Scenario scenario);
}
