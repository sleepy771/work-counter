package com.gmail.sleepy771.workcount.model.reports;

import java.util.Date;

import org.joda.time.DateTime;

public class StepReport extends Report implements Completable {

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
