package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.default_patches.StringDelta;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.LinkedList;

/**
 * Created by filip on 5/22/15.
 */
public class StringDeltaPatcher implements ClassyDeltaPatcher {

    private final DiffMatchPatch dmp = new DiffMatchPatch();

    @Override
    public Object patch(Object original, Delta delta) throws PatcherException {
        if (!(String.class.isInstance(original) && StringDelta.class.isInstance(delta)))
            throw new PatcherException("Incompatible types");
        String originalString = (String) original;
        StringDelta stringDelta = (StringDelta) delta;
        return typedPatch(originalString, stringDelta);
    }

    @Override
    public Delta createDelta(Object original, Object altered) throws PatcherException {
        if (!(String.class.isInstance(original) && String.class.isInstance(altered)))
            throw new PatcherException("Incompatible types");
        LinkedList<DiffMatchPatch.Patch> patches = dmp.patch_make((String) original, (String) altered);
        return new StringDelta(patches);
    }

    @Override
    public Delta invert(Delta delta) throws PatcherException {
        if (!StringDelta.class.isInstance(delta))
            throw new PatcherException("Incompatible types");
        return invertStringDelta(((StringDelta) delta));
    }

    @Override
    public Object reverse(Object original, Delta delta) throws PatcherException {
        if (!(String.class.isInstance(original) && StringDelta.class.isInstance(delta)))
            throw new PatcherException("Incompatible types");
        return typedPatch((String) original, invertStringDelta((StringDelta) delta));
    }

    @Override
    public Class getForClass() {
        return String.class;
    }

    private String typedPatch(String original, StringDelta delta) {
        return (String) dmp.patch_apply(delta.getPatch(), original)[0];
    }

    private StringDelta invertStringDelta(StringDelta delta) {
        return null;
    }
}
