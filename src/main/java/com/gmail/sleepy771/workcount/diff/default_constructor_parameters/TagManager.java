package com.gmail.sleepy771.workcount.diff.default_constructor_parameters;

import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.Releasable;

/**
 * Created by filip on 5/28/15.
 */
public class TagManager extends DefaultAbstractManager<String, InstanceTag>
        implements Releasable {

    private ReleasableManager releasableManager;

    @Override
    protected String getKeyForElement(InstanceTag element) {
        return element.getReferenceName();
    }

    @Override
    public void free() {
        clear();

        releasableManager.unregister(this);
    }

    @Override
    public void setReleasableManager(ReleasableManager r) {
        releasableManager = r;
    }
}
