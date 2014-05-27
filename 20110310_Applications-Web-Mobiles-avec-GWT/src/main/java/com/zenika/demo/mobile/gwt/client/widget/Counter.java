package com.zenika.demo.mobile.gwt.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

public class Counter extends ComplexPanel {

	public Counter(int count) {
		setElement(DOM.createElement("small"));
		getElement().setAttribute("class", "counter");
		getElement().setInnerText(String.valueOf(count));
	}

}
