package com.gmail.sleepy771.workcount.diff.exceptions;

/**
 * Created by filip on 6/3/15.
 */
public class InvalidPatchableClassException extends Exception {

    private final Class invalidClass;

    public InvalidPatchableClassException(Class invalidClass, String message) {
        super("Inavlid class [" + invalidClass.getName() + "]\nError message:\n\t" + message);
        this.invalidClass = invalidClass;
    }

    public Class getInvalidClass() {
        return invalidClass;
    }
}
