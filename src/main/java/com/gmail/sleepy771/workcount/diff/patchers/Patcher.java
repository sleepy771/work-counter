package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patcher {
    Patchable patch(Patchable original, Patch patch) throws PatcherException;

    Patch createPatch(Patchable original, Patchable altered) throws PatcherException;

    Patchable revert(Patchable original, Patch patch) throws PatcherException;

    // TODO remove patchable it seems it's not needed
    Patch invert(Patch patch) throws PatcherException;
}
