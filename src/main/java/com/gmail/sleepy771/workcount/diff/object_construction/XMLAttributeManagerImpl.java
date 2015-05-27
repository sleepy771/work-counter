package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;

/**
 * Created by filip on 5/25/15.
 */
public class XMLAttributeManagerImpl extends DefaultAbstractManager<String, XMLAttributeScheme> implements XMLAttributeManager {
    @Override
    protected String getKeyForElement(XMLAttributeScheme element) {
        return element.getName();
    }
}
