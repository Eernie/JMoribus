package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.matcher.MethodMatcher;
import nl.eernie.jmoribus.matcher.ParameterConverter;
import nl.eernie.jmoribus.model.Table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

final class ReflectionParser {

    private ReflectionParser() {
    }

    public static Object parse(Table table, Type type, MethodMatcher methodMatcher) {

        try {
            if (type instanceof ParameterizedType) {
                Type rawClass = rawClass(type);
                if (List.class.isAssignableFrom((Class<?>) rawClass)) {
                    Class<?> argumentType = (Class<?>) argumentType(type);
                    List<Object> list = new ArrayList<>();
                    for (List<String> row : table.getRows()) {
                        list.add(parse(table.getHeader(), row, argumentType, methodMatcher));
                    }
                    return list;
                }
            } else if (type instanceof Class<?>) {
                return parse(table.getHeader(), table.getRows().get(0), (Class<?>) type, methodMatcher);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private static Object parse(List<String> header, List<String> row, Class<?> argumentType, MethodMatcher methodMatcher) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object object = argumentType.newInstance();
        for (int i = 0; i < header.size(); i++) {
            for (Method method : argumentType.getMethods()) {
                if (method.getName().equalsIgnoreCase("set" + header.get(i))) {
                    Class<?> parameter = method.getParameterTypes()[0];
                    if (parameter.equals(String.class)) {
                        method.invoke(object, row.get(i));
                        break;
                    } else {
                        ParameterConverter parameterConverter = methodMatcher.findConverterFor(parameter);
                        Object value = parameterConverter.convert(row.get(i));
                        method.invoke(object, value);
                        break;
                    }
                }
            }
        }
        return object;
    }

    private static Type rawClass(Type type) {
        return ((ParameterizedType) type).getRawType();
    }

    private static Type argumentType(Type type) {
        return ((ParameterizedType) type).getActualTypeArguments()[0];
    }

}
