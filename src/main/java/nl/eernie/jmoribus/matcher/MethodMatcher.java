package nl.eernie.jmoribus.matcher;

import nl.eernie.jmoribus.annotation.*;
import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Step;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodMatcher {

    private List<Object> objects;

    private List<PossibleStep> possibleSteps = new ArrayList<PossibleStep>();

    private List<ParameterConverter> parameterConverters = new ArrayList<ParameterConverter>();

    private StepParser parser = new StepParser();

    public MethodMatcher(List<Object> objects) {
        this.objects = objects;
        createPossibleStepsAndParameterConverters();

    }

    private void createPossibleStepsAndParameterConverters() {
        for (Object object : objects) {
            for (Method method : object.getClass().getMethods()) {
                createPossibleStep(method,object);
                setRegexMatchers();
                createParameterConverter(method, object);
            }
        }
    }

    private void setRegexMatchers() {
        for (PossibleStep possibleStep : possibleSteps) {
            RegexStepMatcher regexStepMatcher = parser.parseStep(possibleStep.getStepType(), possibleStep.getStep());
            possibleStep.setRegexStepMatcher(regexStepMatcher);
        }
    }

    private void createParameterConverter(Method method, Object object) {
        if(method.isAnnotationPresent(nl.eernie.jmoribus.annotation.ParameterConverter.class)){
            Class<?> returnType = method.getReturnType();
            parameterConverters.add(new ParameterConverter(method,object,returnType));

        }
    }

    private void createPossibleStep(Method method, Object object) {
        if (method.isAnnotationPresent(Given.class)) {
            Given annotation = method.getAnnotation(Given.class);
            possibleSteps.add(new PossibleStep(annotation.value(), method, Feature.StepType.GIVEN, object));
        }
        if (method.isAnnotationPresent(When.class)) {
            When annotation = method.getAnnotation(When.class);
            possibleSteps.add(new PossibleStep(annotation.value(), method, Feature.StepType.WHEN, object));
        }
        if (method.isAnnotationPresent(Then.class)) {
            Then annotation = method.getAnnotation(Then.class);
            possibleSteps.add(new PossibleStep(annotation.value(), method, Feature.StepType.THEN, object));
        }
    }

    public PossibleStep findMatchedStep(Step step) {
        for (PossibleStep possibleStep : possibleSteps) {
            if(possibleStep.getStepType().equals(step.getStepType())) {
                if (possibleStep.getRegexStepMatcher().matches(step.getValue())) {
                    return possibleStep;
                }
            }
        }
        return null;
    }

    public ParameterConverter findConverterFor(Class<?> parameterType) {
        for (ParameterConverter parameterConverter : parameterConverters) {
            if(parameterConverter.getReturnType().equals(parameterType)){
                return parameterConverter;
            }
        }
        throw new RuntimeException("Converter not found"); // TODO: refactor me!
    }
}
