package com.gmail.sleepy771.workcount;

import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.registers.Registrable;

import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 5.5.2015.
 */
public interface Manager<R, T extends Registrable<R>> extends Iterable<Map.Entry<R, T>>{
    void register(R key, T element) throws ManagerException;

    void register(T element) throws ManagerException;

    void unregister(T element);

    boolean isRegistered(T element);

    boolean isRegisteredForKey(R key);

    T get(R key) throws ManagerException;

    T remove(R key);

    Set<R> getRegisteredKeys();
}
