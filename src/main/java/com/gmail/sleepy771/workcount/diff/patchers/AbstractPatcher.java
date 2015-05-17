package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.Identifiable;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;

/**
 * Created by filip on 5/17/15.
 */
public abstract class AbstractPatcher implements Patcher {

    protected void validateVersions(Patch patch, Patchable patchable) throws PatcherException{
        if (patch.getFromVersion() != patchable.getVersion())
            throw new PatcherException();
    }

    protected void validateIds(Identifiable id1, Identifiable id2) throws PatcherException {
        if (!id1.isEqual(id2))
            throw new PatcherException();
    }
}
