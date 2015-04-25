package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsLexer;
import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.exception.UnableToParseStoryException;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class StoryParser
{
    private StoryParser()
    {
    }

    public static List<Story> parseStories(List<ParseableStory> parseableStories)
    {
        Map<String, Scenario> knownScenarios = new HashMap<>();
        List<Story> stories = new ArrayList<>();
        Set<ReferringScenario> referringScenarios = new HashSet<>();
        for (ParseableStory parseableStory : parseableStories)
        {
            stories.add(parseStory(parseableStory, knownScenarios, referringScenarios));
        }
        replaceReferringScenarios(referringScenarios, knownScenarios);
        return stories;
    }

    public static Story parseStory(ParseableStory parseableStory)
    {
        Map<String, Scenario> knownScenarios = new HashMap<>();
        Set<ReferringScenario> referringScenarios = new HashSet<>();
        Story story = parseStory(parseableStory, knownScenarios, referringScenarios);
        replaceReferringScenarios(referringScenarios, knownScenarios);
        return story;
    }

    private static Story parseStory(ParseableStory parseableStory, Map<String, Scenario> knownScenarios, Set<ReferringScenario> referringScenarios)
    {

        GherkinsLexer lexer;
        try
        {
            lexer = new GherkinsLexer(new ANTLRInputStream(parseableStory.getStream()));
        }
        catch (IOException e)
        {
            throw new UnableToParseStoryException("Story " + parseableStory.getUniqueIdentifier() + " is not parsable", e);
        }

        CommonTokenStream token = new CommonTokenStream(lexer);
        GherkinsParser parser = new GherkinsParser(token);
        GherkinsListener listener = new GherkinsListener();
        listener.setScenarios(knownScenarios);
        listener.setReferringScenarios(referringScenarios);
        parser.addParseListener(listener);
        parser.story();

        Story story = listener.getStory();
        story.setUniqueIdentifier(parseableStory.getUniqueIdentifier());

        return story;
    }

    private static void replaceReferringScenarios(Set<ReferringScenario> referringScenarios, Map<String, Scenario> knownScenarios)
    {
        for (ReferringScenario referringScenario : referringScenarios)
        {
            if (knownScenarios.containsKey(referringScenario.getTitle()))
            {
                Scenario scenario = knownScenarios.get(referringScenario.getTitle());
                StepContainer stepContainer = referringScenario.getStepContainer();
                List<Step> steps = stepContainer.getSteps();
                steps.set(steps.indexOf(referringScenario), scenario);
            }
            else
            {
                throw new IllegalArgumentException("Unknown scenario with title " + referringScenario.getTitle());
            }
        }
    }
}
