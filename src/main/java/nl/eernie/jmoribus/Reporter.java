package nl.eernie.jmoribus;


import nl.eernie.jmoribus.models.Scenario;
import nl.eernie.jmoribus.models.Step;
import nl.eernie.jmoribus.models.Story;

public interface Reporter {
    void beforeStory(Story story);

    void beforeScenario(Scenario scenario);

    void beforeStep(Step step);

    void successStep(Step step);

    void pendingStep(Step step);

    void afterScenario(Scenario scenario);

    void afterStory(Story story);
}
