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

    private final LinkedList<DiffMatchPatch.Patch> patch;
    private final int fromVersion;
    private final int toVersion;
    private final Identificator id;

    public StringPatch(Identificator forId, int fromVersion, int toVersion, List<DiffMatchPatch.Patch> patch) {
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.patch = new LinkedList<>(patch);
        this.id = forId;
    }

    @Override
    public int getFromVersion() {
        return fromVersion;
    }

    @Override
    public int getToVersion() {
        return toVersion;
    }

    public LinkedList<DiffMatchPatch.Patch> getPatch() {
        return this.patch;
    }

    @Override
    public String toString() {
        return id.toString() + ':' + fromVersion + ':' + toVersion + ':' + new DiffMatchPatch().patch_toText(patch);
    }

    @Override
    public Identificator getID() {
        return id;
    }

    @Override
    public boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }
}
