package nl.eernie.jmoribus.matcher;


import nl.eernie.jmoribus.model.StepType;

import java.lang.reflect.Method;

public class PossibleStep {
    private String step;
    private Method method;
    private Object methodObject;
    private StepType stepType;
    private RegexStepMatcher regexStepMatcher;
    private String[] categories;
    private String[] requiredVariables;
    private String[] outputVariables;

    public PossibleStep(String step, Method method, StepType stepType, Object object, String[] categories, String[] requiredVariables, String[] outputVariables) {
        this.step = step;
        this.method = method;
        this.stepType = stepType;
        this.methodObject = object;
        this.categories = categories;
        this.requiredVariables = requiredVariables;
        this.outputVariables = outputVariables;
    }

    public String getStep() {
        return step;
    }

    public Method getMethod() {
        return method;
    }

    public Object getMethodObject() {
        return methodObject;
    }

    public StepType getStepType() {
        return stepType;
    }

    public RegexStepMatcher getRegexStepMatcher() {
        return regexStepMatcher;
    }

    public void setRegexStepMatcher(RegexStepMatcher regexStepMatcher) {
        this.regexStepMatcher = regexStepMatcher;
    }

    public String[] getCategories() {
        return categories;
    }

    public String[] getRequiredVariables() {
        return requiredVariables;
    }

    public String[] getOutputVariables() {
        return outputVariables;
    }
}
