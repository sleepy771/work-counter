package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 8.5.2015.
 */
public abstract class AbstractManager<R, T> implements Manager<R, T> {
    private Map<R, T> managerMap;

    public AbstractManager() {
        managerMap = new HashMap<>();
        populate(managerMap);
    }

    @Override
    public final void register(R key, T element) throws ManagerException {
        if (managerMap.containsKey(key))
            throw new ManagerException();
        managerMap.put(key, element);
    }

    @Override
    public final void register(T element) throws ManagerException {
        register(getKeyFromElement(element), element);
    }

    @Override
    public final void unregister(T element) throws ManagerException {
        managerMap.remove(getKeyFromElement(element), element);
    }

    @Override
    public final boolean isRegistered(T element) throws ManagerException {
        return managerMap.containsKey(getKeyFromElement(element)) && managerMap.get(getKeyFromElement(element)).equals(element);
    }

    @Override
    public final boolean isRegisteredForKey(R key) {
        return managerMap.containsKey(key);
    }

    @Override
    public final T get(R key) throws ManagerException {
        T element = managerMap.get(key);
        if (element == null) {
            throw new ManagerException();
        }
        return element;
    }

    @Override
    public final T remove(R key) throws ManagerException{
        T removed = managerMap.remove(key);
        if (removed == null) {
            throw new ManagerException();
        }
        return removed;
    }

    protected Map<R, T> getManagerMap() {
        return Collections.unmodifiableMap(managerMap);
    }

    protected abstract R getKeyFromElement(T element) throws ManagerException;

    protected abstract void populate(Map<R, T> map);
}
