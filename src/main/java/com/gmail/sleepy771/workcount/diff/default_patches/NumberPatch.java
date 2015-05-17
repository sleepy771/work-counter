package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.numbers.NumberManager;

/**
 * Created by filip on 1.5.2015.
 */
@ForClass(forClass = Number.class)
@Deprecated
public class NumberPatch implements Patch {

    private final Number difference;
    private final int fromVersion;
    private final int toVersion;
    private final Identificator id;

    public NumberPatch(Identificator id, int fromVersion, int toVersion, Number difference) {
        this.id = id;
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.difference = difference;
    }

    @Override
    public int getFromVersion() {
        return fromVersion;
    }

    @Override
    public int getToVersion() {
        return toVersion;
    }

    public Number getDifference() {
        return difference;
    }

    public String toString() {
        return Long.toString(id) + ":" + fromVersion + ":" + toVersion + ":" + NumberManager.getInstance().asString(difference);
    }

    public static NumberPatch fromString(String representation) {
        String[] splitPatch = representation.split(":", 3);
        long id = Long.parseLong(splitPatch[0]);
        int fromVersion = Integer.parseInt(splitPatch[1]);
        int toVersion = Integer.parseInt(splitPatch[2]);
        Number n = NumberManager.getInstance().fromString(splitPatch[3]);
        return new NumberPatch(id, fromVersion, toVersion, n);
    }

    @Override
    public Identificator getID() {
        return this.id;
    }

    @Override
    public boolean hasEqualID(HasID hasID) {
        return id.isEqual(hasID.getID());
    }
}
