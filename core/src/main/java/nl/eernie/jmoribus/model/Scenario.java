package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario extends Step implements StepContainer
{
	private String title;
	private List<Step> steps = new ArrayList<>();
	private Story story;

	private Table examplesTable;

	public Scenario()
	{
		super(StepType.REFERRING);
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<Step> getSteps()
	{
		return steps;
	}

	public Table getExamplesTable()
	{
		return examplesTable;
	}

	public void setExamplesTable(Table examplesTable)
	{
		this.examplesTable = examplesTable;
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

	public Scenario copy()
	{
		Scenario target = new Scenario();
		if(examplesTable!=null)
		{
			target.setExamplesTable(examplesTable);
		}
		target.setTitle(title);
		target.setStory(story);
		target.setStepContainer(getStepContainer());
		target.getStepLines().addAll(getStepLines());
		for (Step step : getSteps())
		{
			Step copy = step.copy();
			copy.setStepContainer(target);
			target.getSteps().add(copy);
		}
		return target;
	}
}
