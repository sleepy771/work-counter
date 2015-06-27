package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Id;

/**
 * Created by filip on 5/17/15.
 */
public abstract class PatchableBase implements Patchable {

    private final Id id;
    private final int version;
    private volatile int hashCode;

    public PatchableBase(Id id, int version) {
        this.id = id;
        this.version = version;
    }

    @Override
    public final int getVersion() {
        return version;
    }

    @Override
    public final Id getID() {
        return id;
    }

    @Override
    public final boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }

    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            int hash = 17;
            hash = 31 * hash + this.id.hashCode();
            hash = 31 * hash + this.version;
            hashCode = computeHashCode(hash);
        }
        return hashCode;
    }

    @Override
    public final boolean equals(Object o) {
        if (o.hashCode() != this.hashCode() || !Patchable.class.isInstance(o))
            return false;
        Patchable obj = ((Patchable) o);
        return obj.getVersion() == version && id.isEqual(obj.getID()) && compareObject(obj);
    }

    protected abstract boolean compareObject(Patchable obj);

    protected abstract int computeHashCode(int seed);
}
