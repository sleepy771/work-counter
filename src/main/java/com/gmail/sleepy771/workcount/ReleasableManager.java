package com.gmail.sleepy771.workcount;

import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.Releasable;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

/**
 * Created by filip on 5/27/15.
 */
public class ReleasableManager extends AbstractManager<Class, Releasable> implements Manager<Class, Releasable>, Releasable {

    private static ReleasableManager INSTANCE = new ReleasableManager();

    private ReleasableManager manager = null;

    @Override
    protected void postRegistration(Class key, Releasable element) {
        element.setReleasableManager(this);
    }

    @Override
    protected void postRelease(Class key, Releasable element) {
        element.setReleasableManager(null);
    }

    @Override
    protected Class getKeyForElement(Releasable element) {
        return element.getClass();
    }

    @Override
    public void free() {
        for (Class releasableKey : this.getRegisteredKeys()) {
            if (ReleasableManager.class.equals(releasableKey))
                continue;
            release(releasableKey);
        }
        INSTANCE = null;
        manager = null;
    }

    public void release(Class resource) {
        if (ReleasableManager.class.equals(resource)) {
            free();
            return;
        }
        if (containsKey(resource)) {
            Releasable releasable = getDirectly(resource);
            releasable.free();
            removeDirectly(resource);
        }
    }

    @Override
    public void setReleasableManager(ReleasableManager r) {
        this.manager = r;
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public Releasable get(Class key) throws ManagerException {
        if (!containsKey(key))
            throw new ManagerException("Key not registered");
        return getDirectly(key);
    }

    @Override
    public Releasable remove(Class key) {
        return removeDirectly(key);
    }

    public static ReleasableManager getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ReleasableManager();
        }
        return INSTANCE;
    }

    public boolean isRunningManager() {
        return this == INSTANCE;
    }

    @Override
    protected void populate() {
        registerSilently(this);
    }
}
