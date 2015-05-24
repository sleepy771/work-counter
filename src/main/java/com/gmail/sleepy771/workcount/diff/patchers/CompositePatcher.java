package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;

import java.util.Map;

/**
 * Created by filip on 5/22/15.
 */
public class CompositePatcher extends AbstractPatcher implements Patcher, Classy {

    public CompositePatcher(Class forClass, Map<Class, Patcher> patchersMap) {

    }

    @Override
    public Class getForClass() {
        return null;
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        return null;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        return null;
    }

    @Override
    public Patch invert(Patch patch) throws PatcherException {
        return null;
    }
}
