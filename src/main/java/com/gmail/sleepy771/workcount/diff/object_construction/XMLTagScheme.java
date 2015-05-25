package com.gmail.sleepy771.workcount.diff.object_construction;

import java.util.Set;

/**
 * Created by filip on 5/25/15.
 */
public interface XMLTagScheme extends HasName, XMLExecutable, XMLTagSchemeManager {
    Set<String> getPossibleTags();

    Set<String> getPossibleAttributes();

    boolean isObligatory();

    int getTagLevel();

    XMLAttributeManager getAttributeManager();

    boolean isValid(XMLTag tag);
}
