package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.diff.numbers.NumberManager;

/**
 * Created by filip on 1.5.2015.
 */
public class PatchableNumber implements Patchable<Number> {

    private final Class<? extends Number> numberClass;
    private final Number numberUnderPatch;
    private final int version;
    private final long id;

    public PatchableNumber(long id, int version, Class<? extends Number> numberClass, Number number) {
        this.id = id;
        this.version = version;
        this.numberClass = numberClass;
        this.numberUnderPatch = number;
    }

    public Class<? extends Number> getNumberClass() {
        return numberClass;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Number getObject() {
        return numberUnderPatch;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return Long.toString(id) + ":" + version + ":" + NumberManager.getInstance().asString(numberUnderPatch);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + Long.hashCode(id);
        hash = 31 * hash + Integer.hashCode(version);
        hash = 31 * hash + numberUnderPatch.hashCode();
        return hash;
    }

    public PatchableNumber fromString(String representation) {
        String[] patchableStr = representation.split(":", 2);
        long id = Long.parseLong(patchableStr[0]);
        int version = Integer.parseInt(patchableStr[1]);
        Number number = NumberManager.getInstance().fromString(patchableStr[2]);
        return new PatchableNumber(id, version, number.getClass(), number);
    }
}
