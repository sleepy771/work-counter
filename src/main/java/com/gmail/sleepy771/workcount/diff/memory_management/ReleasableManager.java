package com.gmail.sleepy771.workcount.diff.memory_management;

import com.gmail.sleepy771.workcount.Manager;

/**
 * Created by filip on 6/27/15.
 */
public interface ReleasableManager<T extends Releasable> extends Manager<Class, T> {
}
