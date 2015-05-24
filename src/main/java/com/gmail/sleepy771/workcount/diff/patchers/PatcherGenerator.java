package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;

/**
 * Created by filip on 9.5.2015.
 */
public class PatcherGenerator implements Patcher, Classy {

    private final Scheme scheme;

    public PatcherGenerator(Scheme scheme) {
        this.scheme = scheme;
    }

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
    public Patch invert(Patch patch) throws PatcherException {
        return null;
    }

    @Override
    public Class getForClass() {
        return scheme.getForClass();
    }
}
