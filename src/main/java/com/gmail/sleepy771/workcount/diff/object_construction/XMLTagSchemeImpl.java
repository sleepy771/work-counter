package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 5/25/15.
 */
public class XMLTagSchemeImpl extends DefaultAbstractManager<String, XMLTagScheme> implements XMLTagScheme {
    private final String tagName;
    private final boolean obligatory;
    private final int level;
    private final XMLAttributeManager attributeManager;
    private final XMLExecutable executor;

    public XMLTagSchemeImpl(String name, int level, boolean obligatory, XMLAttributeManager attributeManager, XMLExecutable executable) {
        this.tagName = name;
        this.level = level;
        this.obligatory = obligatory;
        this.attributeManager = attributeManager;
        this.executor = executable;
    }

    public XMLTagSchemeImpl(String name, int level, boolean obligatory, XMLExecutable executable) {
        this(name, level, obligatory, new XMLAttributeManagerImpl(), executable);
    }


    @Override
    public final Set<String> getPossibleTags() {
        return getRegisteredKeys();
    }

    @Override
    public final Set<String> getPossibleAttributes() {
        return getAttributeManager().getRegisteredKeys();
    }

    @Override
    public final boolean isObligatory() {
        return obligatory;
    }

    @Override
    public final int getTagLevel() {
        return level;
    }

    @Override
    public final XMLAttributeManager getAttributeManager() {
        return attributeManager;
    }

    @Override
    public boolean isValid(XMLTag tag) {
        return false;
    }

    @Override
    public final String getName() {
        return tagName;
    }

    @Override
    protected final String getKeyFromElement(XMLTagScheme element) throws ManagerException {
        return element.getName();
    }

    @Override
    protected void populate(Map<String, XMLTagScheme> map) {
    }

    @Override
    public final void execute(XMLTag tag) {
        executor.execute(tag);
    }
}
