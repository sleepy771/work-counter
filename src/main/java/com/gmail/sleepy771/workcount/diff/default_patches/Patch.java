package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Identifiable;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;

import java.io.Serializable;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patch extends Identifiable {
    int getFromVersion();
    int getToVersion();
}
