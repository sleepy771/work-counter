package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filip on 1.5.2015.
 */
@ForClass(forClass = String.class)
public class StringPatch implements Patch {

    private final StringPatchValue patch;
    private final int fromVersion;
    private final int toVersion;
    private final Identificator id;
    private volatile int hashCode;

    public StringPatch(Identificator forId, int fromVersion, int toVersion, StringPatchValue patchValue) {
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.patch = patchValue;
        this.id = forId;
    }

    public StringPatch(Identificator id, int fromVersion, int toVersion, LinkedList<DiffMatchPatch.Patch> patches) {
        this(id, fromVersion, toVersion, new StringPatchValue(new LinkedList<>(patches)));
    }

    @Override
    public int getFromVersion() {
        return fromVersion;
    }

    @Override
    public int getToVersion() {
        return toVersion;
    }

    public StringPatchValue getPatchValue() {
        return this.patch;
    }

    @Override
    public String toString() {
        return id.toString() + ':' + fromVersion + ':' + toVersion + ':' + new DiffMatchPatch().patch_toText(patch.getListOfPatches());
    }

    @Override
    public Identificator getID() {
        return id;
    }

    @Override
    public boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }

    public boolean equals(Object o) {
        if (!StringPatch.class.equals(o.getClass()) || o.hashCode() != hashCode())
            return false;
        StringPatch patch = ((StringPatch) o);
        return hasEqualID(patch) && patch.fromVersion == this.fromVersion && patch.toVersion == this.toVersion && this.patch.equals(patch.patch);
    }

    @Override
    public int hashCode() {
        if (0 == hashCode) {
            int hash = 17;
            hash = 17 * 31 + id.hashCode();
            hash = hash * 31 + fromVersion;
            hash = hash * 31 + toVersion;
            hash = hash * 31 + patch.hashCode();
            hashCode = hash;
        }
        return hashCode;
    }
}
