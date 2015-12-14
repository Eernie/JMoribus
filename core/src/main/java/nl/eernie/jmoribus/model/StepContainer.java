package nl.eernie.jmoribus.model;

import java.util.List;

public interface StepContainer
{
    List<Step> getSteps();

    Story getStory();

    void setStory(Story story);
}
