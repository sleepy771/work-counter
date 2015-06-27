package com.gmail.sleepy771.workcount.diff.class_inspection;

/**
 * Created by filip on 6/1/15.
 */
public class ReleasableReference<T extends Releasable> implements Releasable {
    private T object;

    public ReleasableReference(T object) {
        set(object);
    }

    public ReleasableReference() {
        this(null);
    }

    public final T get() throws NullPointerException {
        if (null == this.object)
            throw new NullPointerException("Dereferenced");
        return object;
    }

    public final boolean isNull() {
        return null == object;
    }

    public final void release(T object) {
        if (object == this.object)
            this.object = null;
    }

    public final void set(T object) {
        if (!isNull()) {
            free();
        }
        this.object = object;
    }

    @Override
    public final void free() {
        this.object.free();
        this.object = null;
    }

    @Override
    public void setReleasableManager(ReleasableManagerImpl r) {
        this.object.setReleasableManager(r);
    }
}
