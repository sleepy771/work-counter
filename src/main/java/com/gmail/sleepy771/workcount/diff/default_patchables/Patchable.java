package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.diff.Identifiable;

/**
 * Created by filip on 18.4.2015.
 */
public interface Patchable<T> extends Identifiable {
    int getVersion();

    T getObject();
}
