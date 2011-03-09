package com.zenika.demo.mobile.gwt.shared;

import java.io.Serializable;

public class Expert implements Serializable {

	private static final long serialVersionUID = 5561106737605114702L;

	private String name;

	private String description;

	public Expert() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
