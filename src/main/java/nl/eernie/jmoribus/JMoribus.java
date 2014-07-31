package nl.eernie.jmoribus;


import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.configuration.Context;
import nl.eernie.jmoribus.matcher.BeforeAfterType;
import nl.eernie.jmoribus.matcher.MethodMatcher;
import nl.eernie.jmoribus.matcher.PossibleStep;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepTeller;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.reporter.Reporter;
import nl.eernie.jmoribus.runner.StepRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class JMoribus {

    private Configuration config;

    public void playAct(List<Story> stories) throws InvocationTargetException, IllegalAccessException {

        List<Object> objects = config.getSteps(new Context());
        MethodMatcher methodMather = new MethodMatcher(objects);
        StepRunner stepRunner = new StepRunner(methodMather);

        Reporter reporter = config.getConcurrentReporter();


        for(Story story: stories){
            reporter.beforeStory(story);
            if(story.getFeature() != null){
                reporter.feature(story.getFeature());
            }
            stepRunner.runBeforeAfter(BeforeAfterType.BEFORE_STORY);
            reporter.beforeBackground(story.getBackground());
            runStepTeller(methodMather, stepRunner, reporter, story.getBackground());
            for (Scenario scenario : story.getScenarios()) {
                reporter.beforeScenario(scenario);
                stepRunner.runBeforeAfter(BeforeAfterType.BEFORE_SCENARIO);
                runStepTeller(methodMather, stepRunner, reporter, scenario);
                reporter.afterScenario(scenario);
                stepRunner.runBeforeAfter(BeforeAfterType.AFTER_SCENARIO);
            }
            reporter.afterStory(story);
            stepRunner.runBeforeAfter(BeforeAfterType.AFTER_STORY);
        }
    }

    private void runStepTeller(MethodMatcher methodMather, StepRunner stepRunner, Reporter reporter, StepTeller stepTeller) {
        if(stepTeller == null){
            return;
        }
        for (Step step : stepTeller.getSteps()) {
            reporter.beforeStep(step);
            PossibleStep matchedStep = methodMather.findMatchedStep(step);
            if(matchedStep !=null){
                try{
                    stepRunner.run(matchedStep,step);
                    reporter.successStep(step);
                }catch(AssertionError e){
                    reporter.failedStep(step, e);
                }catch(Throwable e){
                    reporter.errorStep(step, e);
                }
            }
            else{
                reporter.pendingStep(step);
            }
        }
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }
}
