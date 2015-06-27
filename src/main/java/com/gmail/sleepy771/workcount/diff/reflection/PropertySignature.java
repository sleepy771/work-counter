package com.gmail.sleepy771.workcount.diff.reflection;

/**
 * Created by filip on 6/19/15.
 */
public class PropertySignature extends AbstractSignature {

    public PropertySignature(String name, Class type, Class forClass) {
        super(name, type, forClass);
    }

    @Override
    protected int implHash() {
        return 0;
    }

    @Override
    protected boolean implEquals(Object o) {
        return true;
    }
}
