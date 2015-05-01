package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableNumber;
import com.gmail.sleepy771.workcount.diff.default_patches.NumberPatch;
import com.gmail.sleepy771.workcount.diff.numbers.NumberManager;

/**
 * Created by filip on 1.5.2015.
 */
public class NumberPatcher extends AbstractPatcher<Number, PatchableNumber, NumberPatch> implements Patcher<Number, PatchableNumber, NumberPatch> {
    @Override
    public PatchableNumber patch(PatchableNumber original, NumberPatch patch) {
        checkIds(original, patch);
        checkVersions(patch, original);
        long id = original.getId();
        int version = patch.getToVersion();
        Number number = NumberManager.getInstance().add(original.getObject(), patch.getDifference());
        return new PatchableNumber(id, version, number.getClass(), number);
    }

    @Override
    public NumberPatch createPatch(PatchableNumber original, PatchableNumber altered) {
        checkIds(original, altered);
        long id = original.getId();
        int fromVersion = original.getVersion();
        int toVersion = altered.getVersion();
        Number difference = NumberManager.getInstance().sub(original.getObject(), altered.getObject());
        return new NumberPatch(id, fromVersion, toVersion, difference);
    }

    @Override
    public NumberPatch reverse(PatchableNumber original, NumberPatch patch) {
        return new NumberPatch(patch.getId(), patch.getFromVersion(), patch.getToVersion(), NumberManager.getInstance().negative(patch.getDifference()));
    }

    @Override
    public Class<PatchableNumber> getPatchableClass() {
        return PatchableNumber.class;
    }
}
