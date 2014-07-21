package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Story {

    private String title;
    private Feature feature;
    private List<Scenario> scenarios = new ArrayList<Scenario>();
    private String uniqueIdentifier;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }


    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
