package com.gmail.sleepy771.workcount.diff;

/**
 * Created by filip on 5/17/15.
 */
public interface Id extends IsTypedefCloneable<Id> {
    boolean isEqual(Id id);
}
