package nl.eernie.jmoribus.runner;


import nl.eernie.jmoribus.configuration.Configuration;
import nl.eernie.jmoribus.matcher.*;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepLine;
import nl.eernie.jmoribus.model.Table;
import nl.eernie.jmoribus.parser.ReflectionParser;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class StepRunner {

    private Configuration config;
    private MethodMatcher methodMatcher;

    public StepRunner(MethodMatcher methodMatcher, Configuration config) {
        this.config = config;
        this.methodMatcher = methodMatcher;
    }

    public void run(PossibleStep matchedStep, Step step) throws Throwable {
        List<String> parameterValues = matchedStep.getRegexStepMatcher().getParameterValues(step);
        Object[] parameters = createParameters(matchedStep.getMethod(), parameterValues, step);
        matchedStep.getMethod().invoke(matchedStep.getMethodObject(), parameters);
    }

    private Object[] createParameters(Method method, List<String> parameterValues, Step step) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        int extraValues = ArrayUtils.contains(parameterTypes, WebDriver.class)? 1 : 0;
        if (parameterValues.size() + extraValues != parameterTypes.length) {
            throw new RuntimeException("Velden en waarden komen niet overeen"); //TODO: refactor me
        }
        Iterator<String> values = parameterValues.iterator();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if(parameterType.equals(WebDriver.class)) {
                parameters[i] = config.getWebDriver();
            }else{
                String parameterValue = values.next();
                if (parameterType.equals(String.class)) {
                    parameters[i] = parameterValue;
                } else if (parameterType.equals(Table.class)) {
                    StepLine table = getTable(step, parameterValue);
                    parameters[i] = table;
                } else if (parameterValue.startsWith("TABLE")) {
                    StepLine table = getTable(step, parameterValue);
                    Object object = ReflectionParser.parse((Table) table, method.getGenericParameterTypes()[i], methodMatcher);
                    parameters[i] = object;
                }
                if (parameters[i] == null) {
                    ParameterConverter converter = methodMatcher.findConverterFor(parameterType);
                    parameters[i] = converter.convert(parameterValue);
                }
            }
        }
        return parameters;
    }

    private StepLine getTable(Step step, String parameterValue) {
        String indexString = parameterValue.replace("TABLE", "");
        int lineIndex = Integer.valueOf(indexString);
        return step.getStepLines().get(lineIndex);
    }

    public void runBeforeAfter(BeforeAfterType beforeAfterType) {
        List<BeforeAfterMethod> methods = methodMatcher.findBeforeAfters(beforeAfterType);
        if (methods != null) {
            for (BeforeAfterMethod method : methods) {
                try {
                    method.invoke();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
