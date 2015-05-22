package com.gmail.sleepy771.workcount.diff.default_patches;

/**
 * Created by filip on 5/17/15.
 */
public class NumberPatchValue implements Delta {
    private final Number difference;

    public NumberPatchValue(Number n) {
        difference = n;
    }

    public Number getDifference() {
        return difference;
    }

    @Override
    public final int hashCode() {
        return difference.hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        if (!NumberPatchValue.class.equals(o.getClass()))
            return false;
        return ((NumberPatchValue) o).difference.equals(difference);
    }
}
