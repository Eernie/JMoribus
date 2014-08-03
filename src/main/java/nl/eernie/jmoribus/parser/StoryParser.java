package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StoryParser {

    private static final String FEATURE = "Feature: ";
    private static final String SCENARIO = "Scenario: ";
    private static final String BACKGROUND = "Background:";
    private static final String PROLOGUE = "Prologue ";
    private static final String GIVEN = "Given ";
    private static final String WHEN = "When ";
    private static final String THEN = "Then ";
    private static final String AND = "And ";
    private static final String COMMENT = "!--";
    private static final String EXAMPLES = "Examples:";
    private static final String[] keywords = new String[]{FEATURE, SCENARIO, GIVEN, WHEN, THEN, AND, COMMENT, BACKGROUND, PROLOGUE, EXAMPLES };

    private StoryParser(){}

    public static List<Story> parseStories(List<ParseableStory> parseableStories){
        Map<String, Scenario> knownScenarios = new HashMap<String, Scenario>();
        List<Story> stories = new ArrayList<Story>();
        for (ParseableStory parseableStory : parseableStories) {
            stories.add(parseStory(parseableStory,knownScenarios));
        }
        return stories;
    }

    public static Story parseStory(ParseableStory parseableStory){
        Map<String, Scenario> knownScenarios = new HashMap<String, Scenario>();
        return parseStory(parseableStory,knownScenarios);
    }

    private static Story parseStory(ParseableStory parseableStory,Map<String,Scenario> knownScenarios) {
        try {
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(parseableStory.getStream(),stringWriter);
            String completeStory = stringWriter.toString();

            String lines[] = completeStory.split("\\r?\\n");
            Story story = buildModels(lines,knownScenarios);
            story.setTitle(parseableStory.getTitle());
            story.setUniqueIdentifier(parseableStory.getUniqueIdentifier());
            return story;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Story buildModels(String[] lines, Map<String,Scenario> knownScenarios) {
        ArrayList<String> combinedLines = combineLines(lines);
        Story story = new Story();
        StepTeller stepTeller = new Scenario();
        StepType lastType = null;
        for(String line: combinedLines){
            if(line.startsWith(FEATURE)){
                story.setFeature(parseFeature(line));
            }
            else if(line.startsWith(PROLOGUE)){
                String prologueTitle = StringUtils.removeStart(line,PROLOGUE);
                //TODO: We mustn't force the user a order how to parse the stories, Maybe find the scenarios afterwards but the cost of this wil be much larger
                if(knownScenarios.containsKey(prologueTitle)){
                    stepTeller.getSteps().add(knownScenarios.get(prologueTitle));
                }else{
                    throw new RuntimeException("Scenario doesn't exist");
                }
            }
            else if(line.startsWith(SCENARIO)){
                stepTeller = parseScenario(line);
                addStepTeller(story, stepTeller);
                knownScenarios.put(((Scenario) stepTeller).getTitle(), (Scenario) stepTeller);
                stepTeller.setStory(story);
            }
            else if(line.startsWith(BACKGROUND)){
                stepTeller = new Background();
                addStepTeller(story, stepTeller);
                stepTeller.setStory(story);
            }
            else if(line.startsWith(GIVEN)){
                Step step = parseStep(line, GIVEN, StepType.GIVEN);
                lastType = StepType.GIVEN;
                step.setStepTeller(stepTeller);
                stepTeller.getSteps().add(step);
            }
            else if(line.startsWith(WHEN)){
                Step step = parseStep(line, WHEN, StepType.WHEN);
                lastType = StepType.WHEN;
                step.setStepTeller(stepTeller);
                stepTeller.getSteps().add(step);
            }
            else if(line.startsWith(THEN)){
                Step step = parseStep(line,THEN, StepType.THEN);
                lastType = StepType.THEN;
                step.setStepTeller(stepTeller);
                stepTeller.getSteps().add(step);
            }
            else if(line.startsWith(AND)){
                Step step = parseStep(line, AND , lastType);
                step.setStepTeller(stepTeller);
                stepTeller.getSteps().add(step);
            }
            else if(line.startsWith(EXAMPLES)){
                Step step = parseStep(line, EXAMPLES , StepType.EXAMPLES);
                lastType = StepType.EXAMPLES;
                step.setStepTeller(stepTeller);
                stepTeller.getSteps().add(step);
            }
        }
        return story;
    }

    private static void addStepTeller(Story story, StepTeller stepTeller) {
        if(stepTeller instanceof Background){
            story.setBackground((Background) stepTeller);
        } else if(stepTeller instanceof Scenario){
            story.getScenarios().add((Scenario) stepTeller);
        }
    }

    private static Step parseStep(String line, String keyword, StepType type) {
        String stepText = StringUtils.removeStart(line,keyword);
        return new Step(stepText,type);
    }

    private static Scenario parseScenario(String line) {
        Scenario scenario = new Scenario();
        scenario.setTitle(StringUtils.removeStart(line, SCENARIO));
        return scenario;
    }

    private static Feature parseFeature(String text) {
        Feature feature = new Feature();
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            if(line.startsWith(FEATURE)){
                String title = StringUtils.removeStart(line, FEATURE);
                feature.setTitle(title);
            } else if(line.startsWith("In order ")){
                String inOrder = StringUtils.removeStart(line, "In order ");
                feature.setInOrder(inOrder);
            } else if(line.startsWith("As a ")){
                String asA = StringUtils.removeStart(line,"As a ");
                feature.setAsA(asA);
            } else if(line.startsWith("I want ")){
                String iWant = StringUtils.removeStart(line, "I want ");
                feature.setiWant(iWant);
            }
        }
        return feature;
    }

    private static ArrayList<String> combineLines(String[] lines) {
        String currentLine = "";
        ArrayList<String> combinedLines = new ArrayList<String>();
        for (String line : lines) {
            if(StringUtils.isBlank(line)){
                continue;
            }
            if(startsWithKeyword(line)){
                if(StringUtils.isNotBlank(currentLine)) {
                    combinedLines.add(currentLine);
                }
                currentLine = line;
            } else {
                currentLine = currentLine.concat(System.lineSeparator()).concat(line);
            }
        }
        if(StringUtils.isNotBlank(currentLine)){
            combinedLines.add(currentLine);
        }
        return combinedLines;
    }

    private static boolean startsWithKeyword(String line){
        for (String keyword : keywords) {
            if(line.startsWith(keyword)){
                return true;
            }
        }
        return false;
    }
}
