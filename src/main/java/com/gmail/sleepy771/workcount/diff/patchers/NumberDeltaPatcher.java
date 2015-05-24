package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.default_patches.NumberDelta;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.numbers.NumberManager;

/**
 * Created by filip on 5/22/15.
 */
public class NumberDeltaPatcher implements ClassyDeltaPatcher {
    @Override
    public Object patch(Object original, Delta delta) throws PatcherException {
        try {
            return patchCasted(((Number) original), ((NumberDelta) delta));
        } catch (ClassCastException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Delta createDelta(Object original, Object altered) throws PatcherException {
        try {
            Number originalNumber = ((Number) original);
            Number alteredNumber = ((Number) altered);
            return new NumberDelta(NumberManager.getInstance().sub(alteredNumber, originalNumber));
        } catch (ClassCastException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Delta invert(Delta delta) throws PatcherException {
        try {
            return invertCasted(((NumberDelta) delta));
        } catch (ClassCastException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Object reverse(Object original, Delta delta) throws PatcherException {
        try {
            return patchCasted(((Number) original), ((NumberDelta) delta));
        } catch (ClassCastException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Class getForClass() {
        return Number.class;
    }

    private Number patchCasted(Number original, NumberDelta delta) {
        return NumberManager.getInstance().add(original, delta.getPatch());
    }

    private NumberDelta invertCasted(NumberDelta delta) {
        return new NumberDelta(NumberManager.getInstance().negative(delta.getPatch()));
    }
}
