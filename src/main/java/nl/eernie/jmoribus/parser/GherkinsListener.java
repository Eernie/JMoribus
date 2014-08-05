package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.GherkinsParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class GherkinsListener  implements nl.eernie.jmoribus.GherkinsListener{
    @Override
    public void enterStory(@NotNull GherkinsParser.StoryContext ctx) {
        ctx.depth();
    }

    @Override
    public void exitStory(@NotNull GherkinsParser.StoryContext ctx) {
        ctx.depth();
    }

    @Override
    public void enterTitle(@NotNull GherkinsParser.TitleContext ctx) {

    }

    @Override
    public void exitTitle(@NotNull GherkinsParser.TitleContext ctx) {

    }

    @Override
    public void enterScenario(@NotNull GherkinsParser.ScenarioContext ctx) {

    }

    @Override
    public void exitScenario(@NotNull GherkinsParser.ScenarioContext ctx) {

    }

    @Override
    public void enterPrologue(@NotNull GherkinsParser.PrologueContext ctx) {

    }

    @Override
    public void exitPrologue(@NotNull GherkinsParser.PrologueContext ctx) {

    }

    @Override
    public void enterStep_keyword(@NotNull GherkinsParser.Step_keywordContext ctx) {

    }

    @Override
    public void exitStep_keyword(@NotNull GherkinsParser.Step_keywordContext ctx) {

    }

    @Override
    public void enterLine(@NotNull GherkinsParser.LineContext ctx) {

    }

    @Override
    public void exitLine(@NotNull GherkinsParser.LineContext ctx) {

    }

    @Override
    public void enterScenario_keyword(@NotNull GherkinsParser.Scenario_keywordContext ctx) {

    }

    @Override
    public void exitScenario_keyword(@NotNull GherkinsParser.Scenario_keywordContext ctx) {

    }

    @Override
    public void enterPrologue_keyword(@NotNull GherkinsParser.Prologue_keywordContext ctx) {

    }

    @Override
    public void exitPrologue_keyword(@NotNull GherkinsParser.Prologue_keywordContext ctx) {

    }

    @Override
    public void enterFeature_keyword(@NotNull GherkinsParser.Feature_keywordContext ctx) {

    }

    @Override
    public void exitFeature_keyword(@NotNull GherkinsParser.Feature_keywordContext ctx) {

    }

    @Override
    public void enterStep(@NotNull GherkinsParser.StepContext ctx) {

    }

    @Override
    public void exitStep(@NotNull GherkinsParser.StepContext ctx) {

    }

    @Override
    public void visitTerminal(@NotNull TerminalNode node) {

    }

    @Override
    public void visitErrorNode(@NotNull ErrorNode node) {

    }

    @Override
    public void enterEveryRule(@NotNull ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {

    }
}
