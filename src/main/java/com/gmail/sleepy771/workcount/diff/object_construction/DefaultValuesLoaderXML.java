package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.Manager;

import java.io.*;

/**
 * Created by filip on 5/25/15.
 */
public class DefaultValuesLoaderXML {

    private final File xmlFile;
    private final Manager<Class, DefaultValuesHolder> manager;

    private enum Elements {
        INSTANCES("instances", 1),
        CLASS("class", 2),
        INSTANCE("instance", 2),
        SIMPLE_INHERITED("inherit", 1),
        DEFINED("define", 1),
        OVERRIDE("override", 1),
        DEFAULT_VALUES("default-values", 0),
        FOR_CLASS("for-class", 2),
        PARAMETER_TYPES("parameter-types", 3),
        PARAMETER_TYPE("parameter-type", 4),
        VALUES("values", 3),
        VALUE("value", 4);

        private final String elementTag;

        Elements(String elementTag, int level) {
            this.elementTag = elementTag;
        }
    }

    private enum InstanceAttributes {
        CLASS("class", true),
        OBTAIN_INSTANCE("obtain-instance", true),
        AS("as", true);

        private final String attribute;
        private final boolean necessary;

        InstanceAttributes(String attribute, boolean necessary) {
            this.attribute = attribute;
            this.necessary = necessary;
        }
    }

    private enum ValueTypes {
        EMPTY("empty"),
        INHERITED("inherited"),
        REFERENCED("referenced");

        private final String type;

        ValueTypes(String type) {
            this.type = type;
        }
    }

    private enum ForClassTypes {
        FINAL("final"),
        NON_INHERITABLE("non-inheritable"),
        SUB_CLASSES("sub-classes");

        private final String attribute;

        ForClassTypes(String attribute) {
            this.attribute = attribute;
        }
    }

    public DefaultValuesLoaderXML(File xmlFile, Manager<Class, DefaultValuesHolder> manager) {
        this.xmlFile = xmlFile;
        this.manager = manager;
    }

    public void checkFile() {

    }

    public void validateValues() {

    }

    public void performLoad() {
        try (FileReader fileReader = new FileReader(xmlFile)) {
//TODO implement
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DefaultValuesHolder createValuesHolder() {
        return null;
    }
}
