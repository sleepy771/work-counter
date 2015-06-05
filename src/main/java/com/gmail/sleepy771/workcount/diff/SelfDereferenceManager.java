package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Created by filip on 6/4/15.
 */
public abstract class SelfDereferenceManager<R, T> implements Manager<R, T> {

    private Map<R, WeakReference<T>> managerMapping;

    public SelfDereferenceManager() {
        managerMapping = new HashMap<>();
        populate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register(R key, T element) throws ManagerException {
        if (key == null)
            throw new ManagerException();
        if (isRegisteredForKey(key))
            throw new ManagerException();
        if (Manager.class.isInstance(element)) {
            Manager<R, T> rtManager = (Manager<R, T>) element;
            Set<R> keys = rtManager.getRegisteredKeys();
            keys.add(key);
            for (R k : keys) {
                if (!isRegisteredForKey(k)) {
                    T ref = rtManager.get(key);
                    managerMapping.put(k, new WeakReference<>(ref));
                }
            }
        } else {
            managerMapping.put(key, new WeakReference<>(element));
        }
        postRegister(key, element);
    }

    protected abstract void postRegister(R key, T element);

    protected abstract void postUnregister(R key, T element);

    protected abstract void populate();

    protected abstract R getKeyForElement(T element);

    @Override
    public void register(T element) throws ManagerException {
        register(getKeyForElement(element), element);
    }

    @Override
    public void unregister(T element) {
        if (element == null)
            return;
        R key = getKeyForElement(element);
        if (key == null)
            return;
        if (managerMapping.containsKey(key)) {
            WeakReference<T> inMap = managerMapping.get(key);
            T inMapElement = inMap.get();
            if (inMapElement != null && element.equals(inMapElement)) {
                managerMapping.remove(key);
                postRegister(key, inMap.get());
            }
            if (inMapElement == null) {
                managerMapping.remove(key);
            }
        }
    }

    @Override
    public boolean isRegistered(T element) {
        if (element == null)
            return false;
        R key = getKeyForElement(element);
        if (key == null)
            return false;
        if (!managerMapping.containsKey(key))
            return false;
        T inMapElement = managerMapping.get(key).get();
        if (inMapElement == null) {
            managerMapping.remove(key);
            return false;
        }
        return inMapElement.equals(element);
    }

    @Override
    public boolean isRegisteredForKey(R key) {
        if (key == null)
            return false;
        if (!managerMapping.containsKey(key))
            return false;
        if (managerMapping.get(key).get() == null) {
            managerMapping.remove(key);
            return false;
        }
        return true;
    }

    @Override
    public T get(R key) throws ManagerException {
        if (key == null)
            throw new ManagerException("Invalid key");
        if (!managerMapping.containsKey(key))
            throw new ManagerException("Key does not exist");
        T inMap = managerMapping.get(key).get();
        if (inMap == null) {
            managerMapping.remove(key);
            throw new NullPointerException("Registered element was dereferenced");
        }
        return inMap;
    }

    @Override
    public T remove(R key) {
        if (key == null || !managerMapping.containsKey(key))
            return null;
        WeakReference<T> inMap = managerMapping.remove(key);
        return inMap.get();
    }

    @Override
    public Set<R> getRegisteredKeys() {
        Set<R> keys = new HashSet<>();
        Set<R> dereferenced = new HashSet<>();
        for (Map.Entry<R, WeakReference<T>> entry : managerMapping.entrySet()) {
            if (entry.getValue().get() == null) {
                dereferenced.add(entry.getKey());
            } else {
                keys.add(entry.getKey());
            }
        }
        dereferenced.forEach(managerMapping::remove);
        return keys;
    }

    @Override
    public Iterator<Entry<R, T>> iterator() {
        return new Iterator<Entry<R, T>>() {

            private final Iterator<Map.Entry<R, WeakReference<T>>> entryIterator = managerMapping.entrySet().iterator();

            private Map.Entry<R, T> next = null;

            private boolean hasNext = false, taken = false;

            private void validateNext() {
                if (!taken) {
                    hasNext = false;
                    while (entryIterator.hasNext()) {
                        Map.Entry<R, WeakReference<T>> nextEntry = entryIterator.next();
                        if (nextEntry.getValue().get() != null) {
                            hasNext = true;
                            next = new Map.Entry<R, T>() {

                                private R key = nextEntry.getKey();
                                private T value = nextEntry.getValue().get();

                                @Override
                                public R getKey() {
                                    return key;
                                }

                                @Override
                                public T getValue() {
                                    return value;
                                }

                                @Override
                                public T setValue(T value) {
                                    throw new UnsupportedOperationException();
                                }
                            };
                            return;
                        }
                    }
                    hasNext = false;
                    next = null;
                }
            }

            @Override
            public boolean hasNext() {
                validateNext();
                return hasNext;
            }

            @Override
            public Entry<R, T> next() {
                Map.Entry<R, T> current = next;
                taken = true;
                validateNext();
                return current;
            }
        };
    }

    private void cleanUp() {
        Set<R> dereferencedKeys = managerMapping.entrySet().stream().filter(mapEntry -> mapEntry.getValue().get() == null).map(Entry::getKey).collect(Collectors.toSet());
        dereferencedKeys.forEach(managerMapping::remove);
    }
}
