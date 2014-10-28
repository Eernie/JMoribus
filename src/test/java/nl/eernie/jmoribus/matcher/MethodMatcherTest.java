package nl.eernie.jmoribus.matcher;

import nl.eernie.jmoribus.Steps;
import nl.eernie.jmoribus.exception.NoParameterConverterFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MethodMatcherTest {

    @Test
    public void matcherTest() {

        MethodMatcher methodMatcher = new MethodMatcher(Arrays.<Object>asList(new Steps()));

        ParameterConverter integerConverter = methodMatcher.findConverterFor(Integer.class);

        Assert.assertEquals(Integer.class, integerConverter.getReturnType());

        List<BeforeAfterMethod> beforeAfterMethods = methodMatcher.findBeforeAfters(BeforeAfterType.BEFORE_STORY);
        Assert.assertEquals(BeforeAfterType.BEFORE_STORY, beforeAfterMethods.get(0).getBeforeAfterType());

        beforeAfterMethods = methodMatcher.findBeforeAfters(BeforeAfterType.AFTER_SCENARIO);
        Assert.assertEquals(BeforeAfterType.AFTER_SCENARIO, beforeAfterMethods.get(0).getBeforeAfterType());

        beforeAfterMethods = methodMatcher.findBeforeAfters(BeforeAfterType.AFTER_STORY);
        Assert.assertEquals(BeforeAfterType.AFTER_STORY, beforeAfterMethods.get(0).getBeforeAfterType());

        beforeAfterMethods = methodMatcher.findBeforeAfters(BeforeAfterType.BEFORE_SCENARIO);
        Assert.assertEquals(BeforeAfterType.BEFORE_SCENARIO, beforeAfterMethods.get(0).getBeforeAfterType());
    }

    @Test(expected = NoParameterConverterFoundException.class)
    public void noParameterConverterFoundExceptionTest() {
        MethodMatcher methodMatcher = new MethodMatcher(Arrays.<Object>asList(new Steps()));
        methodMatcher.findConverterFor(Test.class);
    }

}
