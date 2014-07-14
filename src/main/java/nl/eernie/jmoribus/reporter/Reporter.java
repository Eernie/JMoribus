package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;

public interface Reporter {
        void beforeStory(Story story);
    
        void beforeScenario(Scenario scenario);
    
        void beforeStep(Step step);
    
        void successStep(Step step);
    
        void pendingStep(Step step);
    
        void afterScenario(Scenario scenario);
    
        void afterStory(Story story);
    }