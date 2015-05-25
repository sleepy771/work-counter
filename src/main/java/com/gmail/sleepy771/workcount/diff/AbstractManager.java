package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.*;

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
    //TODO make register with Collection<R> ...
    public final void register(R key, T element) throws ManagerException {
        if (managerMap.containsKey(key))
            throw new ManagerException();
        if (Manager.class.isInstance(element)) {
            Manager<R, T> managerElement = ((Manager<R, T>) element);
            Set<R> keys = managerElement.getRegisteredKeys();
            keys.add(key);
            for (R elementKey : keys) {
                if (!managerMap.containsKey(elementKey))
                    managerMap.put(elementKey, element);
            }
        } else {
            managerMap.put(key, element);
        }
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
    public Iterator<Map.Entry<R, T>> iterator() {
        return new Iterator<Map.Entry<R, T>>() {
            private Iterator<Map.Entry<R, T>> internalIterator = managerMap.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return internalIterator != null && internalIterator.hasNext();
            }

            @Override
            public Map.Entry<R, T> next() {
                try {
                    if (internalIterator == null)
                        throw new NoSuchElementException("Internal iterator was released");
                    return internalIterator.next();
                } finally {
                    if (internalIterator != null && !internalIterator.hasNext())
                        internalIterator = null;
                }
            }
        };
    }

    @Override
    public final Set<R> getRegisteredKeys() {
        return managerMap.keySet();
    }

    protected final boolean containsKey(R key) {
        return managerMap.containsKey(key);
    }

    protected final T getDirectly(R key) {
        return managerMap.get(key);
    }

    protected final T removeDirectly(R key) {
        return managerMap.remove(key);
    }

    protected Map<R, T> getManagerMap() {
        return Collections.unmodifiableMap(managerMap);
    }

    protected abstract R getKeyFromElement(T element) throws ManagerException;

    protected abstract void populate(Map<R, T> map);
}
