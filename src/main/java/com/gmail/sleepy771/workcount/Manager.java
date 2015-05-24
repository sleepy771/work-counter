package com.gmail.sleepy771.workcount;

import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 5.5.2015.
 */
public interface Manager<R, T> extends Iterable<Map.Entry<R, T>>{
    void register(R key, T element) throws ManagerException;

    void register(T element) throws ManagerException;

    void unregister(T element) throws ManagerException;

    boolean isRegistered(T element) throws ManagerException;

    boolean isRegisteredForKey(R key);

    T get(R key) throws ManagerException;

    T remove(R key) throws ManagerException;

    Set<R> getRegisteredKeys();
}
