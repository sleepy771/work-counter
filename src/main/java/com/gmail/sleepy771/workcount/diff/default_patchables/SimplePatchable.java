package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.diff.Id;

/**
 * Created by filip on 5/17/15.
 */
public class SimplePatchable extends PatchableBase implements Patchable {

    private final Object value;

    public SimplePatchable(Id id, int version, Object value) {
        super(id, version);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Class getForClass() {
        return value.getClass();
    }

    @Override
    protected boolean compareObject(Patchable obj) {
        return SimplePatchable.class.equals(obj.getClass()) && obj.getValue().equals(value);
    }

    @Override
    protected int computeHashCode(int seed) {
        return 31 * seed + value.hashCode();
    }
}
