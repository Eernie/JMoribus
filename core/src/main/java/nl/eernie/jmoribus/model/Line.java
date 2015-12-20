package nl.eernie.jmoribus.model;

/**
 * A Step consists of multiple steplines. This class represents a line of text.
 */
public class Line implements StepLine
{
	private String text;

	public Line(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}
}
