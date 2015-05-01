package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableString;
import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filip on 1.5.2015.
 */
public class StringPatch implements Patch<PatchableString> {

    private final LinkedList<DiffMatchPatch.Patch> patch;
    private final int fromVersion;
    private final int toVersion;
    private final long forId;

    public StringPatch(long forId, int fromVersion, int toVersion, List<DiffMatchPatch.Patch> patch) {
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.patch = new LinkedList<>(patch);
        this.forId = forId;
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
    public long getId() {
        return forId;
    }

    public LinkedList<DiffMatchPatch.Patch> getPatch() {
        return this.patch;
    }

    @Override
    public String toString() {
        return String.valueOf(forId) + ':' + fromVersion + ':' + toVersion + ':' + new DiffMatchPatch().patch_toText(patch);
    }

    public static StringPatch fromString(String patchRep) {
        String[] patch = patchRep.split(":", 3);
        long forId = Long.parseLong(patch[0]);
        int fromVersion = Integer.parseInt(patch[1]);
        int toVersion = Integer.parseInt(patch[2]);
        List<DiffMatchPatch.Patch> patches = new DiffMatchPatch().patch_fromText(patch[3]);
        return new StringPatch(forId, fromVersion, toVersion, patches);
    }
}
