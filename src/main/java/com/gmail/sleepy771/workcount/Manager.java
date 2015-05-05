package com.gmail.sleepy771.workcount;

/**
 * Created by filip on 5.5.2015.
 */
public interface Manager<T> {
    void register(T element);

    void unregister(T element);


}
