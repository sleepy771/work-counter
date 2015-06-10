package com.gmail.sleepy771.workcount.diff.sample;

import com.gmail.sleepy771.workcount.diff.annotations.CanPatch;
import com.gmail.sleepy771.workcount.diff.annotations.PatchProperty;
import com.gmail.sleepy771.workcount.diff.reflection.PropertyType;

/**
 * Created by filip on 30.4.2015.
 */
@CanPatch
public class SimpleDiffObject {

    private String title;
    private String text;

    public SimpleDiffObject() {
    }

    public SimpleDiffObject(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @PatchProperty
    public String getTitle() {
        return this.title;
    }

    @PatchProperty
    public String getText() {
        return this.text;
    }

    @PatchProperty(type = PropertyType.SETTER)
    public void setText(String text) {
        this.text = text;
    }
}
