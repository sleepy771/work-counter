package com.gmail.sleepy771.workcount.diff.object_construction;

import java.util.Set;

/**
 * Created by filip on 5/25/15.
 */
public interface XMLTagAttribute extends HasName {
    String getAttributeName();

    String getValue();

    Set<String> getPossibleValues();
}
