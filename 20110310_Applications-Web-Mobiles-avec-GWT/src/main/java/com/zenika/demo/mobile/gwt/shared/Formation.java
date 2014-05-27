package com.zenika.demo.mobile.gwt.shared;

import java.io.Serializable;

public class Formation implements Serializable {

	private Expert formateur;

	private String title;

	private String subtitle;

	private String description;

	private String date;

	private String place;

	private int duration;

	public Formation() {
	}

	public Expert getFormateur() {
		return formateur;
	}

	public void setFormateur(Expert formateur) {
		this.formateur = formateur;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
