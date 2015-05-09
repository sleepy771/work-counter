package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 8.5.2015.
 */
public interface PatchableInspector extends Classy {

    public void runInspection();

    public boolean isInspected();

    public boolean isValid();
}