package com.gmail.sleepy771.workcount.model.reports;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

public class ReportEventProxy implements GetReportEvent {

	private final ReportEvent event;
	
	public ReportEventProxy(ReportEvent evt) {
		event = evt;
	}
	
	@Override
	public final long getId() {
		return event.getId();
	}

	@Override
	public final String getTitle() {
		return event.getTitle();
	}

	@Override
	public final String getText() {
		return event.getText();
	}

	@Override
	public final DateTime getPubDate() {
		return event.getPubDate();
	}

	@Override
	public List<String> listNotes() {
		return Collections.unmodifiableList(event.getNotes());
	}

	@Override
	public void clearNotes() {
		event.clearNotes();
	}

}
