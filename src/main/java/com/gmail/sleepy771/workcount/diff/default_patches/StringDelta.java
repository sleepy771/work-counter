package com.gmail.sleepy771.workcount.diff.default_patches;

import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by filip on 5/22/15.
 */
public class StringDelta implements Delta<LinkedList<DiffMatchPatch.Patch>> {

    private final LinkedList<DiffMatchPatch.Patch> patches;

    public StringDelta(LinkedList<DiffMatchPatch.Patch> patches) {
        this.patches = patches;
    }

    public LinkedList<DiffMatchPatch.Patch> getDelta() {
        return patches;
    }

    public int hashCode() {
        return patches.hashCode();
    }

    public boolean equals(Object o) {
        if (!StringDelta.class.isInstance(o))
            return false;
        StringDelta delta = ((StringDelta) o);
        return equalsPatchList(patches, delta.patches);
    }

    private static boolean equalsPatchList(List<DiffMatchPatch.Patch> patchList1, List<DiffMatchPatch.Patch> patchList2) {
        if (patchList1.size() != patchList2.size())
            return false;
        Iterator<DiffMatchPatch.Patch> e1 = patchList1.iterator();
        Iterator<DiffMatchPatch.Patch> e2 = patchList2.iterator();
        while (e1.hasNext() && e2.hasNext()) {
            DiffMatchPatch.Patch o1 = e1.next();
            DiffMatchPatch.Patch o2 = e2.next();
            if (!(o1==null ? o2==null : equalPatch(o1, o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    private static boolean equalPatch(DiffMatchPatch.Patch patch1, DiffMatchPatch.Patch patch2) {
        return patch1.length1 == patch2.length1 && patch1.length2 == patch2.length2
                && patch1.start1 == patch2.start1 && patch1.start2 == patch2.start2
                && patch1.diffs.equals(patch2.diffs);
    }
}
