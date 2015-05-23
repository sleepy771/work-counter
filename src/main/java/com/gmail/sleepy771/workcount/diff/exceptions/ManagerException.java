package com.gmail.sleepy771.workcount.diff.exceptions;

/**
 * Created by filip on 8.5.2015.
 */
public class ManagerException extends Exception {

    public ManagerException() {
        this("");
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(String message) {
        this(message, null);
    }
}
