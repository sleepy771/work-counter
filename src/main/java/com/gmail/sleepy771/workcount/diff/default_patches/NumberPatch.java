package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.numbers.NumberManager;
import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableNumber;

/**
 * Created by filip on 1.5.2015.
 */
public class NumberPatch implements Patch<PatchableNumber> {

    private final Number difference;
    private final int fromVersion;
    private final int toVersion;
    private final long id;

    public NumberPatch(long id, int fromVersion, int toVersion, Number difference) {
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

    @Override
    public long getId() {
        return id;
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
}
