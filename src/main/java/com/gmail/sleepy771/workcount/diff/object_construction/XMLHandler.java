package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by filip on 5/25/15.
 */
public class XMLHandler {

    public enum Tag {
        DEFAULT_VALUES("default-values", 0, true, null),
        INSTANCES("instances", 1, false, DEFAULT_VALUES),
        DEFINE("define", 1, true, DEFAULT_VALUES),
        OVERRIDE("override", 1, false, DEFAULT_VALUES),
        INHERIT("inherit", 1, false, DEFAULT_VALUES),
        CLASS("class", 2, false, INSTANCES),
        INSTANCE("instance", 2, false, INSTANCES),
        FOR_CLASS("for-class", 2, true, DEFINE),
        FOR_CLASS_OVERRIDE("for-class", 2, true, OVERRIDE),
        EXCLUDE_INHERIT("exclude-class", 2, false, INHERIT),
        INCLUDE_INHERIT("include-class", 2, false, INHERIT),
        VALUES("values", 3, true, FOR_CLASS),
        VALUES_OVERRIDE("values", 3, true, FOR_CLASS_OVERRIDE),
        VALUE("value", 4, true, VALUES),
        VALUE_OVERRIDE("value", 4, true, VALUES_OVERRIDE);

        private final String name;
        private final int level;
        private final boolean obligatory;
        private final Tag parent;

        Tag(String name, int level, boolean obligatory, Tag parent) {
            this.name = name;
            this.level = level;
            this.obligatory = obligatory;
            this.parent = parent;
        }

        public List<Tag> getBackTrace() {
            Tag current = this;
            List<Tag> tagList = new ArrayList<>(level + 1);
            while(current != null) {
                tagList.add(current);
                current = current.parent;
            }
            return tagList;
        }

        public List<String> getNameBackTrace() {
            Tag current = this;
            List<String> namesList = new ArrayList<>(level + 1);
            while (current != null) {
                namesList.add(current.name);
                current = current.parent;
            }
            return namesList;
        }

        public List<Tag> getBackTraceWithoutRoot() {
            Tag current = this;
            List<Tag> tagList = new ArrayList<>(level + 1);
            while(current.parent != null) {
                tagList.add(current);
                current = current.parent;
            }
            return tagList;
        }

        public List<String> getNameBackTraceWithoutRoot() {
            Tag current = this;
            List<String> namesList = new ArrayList<>(level + 1);
            while (current.parent != null) {
                namesList.add(current.name);
                current = current.parent;
            }
            return namesList;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public boolean isObligatory() {
            return obligatory;
        }

        public Tag getParent() {
            return parent;
        }

        public static List<Tag> getTagsByName(String name) {
            List<Tag> tagList = new ArrayList<>();
            for (Tag tag : Tag.values()) {
                if (tag.name.equals(name))
                    tagList.add(tag);
            }
            return tagList;
        }

        public static List<Tag> getTagsByLevel(int level) {
            List<Tag> tagList = new ArrayList<>();
            for (Tag tag : Tag.values()) {
                if (tag.level == level)
                    tagList.add(tag);
            }
            return tagList;
        }
    }

    private final XMLTagScheme defaultValuesTag = new XMLTagSchemeImpl("default-values", 0, true, null);

    void performRegistration() throws ManagerException {
        XMLTagScheme instancesScheme = createScheme(Tag.INSTANCES, null);
        XMLTagScheme defineScheme = createScheme(Tag.DEFINE, null);
        XMLTagScheme overrideScheme = createScheme(Tag.OVERRIDE, null);
        XMLTagScheme inheritScheme = createScheme(Tag.INHERIT, null);
        defaultValuesTag.register(instancesScheme);
        defaultValuesTag.register(defineScheme);
        defaultValuesTag.register(overrideScheme);
        defaultValuesTag.register(inheritScheme);

        // instances
        XMLTagScheme classScheme = createScheme(Tag.CLASS, null);
        XMLTagScheme instanceScheme = createScheme(Tag.INSTANCE, null);
        instancesScheme.register(classScheme);
        inheritScheme.register(instanceScheme);

        // define
        XMLTagScheme forClassScheme = createScheme(Tag.FOR_CLASS, null);
        defineScheme.register(forClassScheme);

        // override
        overrideScheme.register(forClassScheme);

        // inherit
        XMLTagScheme excludeInherit = createScheme(Tag.EXCLUDE_INHERIT, null);
        XMLTagScheme includeInherit = createScheme(Tag.INCLUDE_INHERIT, null);
        inheritScheme.register(excludeInherit);
        inheritScheme.register(includeInherit);

        // for-class
        XMLTagScheme valuesScheme = createScheme(Tag.VALUES, null);
        forClassScheme.register(valuesScheme);

        XMLTagScheme valueScheme = createScheme(Tag.VALUE, null);
        valuesScheme.register(valueScheme);
    }

    public void registrateAttributes() throws ManagerException {
        getByBackTraceTagByName(Tag.INSTANCES).getAttributeManager().register();
    }

    @Deprecated
    public XMLTagScheme getByBackTrace(List<Tag> backTrace) throws ManagerException {
        XMLTagScheme scheme = defaultValuesTag;
        for (Tag tag : backTrace) {
            if (!tag.name.equals(scheme.getName())) {
                scheme = scheme.get(tag.name);
            }
        }
        return scheme;
    }

    public XMLTagScheme getByBackTraceWithoutName(List<String> backTrace) throws ManagerException {
        XMLTagScheme scheme = defaultValuesTag;
        for (String tagName : backTrace) {
            scheme = scheme.get(tagName);
        }
        return scheme;
    }

    public XMLTagScheme getByBackTraceTag(Tag child) throws ManagerException {
        return getByBackTrace(child.getBackTrace());
    }

    public XMLTagScheme getByBackTraceTagByName(Tag child) throws ManagerException {
        return getByBackTraceWithoutName(child.getNameBackTraceWithoutRoot());
    }

    public static XMLTagScheme createScheme(Tag tag, XMLExecutable executable) {
        return new XMLTagSchemeImpl(tag.name, tag.level, tag.obligatory, executable);
    }
}
