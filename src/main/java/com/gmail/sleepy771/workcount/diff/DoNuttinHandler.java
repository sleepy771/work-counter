package com.gmail.sleepy771.workcount.diff;

/**
 * Created by filip on 5/25/15.
 */
public class DoNuttinHandler<I> implements ValueHandler<I, I> {

    private DoNuttinHandler() throws Exception {
        throw new Exception("NOPE");
    }

    @Override
    public I prepare(I input) {
        return input;
    }
}
