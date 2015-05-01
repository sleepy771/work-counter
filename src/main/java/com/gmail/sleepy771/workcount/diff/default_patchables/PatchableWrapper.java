package com.gmail.sleepy771.workcount.diff.default_patchables;

/**
 * Created by filip on 1.5.2015.
 */
public class PatchableWrapper<T> implements Patchable<T> {
    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public T getObject() {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }
}
