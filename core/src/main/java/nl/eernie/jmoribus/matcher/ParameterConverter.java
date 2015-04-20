package nl.eernie.jmoribus.matcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParameterConverter
{
    private final Method method;
    private final Object methodObject;
    private final Class<?> returnType;

    public ParameterConverter(Method method, Object methodObject, Class<?> returnType)
    {
        this.method = method;
        this.methodObject = methodObject;
        this.returnType = returnType;
    }

    public Class<?> getReturnType()
    {
        return returnType;
    }

    public Object convert(Object... args) throws InvocationTargetException, IllegalAccessException
    {
        return method.invoke(methodObject, args);
    }
}
