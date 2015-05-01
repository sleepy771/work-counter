package com.gmail.sleepy771.workcount.model.reports;

import java.util.Date;
import java.util.List;

public interface Reopenable extends Completable {
	Date getLastEdited();
	
	List<Change> listChanges();
}
