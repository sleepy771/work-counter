package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.Identifiable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;

/**
 * Created by filip on 1.5.2015.
 */
public abstract class AbstractPatcher<R, T extends Patchable<R>, P extends Patch<T>> implements Patcher<R, T, P> {

    protected void checkIds(Identifiable i1, Identifiable i2) {
        if (i1.getId() != i2.getId())
            throw new IllegalArgumentException("Ids does not match");
    }

    protected void checkVersions(Patch patch, Patchable patchable) {
        if (patch.getFromVersion() != patchable.getVersion())
            throw new IllegalArgumentException("Versions does not match");
    }
}
