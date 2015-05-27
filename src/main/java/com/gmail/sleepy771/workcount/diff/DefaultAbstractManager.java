package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 5/25/15.
 */
public abstract class DefaultAbstractManager<R, T> extends AbstractManager<R, T> implements Manager<R, T> {

    @Override
    public final boolean isRegisteredForKey(R key) {
        return containsKey(key);
    }

    @Override
    public final T get(R key) throws ManagerException {
        if (!containsKey(key))
            throw new ManagerException("Key not present");
        return getDirectly(key);
    }

    @Override
    public final T remove(R key) throws ManagerException {
        if (!containsKey(key))
            throw new ManagerException("Key not present");
        return removeDirectly(key);
    }

    @Override
    protected final void postRelease(R key, T element) {
    }

    @Override
    protected final void postRegistration(R key, T element) {
    }
}
