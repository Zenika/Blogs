package com.zenika.demo.mobile.gwt.client.widget;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Row extends ComplexPanel implements HasText, HasHTML, HasClickHandlers, HasKeyDownHandlers,
		HasBlurHandlers {

	public Row() {
		this.setElement(DOM.createElement("li"));
	}

	@Override
	public void add(Widget w) {
		this.getElement().appendChild(w.getElement());
	}

	@Override
	public String getText() {
		return DOM.getInnerText(getElement());
	}

	@Override
	public void setText(String text) {
		DOM.setInnerText(getElement(), (text == null) ? "" : text);
	}

	@Override
	public String getHTML() {
		return DOM.getInnerHTML(getElement());
	}

	@Override
	public void setHTML(String html) {
		DOM.setInnerHTML(getElement(), (html == null) ? "" : html);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return addDomHandler(handler, KeyDownEvent.getType());
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addDomHandler(handler, BlurEvent.getType());
	}

	public void add(Element element) {
		getElement().appendChild(element);
	}

}