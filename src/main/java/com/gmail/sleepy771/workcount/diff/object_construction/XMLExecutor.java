package com.gmail.sleepy771.workcount.diff.object_construction;

import com.sun.deploy.xml.XMLAttribute;

import java.util.List;

/**
 * Created by filip on 5/25/15.
 */
public interface XMLExecutor<R> {
    R run(XMLTag tag);
}
