package com.gmail.sleepy771.workcount.diff.exceptions;

/**
 * Created by filip on 6/4/15.
 */
public class FieldPropertyException extends Exception {
    public FieldPropertyException(String propertyName, String message) {
        super("PatchProperty: " + propertyName + "\nMessage:\n" + message);
    }

    public FieldPropertyException(String propertyName, Class type, String message) {
        super("PatchProperty: " + propertyName + " of type:\n\t" + type.getName() + "\nMessage:\n" + message);
    }

    public FieldPropertyException(String propertyName, Throwable cause) {
        super("PatchProperty: " + propertyName + " failed", cause);
    }
}
