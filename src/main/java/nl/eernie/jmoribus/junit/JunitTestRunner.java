package nl.eernie.jmoribus.junit;


import nl.eernie.jmoribus.JMoribus;
import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.exception.UnableToParseStoryException;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;

import java.io.IOException;
import java.util.List;

public abstract class JunitTestRunner  {

    public void runStories(){
        try {
            List<Story> stories = StoryParser.parseStories(getParseAbleStories());
            Configuration configuration = getConfiguration();
            JMoribus jMoribus = new JMoribus(configuration);
            jMoribus.playAct(stories);
        } catch (IOException e) {
           throw new UnableToParseStoryException("The parser was not able to parse a story", e);
        }

    }

    protected abstract Configuration getConfiguration();


    protected abstract List<ParseableStory> getParseAbleStories();
}
