package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;

/**
 * Created by filip on 5/17/15.
 */
public class SimplePatchable implements Patchable {

    public final int version;

    public final Object value;

    public final Identificator id;

    public SimplePatchable(Identificator id, int version, Object value) {
        this.value = value;
        this.version = version;
        this.id = id;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public int getVersion() {
        return version;
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
