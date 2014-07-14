package nl.eernie.jmoribus.matcher;

import java.lang.reflect.Method;

public class ParameterConverter {

    private Method method;
    private Object methodObject;
    private Class<?> returnType;

    public ParameterConverter(Method method, Object methodObject, Class<?> returnType) {
        this.method = method;
        this.methodObject = methodObject;
        this.returnType = returnType;
    }

    public Method getMethod() {
        return method;
    }

    public Object getMethodObject() {
        return methodObject;
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}
