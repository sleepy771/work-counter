package com.gmail.sleepy771.workcount.diff.default_patches;

/**
 * Created by filip on 5/22/15.
 */
public class NumberDelta implements Delta<Number> {

    private final Number delta;

    public NumberDelta(Number delta) {
        this.delta = delta;
    }

    public Number getDelta() {
        return null;
    }

    public int hashCode() {
        return delta.hashCode();
    }

    public boolean equals(Object o) {
        return delta.equals(o);
    }
}
