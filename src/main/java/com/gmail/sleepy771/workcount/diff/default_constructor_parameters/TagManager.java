package com.gmail.sleepy771.workcount.diff.default_constructor_parameters;

import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;

/**
 * Created by filip on 5/28/15.
 */
public class TagManager extends DefaultAbstractManager<String, InstanceTag>
        implements Releasable {

    private ReleasableManagerImpl releasableManagerImpl;

    @Override
    protected String getKeyForElement(InstanceTag element) {
        return element.getReferenceName();
    }

    @Override
    public void free() {
        clear();

        releasableManagerImpl.unregister(this);
    }

    @Override
    public void setReleasableManagerImpl(ReleasableManagerImpl r) {
        releasableManagerImpl = r;
    }
}
