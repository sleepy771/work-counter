package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by filip on 5/25/15.
 */
public class XMLHandler {

    public enum Tag {
        DEFAULT_VALUES("default-values", 0, true, true, null),
        INSTANCES("instances", 1, false, true, DEFAULT_VALUES),
        DEFINE("define", 1, true, true, DEFAULT_VALUES),
        OVERRIDE("override", 1, false, true, DEFAULT_VALUES),
        INHERIT("inherit", 1, false, true, DEFAULT_VALUES),
        CLASS("class", 2, false, false, INSTANCES),
        INSTANCE("instance", 2, false, false, INSTANCES),
        FOR_CLASS("for-class", 2, true, true, DEFINE),
        FOR_CLASS_OVERRIDE("for-class", 2, true, true, OVERRIDE),
        EXCLUDE_INHERIT("exclude-class", 2, false, true, INHERIT),
        INCLUDE_INHERIT("include-class", 2, false, true, INHERIT),
        VALUES("values", 3, true, true, FOR_CLASS),
        VALUES_OVERRIDE("values", 3, true, true, FOR_CLASS_OVERRIDE),
        VALUE("value", 4, true, true, VALUES),
        VALUE_OVERRIDE("value", 4, true, true, VALUES_OVERRIDE);

        private final String name;
        private final int level;
        private final boolean obligatory;
        private final boolean pairTag;
        private final Tag parent;

        Tag(String name, int level, boolean obligatory, boolean pairTag, Tag parent) {
            this.name = name;
            this.level = level;
            this.obligatory = obligatory;
            this.parent = parent;
            this.pairTag = pairTag;
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

        public boolean isRoot() {
            return parent == null;
        }

        public boolean isPairTag() {
            return pairTag;
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

    public enum Attribute {
        IMPORT("import", true, Tag.CLASS, "-"),
        AS("as", true, Tag.CLASS, "-"),
        CLASS("class", true, Tag.INSTANCE, "-"),
        OBTAIN_INSTANCE("obtain-instance", true, Tag.INSTANCE, "-"),
        AS_INSTANCE_NAME("as", true, Tag.INSTANCE, "-"),
        INHERIT_AMOUNT("amount", false, Tag.INHERIT, "some,none,all"),
        INHERIT_FROM("from", true, Tag.INHERIT, "-"),
        INCLUDE_CLASS("class", true, Tag.INCLUDE_INHERIT, "-"),
        EXCLUDE_CLASS("class", true, Tag.EXCLUDE_INHERIT, "-"),
        DEFINE_FOR_CLASS_VALUE("value", true, Tag.FOR_CLASS, "-"),
        DEFINE_VALUE_CLASS_INSTANCE("class-instance", false, Tag.VALUE, "-"),
        DEFINE_VALUE_INVOKE("invoke", false, Tag.VALUE, "-"),
        OVERRIDE_FOR_CLASS_VALUE("value", true, Tag.FOR_CLASS_OVERRIDE, "-"),
        OVERRIDE_VALUE_CLASS_INSTANCE("class-instance", false, Tag.VALUE_OVERRIDE, "-"),
        OVERRIDE_VALUE_INVOKE("invoke", false, Tag.VALUE_OVERRIDE, "-");

        private final String attributeName;
        private final boolean obligatory;
        private final Tag forTag;
        private final String possibleAttributes;

        Attribute(String attributeName, boolean obligatory, Tag forTag, String possibleAttributes) {
            this.attributeName = attributeName;
            this.forTag = forTag;
            this.obligatory = obligatory;
            this.possibleAttributes = possibleAttributes.trim();
        }

        public String getAttributeName() {
            return attributeName;
        }

        public boolean isObligatory() {
            return obligatory;
        }

        public boolean isRestricted() {
            return !(null == possibleAttributes || possibleAttributes.isEmpty() || "-".equals(possibleAttributes));
        }

        public String getPossibleAttributes() {
            return possibleAttributes;
        }

        public String[] getPossibleAttributesArray() {
            return !isRestricted() ? new String[0] : possibleAttributes.split("\\s*,\\s*");
        }

    }

    private final XMLTagScheme defaultValuesTag = createScheme(Tag.DEFAULT_VALUES, null);

    // TODO suppress this exception
    public XMLHandler() throws ManagerException {
        registerTags();
        registerAttributes();
    }

    private void registerTags() throws ManagerException {
        List<Tag> sortedList = new ArrayList<>(Arrays.asList(Tag.values()));
        sortedList.sort(new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o1.level - o2.level;
            }
        });
        for (Tag tag : sortedList) {
            if (null != tag.parent) {
                XMLTagScheme scheme = createScheme(tag, null);
                getSchemeByTagWithoutRoot(tag.parent).register(scheme);
            }
        }
    }

    private void registerAttributes() throws ManagerException {
        for (Attribute attribute : Attribute.values()) {
            XMLAttributeScheme scheme = createAttribute(attribute);
            getSchemeByTagWithoutRoot(attribute.forTag).getAttributeManager().register(scheme);
        }
    }

    public XMLTagScheme getByTagBackTraceWithRoot(List<Tag> backTrace) throws ManagerException {
        XMLTagScheme scheme = defaultValuesTag;
        for (Tag tag : backTrace) {
            if (!tag.name.equals(defaultValuesTag.getName())) {
                scheme = scheme.get(tag.name);
            }
        }
        return scheme;
    }

    public XMLTagScheme geSchemeByNameWithoutRoot(List<String> backTrace) throws ManagerException {
        XMLTagScheme scheme = defaultValuesTag;
        for (String tagName : backTrace) {
            scheme = scheme.get(tagName);
        }
        return scheme;
    }

    public XMLTagScheme getByTagWithRoot(Tag child) throws ManagerException {
        return getByTagBackTraceWithRoot(child.getBackTrace());
    }

    public XMLTagScheme getSchemeByTagWithoutRoot(Tag child) throws ManagerException {
        return geSchemeByNameWithoutRoot(child.getNameBackTraceWithoutRoot());
    }

    public static XMLTagScheme createScheme(Tag tag, XMLExecutable executable) {
        return new XMLTagSchemeImpl(tag.name, tag.level, tag.obligatory, tag.pairTag, executable);
    }

    public static XMLAttributeScheme createAttribute(Attribute attribute) {
        return new XMLAttributeSchemeImpl(attribute.attributeName, attribute.obligatory, attribute.possibleAttributes);
    }
}
