package com.gmail.sleepy771.workcount.model.reports;

import java.util.List;

import org.joda.time.DateTime;

public interface GetReportEvent {
	long getId();
	String getTitle();
	String getText();
	List<String> listNotes();
	void clearNotes();
	DateTime getPubDate();
}
