package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.annotations.PatcherTypes;
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
@PatcherTypes(patchType = StringPatch.class)
@ForClass(forClass = String.class)
public class StringPatcher extends AbstractPatcher implements Patcher {

    private final DiffMatchPatch dmp;

    public StringPatcher() {
        dmp = new DiffMatchPatch();
    }

    @Override
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
        String patchedText = patchString(originalText, sPatch.getPatchValue().getListOfPatches());
        Identificator newId = simplePatchable.getID().makeCopy();
        return new SimplePatchable(newId, sPatch.getToVersion(), patchedText);
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        SimplePatchable originalPatchable;
        SimplePatchable alteredPatchable;
        String originalText;
        String alteredText;
        try {
            originalPatchable = ((SimplePatchable) original);
            alteredPatchable = ((SimplePatchable) altered);
            originalText = ((String) originalPatchable.getValue());
            alteredText = ((String) alteredPatchable.getValue());
        } catch (ClassCastException e) {
            throw new PatcherException(e);
        }
        validateIds(originalPatchable, alteredPatchable);
        Identificator clonedId = originalPatchable.getID();
        LinkedList<DiffMatchPatch.Patch> patches = makePatch(originalText, alteredText);
        return new StringPatch(clonedId, originalPatchable.getVersion(), alteredPatchable.getVersion(), patches);
    }

    @Override
    public Patchable revert(Patchable original, Patch patch) throws PatcherException {
        return patch(original, invert(original, patch));
    }

    @Override
    public Patch invert(Patchable patchable, Patch patch) {
        return null;
    }

    private String patchString(String original, LinkedList<DiffMatchPatch.Patch> patch) {
        Object[] patched = dmp.patch_apply(patch, original);
        return (String) patched[0];
    }

    private LinkedList<DiffMatchPatch.Patch> makePatch(String original, String altered) {
        return dmp.patch_make(original, altered);
    }
}
