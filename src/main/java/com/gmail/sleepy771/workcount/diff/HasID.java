package com.gmail.sleepy771.workcount.diff;

/**
 * Created by filip on 5/17/15.
 */
public interface HasID {
    Identificator getID();

    boolean hasEqualID(HasID hasID);
}
