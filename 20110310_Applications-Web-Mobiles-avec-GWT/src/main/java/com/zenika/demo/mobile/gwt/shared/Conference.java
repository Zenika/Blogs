package com.zenika.demo.mobile.gwt.shared;

import java.io.Serializable;

public class Conference implements Serializable {

	private static final long serialVersionUID = 7352691588505641377L;

	private String title;

	private String speakerName;

	private String description;

	private String date;

	private Boolean ended;

	public Conference() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpeakerName() {
		return speakerName;
	}

	public void setSpeakerName(String speakerName) {
		this.speakerName = speakerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setEnded(Boolean ended) {
		this.ended = ended;
	}

	public Boolean getEnded() {
		return ended;
	}

}
