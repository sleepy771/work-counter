package com.gmail.sleepy771.workcount.diff.object_construction;

import java.util.*;

/**
 * Created by filip on 5/25/15.
 */
public class XMLAttributeSchemeImpl implements XMLAttributeScheme {

    private final String attributeName;
    private final Set<String> possibleValues;
    private final boolean isObligatory;
    private final String defaultAttritbute;

    public XMLAttributeSchemeImpl(String attributeName, boolean isObligatory, Collection<String> possibleValues, String defaultAttribute) {
        this.attributeName = attributeName;
        this.isObligatory = isObligatory;
        if (null == possibleValues || possibleValues.isEmpty()) {
            this.possibleValues = null;
            this.defaultAttritbute = null;
        } else {
            this.possibleValues = Collections.unmodifiableSet(new HashSet<>(possibleValues));
            this.defaultAttritbute = defaultAttribute;
        }
    }

    public XMLAttributeSchemeImpl(String attributeName, boolean isObligatory, String possibleValues) {
        this(attributeName, isObligatory, (possibleValues == null || possibleValues.isEmpty() || "-".equals(possibleValues))
                ? new String[0] : possibleValues.split("\\w*,\\w*"));
    }

    public XMLAttributeSchemeImpl(String attributeName, boolean isObligatory, String[] possibleValues) {
        this(attributeName, isObligatory, Arrays.asList((null == possibleValues || possibleValues.length == 0) ? new String[0] : possibleValues), (null == possibleValues || possibleValues.length == 0) ? null : possibleValues[0]);
    }

    public XMLAttributeSchemeImpl(String attributeName, boolean isObligatory) {
        this(attributeName, isObligatory, null, null);
    }

    @Override
    public String getName() {
        return attributeName;
    }

    @Override
    public Set<String> getPossibleAttributeValues() {
        return possibleValues;
    }

    @Override
    public boolean isValidAttributeValue(String attributeValue) {
        return null == possibleValues || possibleValues.isEmpty() || possibleValues.contains(attributeValue);
    }

    @Override
    public boolean hasRestrictedValues() {
        return !(null == possibleValues || possibleValues.isEmpty());
    }

    @Override
    public boolean isObligatory() {
        return isObligatory;
    }
}
