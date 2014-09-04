package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.matcher.MethodMatcher;
import nl.eernie.jmoribus.matcher.ParameterConverter;
import nl.eernie.jmoribus.model.Table;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ReflectionParserTest {

    @Test
    public void testSingleObject() throws InvocationTargetException, IllegalAccessException {

        Table table = new Table();
        table.setHeader(Arrays.asList("columnA", "columnB","columnC"));
        table.getRows().add(Arrays.asList("aaaa","bbb","100"));

        Object o = ReflectionParser.parse(table, TestObject.class, mockMethodMatcher());

        Assert.assertTrue(o instanceof TestObject);
        Assert.assertEquals("aaaa", ((TestObject) o).getColumnA());
        Assert.assertEquals("bbb",((TestObject) o).getColumnB());
        Assert.assertSame(100, ((TestObject) o).getColumnC());

    }

    @Test
    public void testListObject() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Table table = new Table();
        table.setHeader(Arrays.asList("columnA", "columnB","columnC"));
        table.getRows().add(Arrays.asList("aaaa", "bbb", "100"));
        table.getRows().add(Arrays.asList("aaaa2","bbb2","100"));

        Class<ListTest> listTestClass = ListTest.class;
        Method testMethod = listTestClass.getMethod("testMethod", List.class);
        Type type = testMethod.getGenericParameterTypes()[0];

        Object list = ReflectionParser.parse(table, type, mockMethodMatcher());

        Assert.assertTrue(list instanceof List);
        TestObject o = (TestObject) ((List) list).get(0);
        Assert.assertEquals("aaaa", o.getColumnA());
        Assert.assertEquals("bbb", o.getColumnB());
        Assert.assertSame(100, o.getColumnC());

        o = (TestObject) ((List) list).get(1);
        Assert.assertEquals("aaaa2", o.getColumnA());
        Assert.assertEquals("bbb2", o.getColumnB());
        Assert.assertSame(100, o.getColumnC());

    }

    private MethodMatcher mockMethodMatcher() throws InvocationTargetException, IllegalAccessException {

        MethodMatcher methodMatcher = mock(MethodMatcher.class);
        ParameterConverter converter = mock(ParameterConverter.class);
        when(methodMatcher.findConverterFor(Integer.class)).thenReturn(converter);
        when(converter.convert(any())).thenReturn(100);

        return methodMatcher;
    }

    private class ListTest{
        public void testMethod(List<TestObject> testObjects){

        }
    }

}
