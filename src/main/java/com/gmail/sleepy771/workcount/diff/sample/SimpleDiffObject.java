package com.gmail.sleepy771.workcount.diff.sample;

import com.gmail.sleepy771.workcount.diff.annotations.CanPatch;
import com.gmail.sleepy771.workcount.diff.annotations.PatchableProperty;

/**
 * Created by filip on 30.4.2015.
 */
@CanPatch
public class SimpleDiffObject {

    private String title;
    private String text;

    public SimpleDiffObject(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }
}
