package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StoryParser {

    public static final String FEATURE = "Feature: ";
    public static final String SCENARIO = "Scenario: ";
    public static final String GIVEN = "Given ";
    public static final String WHEN = "When ";
    public static final String THEN = "Then ";
    public static final String AND = "And ";
    public static final String COMMENT = "!--";
    private static final String[] keywords = new String[]{FEATURE, SCENARIO, GIVEN, WHEN, THEN, AND, COMMENT};

    private StoryParser(){}

    public static Story parseStory(InputStream stream, String uniqueIdentifier, String title) {
        try {
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(stream,stringWriter);
            String completeStory = stringWriter.toString();

            String lines[] = completeStory.split("\\r?\\n");
            Story story = buildModels(lines);
            story.setTitle(title);
            story.setUniqueIdentifier(uniqueIdentifier);
            return story;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Story buildModels(String[] lines) {
        ArrayList<String> combinedLines = combineLines(lines);
        Story story = new Story();
        Scenario scenario = new Scenario();
        StepType lastType = null;
        for(String line: combinedLines){
            if(line.startsWith(FEATURE)){
                story.setFeature(parseFeature(line));
            }
            else if(line.startsWith(SCENARIO)){
                scenario = parseScenario(line);
                story.getScenarios().add(scenario);
                scenario.setStory(story);
            }
            else if(line.startsWith(GIVEN)){
                Step step = parseStep(line, GIVEN, StepType.GIVEN);
                lastType = StepType.GIVEN;
                step.setScenario(scenario);
                scenario.getSteps().add(step);
            }
            else if(line.startsWith(WHEN)){
                Step step = parseStep(line, WHEN, StepType.WHEN);
                lastType = StepType.WHEN;
                step.setScenario(scenario);
                scenario.getSteps().add(step);
            }
            else if(line.startsWith(THEN)){
                Step step = parseStep(line,THEN, StepType.THEN);
                lastType = StepType.THEN;
                step.setScenario(scenario);
                scenario.getSteps().add(step);
            }
            else if(line.startsWith(AND)){
                Step step = parseStep(line, AND , lastType);
                step.setScenario(scenario);
                scenario.getSteps().add(step);
            }

        }
        return story;
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
