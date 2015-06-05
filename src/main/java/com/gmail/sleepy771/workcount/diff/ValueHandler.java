package com.gmail.sleepy771.workcount.diff;

/**
 * Created by filip on 5/25/15.
 */
public interface ValueHandler<O, I> {
    O prepare(I input);
}
