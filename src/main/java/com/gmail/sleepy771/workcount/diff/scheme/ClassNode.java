package com.gmail.sleepy771.workcount.diff.scheme;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 5/15/15.
 */
public interface ClassNode extends Classy {

    boolean isPatchable();

    boolean hasScheme();
}
