package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;

/**
 * Created by filip on 5/17/15.
 */
public class StringPatcher extends AbstractPatcher implements Patcher {
    @Override
    public Patchable patch(Patchable original, Patch patch) {
        return null;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) {
        return null;
    }

    @Override
    public Patchable revert(Patchable original, Patch patch) {
        return null;
    }

    @Override
    public Patch invert(Patchable patchable, Patch patch) {
        return null;
    }
}
