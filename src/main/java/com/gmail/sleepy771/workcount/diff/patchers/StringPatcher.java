package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.annotations.PatchAs;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.SimplePatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.default_patches.StringPatch;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.LinkedList;

/**
 * Created by filip on 5/17/15.
 */
public class StringPatcher extends AbstractPatcher implements Patcher {
    @Override
    @PatchAs(patchType = StringPatch.class)
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        StringPatch sPatch = null;
        SimplePatchable simplePatchable = null;
        String originalText = null;
        try {
            sPatch = (StringPatch) patch;
            simplePatchable = (SimplePatchable) original;
            originalText = (String) simplePatchable.getValue();
        } catch (ClassCastException e) {
            throw new PatcherException(e);
        }
        validateIds(sPatch, simplePatchable);
        validateVersions(simplePatchable, sPatch);

        String patchedText = patchString(originalText, sPatch.getPatch());

        Identificator newId = simplePatchable.getID().makeCopy();

        SimplePatchable patched = new SimplePatchable(newId, sPatch.getToVersion(), patchedText);

        return null;
    }

    @Override
    @PatchAs(patchType = StringPatch.class)
    public Patch createPatch(Patchable original, Patchable altered) {
        return null;
    }

    @Override
    @PatchAs(patchType = StringPatch.class)
    public Patchable revert(Patchable original, Patch patch) {
        return null;
    }

    @Override
    @PatchAs(patchType = StringPatch.class)
    public Patch invert(Patchable patchable, Patch patch) {
        return null;
    }

    private String patchString(String original, LinkedList<DiffMatchPatch.Patch> patch) {
        DiffMatchPatch dmp = new DiffMatchPatch();
        Object[] patched = dmp.patch_apply(patch, original);
        return (String) patched[0];
    }
}
