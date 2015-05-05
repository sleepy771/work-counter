package com.gmail.sleepy771.workcount.diff.example;

import com.gmail.sleepy771.workcount.diff.annotations.AutoDifferentiable;
import com.gmail.sleepy771.workcount.diff.annotations.PatchableProperty;
import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableString;

/**
 * Created by filip on 30.4.2015.
 */
@AutoDifferentiable
public class SimpleDiffObject {

    private String title;
    private String text;

    public SimpleDiffObject(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @PatchableProperty(patchAs = PatchableString.class)
    public String getTitle() {
        return this.title;
    }

    @PatchableProperty(patchAs = PatchableString.class)
    public String getText() {
        return this.text;
    }
}
