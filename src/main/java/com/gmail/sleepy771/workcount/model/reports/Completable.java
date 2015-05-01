package com.gmail.sleepy771.workcount.model.reports;

import org.joda.time.DateTime;

public interface Completable {
	boolean isComplete();
	
	DateTime getEndDate();
}
