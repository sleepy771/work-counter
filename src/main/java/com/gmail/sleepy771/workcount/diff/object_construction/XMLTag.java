package com.gmail.sleepy771.workcount.diff.object_construction;

import java.util.List;
import java.util.Set;

/**
 * Created by filip on 5/25/15.
 */
public interface XMLTag extends HasName {
    Set<XMLTagAttribute> getAttributes();

    List<XMLTag> getTags();

    XMLTagScheme getScheme();

    boolean hasValue();

    String getValue();
}
