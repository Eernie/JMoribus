package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario extends Step implements StepTeller {

    private String title;
    private Story story;
    private List<Step> steps = new ArrayList<Step>();

    public Scenario(){
        super("",StepType.PROLOGUE);
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
