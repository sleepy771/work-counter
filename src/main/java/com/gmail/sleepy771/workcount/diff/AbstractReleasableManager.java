package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

/**
 * Created by filip on 5/27/15.
 */
public abstract class AbstractReleasableManager<K, T> extends AbstractManager<K, T> implements Manager<K, T>, Releasable {

    private ReleasableManager releasableManager;

    @Override
    protected void populate() {
    }

    @Override
    protected void postRegistration(K key, T element) {
    }

    @Override
    protected void postRelease(K key, T element) {
    }

    @Override
    public final boolean isRegisteredForKey(K key) {
        return containsKey(key);
    }

    @Override
    public final T get(K key) throws ManagerException {
        if (!containsKey(key))
            throw new ManagerException("Key not defined!");
        return getDirectly(key);
    }

    @Override
    public final T remove(K key) {
        return removeDirectly(key);
    }

    @Override
    public final void free() {
        clear();
        releasableManager.unregister(this);
        releasableManager = null;
        postFree();
    }

    @Override
    public final void setReleasableManager(ReleasableManager r) {
        releasableManager = r;
    }

    protected abstract void postFree();
}
