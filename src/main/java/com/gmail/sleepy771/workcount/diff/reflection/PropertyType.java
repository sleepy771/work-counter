package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by filip on 5/25/15.
 */
public enum PropertyType {
    SETTER((methodName, propertyName) -> {
        if (methodName.startsWith("set")) {
            return Character.toString(methodName.charAt(3)).toLowerCase() + methodName.substring(4);
        } else {
            throw new IllegalArgumentException("Could not determine property name");
        }
    }, method -> {
        if (method == null || Method.class != method.getClass())
            throw new IllegalArgumentException();
        Method m = (Method) method;
        if (!(m.getParameterCount() == 1 && void.class == m.getReturnType())) {
            throw new IllegalArgumentException();
        }
        return m.getParameterTypes()[0];
    }),
    GETTER((methodName, propertyName) -> {
        if (methodName.startsWith("get")) {
            return Character.toString(methodName.charAt(3)).toLowerCase() + methodName.substring(4);
        } else {
            return methodName;
        }
    }, method -> {
        if (method == null || Method.class != method.getClass())
            throw new IllegalArgumentException();
        Method m = (Method) method;
        if (m.getParameterCount() != 0) {
            throw new IllegalArgumentException();
        }
        return m.getReturnType();
    }),
    FIELD((fieldName, propertyName) -> fieldName, field -> {
        if (field == null || Field.class != field.getClass()) {
            throw new IllegalArgumentException();
        }
        return ((Field) field).getType();
    });

    private final PropertyNameHandler handler;

    private final PropertyTypeReceiver typeReceiver;

    PropertyType(PropertyNameHandler handler, PropertyTypeReceiver typeReceiver) {
        this.handler = handler;
        this.typeReceiver = typeReceiver;
    }

    public String getPropertyName(String methodName, String propertyName) {
        if ("-".equals(propertyName)) {
            return handler.choosePropertyName(methodName, propertyName);
        } else {
            return propertyName;
        }
    }

    public Class getPropertyType(Object o) {
        return this.typeReceiver.getType(o);
    }
}
