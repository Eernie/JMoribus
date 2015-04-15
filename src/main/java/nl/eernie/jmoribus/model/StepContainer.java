package nl.eernie.jmoribus.model;

import java.util.List;

public interface StepContainer
{
    List<Step> getSteps();

    void setStory(Story story);
}
