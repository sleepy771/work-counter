package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;

/**
 * Created by filip on 5/17/15.
 */
public abstract class PatchBase implements Patch {

    private final int fromVersion;
    private final int toVersion;
    private final Identificator id;
    private volatile int hashCode;

    public PatchBase(Identificator id, int fromVersion, int toVersion) {
        this.id = id;
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
    }

    @Override
    public final int getFromVersion() {
        return fromVersion;
    }

    @Override
    public final int getToVersion() {
        return toVersion;
    }

    @Override
    public final Identificator getID() {
        return id;
    }

    @Override
    public final boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }

    @Override
    public int hashCode() {
        if (0 == hashCode) {
            int hash = 17 * 31 + id.hashCode();
            hash = 31 * hash + fromVersion;
            hash = 31 * hash + toVersion;
            hashCode = computeHash(31 * hash);
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o.hashCode() != hashCode() || !Patch.class.isInstance(o))
            return false;
        Patch p = ((Patch) o);
        return p.getFromVersion() == getFromVersion() && p.getToVersion() == getToVersion() && p.hasEqualID(this) && hasEqualDeltas(p);
    }

    protected abstract boolean hasEqualDeltas(Patch p);

    protected abstract int computeHash(int initSeed);
}
