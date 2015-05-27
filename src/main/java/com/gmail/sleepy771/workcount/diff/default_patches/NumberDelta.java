package com.gmail.sleepy771.workcount.diff.default_patches;

/**
 * Created by filip on 5/22/15.
 */
public class NumberDelta implements Delta<Number> {

    private final Number delta;

    public NumberDelta(Number delta) {
        this.delta = delta;
    }

    public Number getPatch() {
        return null;
    }

    public int hashCode() {
        return delta.hashCode();
    }

    public boolean equals(Object o) {
        return Delta.class.isInstance(o) && delta.equals(o);
    }

    @Override
    public Class getForClass() {
        return Number.class;
    }
}
