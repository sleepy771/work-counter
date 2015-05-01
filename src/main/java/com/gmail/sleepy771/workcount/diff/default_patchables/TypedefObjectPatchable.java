package com.gmail.sleepy771.workcount.diff.default_patchables;

/**
 * Created by filip on 1.5.2015.
 */
public class TypedefObjectPatchable<T> implements Patchable<T> {

    private final T objectUnderPatch;
    private final int version;
    private final long id;

    public TypedefObjectPatchable(long id, int version, T object) {
        this.id = id;
        this.version = version;
        this.objectUnderPatch = object;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public T getObject() {
        return this.objectUnderPatch;
    }

    @Override
    public long getId() {
        return id;
    }
}
