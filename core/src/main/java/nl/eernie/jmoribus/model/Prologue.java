package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Prologue implements StepContainer
{
	private List<Step> steps = new ArrayList<>();
	private Story story;

	public List<Step> getSteps()
	{
		return steps;
	}

	@Override
	public Story getStory()
	{
		return story;
	}

	@Override
	public void setStory(Story story)
	{
		this.story = story;
	}
}
