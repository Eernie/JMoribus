package nl.eernie.jmoribus.matcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeforeAfterMethod {

    private final Method method;
    private final Object methodObject;
    private final BeforeAfterType beforeAfterType;

    public BeforeAfterMethod(Method method, Object methodObject, BeforeAfterType beforeAfterType) {
        this.method = method;
        this.methodObject = methodObject;
        this.beforeAfterType = beforeAfterType;
    }

    public BeforeAfterType getBeforeAfterType() {
        return beforeAfterType;
    }

    public void invoke() throws InvocationTargetException, IllegalAccessException {
        method.invoke(methodObject);
    }

}
