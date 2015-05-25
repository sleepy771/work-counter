package com.gmail.sleepy771.workcount.diff.object_construction;

import java.util.Set;

/**
 * Created by filip on 5/25/15.
 */
public interface XMLAttributeScheme extends HasName {

    String getName();

    Set<String> getPossibleAttributeValues();

    boolean isValidAttributeValue(String attribueValue);

    boolean hasRestrictedValues();

    boolean isObligatory();
}
