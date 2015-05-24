package com.gmail.sleepy771.workcount.registers;

import java.util.Set;

/**
 * Created by filip on 5/24/15.
 */
public interface Register<K, R extends Registrable> {
    void register(R registrable);

    void register(K key, R registrable);

    void unregister(K key, R registrable);

    R unregister(K key);

    Set<K> unregisterAll(R registrable);

    R get(K key);

    void clear();
}
