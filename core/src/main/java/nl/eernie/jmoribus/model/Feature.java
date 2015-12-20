package nl.eernie.jmoribus.model;

public class Feature
{
	private String content;
	private Story story;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Story getStory()
	{
		return story;
	}

	public void setStory(Story story)
	{
		this.story = story;
	}

	@Override
	public String toString()
	{
		return "Feature{" +
				"content='" + content + '\'' +
				", story=" + story +
				'}';
	}
}
