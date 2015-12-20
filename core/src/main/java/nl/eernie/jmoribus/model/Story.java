package nl.eernie.jmoribus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Story implements Serializable
{
    private final String uniqueID = UUID.randomUUID().toString();
    private String title;
    private Feature feature;
    private Prologue prologue;
    private List<Scenario> scenarios = new ArrayList<>();
    private String uniqueIdentifier;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Feature getFeature()
    {
        return feature;
    }

    public void setFeature(Feature feature)
    {
        this.feature = feature;
    }

    public Prologue getPrologue()
    {
        return prologue;
    }

    public void setPrologue(Prologue prologue)
    {
        this.prologue = prologue;
    }

    public List<Scenario> getScenarios()
    {
        return scenarios;
    }

    public String getUniqueIdentifier()
    {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier)
    {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getUniqueID()
    {
        return uniqueID;
    }
}
