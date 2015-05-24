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
    public final Patchable revert(Patchable original, Patch patch) throws PatcherException {
        return patch(original, invert(patch));
    }

    protected final void validateVersion(int patchableVersion, int patchVersion) throws PatcherException {
        if (patchableVersion != patchVersion)
            throw new PatcherException("Versions does not match. Patchable version: " + patchableVersion + " != Patch [from/to]Version: " + patchVersion);
    }

    protected void validateFromVersion(Patchable patchable, Patch patch) throws PatcherException{
        validateVersion(patchable.getVersion(), patch.getFromVersion());
    }

    protected void validateIds(HasID id1, HasID id2) throws PatcherException {
        if (!id1.hasEqualID(id2))
            throw new PatcherException("Identificators are not equal: " + id1.getID().toString() + " != " + id2.getID().toString());
    }
}
