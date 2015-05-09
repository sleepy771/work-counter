package com.gmail.sleepy771.workcount.diff.scheme;

import com.gmail.sleepy771.workcount.diff.class_inspection.PatchableInspector;

/**
 * Created by filip on 9.5.2015.
 */
public interface SchemeGenerator {
    Scheme createScheme(PatchableInspector inspector);
}
