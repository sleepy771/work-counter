package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.numbers.NumberManager;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

/**
 * Created by filip on 1.5.2015.
 */
@ForClass(forClass = Number.class)
public class NumberPatch implements Patch {

    private final NumberPatchValue difference;
    private final int fromVersion;
    private final int toVersion;
    private final Identificator id;
    private volatile int hashCode;

    public NumberPatch(Identificator id, int fromVersion, int toVersion, NumberPatchValue difference) {
        this.id = id;
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.difference = difference;
    }

    public NumberPatch(Identificator id, int fromVersion, int toVersion, Number difference) {
        this(id, fromVersion, toVersion, new NumberPatchValue(difference));
    }

    @Override
    public int getFromVersion() {
        return fromVersion;
    }

    @Override
    public int getToVersion() {
        return toVersion;
    }

    @Override
    public Patch getDeltaFor(Signature signature) throws IllegalArgumentException {
        return null;
    }

    public NumberPatchValue getDifference() {
        return difference;
    }

    public String toString() {
        return id.toString() + ":" + fromVersion + ":" + toVersion + ":" + NumberManager.getInstance().asString(difference.getDifference());
    }

    @Override
    public Identificator getID() {
        return this.id;
    }

    @Override
    public boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }

    @Override
    public int hashCode() {
        if (0 == hashCode) {
            int hash = 17;
            hash = 31 * hash + id.hashCode();
            hash = 31 * hash + fromVersion;
            hash = 31 * hash + toVersion;
            hash = 31 * hash + difference.hashCode();
            hashCode = hash;
        }
        return hashCode;
    }
}
