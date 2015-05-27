package com.gmail.sleepy771.workcount.diff;

import com.gmail.sleepy771.workcount.ReleasableManager;

/**
 * Created by filip on 5/27/15.
 */
public interface Releasable {
    void free();

    void setReleasableManager(ReleasableManager r);
}
