package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MethodMatcher {

    private MethodMatcher() {
    }

    public static List<PossibleStep> createPossibleSteps(List<Object> objects) {
        List<PossibleStep> possibleSteps = new ArrayList<PossibleStep>();
        for (Object object : objects) {
            for (Method method : object.getClass().getMethods()) {
                PossibleStep possibleStep = createPossibleStep(method);
                if(possibleStep!=null){
                    possibleSteps.add(possibleStep);
                }
            }
        }
        return possibleSteps;
    }



    public static PossibleStep createPossibleStep(Method method) {
        if (method.isAnnotationPresent(Given.class)) {
            Given annotation = method.getAnnotation(Given.class);
            return new PossibleStep(annotation.value(), method, StepType.GIVEN);
        }
        if (method.isAnnotationPresent(When.class)) {
            When annotation = method.getAnnotation(When.class);
            return new PossibleStep(annotation.value(), method, StepType.WHEN);
        }
        if (method.isAnnotationPresent(Then.class)) {
            Then annotation = method.getAnnotation(Then.class);
            return new PossibleStep(annotation.value(), method, StepType.THEN);
        }
        return null;
    }

}
