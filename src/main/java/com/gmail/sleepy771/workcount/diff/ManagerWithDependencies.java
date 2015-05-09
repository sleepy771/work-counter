package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.Manager;

import java.util.List;

/**
 * Created by filip on 8.5.2015.
 */
public interface ManagerWithDependencies<R, T> extends Manager<R, T> {

    List<T> removeWithDependencies(R key);
}
