package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;

/**
 * Created by filip on 8.5.2015.
 */
public interface PatchableInspector extends Classy {

    void runInspection();

    boolean isInspected();

    boolean isValid();

    Scheme getScheme();
}
