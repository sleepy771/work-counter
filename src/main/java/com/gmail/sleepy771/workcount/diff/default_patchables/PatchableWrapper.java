package com.gmail.sleepy771.workcount.diff.default_patchables;

/**
 * Created by filip on 1.5.2015.
 */
public abstract class PatchableWrapper<T> implements Patchable<T> {
    @Override
    public abstract int getVersion();

    @Override
    public abstract T getObject();

    @Override
    public abstract long getId();
}
