package nl.eernie.jmoribus.matcher;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexStepMatcher {

    private final Pattern regexPattern;
    private final String[] parameterNames;
    private final StepType stepType;
    private final String annotatedPattern;
    private Matcher matcher;


    public RegexStepMatcher(StepType stepType, String annotatedPattern, Pattern regexPattern, String[] parameterNames) {
        this.regexPattern = regexPattern;
        this.parameterNames = parameterNames;
        this.stepType = stepType;
        this.annotatedPattern = annotatedPattern;
    }

    public boolean matches(String stepWithoutStartingWord) {
        matcher(stepWithoutStartingWord);
        return matcher.matches();
    }

    public boolean find(String stepWithoutStartingWord) {
        matcher(stepWithoutStartingWord);
        return matcher.find();
    }

    public String parameter(int matchedPosition) {
        return matcher.group(matchedPosition);
    }

    private void matcher(String patternToMatch) {
        matcher = regexPattern.matcher(patternToMatch);
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public String getAnnotatedPattern() {
        return annotatedPattern;
    }

    public StepType getStepType() {
        return stepType;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    public Pattern getRegexPattern() {
        return regexPattern;
    }

    public List<String> getParameterValues(Step step) {
        Matcher matcher = regexPattern.matcher(step.getValue());
        matcher.find();
        List<String> parameters = new ArrayList<String>();
        if(matcher.groupCount()>0){
            for (int i = 1; i <= matcher.groupCount(); i++) {
                parameters.add(matcher.group(i));
            }
        }
        return parameters;
    }
}