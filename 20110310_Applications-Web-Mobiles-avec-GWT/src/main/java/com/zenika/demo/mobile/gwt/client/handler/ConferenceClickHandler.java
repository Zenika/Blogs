package com.zenika.demo.mobile.gwt.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.zenika.demo.mobile.gwt.shared.Conference;

public class ConferenceClickHandler implements ClickHandler {

	private final Conference conference;

	public ConferenceClickHandler(Conference conference) {
		this.conference = conference;
	}

	@Override
	public void onClick(ClickEvent event) {
		fillConferenceDetail();
	}

	private void fillConferenceDetail() {
		RootPanel formationDetailTitle = RootPanel.get("conferencedetailtitle");
		formationDetailTitle.clear();
		formationDetailTitle.add(new Label(conference.getTitle()));

		RootPanel formationDetailPanel = RootPanel.get("conferencedetailpanel");
		formationDetailPanel.clear();

		FlexTable table = new FlexTable();
		table.setCellSpacing(5);
		int row = -1;
		table.setText(++row, 0, "Date");
		table.setText(row, 1, conference.getDate());
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, "Speaker");
		table.setText(row, 1, conference.getSpeakerName());
		table.getCellFormatter().addStyleName(row, 0, "rowTitle");

		table.setText(++row, 0, conference.getDescription());
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
		formationDetailPanel.add(table);
	}
}
