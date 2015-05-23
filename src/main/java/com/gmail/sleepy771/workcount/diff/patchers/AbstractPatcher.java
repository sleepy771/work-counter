package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;

/**
 * Created by filip on 5/17/15.
 */
public abstract class AbstractPatcher implements Patcher {

    @Override
    public Patchable revert(Patchable original, Patch patch) throws PatcherException {
        return patch(original, invert(original, patch));
    }

    protected void validateVersions(Patchable patchable, Patch patch) throws PatcherException{
        if (patch.getFromVersion() != patchable.getVersion())
            throw new PatcherException("Versions does not match. Patchable " + patchable.toString() + " version: " + patchable.getVersion() + " != Patch " + patch.toString() + " fromVersion: " + patch.getFromVersion());
    }

    protected void validateIds(HasID id1, HasID id2) throws PatcherException {
        if (!id1.hasEqualID(id2))
            throw new PatcherException("Identificators are not equal: " + id1.getID().toString() + " != " + id2.getID().toString());
    }
}
