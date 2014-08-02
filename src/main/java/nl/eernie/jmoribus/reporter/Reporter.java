package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.*;

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

    void feature(Feature feature);

    void beforeBackground(Background background);

    void beforePrologue(Scenario scenario);

    void afterPrologue(Scenario scenario);
}