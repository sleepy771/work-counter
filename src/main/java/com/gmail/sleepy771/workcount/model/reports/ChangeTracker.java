package com.gmail.sleepy771.workcount.model.reports;

import java.util.List;

/**
 * Created by filip on 20.4.2015.
 */
public interface ChangeTracker {
    List<Change> listChanges();

    List<Change> listChanges(int fromVersion);
}
