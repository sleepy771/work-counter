package com.gmail.sleepy771.workcount.diff.exceptions;

/**
 * Created by filip on 5/17/15.
 */
public class PatcherException extends Throwable {

    public PatcherException() {
        this("", null);
    }

    public PatcherException(String message) {
        this(message, null);
    }

    public PatcherException(String message, Exception e) {
        super(message, e);
    }

    public PatcherException(Exception e) {
        this("Caused by: " + e.getClass().getName(), e);
    }
}
