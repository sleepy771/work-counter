package com.gmail.sleepy771.workcount.model.reports;

import java.util.List;

public class Report extends ReportEvent {
	
	private List<Report> relations;
	
	public boolean isRelated() {
		return relations != null && relations.isEmpty();
	}
	
	public List<Report> getRelations() {
		return relations;
	}
}
