package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.*;

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
        System.out.println("Before Step:    "+ step.getCombinedStepLines() + " - " + step.getStepType());
    }

    @Override
    public void successStep(Step step) {
        System.out.println("Success Step:   "+ step.getCombinedStepLines() + " - " + step.getStepType());
    }

    @Override
    public void pendingStep(Step step) {
        System.out.println("Pending Step:   "+ step.getCombinedStepLines() + " - " + step.getStepType());
    }

    @Override
    public void afterScenario(Scenario scenario) {
        System.out.println("After Scenario: "+ scenario.getTitle());
    }

    @Override
    public void afterStory(Story story) {
        System.out.println("After Story:    " + story.getTitle() + " - " + story.getUniqueIdentifier());
    }

    @Override
    public void failedStep(Step step, AssertionError e) {
        System.out.println("Failed step:   " + step.getCombinedStepLines() + " Assertion error:" + e.getMessage());
    }

    @Override
    public void errorStep(Step step, Throwable e) {
        System.out.println("Error in step: " + step.getCombinedStepLines() + "Exception :" + e.getMessage());
    }

    @Override
    public void feature(Feature feature) {
        System.out.println("Feature: "+ feature.toString());
    }

    @Override
    public void beforeBackground(Prologue prologue) {
        System.out.println("Before Background.");
    }

    @Override
    public void beforePrologue(Scenario scenario) {
        System.out.println("before Prologue scenario: " + scenario.getTitle());
    }

    @Override
    public void afterPrologue(Scenario scenario) {
        System.out.println("after Prologue scenario: " + scenario.getTitle());
    }


}
