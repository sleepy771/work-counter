package com.gmail.sleepy771.workcount.diff.default_patchables;

/**
 * Created by filip on 1.5.2015.
 */
public class PatchableString implements Patchable<String> {

    private final String textRepresentation;
    private final int version;
    private final long id;

    public PatchableString(long id, int version, String text) {
        this.id = id;
        this.version = version;
        this.textRepresentation = text;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getObject() {
        return textRepresentation;
    }

    @Override
    public String toString() {
        return Long.toString(id) + ":" + version + ":" + textRepresentation;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + Long.hashCode(id);
        hash = hash * 31 + Integer.hashCode(version);
        hash = hash * 31 + textRepresentation.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if (!PatchableString.class.isInstance(o))
            return false;
        PatchableString ps = (PatchableString) o;
        return this.id == ps.id && this.version == ps.version && this.textRepresentation.equals(ps.textRepresentation);
    }

    public static PatchableString fromString(String representation) {
        String[] split = representation.split(":", 2);
        long id = Long.valueOf(split[0]);
        int version = Integer.valueOf(split[1]);
        return new PatchableString(id, version, split[2]);
    }
}
