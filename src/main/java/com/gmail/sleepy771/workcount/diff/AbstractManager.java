package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.*;

/**
 * Created by filip on 8.5.2015.
 */
public abstract class AbstractManager<R, T> implements Manager<R, T> {
    private final Map<R, T> managerMap;

    public AbstractManager() {
        managerMap = new HashMap<>();
        populate();
    }

    protected AbstractManager(Map<R, T> map) {
        managerMap = map;
        managerMap.clear();
        populate();
    }

    @Override
    //TODO make register with Collection<R> ...
    @SuppressWarnings("unchecked")
    public final void register(R key, T element) throws ManagerException {
        if (null == key)
            throw new ManagerException("Key not defined");
        if (managerMap.containsKey(key))
            throw new ManagerException();
        if (Manager.class.isInstance(element)) {
            Manager<R, T> managerElement = (Manager<R, T>) element;
            Set<R> keys = managerElement.getRegisteredKeys();
            keys.add(key);
            keys.stream().filter(elementKey -> !managerMap.containsKey(elementKey)).forEach(elementKey -> managerMap.put(elementKey, element));
        } else {
            managerMap.put(key, element);
        }
        postRegistration(key, element);
    }

    @Override
    public final void register(T element) throws ManagerException {
        register(getKeyForElement(element), element);
    }

    @Override
    public final void unregister(T element) {
        R key = getKeyForElement(element);
        if (null == key)
            return;
        managerMap.remove(key, element);
        postRelease(key, element);
    }

    @Override
    public final boolean isRegistered(T element) {
        R key = getKeyForElement(element);
        return null != key && managerMap.containsKey(key) && managerMap.get(key).equals(element);
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

    protected final boolean registerSilently(R key, T element) {
        try {
            register(key, element);
            return true;
        } catch (ManagerException e) {
            // TODO do log
            return false;
        }
    }

    protected final boolean registerSilently(T element) {
        try {
            register(element);
            return true;
        } catch (ManagerException e) {
            // TODO do log
            return false;
        }
    }

    protected final T getDirectly(R key) {
        return managerMap.get(key);
    }

    protected final T removeDirectly(R key) {
        return managerMap.remove(key);
    }

    protected final void clear() {
        managerMap.clear();
    }

    protected Map<R, T> getManagerMap() {
        return Collections.unmodifiableMap(managerMap);
    }

    protected abstract void populate();

    protected abstract void postRegistration(R key, T element);

    protected abstract  void postRelease(R key, T element);

    protected abstract R getKeyForElement(T element);
}
