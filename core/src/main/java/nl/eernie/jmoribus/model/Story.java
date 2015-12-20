package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Story
{
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

}
