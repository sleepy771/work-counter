package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.SimplePatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;

/**
 * Created by filip on 5/23/15.
 */
public class DefaultPatcher extends AbstractPatcher implements Patcher {

    private final DeltaPatcherManager deltaPatcher;

    public DefaultPatcher(DeltaPatcherManager dmp) {
        this.deltaPatcher = dmp;
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        validateIds(original, patch);
        validateVersions(original, patch);
        Object patched = deltaPatcher.patch(original.getValue(), patch.getDeltaFor(null));
        return new SimplePatchable(original.getID(), patch.getToVersion(), patched);
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        validateIds(original, altered);
        return null;
    }

    @Override
    public Patchable revert(Patchable original, Patch patch) throws PatcherException {
        return patch(original, invert(original, patch));
    }

    @Override
    public Patch invert(Patchable patchable, Patch patch) throws PatcherException {
        validateIds(patchable, patch);
        return null;
    }
}
