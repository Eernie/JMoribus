package nl.eernie.jmoribus;


import java.lang.reflect.Method;

public class PossibleStep {
    private String step;
    private Method method;
    private StepType stepType;
    private RegexStepMatcher regexStepMatcher;

    public PossibleStep(String step, Method method, StepType stepType) {
        this.step = step;
        this.method = method;
        this.stepType = stepType;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public void setRegexStepMatcher(RegexStepMatcher regexStepMatcher) {
        this.regexStepMatcher = regexStepMatcher;
    }

    public RegexStepMatcher getRegexStepMatcher() {
        return regexStepMatcher;
    }
}
