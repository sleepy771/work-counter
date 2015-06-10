package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.SimplePatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.default_patches.InstancePatch;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;

/**
 * Created by filip on 5/23/15.
 */
public class DefaultPatcher extends AbstractPatcher implements Patcher {

    private final DeltaPatcherManager deltaPatcher;

    public DefaultPatcher(DeltaPatcherManager dmp) {
        this.deltaPatcher = dmp;
    }

    public DeltaPatcherManager getDeltaPatcher() {
        return this.deltaPatcher;
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        validateIds(original, patch);
        validateFromVersion(original, patch);
        Object patched = deltaPatcher.patch(original.getValue(), patch.getDeltaFor(new MethodSignature(original.getForClass())));
        return new SimplePatchable(original.getID(), patch.getToVersion(), patched);
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        validateIds(original, altered);
        Delta delta = deltaPatcher.createDelta(original.getValue(), altered.getValue());
        return new InstancePatch(original.getID(), original.getVersion(), altered.getVersion(), original.getForClass(), delta);
    }

    @Override
    public Patch invert(Patch patch) throws PatcherException {
        Delta delta = deltaPatcher.invert(patch.getDeltaFor(new MethodSignature(patch.getForClass())));
        return new InstancePatch(patch.getID(), patch.getToVersion(), patch.getFromVersion(), patch.getForClass(), delta);
    }
}
