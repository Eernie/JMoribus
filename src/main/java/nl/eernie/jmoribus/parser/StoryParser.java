package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsLexer;
import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.model.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StoryParser {


    private StoryParser(){}

    public static List<Story> parseStories(List<ParseableStory> parseableStories) throws IOException {
        Map<String, Scenario> knownScenarios = new HashMap<String, Scenario>();
        List<Story> stories = new ArrayList<Story>();
        for (ParseableStory parseableStory : parseableStories) {
            stories.add(parseStory(parseableStory,knownScenarios));
        }
        return stories;
    }

    public static Story parseStory(ParseableStory parseableStory) throws IOException {
        Map<String, Scenario> knownScenarios = new HashMap<String, Scenario>();
        return parseStory(parseableStory,knownScenarios);
    }

    private static Story parseStory(ParseableStory parseableStory, Map<String, Scenario> knownScenarios) throws IOException {

        GherkinsLexer lexer = new GherkinsLexer(new ANTLRInputStream(parseableStory.getStream()));

        CommonTokenStream token = new CommonTokenStream(lexer);
        GherkinsParser parser = new GherkinsParser(token);
        GherkinsListener listener = new GherkinsListener();
        listener.setKnownScenarios(knownScenarios);
        parser.addParseListener(listener);
        parser.story();

        Story story= listener.getStory();
        story.setTitle(parseableStory.getTitle());
        story.setUniqueIdentifier(parseableStory.getUniqueIdentifier());

        return story;
    }


}
