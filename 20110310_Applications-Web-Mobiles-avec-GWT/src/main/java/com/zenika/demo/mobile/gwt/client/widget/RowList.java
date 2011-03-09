package com.zenika.demo.mobile.gwt.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class RowList extends ComplexPanel {

	public RowList() {
		setElement(DOM.createElement("UL"));
	}

	@Override
	public void add(Widget w) {
		super.add(w, getElement());
	}

}
