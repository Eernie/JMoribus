package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsLexer;
import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.exception.UnableToParseStoryException;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Story;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StoryParser {

    private StoryParser() {
    }

    public static List<Story> parseStories(List<ParseableStory> parseableStories) {
        Map<String, Scenario> knownScenarios = new HashMap<>();
        List<Story> stories = new ArrayList<>();
        for (ParseableStory parseableStory : parseableStories) {
            stories.add(parseStory(parseableStory, knownScenarios));
        }
        return stories;
    }

    public static Story parseStory(ParseableStory parseableStory) {
        Map<String, Scenario> knownScenarios = new HashMap<>();
        return parseStory(parseableStory, knownScenarios);
    }

    private static Story parseStory(ParseableStory parseableStory, Map<String, Scenario> knownScenarios) {

        GherkinsLexer lexer = null;
        try {
            lexer = new GherkinsLexer(new ANTLRInputStream(parseableStory.getStream()));
        } catch (IOException e) {
            throw new UnableToParseStoryException("Story " + parseableStory.getUniqueIdentifier() + " is not parsable", e);
        }

        CommonTokenStream token = new CommonTokenStream(lexer);
        GherkinsParser parser = new GherkinsParser(token);
        GherkinsListener listener = new GherkinsListener();
        listener.setScenarios(knownScenarios);
        parser.addParseListener(listener);
        parser.story();

        Story story = listener.getStory();
        story.setUniqueIdentifier(parseableStory.getUniqueIdentifier());


        return story;
    }
}
