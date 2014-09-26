package nl.eernie.jmoribus.junit;


import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;

import java.util.List;

public abstract class JunitTestRunner  {

    private List<Story> stories = StoryParser.parseStories(createParseAbleStories());

    private Configuration configuration = createConfiguration();

    public void runStories(){
        JMoribus jMoribus = new JMoribus(configuration);
        jMoribus.runStories(stories);
    }

    protected abstract Configuration createConfiguration();

    protected abstract List<ParseableStory> createParseAbleStories();

    public List<Story> getStories() {
        return stories;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
