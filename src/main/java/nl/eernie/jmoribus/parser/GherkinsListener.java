package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsBaseListener;
import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.model.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GherkinsListener extends GherkinsBaseListener {

    private Story story;
    private Feature feature;
    private Scenario scenario;
    private Map<String,Scenario> scenarios = new HashMap<String, Scenario>();

    private StepType stepType;


    @Override
    public void enterStory(@NotNull GherkinsParser.StoryContext ctx) {
        story = new Story();
    }

    @Override
    public void enterFeature(@NotNull GherkinsParser.FeatureContext ctx) {
        feature = new Feature();
        story.setFeature(feature);
    }

    @Override
    public void exitFeature_title(@NotNull GherkinsParser.Feature_titleContext ctx) {
        feature.setTitle(ctx.getText());
    }

    @Override
    public void exitScenario_title(@NotNull GherkinsParser.Scenario_titleContext ctx) {
        scenario.setTitle(ctx.getText());
    }

    @Override
    public void enterScenario(@NotNull GherkinsParser.ScenarioContext ctx) {
        scenario = new Scenario();
        story.getScenarios().add(scenario);
    }

    @Override
    public void enterPrologue(@NotNull GherkinsParser.PrologueContext ctx) {
        scenario = new Scenario();
        story.getScenarios().add(scenario);
    }

    @Override
    public void exitScenario(@NotNull GherkinsParser.ScenarioContext ctx) {
        scenarios.put(scenario.getTitle(),scenario);
    }

    @Override
    public void exitStep_keyword(@NotNull GherkinsParser.Step_keywordContext ctx) {
        switch (ctx.getText()){
            case "Given":
                stepType = StepType.GIVEN;
                break;
            case "When":
                stepType = StepType.WHEN;
                break;
            case "Then":
                stepType = StepType.THEN;
                break;
            case "Revering:":
                stepType = StepType.REVERING;
                break;
        }
    }

    @Override
    public void exitStep_line(@NotNull GherkinsParser.Step_lineContext ctx) {
        Step step;
        if(stepType != StepType.REVERING){
            step = new Step(ctx.getText(),stepType);
        }else{
            step = scenarios.get(ctx.getText());
        }
        scenario.getSteps().add(step);
    }

    public Story getStory() {
        return story;
    }
}