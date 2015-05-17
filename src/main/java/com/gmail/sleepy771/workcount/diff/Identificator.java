package com.gmail.sleepy771.workcount.diff;

/**
 * Created by filip on 5/17/15.
 */
public interface Identificator extends IsTypedefCloneable<Identificator> {
    boolean isEqual(Identificator id);
}
