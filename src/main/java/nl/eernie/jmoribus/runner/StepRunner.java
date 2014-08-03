package nl.eernie.jmoribus.runner;


import nl.eernie.jmoribus.matcher.*;
import nl.eernie.jmoribus.model.Step;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class StepRunner {

    private MethodMatcher methodMatcher;

    public StepRunner(MethodMatcher methodMatcher){

        this.methodMatcher = methodMatcher;
    }

    public void run(PossibleStep matchedStep, Step step) throws Throwable {
        List<String> parameterValues = matchedStep.getRegexStepMatcher().getParameterValues(step);
        try {
            Object[] parameters = createParameters(matchedStep.getMethod(), parameterValues);
            matchedStep.getMethod().invoke(matchedStep.getMethodObject(),parameters);
        } catch (IllegalAccessException e) {
            throw e.getCause();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }

    }

    private Object[] createParameters(Method method, List<String> parameterValues) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        if(parameterValues.size()!=parameterTypes.length){
            throw new RuntimeException("Velden en waarden komen niet overeen"); //TODO: refactor me
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            String parameterValue = parameterValues.get(i);
            if(parameterType.equals(String.class)){
                parameters[i] = parameterValue;
            }else{
                ParameterConverter converter = methodMatcher.findConverterFor(parameterType);
                parameters[i] = converter.getMethod().invoke(converter.getMethodObject(), parameterValue);
            }
        }
        return parameters;
    }

    public void runBeforeAfter(BeforeAfterType beforeAfterType){
        List<BeforeAfterMethod> methods = methodMatcher.findBeforeAfters(beforeAfterType);
        if(methods != null){
            for (BeforeAfterMethod method : methods) {
                try {
                    method.getMethod().invoke(method.getMethodObject());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
