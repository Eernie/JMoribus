package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsBaseListener;
import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.model.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GherkinsListener extends GherkinsBaseListener {

    private Story story;
    private Feature feature;
    private StepContainer prologueOrScenario;

    private StepType stepType;
    private Table table = new Table();
    private List<String> row = new ArrayList<>();

    @Override
    public void enterStory(@NotNull GherkinsParser.StoryContext ctx) {
        story = new Story();
    }

    @Override
    public void enterFeature(@NotNull GherkinsParser.FeatureContext ctx) {
        feature = new Feature();
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
    public void exitFeature(@NotNull GherkinsParser.FeatureContext ctx) {
        story.setFeature(feature);
        feature = null;
    }

    @Override
    public void enterScenario(@NotNull GherkinsParser.ScenarioContext ctx) {
        prologueOrScenario = new Scenario();
    }

    @Override
    public void exitScenario_title(@NotNull GherkinsParser.Scenario_titleContext ctx) {
        ((Scenario) prologueOrScenario).setTitle(ctx.getText().trim());
    }

    @Override
    public void exitScenario(@NotNull GherkinsParser.ScenarioContext ctx) {
        story.getScenarios().add((Scenario)prologueOrScenario);
        prologueOrScenario = null;
    }

    @Override
    public void enterPrologue(@NotNull GherkinsParser.PrologueContext ctx) {
        prologueOrScenario = new Prologue();
    }

    @Override
    public void exitPrologue(@NotNull GherkinsParser.PrologueContext ctx) {
        story.setPrologue((Prologue) prologueOrScenario);
        prologueOrScenario = null;
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
            case "And":
                // Steptype stays the same as previous steptype
                break;
            case "Referring":
                stepType = StepType.REFERRING;
                break;
            default:
                throw new IllegalArgumentException("Unknown step type " + ctx.getText());
        }
    }

    @Override
    public void enterTable(@NotNull GherkinsParser.TableContext ctx) {
        table = new Table();
    }

    @Override
    public void exitCell(@NotNull GherkinsParser.CellContext ctx) {
        String cell = ctx.getText();
        cell = cell.replace("|","").trim();
        row.add(cell);
    }

    @Override
    public void exitTable_row(@NotNull GherkinsParser.Table_rowContext ctx) {
        if(table.getHeader() == null){
            table.setHeader(row);
        }else{
            table.getRows().add(row);
        }
        row = new ArrayList<>();
    }

    @Override
    public void exitTable(@NotNull GherkinsParser.TableContext ctx) {
        if(ctx.getParent() instanceof GherkinsParser.ExamplesContext){
            prologueOrScenario.getSteps().add(new Step(table,StepType.EXAMPLES));
        }else{
            // add table to step
        }
        table = null;
    }

    @Override
    public void exitStep_line(@NotNull GherkinsParser.Step_lineContext ctx) {
        prologueOrScenario.getSteps().add(new Step(new Line(ctx.getText().trim()), stepType));
    }

    public Story getStory() {
        return story;
    }
}