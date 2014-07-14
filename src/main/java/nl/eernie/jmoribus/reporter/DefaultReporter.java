package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;

public class DefaultReporter implements Reporter {

    @Override
    public void beforeStory(Story story) {
        System.out.println("Before Story:   " + story.getTitle() + " - " + story.getUniqueIdentifier());
    }

    @Override
    public void beforeScenario(Scenario scenario) {
        System.out.println("Before Scenario:"+ scenario.getTitle());
    }

    @Override
    public void beforeStep(Step step) {
        System.out.println("Before Step:    "+ step.getValue() + " - " + step.getStepType());
    }

    @Override
    public void successStep(Step step) {
        System.out.println("Success Step:   "+ step.getValue() + " - " + step.getStepType());
    }

    @Override
    public void pendingStep(Step step) {
        System.out.println("Pending Step:   "+ step.getValue() + " - " + step.getStepType());
    }

    @Override
    public void afterScenario(Scenario scenario) {
        System.out.println("After Scenario: "+ scenario.getTitle());
    }

    @Override
    public void afterStory(Story story) {
        System.out.println("After Story:    " + story.getTitle() + " - " + story.getUniqueIdentifier());
    }
}
