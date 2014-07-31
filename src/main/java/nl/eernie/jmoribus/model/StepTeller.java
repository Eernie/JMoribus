package nl.eernie.jmoribus.model;

import java.util.List;

public interface StepTeller {

    Story getStory();
    void setStory(Story story);
    List<Step> getSteps();
}