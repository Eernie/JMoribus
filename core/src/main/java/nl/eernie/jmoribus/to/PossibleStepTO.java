package nl.eernie.jmoribus.to;

import nl.eernie.jmoribus.model.StepType;

public class PossibleStepTO
{

	private final String step;
	private final StepType stepType;
	private final String[] categories;
	private final String[] requiredVariables;
	private final String[] outputVariables;

	public PossibleStepTO(String step, StepType stepType, String[] categories, String[] requiredVariables, String[] outputVariables)
	{
		this.step = step;
		this.stepType = stepType;
		this.categories = categories;
		this.requiredVariables = requiredVariables;
		this.outputVariables = outputVariables;
	}

	public String getStep()
	{
		return step;
	}

	public StepType getStepType()
	{
		return stepType;
	}

	public String[] getCategories()
	{
		return categories;
	}

	public String[] getRequiredVariables()
	{
		return requiredVariables;
	}

	public String[] getOutputVariables()
	{
		return outputVariables;
	}
}
