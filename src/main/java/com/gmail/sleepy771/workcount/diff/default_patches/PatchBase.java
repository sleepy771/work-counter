package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;

/**
 * Created by filip on 5/17/15.
 */
public class PatchBase implements Patch {

    private final int fromVersion;
    private final int toVersion;
    private final Identificator id;

    public PatchBase(Identificator id, int fromVersion, int toVersion) {
        this.id = id;
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
    }

    @Override
    public final int getFromVersion() {
        return fromVersion;
    }

    @Override
    public final int getToVersion() {
        return toVersion;
    }

    @Override
    public final Identificator getID() {
        return id;
    }

    @Override
    public final boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }
}
