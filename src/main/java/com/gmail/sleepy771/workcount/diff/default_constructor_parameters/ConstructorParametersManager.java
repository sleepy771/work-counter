package com.gmail.sleepy771.workcount.diff.default_constructor_parameters;

import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.Releasable;

/**
 * Created by filip on 5/28/15.
 */
public class ConstructorParametersManager extends DefaultAbstractManager<Class, ConstructorParameters>
        implements Releasable {

    private ReleasableManager releasableManager;

    @Override
    protected Class getKeyForElement(ConstructorParameters element) {
        return element.getForClass();
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
