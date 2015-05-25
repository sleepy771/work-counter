package com.gmail.sleepy771.workcount.diff;

/**
 * Created by filip on 5/25/15.
 */
public class DoNuttinHandler<I> implements PreProcessHandler<I, I> {

    public static final DoNuttinHandler INSTANCE = new DoNuttinHandler();

    @Override
    public I prepare(I input) {
        return input;
    }
}
