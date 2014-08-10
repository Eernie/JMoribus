package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsBaseListener;
import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.model.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GherkinsListener extends GherkinsBaseListener {

    private Story story;
    private Feature feature;
    private StepTeller stepTeller;
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
        feature.setTitle(ctx.getText().trim());
    }

    @Override
    public void exitFeature_content(@NotNull GherkinsParser.Feature_contentContext ctx) {
        feature.setContent(ctx.getText().trim());
    }

    @Override
    public void exitScenario_title(@NotNull GherkinsParser.Scenario_titleContext ctx) {
        ((Scenario)stepTeller).setTitle(ctx.getText().trim());
    }

    @Override
    public void enterScenario(@NotNull GherkinsParser.ScenarioContext ctx) {
        stepTeller = new Scenario();
        stepTeller.setStory(story);
        story.getScenarios().add(((Scenario)stepTeller));
    }

    @Override
    public void enterPrologue(@NotNull GherkinsParser.PrologueContext ctx) {
        stepTeller = new Prologue();
        stepTeller.setStory(story);
        story.setPrologue((Prologue) stepTeller);
    }

    @Override
    public void exitScenario(@NotNull GherkinsParser.ScenarioContext ctx) {
        scenarios.put(((Scenario)stepTeller).getTitle(), ((Scenario)stepTeller));
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
            step = new Step(ctx.getText().trim(),stepType);
        }else{
            step = scenarios.get(ctx.getText().trim());
        }
        step.setStepTeller(stepTeller);
        stepTeller.getSteps().add(step);
    }

    public Story getStory() {
        return story;
    }

    public void setKnownScenarios(Map<String,Scenario> knownScenarios) {
        this.scenarios = knownScenarios;
    }
}