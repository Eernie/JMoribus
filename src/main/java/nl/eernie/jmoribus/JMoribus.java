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

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        JMoribus jMoribus = new JMoribus();
        jMoribus.setConfig(new ConfigurationImpl());
        Story story = new Story();
        Scenario scenario = new Scenario();
        Step step = new Step("dddd Dit is een hele lange var more text",StepType.WHEN);
        Step step2 = new Step("dddd $testvar more text",StepType.THEN);
        Step step3 = new Step("bla bla bla 400", StepType.WHEN);
        scenario.setSteps(Arrays.asList(step,step2,step3));
        story.setScenarios(Arrays.asList(scenario));
        jMoribus.playAct(Arrays.asList(story));
    }

    public void playAct(List<Story> stories) throws InvocationTargetException, IllegalAccessException {

        List<Object> objects = config.getSteps(new Context());
        MethodMatcher methodMather = new MethodMatcher(objects);
        StepRunner stepRunner = new StepRunner(methodMather);

        for(Story story: stories){
            for (Scenario scenario : story.getScenarios()) {
                for (Step step : scenario.getSteps()) {
                    PossibleStep matchedStep = methodMather.findMatchedStep(step);
                    if(matchedStep !=null){
                        stepRunner.run(matchedStep,step);
                    }
                    else{
                        System.out.println("Step was pending : "+ step.getValue());
                    }
                }
            }
        }

    }



    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }
}
