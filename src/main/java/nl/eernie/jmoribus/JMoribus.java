package nl.eernie.jmoribus;


import java.util.List;
import java.util.Map;

public class JMoribus {

    private Configuration config;

    public static void main(String[] args) {
        JMoribus jMoribus = new JMoribus();
        jMoribus.setConfig(new ConfigurationImpl());
        jMoribus.playAct("dddd ddd more text");
    }

    public void playAct(String act) {

        List<Object> objects = config.getSteps(new Context());
        List<PossibleStep> possibleSteps = MethodMatcher.createPossibleSteps(objects);

        StepParser parser = new StepParser();
        for (PossibleStep possibleStep : possibleSteps) {
            RegexStepMatcher regexStepMatcher = parser.parseStep(possibleStep.getStepType(), possibleStep.getStep());
            possibleStep.setRegexStepMatcher(regexStepMatcher);

        }

    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }
}
