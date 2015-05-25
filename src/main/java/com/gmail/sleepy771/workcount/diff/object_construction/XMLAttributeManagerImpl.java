package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 5/25/15.
 */
public class XMLAttributeManagerImpl extends DefaultAbstractManager<String, XMLAttributeScheme> implements XMLAttributeManager {
    @Override
    protected String getKeyFromElement(XMLAttributeScheme element) throws ManagerException {
        return element.getName();
    }

    @Override
    protected void populate(Map<String, XMLAttributeScheme> map) {
    }
}
