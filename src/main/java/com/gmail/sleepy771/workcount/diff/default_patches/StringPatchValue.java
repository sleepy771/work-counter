package com.gmail.sleepy771.workcount.diff.default_patches;

import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by filip on 5/17/15.
 */
public class StringPatchValue implements PatchValue {

    private final LinkedList<DiffMatchPatch.Patch> patches;
    private volatile int hashCode;

    public StringPatchValue(LinkedList<DiffMatchPatch.Patch> patches) {
        this.patches = patches;
    }

    public LinkedList<DiffMatchPatch.Patch> getListOfPatches() {
        return new LinkedList<>(this.patches);
    }

    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            hashCode = patches.hashCode();
        }
        return hashCode;
    }

    @Override
    public final boolean equals(Object o) {
        if (!StringPatchValue.class.equals(o.getClass()) || o.hashCode() != hashCode())
            return false;
        StringPatchValue patch = ((StringPatchValue) o);
        Iterator<DiffMatchPatch.Patch> patchIterator = patch.patches.iterator();
        Iterator<DiffMatchPatch.Patch> iterator = patches.iterator();
        while (patchIterator.hasNext() && iterator.hasNext()) {
            if (!patchIterator.next().equals(iterator.next()))
                return false;
        }
        return true;
    }
}
