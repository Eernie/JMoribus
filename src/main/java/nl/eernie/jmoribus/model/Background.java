package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Background implements StepTeller{

    private Story story;
    private List<Step> steps = new ArrayList<Step>();

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
