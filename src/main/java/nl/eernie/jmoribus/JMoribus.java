package nl.eernie.jmoribus;


import nl.eernie.jmoribus.models.Scenario;
import nl.eernie.jmoribus.models.Step;
import nl.eernie.jmoribus.models.Story;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JMoribus {

    private Configuration config;

    public void playAct(List<Story> stories) throws InvocationTargetException, IllegalAccessException {

        List<Object> objects = config.getSteps(new Context());
        MethodMatcher methodMather = new MethodMatcher(objects);
        StepRunner stepRunner = new StepRunner(methodMather);

        ConcurrentReporter reporter = config.getConcurrentReporter();

        for(Story story: stories){
            reporter.beforeStory(story);
            for (Scenario scenario : story.getScenarios()) {
                reporter.beforeScenario(scenario);
                for (Step step : scenario.getSteps()) {
                    reporter.beforeStep(step);
                    PossibleStep matchedStep = methodMather.findMatchedStep(step);
                    if(matchedStep !=null){
                        stepRunner.run(matchedStep,step);
                        reporter.successStep(step);
                    }
                    else{
                        reporter.pendingStep(step);
                    }
                }
                reporter.afterScenario(scenario);
            }
            reporter.afterStory(story);
        }
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }
}
