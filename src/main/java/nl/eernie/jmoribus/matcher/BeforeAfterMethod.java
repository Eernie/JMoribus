package nl.eernie.jmoribus.matcher;

import java.lang.reflect.Method;

public class BeforeAfterMethod {

    private Method method;
    private Object methodObject;
    private BeforeAfterType beforeAfterType;

    public BeforeAfterMethod(Method method, Object methodObject, BeforeAfterType beforeAfterType) {
        this.method = method;
        this.methodObject = methodObject;
        this.beforeAfterType = beforeAfterType;
    }

    public Method getMethod() {
        return method;
    }

    public Object getMethodObject() {
        return methodObject;
    }

    public BeforeAfterType getBeforeAfterType() {
        return beforeAfterType;
    }
}
