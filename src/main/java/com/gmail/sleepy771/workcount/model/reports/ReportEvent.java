package com.gmail.sleepy771.workcount.model.reports;

import java.util.Collections;
import java.util.List;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import org.joda.time.DateTime;

public class ReportEvent implements GetReportEvent, Patchable {
	private long id;
	private String title;
	private String text;
	private List<String> notes;
	private DateTime pubDate;
	
	/**
	 * @return the id
	 */
	@Override
	public final long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	@Override
	public final String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the text
	 */
	@Override
	public final String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public final void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the notes
	 */
	public final List<String> getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public final void setNotes(List<String> notes) {
		this.notes = notes;
	}
	/**
	 * @return the publication date
	 */
	@Override
	public final DateTime getPubDate() {
		return pubDate;
	}
	/**
	 * @param pubDate the publication date to set
	 */
	public final void setPubDate(DateTime pubDate) {
		this.pubDate = pubDate;
	}
	@Override
	public List<String> listNotes() {
		return Collections.unmodifiableList(this.notes);
	}
	@Override
	public void clearNotes() {
		notes.clear();
	}
}
