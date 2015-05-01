package com.gmail.sleepy771.workcount.model.reports;

import java.util.List;

import org.joda.time.DateTime;

public class WorkReport extends Report implements Completable {
	private DateTime dueDate;
	private List<StepReport> steps;
	
	public boolean hasDueDate() {
		return dueDate != null;
	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DateTime getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public Object getObject() {
		return null;
	}
}
