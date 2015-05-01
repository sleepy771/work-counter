package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableString;
import com.gmail.sleepy771.workcount.diff.default_patches.StringPatch;
import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.LinkedList;

/**
 * Created by filip on 1.5.2015.
 */
public class StringPatcher extends AbstractPatcher<String, PatchableString, StringPatch> implements Patcher<String, PatchableString, StringPatch> {
    @Override
    public PatchableString patch(PatchableString original, StringPatch patch) {
        checkIds(original, patch);
        checkVersions(patch, original);
        DiffMatchPatch dmp = new DiffMatchPatch();
        String patched = dmp.patch_apply(patch.getPatch(), original.getObject())[0].toString();
        return new PatchableString(original.getId(), patch.getToVersion(), patched);
    }

    @Override
    public StringPatch createPatch(PatchableString original, PatchableString altered) {
        checkIds(original, altered);
        LinkedList<DiffMatchPatch.Patch> patches = new DiffMatchPatch().patch_make(original.getObject(), altered.getObject());
        return new StringPatch(original.getId(), original.getVersion(), altered.getVersion(), patches);
    }

    @Override
    public StringPatch reverse(PatchableString original, StringPatch patch) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Class<PatchableString> getPatchableClass() {
        return PatchableString.class;
    }
}
