package com.gmail.sleepy771.workcount.diff.annotations;

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
    }),
    GETTER((methodName, propertyName) -> {
        if (methodName.startsWith("get")) {
            return Character.toString(methodName.charAt(3)).toLowerCase() + methodName.substring(4);
        } else {
            return methodName;
        }
    }),
    FIELD((fieldName, propertyName) -> fieldName);

    private final PropertyNameHandler handler;

    PropertyType(PropertyNameHandler handler) {
        this.handler = handler;
    }

    public String getPropertyName(String methodName, String propertyName) {
        if ("-".equals(propertyName)) {
            return handler.choosePropertyName(methodName, propertyName);
        } else {
            return propertyName;
        }
    }
}
